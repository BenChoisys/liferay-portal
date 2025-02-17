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

package com.liferay.search.experiences.rest.client.dto.v1_0;

import com.liferay.search.experiences.rest.client.function.UnsafeSupplier;
import com.liferay.search.experiences.rest.client.serdes.v1_0.GeneralSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class General implements Cloneable, Serializable {

	public static General toDTO(String json) {
		return GeneralSerDes.toDTO(json);
	}

	public Boolean getApplyIndexerClauses() {
		return applyIndexerClauses;
	}

	public void setApplyIndexerClauses(Boolean applyIndexerClauses) {
		this.applyIndexerClauses = applyIndexerClauses;
	}

	public void setApplyIndexerClauses(
		UnsafeSupplier<Boolean, Exception> applyIndexerClausesUnsafeSupplier) {

		try {
			applyIndexerClauses = applyIndexerClausesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean applyIndexerClauses;

	public String[] getClauseContributorsExcludes() {
		return clauseContributorsExcludes;
	}

	public void setClauseContributorsExcludes(
		String[] clauseContributorsExcludes) {

		this.clauseContributorsExcludes = clauseContributorsExcludes;
	}

	public void setClauseContributorsExcludes(
		UnsafeSupplier<String[], Exception>
			clauseContributorsExcludesUnsafeSupplier) {

		try {
			clauseContributorsExcludes =
				clauseContributorsExcludesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] clauseContributorsExcludes;

	public String[] getClauseContributorsIncludes() {
		return clauseContributorsIncludes;
	}

	public void setClauseContributorsIncludes(
		String[] clauseContributorsIncludes) {

		this.clauseContributorsIncludes = clauseContributorsIncludes;
	}

	public void setClauseContributorsIncludes(
		UnsafeSupplier<String[], Exception>
			clauseContributorsIncludesUnsafeSupplier) {

		try {
			clauseContributorsIncludes =
				clauseContributorsIncludesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] clauseContributorsIncludes;

	public String[] getSearchableAssetTypes() {
		return searchableAssetTypes;
	}

	public void setSearchableAssetTypes(String[] searchableAssetTypes) {
		this.searchableAssetTypes = searchableAssetTypes;
	}

	public void setSearchableAssetTypes(
		UnsafeSupplier<String[], Exception>
			searchableAssetTypesUnsafeSupplier) {

		try {
			searchableAssetTypes = searchableAssetTypesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] searchableAssetTypes;

	@Override
	public General clone() throws CloneNotSupportedException {
		return (General)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof General)) {
			return false;
		}

		General general = (General)object;

		return Objects.equals(toString(), general.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return GeneralSerDes.toJSON(this);
	}

}