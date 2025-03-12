<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package}.mapper.${name?cap_first}GenerateMapper">

    <select id="$listQuery" resultType="java.util.HashMap" >
        <include refid="ListBasic.SelectCondition"/>
        <include refid="ListBasic.WhereCondition"/>
        <include refid="ListBasic.OrderByCondition"/>
    </select>

</mapper>