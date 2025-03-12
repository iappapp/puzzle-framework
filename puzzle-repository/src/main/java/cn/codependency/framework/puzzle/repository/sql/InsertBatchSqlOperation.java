package cn.codependency.framework.puzzle.repository.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class InsertBatchSqlOperation<Entity, Mapper extends BaseMapper<Entity>> implements SqlOperation {

    private Mapper mapper;

    private List<Entity> entities;

    public InsertBatchSqlOperation(Mapper mapper, List<Entity> entities) {
        this.mapper = mapper;
        this.entities = entities;
    }

    @Override
    public void doOperate(SqlSession sqlSession) {
        if (CollectionUtils.isNotEmpty(entities) && Objects.nonNull(getMapper())) {
            String sqlStatement = this.sqlStatement("batchInsert");
            sqlSession.update(sqlStatement, entities);
        }
    }

    @Override
    public Class<?> entityClass() {
        if (CollectionUtils.isNotEmpty(entities)) {
            return entities.get(0).getClass();
        }
        return Object.class;
    }
}
