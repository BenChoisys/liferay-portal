/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import PropTypes from 'prop-types';
import {useEffect} from 'react';

import {config} from '../../config/index';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector, useSelectorCallback} from '../../store/index';
import updateEditableValues from '../../thunks/updateEditableValues';
import {useToControlsId} from '../CollectionItemContext';
import {
	useEditableProcessorClickPosition,
	useEditableProcessorUniqueId,
	useIsProcessorEnabled,
	useSetEditableProcessorUniqueId,
} from './EditableProcessorContext';

export default function FragmentContentProcessor({
	fragmentEntryLinkId,
	itemId,
}) {
	const dispatch = useDispatch();
	const editableProcessorClickPosition = useEditableProcessorClickPosition();
	const editableProcessorUniqueId = useEditableProcessorUniqueId();
	const toControlsId = useToControlsId();
	const setEditableProcessorUniqueId = useSetEditableProcessorUniqueId();
	const isProcessorEnabled = useIsProcessorEnabled();
	const languageId = useSelector(
		(state) => state.languageId || config.defaultLanguageId
	);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	const editable = useSelectorCallback(
		(state) =>
			Object.values(state.editables?.[toControlsId(itemId)] || {}).find(
				(editable) =>
					editableProcessorUniqueId &&
					isProcessorEnabled(editable.itemId)
			) || {
				editableId: null,
				editableValueNamespace: null,
				element: null,
				processor: null,
			},
		[editableProcessorUniqueId, isProcessorEnabled, itemId, toControlsId]
	);

	const editableValues = useSelectorCallback(
		(state) =>
			state.fragmentEntryLinks[fragmentEntryLinkId] &&
			state.fragmentEntryLinks[fragmentEntryLinkId].editableValues,
		[fragmentEntryLinkId]
	);

	useEffect(() => {
		if (!editable.element || !editableValues) {
			return;
		}

		const editableValue =
			editableValues[editable.editableValueNamespace][
				editable.editableId
			];

		editable.processor.createEditor(
			editable.element,
			(value) => {
				const previousValue = editableValue[languageId];

				if (
					!previousValue &&
					value === editableValue.defaultValue.trim()
				) {
					return;
				}

				if (previousValue === value) {
					return;
				}

				let nextEditableValue = {
					...editableValue,
				};

				nextEditableValue = {
					...nextEditableValue,
					[languageId]: value,
				};

				dispatch(
					updateEditableValues({
						editableValues: {
							...editableValues,
							[editable.editableValueNamespace]: {
								...editableValues[
									editable.editableValueNamespace
								],
								[editable.editableId]: nextEditableValue,
							},
						},
						fragmentEntryLinkId,
						segmentsExperienceId,
					})
				);
			},
			() => {
				setEditableProcessorUniqueId(null);
			},
			editableProcessorClickPosition
		);

		return () => {
			if (!editableProcessorUniqueId) {
				editable.processor.destroyEditor(
					editable.element,
					editableValue.config
				);
			}
		};
	}, [
		dispatch,
		editable.editableId,
		editable.editableValueNamespace,
		editable.element,
		editable.processor,
		editableProcessorClickPosition,
		editableProcessorUniqueId,
		editableValues,
		fragmentEntryLinkId,
		languageId,
		segmentsExperienceId,
		setEditableProcessorUniqueId,
	]);

	return null;
}

FragmentContentProcessor.propTypes = {
	fragmentEntryLinkId: PropTypes.string.isRequired,
};
