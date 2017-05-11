/**
 *
 */
package com.android_mobile.core.utiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> col) {
        return col == null || col.size() == 0;
    }

    public static <T> boolean isNotEmpty(Collection<T> col) {
        return !isEmpty(col);
    }

    public static <K, V> boolean isEmpty(Map<K, V> map) {
        return map == null || map.size() == 0;
    }

    public static <T> List<T> copy(List<T> list) {
        if (isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        List<T> copied = new ArrayList<T>();
        copied.addAll(list);
        return copied;
    }
}
