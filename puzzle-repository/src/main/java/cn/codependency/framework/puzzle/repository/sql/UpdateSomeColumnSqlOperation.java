package cn.codependency.framework.puzzle.repository.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class UpdateSomeColumnSqlOperation<Entity, Mapper extends BaseMapper<Entity>> implements SqlOperation {

    private Mapper mapper;

    private Entity entity;

    private Collection<String> changedFields;

    public UpdateSomeColumnSqlOperation(Mapper mapper, Entity entity, Collection<String> changedFields) {
        this.mapper = mapper;
        this.entity = entity;
        this.changedFields = changedFields;
    }

    @Override
    public void doOperate(SqlSession sqlSession) {
        if (Objects.nonNull(entity) && Objects.nonNull(mapper) && CollectionUtils.isNotEmpty(changedFields)) {
            String sqlStatement = this.sqlStatement("updateSomeColumnById");
            Map<String, Object> param = new HashMap<>();
            param.put("et", entity);
            param.put("updateColumns", changedFields);
            sqlSession.update(sqlStatement, param);
        }
    }

    @Override
    public Class<?> entityClass() {
        if (Objects.nonNull(entity)) {
            return entity.getClass();
        }
        return Object.class;
    }
}
