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
import com.liferay.search.experiences.rest.client.serdes.v1_0.HighlightSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class Highlight implements Cloneable, Serializable {

	public static Highlight toDTO(String json) {
		return HighlightSerDes.toDTO(json);
	}

	public Integer getFragmentOffset() {
		return fragmentOffset;
	}

	public void setFragmentOffset(Integer fragmentOffset) {
		this.fragmentOffset = fragmentOffset;
	}

	public void setFragmentOffset(
		UnsafeSupplier<Integer, Exception> fragmentOffsetUnsafeSupplier) {

		try {
			fragmentOffset = fragmentOffsetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer fragmentOffset;

	@Override
	public Highlight clone() throws CloneNotSupportedException {
		return (Highlight)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Highlight)) {
			return false;
		}

		Highlight highlight = (Highlight)object;

		return Objects.equals(toString(), highlight.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return HighlightSerDes.toJSON(this);
	}

}