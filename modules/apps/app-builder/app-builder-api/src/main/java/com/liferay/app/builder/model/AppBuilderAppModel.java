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

package com.liferay.app.builder.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.model.LocalizedModel;
import com.liferay.portal.kernel.model.ShardedModel;
import com.liferay.portal.kernel.model.StagedAuditedModel;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model interface for the AppBuilderApp service. Represents a row in the &quot;AppBuilderApp&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation <code>com.liferay.app.builder.model.impl.AppBuilderAppModelImpl</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in <code>com.liferay.app.builder.model.impl.AppBuilderAppImpl</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderApp
 * @generated
 */
@ProviderType
public interface AppBuilderAppModel
	extends BaseModel<AppBuilderApp>, GroupedModel, LocalizedModel,
			ShardedModel, StagedAuditedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a app builder app model instance should use the {@link AppBuilderApp} interface instead.
	 */

	/**
	 * Returns the primary key of this app builder app.
	 *
	 * @return the primary key of this app builder app
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this app builder app.
	 *
	 * @param primaryKey the primary key of this app builder app
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the uuid of this app builder app.
	 *
	 * @return the uuid of this app builder app
	 */
	@AutoEscape
	@Override
	public String getUuid();

	/**
	 * Sets the uuid of this app builder app.
	 *
	 * @param uuid the uuid of this app builder app
	 */
	@Override
	public void setUuid(String uuid);

	/**
	 * Returns the app builder app ID of this app builder app.
	 *
	 * @return the app builder app ID of this app builder app
	 */
	public long getAppBuilderAppId();

	/**
	 * Sets the app builder app ID of this app builder app.
	 *
	 * @param appBuilderAppId the app builder app ID of this app builder app
	 */
	public void setAppBuilderAppId(long appBuilderAppId);

	/**
	 * Returns the group ID of this app builder app.
	 *
	 * @return the group ID of this app builder app
	 */
	@Override
	public long getGroupId();

	/**
	 * Sets the group ID of this app builder app.
	 *
	 * @param groupId the group ID of this app builder app
	 */
	@Override
	public void setGroupId(long groupId);

	/**
	 * Returns the company ID of this app builder app.
	 *
	 * @return the company ID of this app builder app
	 */
	@Override
	public long getCompanyId();

	/**
	 * Sets the company ID of this app builder app.
	 *
	 * @param companyId the company ID of this app builder app
	 */
	@Override
	public void setCompanyId(long companyId);

	/**
	 * Returns the user ID of this app builder app.
	 *
	 * @return the user ID of this app builder app
	 */
	@Override
	public long getUserId();

	/**
	 * Sets the user ID of this app builder app.
	 *
	 * @param userId the user ID of this app builder app
	 */
	@Override
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this app builder app.
	 *
	 * @return the user uuid of this app builder app
	 */
	@Override
	public String getUserUuid();

	/**
	 * Sets the user uuid of this app builder app.
	 *
	 * @param userUuid the user uuid of this app builder app
	 */
	@Override
	public void setUserUuid(String userUuid);

	/**
	 * Returns the user name of this app builder app.
	 *
	 * @return the user name of this app builder app
	 */
	@AutoEscape
	@Override
	public String getUserName();

	/**
	 * Sets the user name of this app builder app.
	 *
	 * @param userName the user name of this app builder app
	 */
	@Override
	public void setUserName(String userName);

	/**
	 * Returns the create date of this app builder app.
	 *
	 * @return the create date of this app builder app
	 */
	@Override
	public Date getCreateDate();

	/**
	 * Sets the create date of this app builder app.
	 *
	 * @param createDate the create date of this app builder app
	 */
	@Override
	public void setCreateDate(Date createDate);

	/**
	 * Returns the modified date of this app builder app.
	 *
	 * @return the modified date of this app builder app
	 */
	@Override
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this app builder app.
	 *
	 * @param modifiedDate the modified date of this app builder app
	 */
	@Override
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the ddm structure ID of this app builder app.
	 *
	 * @return the ddm structure ID of this app builder app
	 */
	public long getDdmStructureId();

	/**
	 * Sets the ddm structure ID of this app builder app.
	 *
	 * @param ddmStructureId the ddm structure ID of this app builder app
	 */
	public void setDdmStructureId(long ddmStructureId);

	/**
	 * Returns the ddm structure layout ID of this app builder app.
	 *
	 * @return the ddm structure layout ID of this app builder app
	 */
	public long getDdmStructureLayoutId();

	/**
	 * Sets the ddm structure layout ID of this app builder app.
	 *
	 * @param ddmStructureLayoutId the ddm structure layout ID of this app builder app
	 */
	public void setDdmStructureLayoutId(long ddmStructureLayoutId);

	/**
	 * Returns the de data list view ID of this app builder app.
	 *
	 * @return the de data list view ID of this app builder app
	 */
	public long getDeDataListViewId();

	/**
	 * Sets the de data list view ID of this app builder app.
	 *
	 * @param deDataListViewId the de data list view ID of this app builder app
	 */
	public void setDeDataListViewId(long deDataListViewId);

	/**
	 * Returns the name of this app builder app.
	 *
	 * @return the name of this app builder app
	 */
	public String getName();

	/**
	 * Returns the localized name of this app builder app in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized name of this app builder app
	 */
	@AutoEscape
	public String getName(Locale locale);

	/**
	 * Returns the localized name of this app builder app in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this app builder app. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@AutoEscape
	public String getName(Locale locale, boolean useDefault);

	/**
	 * Returns the localized name of this app builder app in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized name of this app builder app
	 */
	@AutoEscape
	public String getName(String languageId);

	/**
	 * Returns the localized name of this app builder app in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized name of this app builder app
	 */
	@AutoEscape
	public String getName(String languageId, boolean useDefault);

	@AutoEscape
	public String getNameCurrentLanguageId();

	@AutoEscape
	public String getNameCurrentValue();

	/**
	 * Returns a map of the locales and localized names of this app builder app.
	 *
	 * @return the locales and localized names of this app builder app
	 */
	public Map<Locale, String> getNameMap();

	/**
	 * Sets the name of this app builder app.
	 *
	 * @param name the name of this app builder app
	 */
	public void setName(String name);

	/**
	 * Sets the localized name of this app builder app in the language.
	 *
	 * @param name the localized name of this app builder app
	 * @param locale the locale of the language
	 */
	public void setName(String name, Locale locale);

	/**
	 * Sets the localized name of this app builder app in the language, and sets the default locale.
	 *
	 * @param name the localized name of this app builder app
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameCurrentLanguageId(String languageId);

	/**
	 * Sets the localized names of this app builder app from the map of locales and localized names.
	 *
	 * @param nameMap the locales and localized names of this app builder app
	 */
	public void setNameMap(Map<Locale, String> nameMap);

	/**
	 * Sets the localized names of this app builder app from the map of locales and localized names, and sets the default locale.
	 *
	 * @param nameMap the locales and localized names of this app builder app
	 * @param defaultLocale the default locale
	 */
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	/**
	 * Returns the status of this app builder app.
	 *
	 * @return the status of this app builder app
	 */
	public int getStatus();

	/**
	 * Sets the status of this app builder app.
	 *
	 * @param status the status of this app builder app
	 */
	public void setStatus(int status);

	@Override
	public String[] getAvailableLanguageIds();

	@Override
	public String getDefaultLanguageId();

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException;

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

}