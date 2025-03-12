package cn.codependency.framework.puzzle.repository;

import cn.codependency.framework.puzzle.repository.annotation.AutoRepository;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.Objects;


public class RepositoryAutoCommitIntercept implements MethodInterceptor {

    @Resource
    Repositorys repositorys;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        boolean autoCommit = false;
        boolean readOnly = false;
        AutoRepository annotation = AnnotationUtils.findAnnotation(invocation.getMethod(), AutoRepository.class);

        if (Objects.nonNull(annotation)) {
            autoCommit = annotation.autoCommit();
            readOnly = annotation.readOnly();
        } else {
            annotation = AnnotationUtils.findAnnotation(invocation.getMethod().getDeclaringClass(), AutoRepository.class);
            if (Objects.nonNull(annotation)) {
                autoCommit = annotation.autoCommit();
                readOnly = annotation.readOnly();
            }
        }

        // 兼容Graphql QueryMapping
        String graphqlQueryMapping = "org.springframework.graphql.data.method.annotation.QueryMapping";
        try {
            Class<? extends Annotation> aClass = (Class<? extends Annotation>) Class.forName(graphqlQueryMapping);
            Annotation queryMapping = AnnotationUtils.findAnnotation(invocation.getMethod(), aClass);
            if (Objects.nonNull(queryMapping)) {
                readOnly = true;
            }
        } catch (Exception e) {
        }

        if (!readOnly && autoCommit) {
            return repositorys.autoCommit(invocation::proceed);
        }
        return invocation.proceed();
    }
}
