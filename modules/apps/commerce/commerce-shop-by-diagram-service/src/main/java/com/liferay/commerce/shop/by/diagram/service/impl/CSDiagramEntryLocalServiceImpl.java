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

package com.liferay.commerce.shop.by.diagram.service.impl;

import com.liferay.commerce.shop.by.diagram.exception.DuplicateCSDiagramEntryException;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.base.CSDiagramEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry",
	service = AopService.class
)
public class CSDiagramEntryLocalServiceImpl
	extends CSDiagramEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CSDiagramEntry addCSDiagramEntry(
			long userId, long cpDefinitionId, long cpInstanceId,
			long cProductId, boolean diagram, int quantity, String sequence,
			String sku, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(null, cpDefinitionId, sequence);

		long csDiagramEntryId = counterLocalService.increment();

		CSDiagramEntry csDiagramEntry = csDiagramEntryPersistence.create(
			csDiagramEntryId);

		csDiagramEntry.setCompanyId(user.getCompanyId());
		csDiagramEntry.setUserId(user.getUserId());
		csDiagramEntry.setUserName(user.getFullName());
		csDiagramEntry.setCPDefinitionId(cpDefinitionId);
		csDiagramEntry.setCPInstanceId(cpInstanceId);
		csDiagramEntry.setCProductId(cProductId);
		csDiagramEntry.setDiagram(diagram);
		csDiagramEntry.setQuantity(quantity);
		csDiagramEntry.setSequence(sequence);
		csDiagramEntry.setSku(sku);
		csDiagramEntry.setExpandoBridgeAttributes(serviceContext);

		return csDiagramEntryPersistence.update(csDiagramEntry);
	}

	@Override
	public void deleteCSDiagramEntries(long cpDefinitionId) {
		List<CSDiagramEntry> csDiagramEntries =
			csDiagramEntryPersistence.findByCPDefinitionId(cpDefinitionId);

		for (CSDiagramEntry csDiagramEntry : csDiagramEntries) {
			csDiagramEntryLocalService.deleteCSDiagramEntry(csDiagramEntry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CSDiagramEntry deleteCSDiagramEntry(CSDiagramEntry csDiagramEntry) {
		csDiagramEntry = csDiagramEntryPersistence.remove(csDiagramEntry);

		expandoRowLocalService.deleteRows(csDiagramEntry.getCSDiagramEntryId());

		return csDiagramEntry;
	}

	@Override
	public CSDiagramEntry fetchCSDiagramEntry(
		long cpDefinitionId, String sequence) {

		return csDiagramEntryPersistence.fetchByCPDI_S(
			cpDefinitionId, sequence);
	}

	@Override
	public List<CSDiagramEntry> getCSDiagramEntries(
		long cpDefinitionId, int start, int end) {

		return csDiagramEntryPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end);
	}

	@Override
	public int getCSDiagramEntriesCount(long cpDefinitionId) {
		return csDiagramEntryPersistence.countByCPDefinitionId(cpDefinitionId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CSDiagramEntry getCSDiagramEntry(
			long cpDefinitionId, String sequence)
		throws PortalException {

		return csDiagramEntryPersistence.findByCPDI_S(cpDefinitionId, sequence);
	}

	@Override
	public CSDiagramEntry updateCSDiagramEntry(
			long csDiagramEntryId, long cpInstanceId, long cProductId,
			boolean diagram, int quantity, String sequence, String sku,
			ServiceContext serviceContext)
		throws PortalException {

		CSDiagramEntry csDiagramEntry =
			csDiagramEntryLocalService.getCSDiagramEntry(csDiagramEntryId);

		validate(csDiagramEntry, csDiagramEntry.getCPDefinitionId(), sequence);

		csDiagramEntry.setCPInstanceId(cpInstanceId);
		csDiagramEntry.setCProductId(cProductId);
		csDiagramEntry.setDiagram(diagram);
		csDiagramEntry.setQuantity(quantity);
		csDiagramEntry.setSequence(sequence);
		csDiagramEntry.setSku(sku);
		csDiagramEntry.setExpandoBridgeAttributes(serviceContext);

		return csDiagramEntryPersistence.update(csDiagramEntry);
	}

	protected void validate(
			CSDiagramEntry oldCSDiagramEntry, long cpDefinitionId,
			String sequence)
		throws PortalException {

		CSDiagramEntry csDiagramEntry = csDiagramEntryPersistence.fetchByCPDI_S(
			cpDefinitionId, sequence);

		if (!Objects.equals(oldCSDiagramEntry, csDiagramEntry)) {
			throw new DuplicateCSDiagramEntryException();
		}
	}

}