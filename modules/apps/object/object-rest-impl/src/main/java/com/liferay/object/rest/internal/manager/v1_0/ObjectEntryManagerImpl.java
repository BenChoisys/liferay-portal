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

package com.liferay.object.rest.internal.manager.v1_0;

import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.internal.dto.v1_0.converter.ObjectEntryDTOConverter;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.ActionUtil;
import com.liferay.portal.vulcan.util.GroupUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.io.Serializable;

import java.text.ParseException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(immediate = true, service = ObjectEntryManager.class)
public class ObjectEntryManagerImpl implements ObjectEntryManager {

	@Override
	public ObjectEntry addObjectEntry(
			DTOConverterContext dtoConverterContext, long userId,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		return _toObjectEntry(
			dtoConverterContext,
			_objectEntryLocalService.addObjectEntry(
				userId, _getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				new ServiceContext()));
	}

	@Override
	public ObjectEntry addOrUpdateObjectEntry(
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, long userId,
			ObjectDefinition objectDefinition, ObjectEntry objectEntry,
			String scopeKey)
		throws Exception {

		return _toObjectEntry(
			dtoConverterContext,
			_objectEntryLocalService.addOrUpdateObjectEntry(
				externalReferenceCode, userId,
				_getGroupId(objectDefinition, scopeKey),
				objectDefinition.getObjectDefinitionId(),
				_toObjectValues(
					objectDefinition.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				new ServiceContext()));
	}

	@Override
	public void deleteObjectEntry(long objectEntryId) throws Exception {
		_objectEntryLocalService.deleteObjectEntry(objectEntryId);
	}

	@Override
	public void deleteObjectEntry(
			String externalReferenceCode, long companyId,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		_objectEntryLocalService.deleteObjectEntry(
			externalReferenceCode, companyId,
			_getGroupId(objectDefinition, scopeKey));
	}

	@Override
	public Page<ObjectEntry> getObjectEntries(
			long companyId, ObjectDefinition objectDefinition, String scopeKey,
			Aggregation aggregation, DTOConverterContext dtoConverterContext,
			Filter filter, Pagination pagination, String search, Sort[] sorts)
		throws Exception {

		long groupId = _getGroupId(objectDefinition, scopeKey);

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		UriInfo uriInfo = uriInfoOptional.orElse(null);

		return SearchUtil.search(
			HashMapBuilder.put(
				"create",
				ActionUtil.addAction(
					"ADD_OBJECT_ENTRY", ObjectEntryResourceImpl.class, 0L,
					"postObjectEntry", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, uriInfo)
			).put(
				"get",
				ActionUtil.addAction(
					ActionKeys.VIEW, ObjectEntryResourceImpl.class, 0L,
					"getObjectEntriesPage", null, objectDefinition.getUserId(),
					_getObjectEntriesPermissionName(
						objectDefinition.getObjectDefinitionId()),
					groupId, uriInfo)
			).build(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"objectDefinitionId",
						String.valueOf(
							objectDefinition.getObjectDefinitionId())),
					BooleanClauseOccur.MUST);
			},
			filter, objectDefinition.getClassName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setAttribute(
					Field.STATUS, WorkflowConstants.STATUS_ANY);
				searchContext.setAttribute(
					"objectDefinitionId",
					objectDefinition.getObjectDefinitionId());
				searchContext.setCompanyId(companyId);
				searchContext.setGroupIds(new long[] {groupId});
			},
			sorts,
			document -> getObjectEntry(
				dtoConverterContext,
				GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext, long objectEntryId)
		throws Exception {

		return _toObjectEntry(
			dtoConverterContext,
			_objectEntryLocalService.getObjectEntry(objectEntryId));
	}

	@Override
	public ObjectEntry getObjectEntry(
			DTOConverterContext dtoConverterContext,
			String externalReferenceCode, long companyId,
			ObjectDefinition objectDefinition, String scopeKey)
		throws Exception {

		return _toObjectEntry(
			dtoConverterContext,
			_objectEntryLocalService.getObjectEntry(
				externalReferenceCode, companyId,
				_getGroupId(objectDefinition, scopeKey)));
	}

