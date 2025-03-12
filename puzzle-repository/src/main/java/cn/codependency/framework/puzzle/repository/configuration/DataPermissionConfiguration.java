package cn.codependency.framework.puzzle.repository.configuration;

import cn.codependency.framework.puzzle.repository.Repositorys;
import cn.codependency.framework.puzzle.repository.processor.DataPermissionRepositoryProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class DataPermissionConfiguration {

    @Bean
    public DataPermissionRepositoryProcessor dataPermissionRepositoryProcessor(Repositorys repositorys) {
        DataPermissionRepositoryProcessor dataPermissionRepositoryProcessor = new DataPermissionRepositoryProcessor();
        repositorys.addProcessor(dataPermissionRepositoryProcessor);
        return dataPermissionRepositoryProcessor;
    }

}
