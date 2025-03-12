package cn.codependency.framework.puzzle.repository.configuration;

import cn.codependency.framework.puzzle.repository.RepositoryAutoCommitIntercept;
import cn.codependency.framework.puzzle.repository.annotation.AutoRepository;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class RepositoryAutoCommitConfiguration {

    @Bean
    @Primary
    public RepositoryAutoCommitIntercept repositoryAutoCommitIntercept() {
        return new RepositoryAutoCommitIntercept();
    }

    @Bean
    @Primary
    public Advisor repositoryAutoCommitInterceptAdvisor(RepositoryAutoCommitIntercept repositoryAutoCommitIntercept,
                                                        @Value("${puzzle.repository.aop.auto.commit.order:2147483647}") int repositoryAutoCommitAopOrder) {

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("@within(" + AutoRepository.class.getName() + ")||@annotation(" + AutoRepository.class.getName() + ")");
        advisor.setOrder(repositoryAutoCommitAopOrder);
        advisor.setAdvice(repositoryAutoCommitIntercept);
        return advisor;
    }

}
