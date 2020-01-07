/*
 * Copyright 2019 Red Hat
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.hacep.consumer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class BidirectionalMap<K, V> extends HashMap<K, V> implements Serializable {

  private final Map<V, K> inversedMap = new HashMap();

  @Override
  public V remove(Object key) {
    V val = super.remove(key);
    inversedMap.remove(val);
    return val;
  }

  @Override
  public V put(K key, V value) {
    inversedMap.put(value, key);
    return super.put(key, value);
  }

  public K getKey(V value) {
    return inversedMap.get(value);
  }

  public K removeValue(V value) {
    K key = inversedMap.get(value);
    super.remove(key);
    return key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BidirectionalMap)) {
      return false;
    }

    BidirectionalMap<?, ?> map = (BidirectionalMap<?, ?>) o;

    return new EqualsBuilder()
            .appendSuper(super.equals(o))
            .append(inversedMap, map.inversedMap)
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
            .appendSuper(super.hashCode())
            .append(inversedMap)
            .toHashCode();
  }
}