	@Override
	public ObjectEntry updateObjectEntry(
			DTOConverterContext dtoConverterContext, long userId,
			long objectEntryId, ObjectEntry objectEntry)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryLocalService.getObjectEntry(objectEntryId);

		return _toObjectEntry(
			dtoConverterContext,
			_objectEntryLocalService.updateObjectEntry(
				userId, objectEntryId,
				_toObjectValues(
					serviceBuilderObjectEntry.getObjectDefinitionId(),
					objectEntry.getProperties(),
					dtoConverterContext.getLocale()),
				new ServiceContext()));
	}

	private long _getGroupId(
		ObjectDefinition objectDefinition, String scopeKey) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (objectScopeProvider.isGroupAware()) {
			if (Objects.equals("site", objectDefinition.getScope())) {
				return GroupUtil.getGroupId(
					objectDefinition.getCompanyId(), scopeKey,
					_groupLocalService);
			}

			return GroupUtil.getDepotGroupId(
				scopeKey, objectDefinition.getCompanyId(),
				_depotEntryLocalService, _groupLocalService);
		}

		return 0;
	}

	private String _getObjectEntriesPermissionName(long objectDefinitionId) {
		return ObjectConstants.RESOURCE_NAME + "#" + objectDefinitionId;
	}

	private String _getObjectEntryPermissionName(long objectDefinitionId) {
		return ObjectDefinition.class.getName() + "#" + objectDefinitionId;
	}

	private Date _toDate(Locale locale, String valueString) {
		if (Validator.isNull(valueString)) {
			return null;
		}

		try {
			return DateUtil.parseDate(
				"yyyy-MM-dd'T'HH:mm:ss'Z'", valueString, locale);
		}
		catch (ParseException parseException1) {
			try {
				return DateUtil.parseDate("yyyy-MM-dd", valueString, locale);
			}
			catch (ParseException parseException2) {
				throw new BadRequestException(
					"Unable to parse date that does not conform to ISO-8601",
					parseException2);
			}
		}
	}

	private ObjectEntry _toObjectEntry(
		DTOConverterContext dtoConverterContext,
		com.liferay.object.model.ObjectEntry objectEntry) {

		Optional<UriInfo> uriInfoOptional =
			dtoConverterContext.getUriInfoOptional();

		UriInfo uriInfo = uriInfoOptional.orElse(null);

		return _objectEntryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				dtoConverterContext.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"delete",
					ActionUtil.addAction(
						ActionKeys.DELETE, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "deleteObjectEntry",
						null, objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(), uriInfo)
				).put(
					"get",
					ActionUtil.addAction(
						ActionKeys.VIEW, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "getObjectEntry", null,
						objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(), uriInfo)
				).put(
					"update",
					ActionUtil.addAction(
						ActionKeys.UPDATE, ObjectEntryResourceImpl.class,
						objectEntry.getObjectEntryId(), "putObjectEntry", null,
						objectEntry.getUserId(),
						_getObjectEntryPermissionName(
							objectEntry.getObjectDefinitionId()),
						objectEntry.getGroupId(), uriInfo)
				).build(),
				dtoConverterContext.getDTOConverterRegistry(),
				dtoConverterContext.getHttpServletRequest(),
				objectEntry.getObjectEntryId(), dtoConverterContext.getLocale(),
				uriInfo, dtoConverterContext.getUser()),
			objectEntry);
	}

	private Map<String, Serializable> _toObjectValues(
		long objectDefinitionId, Map<String, Object> properties,
		Locale locale) {

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(objectDefinitionId);

		Map<String, Serializable> values = new HashMap<>();

		for (ObjectField objectField : objectFields) {
			String name = objectField.getName();

			Object object = properties.get(name);

			if (object == null) {
				continue;
			}

			if (Objects.equals(objectField.getType(), "Date")) {
				values.put(name, _toDate(locale, String.valueOf(object)));
			}
			else {
				values.put(name, (Serializable)object);
			}
		}

		return values;
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectEntryDTOConverter _objectEntryDTOConverter;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}