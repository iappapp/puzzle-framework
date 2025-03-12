package cn.codependency.framework.puzzle.repository.sql.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UpdateSomeColumnParam implements Serializable {

    private Object entity;

    private Collection<String> updateColumns;

}
