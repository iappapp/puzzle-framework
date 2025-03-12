package cn.codependency.framework.puzzle.repository.configuration;

import cn.codependency.framework.puzzle.repository.RepositoryAutoClearIntercept;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class RepositoryAutoClearConfiguration {

    @Bean
    @Primary
    public RepositoryAutoClearIntercept autoRepositoryIntercept() {
        return new RepositoryAutoClearIntercept();
    }

    @Bean
    @Primary
    public Advisor autoRepositoryInterceptAdvisor(RepositoryAutoClearIntercept repositoryAutoClearIntercept,
                                                  @Value("${puzzle.repository.aop.auto.clear.expression:execution(* cn.codependency..*..service..*.*(..))}") String autoRepositoryAopExpression,
                                                  @Value("${puzzle.repository.aop.auto.clear.order:2147483646}") int autoRepositoryAopOrder) {

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(autoRepositoryAopExpression);
        advisor.setOrder(autoRepositoryAopOrder);
        advisor.setAdvice(repositoryAutoClearIntercept);
        return advisor;
    }

}
