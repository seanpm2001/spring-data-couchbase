/*
 * Copyright 2012-2024 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.data.couchbase.core.mapping;

import static java.util.stream.Collectors.toSet;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.springframework.data.mapping.model.SimpleTypeHolder;

import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;

public abstract class CouchbaseSimpleTypes {

	public static final SimpleTypeHolder JSON_TYPES = new SimpleTypeHolder(
			Stream.of(JsonObject.class, JsonArray.class, Number.class).collect(toSet()), true);

	public static final SimpleTypeHolder DOCUMENT_TYPES = new SimpleTypeHolder(
			Stream.of(CouchbaseDocument.class, CouchbaseList.class, Number.class).collect(toSet()), true);

	private CouchbaseSimpleTypes() {}

	public static final Set<Class<?>> AUTOGENERATED_ID_TYPES;

	static {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(String.class);
		classes.add(BigInteger.class);
		AUTOGENERATED_ID_TYPES = Collections.unmodifiableSet(classes);

		Set<Class<?>> simpleTypes = new HashSet<>();
		simpleTypes.add(Pattern.class);
		simpleTypes.add(UUID.class);
		simpleTypes.add(Instant.class);

		COUCHBASE_SIMPLE_TYPES = Collections.unmodifiableSet(simpleTypes);
	}

	private static final Set<Class<?>> COUCHBASE_SIMPLE_TYPES;

	public static final SimpleTypeHolder HOLDER = new SimpleTypeHolder(COUCHBASE_SIMPLE_TYPES, true) {

		@Override
		public boolean isSimpleType(Class<?> type) {

			if (type.isEnum()) {
				return true;
			}

			if (type.getName().startsWith("java.time")) {
				return false;
			}

			return super.isSimpleType(type);
		}
	};

}
