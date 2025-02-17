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

package com.liferay.search.experiences.internal.blueprint.parameter;

import com.liferay.search.experiences.blueprint.parameter.SXPParameter;
import com.liferay.search.experiences.blueprint.parameter.SXPParameterData;

import java.util.Objects;
import java.util.Set;

/**
 * @author Petteri Karttunen
 */
public class SXPParameterDataImpl implements SXPParameterData {

	public SXPParameterDataImpl(
		String keywords, Set<SXPParameter> sxpParameters) {

		_keywords = keywords;
		_sxpParameters = sxpParameters;
	}

	@Override
	public String getKeywords() {
		return _keywords;
	}

	@Override
	public SXPParameter getSXPParameterByName(String name) {
		for (SXPParameter sxpParameter : _sxpParameters) {
			if (Objects.equals(sxpParameter.getName(), name)) {
				return sxpParameter;
			}
		}

		return null;
	}

	@Override
	public SXPParameter getSXPParameterByTemplateVariable(
		String templateVariable) {

		for (SXPParameter sxpParameter : _sxpParameters) {
			if (Objects.equals(
					sxpParameter.getTemplateVariable(), templateVariable)) {

				return sxpParameter;
			}
		}

		return null;
	}

	@Override
	public Set<SXPParameter> getSXPParameters() {
		return _sxpParameters;
	}

	private final String _keywords;
	private final Set<SXPParameter> _sxpParameters;

}