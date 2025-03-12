package cn.codependency.framework.puzzle.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tenant implements Serializable {

    private Long id;

    private String name;

    private Map<String, Object> attributes = new HashMap<>();

}
