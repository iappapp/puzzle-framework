package ${generatorPackage}.list;

import cn.codependency.framework.puzzle.model.PagedParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListQueryParam extends PagedParam {

    private Boolean dataPermission;

    private String permissionField;

    private String permissionQuery;

    private String mainTable;

    private List<String> columnNames;

    private List<QueryColumn> columns;

    private List<Join> joins;

    private List<QueryCondition> queries;

    private List<OrderBy> orders;

    private Boolean distinct;

    private List<QueryColumn> extColumns;


    @Getter
    @Setter
    public static class Join {
        private String name;

        private String tableName;

        private String condition;
    }

    @Getter
    @Setter
    public static class QueryCondition {
        private String field;

        private String fieldName;

        private List<Object> values;

        private String type;

        private Boolean negate;

        private String dbField;

        private List<QueryCondition> conditions;
    }

    @Getter
    @Setter
    public static class OrderBy {

        private String field;

        private String expression;

        private String order;

        private String dbField;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class QueryColumn {

        public QueryColumn(String field) {
            this.field = field;
        }

        private String field;

        private String fieldName;

        private String dbField;

        private String enums;

        private String mapping;

        private String expression;

        private String title;
    }
}
