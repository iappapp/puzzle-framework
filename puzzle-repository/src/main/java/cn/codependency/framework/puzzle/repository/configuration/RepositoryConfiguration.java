package cn.codependency.framework.puzzle.repository.configuration;

import cn.codependency.framework.puzzle.repository.RepositoryFactory;
import cn.codependency.framework.puzzle.repository.RepositoryUtils;
import cn.codependency.framework.puzzle.repository.Repositorys;
import cn.codependency.framework.puzzle.repository.SimpleRepositoryFactory;
import cn.codependency.framework.puzzle.repository.utils.BeanUtils;
import cn.codependency.framework.puzzle.repository.utils.TxUtils;
import cn.codependency.framework.puzzle.repository.processor.RepositoryRegisterPostProcessor;
import com.baomidou.mybatisplus.annotation.DbType;
import cn.codependency.framework.puzzle.common.id.IdGeneratorUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Import(RepositoryRegisterPostProcessor.class)
public class RepositoryConfiguration {


    @Bean
    public RepositoryFactory repositoryFactory() {
        return new SimpleRepositoryFactory();
    }

    @Bean
    public RepositoryUtils repositoryUtils(IdGeneratorUtils idGeneratorUtils, @Value("${puzzle.database.type:MYSQL}") String dbType) {
        DbType databaseType = DbType.getDbType(dbType);
        return new RepositoryUtils(idGeneratorUtils, databaseType);
    }

    @Bean
    @Primary
    public Repositorys repositorys(RepositoryFactory repositoryFactory) {
        return new Repositorys(repositoryFactory);
    }

    @Bean
    public BeanUtils beanUtils() {
        return new BeanUtils();
    }

    @Bean
    public TxUtils txUtils() {
        return new TxUtils();
    }
}
