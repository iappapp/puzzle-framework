package cn.codependency.framework.puzzle.repository.processor;

import cn.codependency.framework.puzzle.repository.Repository;
import cn.codependency.framework.puzzle.repository.RepositoryFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;

public class RepositoryRegisterPostProcessor implements BeanPostProcessor {

    @Resource
    private RepositoryFactory repositoryFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Repository) {
            repositoryFactory.register((Repository)bean);
        }
        return bean;
    }
}