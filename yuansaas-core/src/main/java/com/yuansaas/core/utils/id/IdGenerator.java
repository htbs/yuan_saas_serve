package com.yuansaas.core.utils.id;

/**
 * Id 生成的委托接口
 *
 * @author HTB 2026/1/23 10:34
 */
public sealed interface IdGenerator permits SnowflakeIdGenerator{

    /**
     * 获取Id type
     */
    IdType type();

    /**
     * 生成下一个Long 类型ID
     * @return  Long 类型ID
     */
    long nextId();


}
