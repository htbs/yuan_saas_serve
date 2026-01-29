package com.yuansaas.core.utils;

import cn.hutool.core.collection.CollUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * 集合工具类
 *
 * @author LXZ 2026/1/27 19:12
 */
public class CollectionUtils {


    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func) {
        if (CollUtil.isEmpty(from)) {
            return new HashSet<>();
        }
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }
}
