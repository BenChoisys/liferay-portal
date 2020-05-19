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

package com.liferay.account.model.impl;

import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupModel;
import com.liferay.account.model.AccountGroupSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the AccountGroup service. Represents a row in the &quot;AccountGroup&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>AccountGroupModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link AccountGroupImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupImpl
 * @generated
 */
@JSON(strict = true)
public class AccountGroupModelImpl
	extends BaseModelImpl<AccountGroup> implements AccountGroupModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a account group model instance should use the <code>AccountGroup</code> interface instead.
	 */
	public static final String TABLE_NAME = "AccountGroup";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"externalReferenceCode", Types.VARCHAR},
		{"accountGroupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"description", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("accountGroupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table AccountGroup (mvccVersion LONG default 0 not null,externalReferenceCode VARCHAR(75) null,accountGroupId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name VARCHAR(75) null,description VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table AccountGroup";

	public static final String ORDER_BY_JPQL =
		" ORDER BY accountGroup.accountGroupId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY AccountGroup.accountGroupId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final long COMPANYID_COLUMN_BITMASK = 1L;

	public static final long EXTERNALREFERENCECODE_COLUMN_BITMASK = 2L;

	public static final long ACCOUNTGROUPID_COLUMN_BITMASK = 4L;

	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
		_entityCacheEnabled = entityCacheEnabled;
	}

	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
		_finderCacheEnabled = finderCacheEnabled;
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static AccountGroup toModel(AccountGroupSoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		AccountGroup model = new AccountGroupImpl();

		model.setMvccVersion(soapModel.getMvccVersion());
		model.setExternalReferenceCode(soapModel.getExternalReferenceCode());
		model.setAccountGroupId(soapModel.getAccountGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<AccountGroup> toModels(AccountGroupSoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<AccountGroup> models = new ArrayList<AccountGroup>(
			soapModels.length);

		for (AccountGroupSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public AccountGroupModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _accountGroupId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setAccountGroupId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _accountGroupId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return AccountGroup.class;
	}

	@Override
	public String getModelClassName() {
		return AccountGroup.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<AccountGroup, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		for (Map.Entry<String, Function<AccountGroup, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AccountGroup, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((AccountGroup)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<AccountGroup, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<AccountGroup, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(AccountGroup)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<AccountGroup, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<AccountGroup, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, AccountGroup>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			AccountGroup.class.getClassLoader(), AccountGroup.class,
			ModelWrapper.class);

		try {
			Constructor<AccountGroup> constructor =
				(Constructor<AccountGroup>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<AccountGroup, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<AccountGroup, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<AccountGroup, Object>> attributeGetterFunctions =
			new LinkedHashMap<String, Function<AccountGroup, Object>>();
		Map<String, BiConsumer<AccountGroup, ?>> attributeSetterBiConsumers =
			new LinkedHashMap<String, BiConsumer<AccountGroup, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", AccountGroup::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<AccountGroup, Long>)AccountGroup::setMvccVersion);
		attributeGetterFunctions.put(
			"externalReferenceCode", AccountGroup::getExternalReferenceCode);
		attributeSetterBiConsumers.put(
			"externalReferenceCode",
			(BiConsumer<AccountGroup, String>)
				AccountGroup::setExternalReferenceCode);
		attributeGetterFunctions.put(
			"accountGroupId", AccountGroup::getAccountGroupId);
		attributeSetterBiConsumers.put(
			"accountGroupId",
			(BiConsumer<AccountGroup, Long>)AccountGroup::setAccountGroupId);
		attributeGetterFunctions.put("companyId", AccountGroup::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<AccountGroup, Long>)AccountGroup::setCompanyId);
		attributeGetterFunctions.put("userId", AccountGroup::getUserId);
		attributeSetterBiConsumers.put(
			"userId", (BiConsumer<AccountGroup, Long>)AccountGroup::setUserId);
		attributeGetterFunctions.put("userName", AccountGroup::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<AccountGroup, String>)AccountGroup::setUserName);
		attributeGetterFunctions.put("createDate", AccountGroup::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<AccountGroup, Date>)AccountGroup::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", AccountGroup::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<AccountGroup, Date>)AccountGroup::setModifiedDate);
		attributeGetterFunctions.put("name", AccountGroup::getName);
		attributeSetterBiConsumers.put(
			"name", (BiConsumer<AccountGroup, String>)AccountGroup::setName);
		attributeGetterFunctions.put(
			"description", AccountGroup::getDescription);
		attributeSetterBiConsumers.put(
			"description",
			(BiConsumer<AccountGroup, String>)AccountGroup::setDescription);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@JSON
	@Override
	public String getExternalReferenceCode() {
		if (_externalReferenceCode == null) {
			return "";
		}
		else {
			return _externalReferenceCode;
		}
	}

	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_columnBitmask |= EXTERNALREFERENCECODE_COLUMN_BITMASK;

		if (_originalExternalReferenceCode == null) {
			_originalExternalReferenceCode = _externalReferenceCode;
		}

		_externalReferenceCode = externalReferenceCode;
	}

	public String getOriginalExternalReferenceCode() {
		return GetterUtil.getString(_originalExternalReferenceCode);
	}

	@JSON
	@Override
	public long getAccountGroupId() {
		return _accountGroupId;
	}

	@Override
	public void setAccountGroupId(long accountGroupId) {
		_accountGroupId = accountGroupId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_columnBitmask |= COMPANYID_COLUMN_BITMASK;

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = _companyId;
		}

		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public String getName() {
		if (_name == null) {
			return "";
		}
		else {
			return _name;
		}
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@JSON
	@Override
	public String getDescription() {
		if (_description == null) {
			return "";
		}
		else {
			return _description;
		}
	}

	@Override
	public void setDescription(String description) {
		_description = description;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), AccountGroup.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public AccountGroup toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, AccountGroup>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		AccountGroupImpl accountGroupImpl = new AccountGroupImpl();

		accountGroupImpl.setMvccVersion(getMvccVersion());
		accountGroupImpl.setExternalReferenceCode(getExternalReferenceCode());
		accountGroupImpl.setAccountGroupId(getAccountGroupId());
		accountGroupImpl.setCompanyId(getCompanyId());
		accountGroupImpl.setUserId(getUserId());
		accountGroupImpl.setUserName(getUserName());
		accountGroupImpl.setCreateDate(getCreateDate());
		accountGroupImpl.setModifiedDate(getModifiedDate());
		accountGroupImpl.setName(getName());
		accountGroupImpl.setDescription(getDescription());

		accountGroupImpl.resetOriginalValues();

		return accountGroupImpl;
	}

	@Override
	public int compareTo(AccountGroup accountGroup) {
		long primaryKey = accountGroup.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AccountGroup)) {
			return false;
		}

		AccountGroup accountGroup = (AccountGroup)obj;

		long primaryKey = accountGroup.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public void resetOriginalValues() {
		AccountGroupModelImpl accountGroupModelImpl = this;

		accountGroupModelImpl._originalExternalReferenceCode =
			accountGroupModelImpl._externalReferenceCode;

		accountGroupModelImpl._originalCompanyId =
			accountGroupModelImpl._companyId;

		accountGroupModelImpl._setOriginalCompanyId = false;

		accountGroupModelImpl._setModifiedDate = false;

		accountGroupModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<AccountGroup> toCacheModel() {
		AccountGroupCacheModel accountGroupCacheModel =
			new AccountGroupCacheModel();

		accountGroupCacheModel.mvccVersion = getMvccVersion();

		accountGroupCacheModel.externalReferenceCode =
			getExternalReferenceCode();

		String externalReferenceCode =
			accountGroupCacheModel.externalReferenceCode;

		if ((externalReferenceCode != null) &&
			(externalReferenceCode.length() == 0)) {

			accountGroupCacheModel.externalReferenceCode = null;
		}

		accountGroupCacheModel.accountGroupId = getAccountGroupId();

		accountGroupCacheModel.companyId = getCompanyId();

		accountGroupCacheModel.userId = getUserId();

		accountGroupCacheModel.userName = getUserName();

		String userName = accountGroupCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			accountGroupCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			accountGroupCacheModel.createDate = createDate.getTime();
		}
		else {
			accountGroupCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			accountGroupCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			accountGroupCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		accountGroupCacheModel.name = getName();

		String name = accountGroupCacheModel.name;

		if ((name != null) && (name.length() == 0)) {
			accountGroupCacheModel.name = null;
		}

		accountGroupCacheModel.description = getDescription();

		String description = accountGroupCacheModel.description;

		if ((description != null) && (description.length() == 0)) {
			accountGroupCacheModel.description = null;
		}

		return accountGroupCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<AccountGroup, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<AccountGroup, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AccountGroup, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(attributeGetterFunction.apply((AccountGroup)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<AccountGroup, Object>> attributeGetterFunctions =
			getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<AccountGroup, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<AccountGroup, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(attributeGetterFunction.apply((AccountGroup)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, AccountGroup>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private static boolean _entityCacheEnabled;
	private static boolean _finderCacheEnabled;

	private long _mvccVersion;
	private String _externalReferenceCode;
	private String _originalExternalReferenceCode;
	private long _accountGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _name;
	private String _description;
	private long _columnBitmask;
	private AccountGroup _escapedModel;

}