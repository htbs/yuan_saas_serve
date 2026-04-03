package com.yuansaas.core.utils.id;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Id生成的工厂函数
 *
 * @author HTB 2026/1/23 10:55
 */
public class IdGeneratorFactory {

    private final Map<IdType, IdGenerator> generators;

    /**
     * 工具类
     */
    public IdGeneratorFactory(List<IdGenerator> list) {
        this.generators = list.stream()
                .collect(Collectors.toMap(
                        IdGenerator::type,
                        Function.identity()
                ));
    }

    /**
     * 实际的实现类
     */
    public IdGenerator get(IdType type) {
        return generators.get(type);
    }

    /**
     * 使用
     * ： UserService 中注入：IdGeneratorFactory factory 之后，factory.get(IdType.SNOWFLAKE).nextId();
     */
}
