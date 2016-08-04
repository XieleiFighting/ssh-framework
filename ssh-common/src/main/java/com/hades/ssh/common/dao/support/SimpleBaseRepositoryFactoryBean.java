package com.hades.ssh.common.dao.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.EvaluationContextProvider;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.util.StringUtils;

import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.common.dao.callback.SearchCallback;
import com.hades.ssh.common.dao.support.annotation.SearchableQuery;

/**
 * 基础Repostory简单实现 factory bean
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 下午3:33:19
 * <p>Version: 1.0
 */
public class SimpleBaseRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable> 
		extends JpaRepositoryFactoryBean<R, T, ID> {

	public SimpleBaseRepositoryFactoryBean() {
    }
	
	@SuppressWarnings("rawtypes")
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new SimpleBaseRepositoryFactory(entityManager);
	}
}
class SimpleBaseRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {
	
	private EntityManager entityManager;

    public SimpleBaseRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }
    
    protected Object getTargetRepository(RepositoryInformation information) {
    	Class<?> repositoryInterface = information.getRepositoryInterface();
    	
    	if (isBaseRepository(repositoryInterface)) {
    		@SuppressWarnings("unchecked")
			JpaEntityInformation<T, ID> entityInformation = getEntityInformation((Class<T>)information.getDomainType());
    		SimpleBaseRepository<T, ID> repository = new SimpleBaseRepository<T, ID>(entityInformation, entityManager);
    		
    		SearchableQuery searchableQuery = AnnotationUtils.findAnnotation(repositoryInterface, SearchableQuery.class);
            if (searchableQuery != null) {
                String countAllQL = searchableQuery.countAllQuery();
                if (!StringUtils.isEmpty(countAllQL)) {
                    repository.setCountAllQL(countAllQL);
                }
                String findAllQL = searchableQuery.findAllQuery();
                if (!StringUtils.isEmpty(findAllQL)) {
                    repository.setFindAllQL(findAllQL);
                }
                Class<? extends SearchCallback> callbackClass = searchableQuery.callbackClass();
                if (callbackClass != null && callbackClass != SearchCallback.class) {
                    repository.setSearchCallback(BeanUtils.instantiate(callbackClass));
                }

                repository.setJoins(searchableQuery.joins());

            }

            return repository;
    	}
    	
    	return super.getTargetRepository(information);
    }
    
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (isBaseRepository(metadata.getRepositoryInterface())) {
            return SimpleBaseRepository.class;
        }
        return super.getRepositoryBaseClass(metadata);
    }

    private boolean isBaseRepository(Class<?> repositoryInterface) {
        return BaseDao.class.isAssignableFrom(repositoryInterface);
    }

    @Override
    protected QueryLookupStrategy getQueryLookupStrategy(QueryLookupStrategy.Key key, EvaluationContextProvider evaluationContextProvider) {
        return super.getQueryLookupStrategy(key, evaluationContextProvider);
    }
}
