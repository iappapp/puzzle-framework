package cn.codependency.framework.puzzle.repository.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class DeleteBatchSqlOperation<Mapper extends BaseMapper<?>> implements SqlOperation {

    private Mapper mapper;

    private Class<?> entityClass;

    private List<? extends Serializable> ids;

    public DeleteBatchSqlOperation(Mapper mapper, Class<?> entityClass, List<? extends Serializable> ids) {
        this.mapper = mapper;
        this.entityClass = entityClass;
        this.ids = ids;
    }

    @Override
    public void doOperate(SqlSession sqlSession) {
        if (Objects.nonNull(mapper) && CollectionUtils.isNotEmpty(ids)) {
            String sqlStatement = this.sqlStatement("deleteByIds");
            Map<String, Object> param = new HashMap<>();
            param.put("coll", ids);
            sqlSession.delete(sqlStatement, param);
        }
    }

    @Override
    public Class<?> entityClass() {
        return Objects.isNull(entityClass) ? Object.class : entityClass;
    }
}
