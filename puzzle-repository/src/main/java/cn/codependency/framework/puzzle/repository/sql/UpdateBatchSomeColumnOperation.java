package cn.codependency.framework.puzzle.repository.sql;

import cn.codependency.framework.puzzle.repository.sql.param.UpdateSomeColumnParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class UpdateBatchSomeColumnOperation<Entity, Mapper extends BaseMapper<Entity>> implements SqlOperation {

    private Mapper mapper;

    private List<UpdateSomeColumnParam> items;

    public UpdateBatchSomeColumnOperation(Mapper mapper, List<UpdateSomeColumnParam> items) {
        this.mapper = mapper;
        this.items = items;
    }

    @Override
    public void doOperate(SqlSession sqlSession) {
        if (CollectionUtils.isNotEmpty(items) && Objects.nonNull(getMapper())) {
            String sqlStatement = this.sqlStatement("updateBatchSomeColumn");
            sqlSession.update(sqlStatement, items);
        }
    }

    @Override
    public Class<?> entityClass() {
        if (CollectionUtils.isNotEmpty(items)) {
            if (Objects.nonNull(items.get(0).getEntity())) {
                return items.get(0).getEntity().getClass();
            }
        }
        return Object.class;
    }
}
