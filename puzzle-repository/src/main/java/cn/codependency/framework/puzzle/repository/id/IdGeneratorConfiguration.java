package cn.codependency.framework.puzzle.repository.id;


import cn.codependency.framework.puzzle.common.id.IdGeneratorFactory;
import cn.codependency.framework.puzzle.common.id.IdGeneratorUtils;
import org.springframework.context.annotation.Bean;

public class IdGeneratorConfiguration {

    @Bean
    public IdGeneratorFactory idGeneratorFactory() {
        return new DefaultIdGeneratorFactory();
    }

    @Bean
    public IdGeneratorUtils idGeneratorUtils(IdGeneratorFactory idGeneratorFactory) {
        return new IdGeneratorUtils(idGeneratorFactory);
    }
}
