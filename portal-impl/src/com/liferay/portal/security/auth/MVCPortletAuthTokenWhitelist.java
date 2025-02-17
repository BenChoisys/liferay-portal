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

package com.liferay.portal.security.auth;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.auth.BaseAuthTokenWhitelist;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.registry.util.StringPlus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tomas Polesovsky
 */
public class MVCPortletAuthTokenWhitelist extends BaseAuthTokenWhitelist {

	public MVCPortletAuthTokenWhitelist() {
		trackWhitelistServices(
			"auth.token.ignore.mvc.action", MVCActionCommand.class,
			_portletCSRFWhitelist);
		trackWhitelistServices(
			"portlet.add.default.resource.check.whitelist.mvc.action",
			MVCActionCommand.class, _portletInvocationWhitelistAction);
		trackWhitelistServices(
			"portlet.add.default.resource.check.whitelist.mvc.action",
			MVCRenderCommand.class, _portletInvocationWhitelistRender);
		trackWhitelistServices(
			"portlet.add.default.resource.check.whitelist.mvc.action",
			MVCResourceCommand.class, _portletInvocationWhitelistResource);
	}

	@Override
	public boolean isPortletCSRFWhitelisted(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		String portletId = portlet.getPortletId();

		String[] mvcActionCommandNames = getMVCActionCommandNames(
			httpServletRequest, portletId);

		return _containsAll(
			portletId, _portletCSRFWhitelist, mvcActionCommandNames);
	}

	@Override
	public boolean isPortletInvocationWhitelisted(
		HttpServletRequest httpServletRequest, Portlet portlet) {

		String portletId = portlet.getPortletId();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isLifecycleAction()) {
			String[] mvcActionCommandNames = getMVCActionCommandNames(
				httpServletRequest, portletId);

			return _containsAll(
				portletId, _portletInvocationWhitelistAction,
				mvcActionCommandNames);
		}
		else if (themeDisplay.isLifecycleRender()) {
			String namespace = PortalUtil.getPortletNamespace(portletId);

			String mvcRenderCommandName = httpServletRequest.getParameter(
				namespace.concat("mvcRenderCommandName"));

			return _contains(
				portletId, _portletInvocationWhitelistRender,
				mvcRenderCommandName);
		}
		else if (themeDisplay.isLifecycleResource()) {
			String ppid = httpServletRequest.getParameter("p_p_id");

			if (!portletId.equals(ppid)) {
				return false;
			}

			String mvcResourceCommandName = httpServletRequest.getParameter(
				"p_p_resource_id");

			return _contains(
				portletId, _portletInvocationWhitelistResource,
				mvcResourceCommandName);
		}

		return false;
	}

	@Override
	public boolean isPortletURLCSRFWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		return _containsAll(
			liferayPortletURL.getPortletId(), _portletCSRFWhitelist,
			getMVCActionCommandNames(liferayPortletURL));
	}

	@Override
	public boolean isPortletURLPortletInvocationWhitelisted(
		LiferayPortletURL liferayPortletURL) {

		String portletId = liferayPortletURL.getPortletId();

		String lifecycle = liferayPortletURL.getLifecycle();

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			return _containsAll(
				portletId, _portletInvocationWhitelistAction,
				getMVCActionCommandNames(liferayPortletURL));
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE)) {
			String mvcRenderCommandName = liferayPortletURL.getParameter(
				"mvcRenderCommandName");

			return _contains(
				portletId, _portletInvocationWhitelistRender,
				mvcRenderCommandName);
		}
		else if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			String mvcResourceCommandName = liferayPortletURL.getResourceID();

			return _contains(
				portletId, _portletInvocationWhitelistResource,
				mvcResourceCommandName);
		}

		return false;
	}

	protected String[] getMVCActionCommandNames(
		HttpServletRequest httpServletRequest, String portletId) {

		String namespace = PortalUtil.getPortletNamespace(portletId);

		String[] actionNames = httpServletRequest.getParameterValues(
			namespace.concat(ActionRequest.ACTION_NAME));

		String actions = StringUtil.merge(actionNames);

		return StringUtil.split(actions);
	}

	protected String[] getMVCActionCommandNames(
		LiferayPortletURL liferayPortletURL) {

		Map<String, String[]> parameterMap =
			liferayPortletURL.getParameterMap();

		String[] actionNames = parameterMap.get(ActionRequest.ACTION_NAME);

		String actions = StringUtil.merge(actionNames);

		return StringUtil.split(actions);
	}

	protected String getWhitelistValue(
		String portletName, String whitelistAction) {

		return StringBundler.concat(
			portletName, StringPool.POUND, whitelistAction);
	}

	protected void trackWhitelistServices(
		String whitelistName, Class<?> serviceClass, Set<String> whiteList) {

		ServiceTracker<Object, Object> serviceTracker = new ServiceTracker<>(
			SystemBundleUtil.getBundleContext(),
			SystemBundleUtil.createFilter(
				StringBundler.concat(
					"(&(&(", whitelistName, "=*)(javax.portlet.name=*))",
					"(objectClass=", serviceClass.getName(), "))")),
			new TokenWhitelistTrackerCustomizer(whiteList));

		serviceTracker.open();

		serviceTrackers.add(serviceTracker);
	}

	private boolean _contains(
		String portletId, Set<String> whitelist, String item) {

		if (Validator.isBlank(item)) {
			return false;
		}

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		return whitelist.contains(getWhitelistValue(rootPortletId, item));
	}

	private boolean _containsAll(
		String portletId, Set<String> whitelist, String[] items) {

		if (items.length == 0) {
			return false;
		}

		String rootPortletId = PortletIdCodec.decodePortletName(portletId);

		for (String action : items) {
			if (!whitelist.contains(getWhitelistValue(rootPortletId, action))) {
				return false;
			}
		}

		return true;
	}

	private final Set<String> _portletCSRFWhitelist = Collections.newSetFromMap(
		new ConcurrentHashMap<>());
	private final Set<String> _portletInvocationWhitelistAction =
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private final Set<String> _portletInvocationWhitelistRender =
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private final Set<String> _portletInvocationWhitelistResource =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

	private class TokenWhitelistTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		public TokenWhitelistTrackerCustomizer(Set<String> whitelist) {
			_whitelist = whitelist;
		}

		@Override
		public Object addingService(ServiceReference<Object> serviceReference) {
			Collection<String> whitelistValues = new ArrayList<>();

			List<String> whitelistActions = StringPlus.asList(
				serviceReference.getProperty("mvc.command.name"));

			List<String> portletNames = StringPlus.asList(
				serviceReference.getProperty("javax.portlet.name"));

			for (String portletName : portletNames) {
				for (String whitelistAction : whitelistActions) {
					whitelistValues.add(
						getWhitelistValue(portletName, whitelistAction));
				}
			}

			_whitelist.addAll(whitelistValues);

			return whitelistValues;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Object object) {

			removedService(serviceReference, object);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Object object) {

			Collection<String> whitelistValues = (Collection<String>)object;

			_whitelist.removeAll(whitelistValues);
		}

		private final Set<String> _whitelist;

	}

}