/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.persistence.exception.DuplicateSamlIdpSpConnectionSamlSpEntityIdException;
import com.liferay.saml.persistence.exception.SamlIdpSpConnectionMetadataUrlException;
import com.liferay.saml.persistence.exception.SamlIdpSpConnectionMetadataXmlException;
import com.liferay.saml.persistence.exception.SamlIdpSpConnectionNameException;
import com.liferay.saml.persistence.exception.SamlIdpSpConnectionSamlSpEntityIdException;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.service.base.SamlIdpSpConnectionLocalServiceBaseImpl;
import com.liferay.saml.util.MetadataUtil;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = "model.class.name=com.liferay.saml.persistence.model.SamlIdpSpConnection",
	service = AopService.class
)
public class SamlIdpSpConnectionLocalServiceImpl
	extends SamlIdpSpConnectionLocalServiceBaseImpl {

	@Override
	public SamlIdpSpConnection addSamlIdpSpConnection(
			String samlSpEntityId, int assertionLifetime, String attributeNames,
			boolean attributesEnabled, boolean attributesNamespaceEnabled,
			boolean enabled, boolean encryptionForced, String metadataUrl,
			InputStream metadataXmlInputStream, String name,
			String nameIdAttribute, String nameIdFormat,
			ServiceContext serviceContext)
		throws PortalException {

		Date now = new Date();

		if (Validator.isNull(samlSpEntityId)) {
			throw new SamlIdpSpConnectionSamlSpEntityIdException(
				"SAML SP entity ID is null");
		}

		if (Validator.isNull(name)) {
			throw new SamlIdpSpConnectionNameException("Name is null");
		}

		SamlIdpSpConnection existingSamlIdpSpConnection =
			samlIdpSpConnectionPersistence.fetchByC_SSEI(
				serviceContext.getCompanyId(), samlSpEntityId);

		if (existingSamlIdpSpConnection != null) {
			throw new DuplicateSamlIdpSpConnectionSamlSpEntityIdException();
		}

		long samlIdpSpConnectionId = counterLocalService.increment(
			SamlIdpSpConnection.class.getName());

		SamlIdpSpConnection samlIdpSpConnection =
			samlIdpSpConnectionPersistence.create(samlIdpSpConnectionId);

		samlIdpSpConnection.setCompanyId(serviceContext.getCompanyId());
		samlIdpSpConnection.setCreateDate(now);
		samlIdpSpConnection.setModifiedDate(now);
		samlIdpSpConnection.setSamlSpEntityId(samlSpEntityId);
		samlIdpSpConnection.setAssertionLifetime(assertionLifetime);
		samlIdpSpConnection.setAttributeNames(attributeNames);
		samlIdpSpConnection.setAttributesEnabled(attributesEnabled);
		samlIdpSpConnection.setAttributesNamespaceEnabled(
			attributesNamespaceEnabled);
		samlIdpSpConnection.setEnabled(enabled);
		samlIdpSpConnection.setEncryptionForced(encryptionForced);
		samlIdpSpConnection.setExpandoBridgeAttributes(serviceContext);
		samlIdpSpConnection.setMetadataUpdatedDate(now);

		if ((metadataXmlInputStream == null) &&
			Validator.isNotNull(metadataUrl)) {

			samlIdpSpConnection.setMetadataUrl(metadataUrl);

			try {
				metadataXmlInputStream = _metadataUtil.getMetadata(metadataUrl);
			}
			catch (Exception exception) {
				throw new SamlIdpSpConnectionMetadataUrlException(
					StringBundler.concat(
						"Unable to get metadata from ", metadataUrl, ": ",
						exception.getMessage()),
					exception);
			}
		}

		if (metadataXmlInputStream == null) {
			throw new SamlIdpSpConnectionMetadataUrlException(
				"Unable to get metadata from " + metadataUrl);
		}

		samlIdpSpConnection.setMetadataXml(
			getMetadataXml(metadataXmlInputStream, samlSpEntityId));
		samlIdpSpConnection.setName(name);
		samlIdpSpConnection.setNameIdAttribute(nameIdAttribute);
		samlIdpSpConnection.setNameIdFormat(nameIdFormat);

		samlIdpSpConnectionPersistence.update(samlIdpSpConnection);

		return samlIdpSpConnection;
	}

	@Override
	public SamlIdpSpConnection getSamlIdpSpConnection(
			long companyId, String samlSpEntityId)
		throws PortalException {

		return samlIdpSpConnectionPersistence.findByC_SSEI(
			companyId, samlSpEntityId);
	}

	@Override
	public List<SamlIdpSpConnection> getSamlIdpSpConnections(long companyId) {
		return samlIdpSpConnectionPersistence.findByCompanyId(companyId);
	}

	@Override
	public List<SamlIdpSpConnection> getSamlIdpSpConnections(
		long companyId, int start, int end) {

		return samlIdpSpConnectionPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<SamlIdpSpConnection> getSamlIdpSpConnections(
		long companyId, int start, int end,
		OrderByComparator orderByComparator) {

		return samlIdpSpConnectionPersistence.findByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getSamlIdpSpConnectionsCount(long companyId) {
		return samlIdpSpConnectionPersistence.countByCompanyId(companyId);
	}

	@Override
	public void updateMetadata(long samlIdpSpConnectionId)
		throws PortalException {

		SamlIdpSpConnection samlIdpSpConnection =
			samlIdpSpConnectionPersistence.findByPrimaryKey(
				samlIdpSpConnectionId);

		String metadataUrl = samlIdpSpConnection.getMetadataUrl();

		if (Validator.isNull(metadataUrl)) {
			return;
		}

		InputStream metadataXmlInputStream = null;

		try {
			metadataXmlInputStream = _metadataUtil.getMetadata(metadataUrl);
		}
		catch (Exception exception) {
			throw new SamlIdpSpConnectionMetadataUrlException(
				StringBundler.concat(
					"Unable to get metadata from ", metadataUrl, ": ",
					exception.getMessage()),
				exception);
		}

		String metadataXml = StringPool.BLANK;

		try {
			metadataXml = _metadataUtil.parseMetadataXml(
				metadataXmlInputStream,
				samlIdpSpConnection.getSamlSpEntityId());
		}
		catch (Exception exception) {
			throw new SamlIdpSpConnectionMetadataXmlException(
				"Unable to parse SAML metadata from " + metadataUrl, exception);
		}

		samlIdpSpConnection.setMetadataUpdatedDate(new Date());
		samlIdpSpConnection.setMetadataXml(metadataXml);

		samlIdpSpConnectionPersistence.update(samlIdpSpConnection);
	}

	@Override
	public SamlIdpSpConnection updateSamlIdpSpConnection(
			long samlIdpSpConnectionId, String samlSpEntityId,
			int assertionLifetime, String attributeNames,
			boolean attributesEnabled, boolean attributesNamespaceEnabled,
			boolean enabled, boolean encryptionForced, String metadataUrl,
			InputStream metadataXmlInputStream, String name,
			String nameIdAttribute, String nameIdFormat,
			ServiceContext serviceContext)
		throws PortalException {

		Date now = new Date();

		if (Validator.isNull(samlSpEntityId)) {
			throw new SamlIdpSpConnectionSamlSpEntityIdException(
				"SAML SP entity ID is null");
		}

		SamlIdpSpConnection samlIdpSpConnection =
			samlIdpSpConnectionPersistence.fetchByPrimaryKey(
				samlIdpSpConnectionId);

		if (!samlSpEntityId.equals(samlIdpSpConnection.getSamlSpEntityId())) {
			SamlIdpSpConnection existingSamlIdpSpConnection =
				samlIdpSpConnectionPersistence.fetchByC_SSEI(
					serviceContext.getCompanyId(), samlSpEntityId);

			if (existingSamlIdpSpConnection != null) {
				throw new DuplicateSamlIdpSpConnectionSamlSpEntityIdException(
					"Duplicate SAML IDP SP connection for " + samlSpEntityId);
			}
		}

		samlIdpSpConnection.setModifiedDate(now);
		samlIdpSpConnection.setSamlSpEntityId(samlSpEntityId);
		samlIdpSpConnection.setAssertionLifetime(assertionLifetime);
		samlIdpSpConnection.setAttributeNames(attributeNames);
		samlIdpSpConnection.setAttributesEnabled(attributesEnabled);
		samlIdpSpConnection.setAttributesNamespaceEnabled(
			attributesNamespaceEnabled);
		samlIdpSpConnection.setEnabled(enabled);
		samlIdpSpConnection.setEncryptionForced(encryptionForced);
		samlIdpSpConnection.setExpandoBridgeAttributes(serviceContext);

		if ((metadataXmlInputStream == null) &&
			Validator.isNotNull(metadataUrl)) {

			samlIdpSpConnection.setMetadataUrl(metadataUrl);

			try {
				metadataXmlInputStream = _metadataUtil.getMetadata(metadataUrl);
			}
			catch (Exception exception) {
				throw new SamlIdpSpConnectionMetadataUrlException(
					StringBundler.concat(
						"Unable to get metadata from ", metadataUrl, ": ",
						exception.getMessage()),
					exception);
			}
		}

		String metadataXml = StringPool.BLANK;

		if (metadataXmlInputStream != null) {
			metadataXml = getMetadataXml(
				metadataXmlInputStream, samlSpEntityId);
		}

		if (Validator.isNotNull(metadataXml)) {
			samlIdpSpConnection.setMetadataUpdatedDate(now);
			samlIdpSpConnection.setMetadataXml(metadataXml);
		}

		samlIdpSpConnection.setName(name);
		samlIdpSpConnection.setNameIdAttribute(nameIdAttribute);
		samlIdpSpConnection.setNameIdFormat(nameIdFormat);

		samlIdpSpConnectionPersistence.update(samlIdpSpConnection);

		return samlIdpSpConnection;
	}

	protected String getMetadataXml(
			InputStream metadataXmlInputStream, String samlSpEntityId)
		throws PortalException {

		String metadataXml = StringPool.BLANK;

		try {
			metadataXml = _metadataUtil.parseMetadataXml(
				metadataXmlInputStream, samlSpEntityId);
		}
		catch (Exception exception) {
			throw new SamlIdpSpConnectionMetadataXmlException(exception);
		}

		if (Validator.isNull(metadataXml)) {
			throw new SamlIdpSpConnectionSamlSpEntityIdException(
				"Metadata XML is null for " + samlSpEntityId);
		}

		return metadataXml;
	}

	@Reference
	private MetadataUtil _metadataUtil;

}