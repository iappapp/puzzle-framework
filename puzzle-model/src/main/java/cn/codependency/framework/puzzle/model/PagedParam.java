package cn.codependency.framework.puzzle.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagedParam extends BaseParam {
    private Integer pageSize;
    private Integer pageIndex;
    private Integer pageOffset;
    private Boolean totalCount;
}
