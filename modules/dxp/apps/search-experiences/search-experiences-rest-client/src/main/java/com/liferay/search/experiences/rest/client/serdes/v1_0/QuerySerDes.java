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

package com.liferay.search.experiences.rest.client.serdes.v1_0;

import com.liferay.search.experiences.rest.client.dto.v1_0.Clause;
import com.liferay.search.experiences.rest.client.dto.v1_0.Query;
import com.liferay.search.experiences.rest.client.json.BaseJSONParser;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Stream;

import javax.annotation.Generated;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Generated("")
public class QuerySerDes {

	public static Query toDTO(String json) {
		QueryJSONParser queryJSONParser = new QueryJSONParser();

		return queryJSONParser.parseToDTO(json);
	}

	public static Query[] toDTOs(String json) {
		QueryJSONParser queryJSONParser = new QueryJSONParser();

		return queryJSONParser.parseToDTOs(json);
	}

	public static String toJSON(Query query) {
		if (query == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (query.getClauses() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"clauses\": ");

			sb.append("[");

			for (int i = 0; i < query.getClauses().length; i++) {
				sb.append(String.valueOf(query.getClauses()[i]));

				if ((i + 1) < query.getClauses().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (query.getEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enabled\": ");

			sb.append(query.getEnabled());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		QueryJSONParser queryJSONParser = new QueryJSONParser();

		return queryJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(Query query) {
		if (query == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (query.getClauses() == null) {
			map.put("clauses", null);
		}
		else {
			map.put("clauses", String.valueOf(query.getClauses()));
		}

		if (query.getEnabled() == null) {
			map.put("enabled", null);
		}
		else {
			map.put("enabled", String.valueOf(query.getEnabled()));
		}

		return map;
	}

	public static class QueryJSONParser extends BaseJSONParser<Query> {

		@Override
		protected Query createDTO() {
			return new Query();
		}

		@Override
		protected Query[] createDTOArray(int size) {
			return new Query[size];
		}

		@Override
		protected void setField(
			Query query, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "clauses")) {
				if (jsonParserFieldValue != null) {
					query.setClauses(
						Stream.of(
							toStrings((Object[])jsonParserFieldValue)
						).map(
							object -> ClauseSerDes.toDTO((String)object)
						).toArray(
							size -> new Clause[size]
						));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "enabled")) {
				if (jsonParserFieldValue != null) {
					query.setEnabled((Boolean)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			Class<?> valueClass = value.getClass();

			if (value instanceof Map) {
				sb.append(_toJSON((Map)value));
			}
			else if (valueClass.isArray()) {
				Object[] values = (Object[])value;

				sb.append("[");

				for (int i = 0; i < values.length; i++) {
					sb.append("\"");
					sb.append(_escape(values[i]));
					sb.append("\"");

					if ((i + 1) < values.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(entry.getValue()));
				sb.append("\"");
			}
			else {
				sb.append(String.valueOf(entry.getValue()));
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

}