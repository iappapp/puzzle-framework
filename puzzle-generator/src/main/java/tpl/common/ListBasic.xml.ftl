<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="ListBasic">

    <sql id="SelectCondition">
        select <if test="distinct != null and distinct == true" > distinct on (_main.id) </if>
        <foreach item="column" collection="columns" separator="," >
            <choose>
                <when test="column.expression != null">${"$"}{column.expression}</when><otherwise>${"$"}{column.dbField}</otherwise>
            </choose>
            as  <choose>
                <when test="column.title != null" >"${"$"}{column.title}" </when>
                <otherwise>"${"$"}{column.fieldName}"</otherwise>
            </choose>
        </foreach>
        from ${"$"}{mainTable} _main
        <foreach collection="joins" item="join">
            left join ${"$"}{join.tableName} as ${"$"}{join.name} on ${"$"}{join.condition}
        </foreach>
    </sql>

    <sql id="WhereCondition">
        <where>
            <if test="dataPermission">
                to_tsvector('simple', _main.${"$"}{permissionField}) @@ to_tsquery(${"#"}{permissionQuery})
            </if>

            <if test="queries != null and queries.size() > 0">
                and (
                <foreach item="query" collection="queries" separator="and">
                    <if test="query.type != null">
                        <choose>
                            <when test="query.type == 'equals'">
                                <if test="query.negate == null or query.negate == false">
                                    ${"$"}{query.dbField} = ${"#"}{query.values[0]}
                                </if>

                                <if test="query.negate != null and query.negate == true">
                                    ${"$"}{query.dbField} != ${"#"}{query.values[0]}
                                </if>
                            </when>
                            <when test="query.type == 'isNull'">
                                <if test="query.negate == null or query.negate == false">
                                    ${"$"}{query.dbField} is null
                                </if>

                                <if test="query.negate != null and query.negate == true">
                                    ${"$"}{query.dbField} is not null
                                </if>
                            </when>
                            <when test="query.type == 'contains'">
                                <if test="query.negate == null or query.negate == false">
                                    ${"$"}{query.dbField} in
                                    <foreach collection="query.values" item="item" open="(" close=")" separator=",">
                                        ${"#"}{item}
                                    </foreach>
                                </if>

                                <if test="query.negate != null and query.negate == true">
                                    ${"$"}{query.dbField} not in
                                    <foreach collection="query.values" item="item" open="(" close=")" separator=",">
                                        ${"#"}{item}
                                    </foreach>
                                </if>

                            </when>
                            <when test="query.type == 'fuzzy' ">
                                <if test="query.negate == null or query.negate == false">
                                    ${"$"}{query.dbField} like concat('%', ${"#"}{query.values[0]}, '%')
                                </if>
                                <if test="query.negate != null and query.negate == true">
                                    ${"$"}{query.dbField} not like concat('%', ${"#"}{query.values[0]}, '%')
                                </if>
                            </when>
                            <when test="query.type == 'isTrue'">
                                <if test="query.negate == null or query.negate == false">
                                    ${"$"}{query.dbField} = true
                                </if>
                                <if test="query.negate != null and query.negate == true">
                                    ${"$"}{query.dbField} = false
                                </if>
                            </when>
                            <when test="query.type == 'dateRange' ">
                                <if test="query.values[0] != null">
                                    ${"$"}{query.dbField} &gt;= ${"#"}{query.values[0]}::timestamp
                                </if>
                                <if test="query.values[1] != null">
                                    and ${"$"}{query.dbField} &lt; ${"#"}{query.values[1]}::timestamp
                                </if>
                            </when>

                            <when test="query.type == 'or' ">
                                <if test="query.conditions.size() > 0">
                                    (
                                    <foreach item="orQuery" collection="query.conditions" separator="or">

                                        <choose>
                                            <when test="orQuery.type == 'equals'">
                                                <if test="orQuery.negate == null or orQuery.negate == false">
                                                    ${"$"}{orQuery.dbField} = ${"#"}{orQuery.values[0]}
                                                </if>

                                                <if test="orQuery.negate != null and orQuery.negate == true">
                                                    ${"$"}{orQuery.dbField} != ${"#"}{orQuery.values[0]}
                                                </if>
                                            </when>
                                            <when test="orQuery.type == 'isNull'">
                                                <if test="orQuery.negate == null or orQuery.negate == false">
                                                    ${"$"}{orQuery.dbField} is null
                                                </if>

                                                <if test="orQuery.negate != null and orQuery.negate == true">
                                                    ${"$"}{orQuery.dbField} is not null
                                                </if>
                                            </when>
                                            <when test="orQuery.type == 'contains'">
                                                <if test="orQuery.negate == null or orQuery.negate == false">
                                                    ${"$"}{orQuery.dbField} in
                                                    <foreach collection="orQuery.values" item="item" open="(" close=")"
                                                             separator=",">
                                                        ${"#"}{item}
                                                    </foreach>
                                                </if>

                                                <if test="orQuery.negate != null and orQuery.negate == true">
                                                    ${"$"}{orQuery.dbField} not in
                                                    <foreach collection="orQuery.values" item="item" open="(" close=")"
                                                             separator=",">
                                                        ${"#"}{item}
                                                    </foreach>
                                                </if>

                                            </when>
                                            <when test="orQuery.type == 'fuzzy' ">
                                                <if test="orQuery.negate == null or orQuery.negate == false">
                                                    ${"$"}{orQuery.dbField} like concat('%', ${"#"}{orQuery.values[0]}, '%')
                                                </if>
                                                <if test="query.negate != null and query.negate == true">
                                                    ${"$"}{orQuery.dbField} not like concat('%', ${"#"}{orQuery.values[0]}, '%')
                                                </if>
                                            </when>
                                            <when test="orQuery.type == 'isTrue'">
                                                <if test="orQuery.negate == null or orQuery.negate == false">
                                                    ${"$"}{orQuery.dbField} = true
                                                </if>
                                                <if test="orQuery.negate != null and orQuery.negate == true">
                                                    ${"$"}{orQuery.dbField} = false
                                                </if>
                                            </when>
                                            <when test="orQuery.type == 'dateRange' ">
                                                <if test="orQuery.values[0] != null">
                                                    ${"$"}{orQuery.dbField} &gt;= ${"#"}{orQuery.values[0]}::timestamp
                                                </if>
                                                <if test="orQuery.values[1] != null">
                                                    ${"$"}{orQuery.dbField} &lt; ${"#"}{orQuery.values[1]}::timestamp
                                                </if>
                                            </when>
                                            <otherwise>
                                                1 = 1
                                            </otherwise>
                                        </choose>
                                    </foreach>
                                    )
                                </if>
                            </when>
                            <otherwise>
                                1 = 1
                            </otherwise>
                        </choose>
                    </if>
                </foreach>
                )
            </if>
        </where>
    </sql>

    <sql id="OrderByCondition">
        <if test="orders != null and orders.size() > 0" >
            order by
            <foreach item="item" collection="orders" separator=",">
                <if test="item.expression != null" >
                    ${"$"}{item.expression} ${"$"}{item.order}
                </if>

                <if test="item.dbField != null">
                    ${"$"}{item.dbField} ${"$"}{item.order}
                </if>
            </foreach>
        </if>
    </sql>

</mapper>