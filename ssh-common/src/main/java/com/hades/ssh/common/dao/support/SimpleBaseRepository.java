package com.hades.ssh.common.dao.support;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.hades.ssh.common.dao.BaseDao;
import com.hades.ssh.common.dao.RepositoryHelper;
import com.hades.ssh.common.dao.callback.SearchCallback;
import com.hades.ssh.common.dao.support.annotation.QueryJoin;
import com.hades.ssh.common.entity.search.Searchable;
import com.hades.ssh.common.plugin.entity.LogicDeleteable;

/**
 * <p>抽象基础Custom Repository实现</p>
 * <p>User: XieLei
 * <p>Date: 2016年8月3日 上午11:39:01
 * <p>Version: 1.0
 */
public class SimpleBaseRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseDao<T, ID> {

	public static final String LOGIC_DELETE_ALL_QUERY_STRING = "update %s x set x.deleted=true where x in (?1)";
    public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x in (?1)";
    public static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
    public static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1=1 ";

    private final EntityManager em;
    private final JpaEntityInformation<T, ?> entityInformation;

    private final RepositoryHelper repositoryHelper;

    private CrudMethodMetadata metadata;

    private Class<T> entityClass;
    private String entityName;
    private String idName;
    
    /**
     * 查询所有的QL
     */
    private String findAllQL;
    /**
     * 统计QL
     */
    private String countAllQL;

    private QueryJoin[] joins;

    private SearchCallback searchCallback = SearchCallback.DEFAULT;
    
	public SimpleBaseRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
        this.entityClass = this.entityInformation.getJavaType();
        this.entityName = this.entityInformation.getEntityName();
        this.idName = this.entityInformation.getIdAttributeNames().iterator().next();
        this.em = entityManager;

        repositoryHelper = new RepositoryHelper(entityClass);

        findAllQL = String.format(FIND_QUERY_STRING, entityName);
        countAllQL = String.format(COUNT_QUERY_STRING, entityName);
	}
	
	public void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata) {
		this.metadata = crudMethodMetadata;
	}

	protected CrudMethodMetadata getRepositoryMethodMetadata() {
		return metadata;
	}
	
	/**
     * 设置searchCallback
     *
     * @param searchCallback
     */
    public void setSearchCallback(SearchCallback searchCallback) {
        this.searchCallback = searchCallback;
    }
    
    /**
     * 设置查询所有的ql
     *
     * @param findAllQL
     */
    public void setFindAllQL(String findAllQL) {
        this.findAllQL = findAllQL;
    }

    /**
     * 设置统计的ql
     *
     * @param countAllQL
     */
    public void setCountAllQL(String countAllQL) {
        this.countAllQL = countAllQL;
    }

    public void setJoins(QueryJoin[] joins) {
        this.joins = joins;
    }
    
	/////////////////////////////////////////////////
	////////覆盖默认spring data jpa的实现////////////////
	/////////////////////////////////////////////////

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    @Transactional
    @Override
    public void delete(final ID id) {
        T m = findOne(id);
        delete(m);
    }
    
    /**
     * 删除实体
     *
     * @param m 实体
     */
    @Transactional
    @Override
    public void delete(final T m) {
        if (m == null) {
            return;
        }
        if (m instanceof LogicDeleteable) {
            ((LogicDeleteable) m).markDeleted();
            save(m);
        } else {
            super.delete(m);
        }
    }
    
    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    @Transactional
	@Override
	public void delete(final ID[] ids) {
    	if (ArrayUtils.isEmpty(ids)) {
            return;
        }
        List<T> entities = Lists.newArrayList();
        for (ID id : ids) {
            T entity = null;
            try {
            	entity = entityClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass + " error", e);
            }
            try {
                BeanUtils.setProperty(entity, idName, id);
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass + " error, can not set id", e);
            }
            entities.add(entity);
        }
        deleteInBatch(entities);
	}
    
    @Transactional
    @Override
    public void deleteInBatch(final Iterable<T> entities) {
        Iterator<T> iter = entities.iterator();
        if (entities == null || !iter.hasNext()) {
            return;
        }

        Set<T> models = Sets.newHashSet(iter);

        boolean logicDeleteableEntity = LogicDeleteable.class.isAssignableFrom(this.entityClass);

        if (logicDeleteableEntity) {
            String ql = String.format(LOGIC_DELETE_ALL_QUERY_STRING, entityName);
            repositoryHelper.batchUpdate(ql, models);
        } else {
            String ql = String.format(DELETE_ALL_QUERY_STRING, entityName);
            repositoryHelper.batchUpdate(ql, models);
        }
    }
    
    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    @Transactional
    @Override
    public T findOne(ID id) {
    	if (id == null) {
    		return null;
    	}
    	if (id instanceof Integer && ((Integer) id).intValue() == 0) {
            return null;
        }
    	if (id instanceof Long && ((Long) id).longValue() == 0L) {
            return null;
        }
    	return super.findOne(id);
    }
    
    ////////根据Specification查询 直接从SimpleJpaRepository复制过来的///////////////////////////////////
    @Override
    public T findOne(Specification<T> spec) {
        try {
            return getQuery(spec, (Sort) null).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll(ID[])
     */
    public List<T> findAll(Iterable<ID> ids) {

        return getQuery(new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<?> path = root.get(entityInformation.getIdAttribute());
                return path.in(cb.parameter(Iterable.class, "ids"));
            }
        }, (Sort) null).setParameter("ids", ids).getResultList();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification)
     */
    public List<T> findAll(Specification<T> spec) {
        return getQuery(spec, (Sort) null).getResultList();
    }
    
    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification, org.springframework.data.domain.Pageable)
     */
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {

        TypedQuery<T> query = getQuery(spec, pageable);
        return pageable == null ? new PageImpl<T>(query.getResultList()) : readPage(query, pageable, spec);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification, org.springframework.data.domain.Sort)
     */
    public List<T> findAll(Specification<T> spec, Sort sort) {

        return getQuery(spec, sort).getResultList();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#count(org.springframework.data.jpa.domain.Specification)
     */
    public long count(Specification<T> spec) {

        return getCountQuery(spec).getSingleResult();
    }
    ////////根据Specification查询 直接从SimpleJpaRepository复制过来的///////////////////////////////////


    ///////直接从SimpleJpaRepository复制过来的///////////////////////////////

    /**
     * Creates a new count query for the given {@link org.springframework.data.jpa.domain.Specification}.
     *
     * @param spec can be {@literal null}.
     * @return
     */
    @Override
    protected TypedQuery<Long> getCountQuery(Specification<T> spec) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<T> root = applySpecificationToCriteria(spec, query);

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        TypedQuery<Long> q = em.createQuery(query);
        repositoryHelper.applyEnableQueryCache(q);
        return q;
    }

    /**
     * Creates a new {@link javax.persistence.TypedQuery} from the given {@link org.springframework.data.jpa.domain.Specification}.
     *
     * @param spec     can be {@literal null}.
     * @param pageable can be {@literal null}.
     * @return
     */
    protected TypedQuery<T> getQuery(Specification<T> spec, Pageable pageable) {
        Sort sort = pageable == null ? null : pageable.getSort();
        return getQuery(spec, sort);
    }

    /**
     * Creates a {@link javax.persistence.TypedQuery} for the given {@link org.springframework.data.jpa.domain.Specification} and {@link org.springframework.data.domain.Sort}.
     *
     * @param spec can be {@literal null}.
     * @param sort can be {@literal null}.
     * @return
     */
    protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);

        Root<T> root = applySpecificationToCriteria(spec, query);
        query.select(root);

        applyJoins(root);

        if (sort != null) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }

        TypedQuery<T> q = em.createQuery(query);

        repositoryHelper.applyEnableQueryCache(q);

        return applyRepositoryMethodMetadata(q);
    }

    private void applyJoins(Root<T> root) {
        if(joins == null) {
            return;
        }

        for(QueryJoin join : joins) {
            root.join(join.property(), join.joinType());
        }
    }


    /**
     * Applies the given {@link org.springframework.data.jpa.domain.Specification} to the given {@link javax.persistence.criteria.CriteriaQuery}.
     *
     * @param spec  can be {@literal null}.
     * @param query must not be {@literal null}.
     * @return
     */
    private <S> Root<T> applySpecificationToCriteria(Specification<T> spec, CriteriaQuery<S> query) {

        Assert.notNull(query);
        Root<T> root = query.from(entityClass);

        if (spec == null) {
            return root;
        }

        CriteriaBuilder builder = em.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    private TypedQuery<T> applyRepositoryMethodMetadata(TypedQuery<T> query) {

		if (metadata == null) {
			return query;
		}

		LockModeType type = metadata.getLockModeType();
		TypedQuery<T> toReturn = type == null ? query : query.setLockMode(type);

		applyQueryHints(toReturn);

		return toReturn;
	}
    
    private void applyQueryHints(Query query) {

		for (Entry<String, Object> hint : getQueryHints().entrySet()) {
			query.setHint(hint.getKey(), hint.getValue());
		}
	}
    ///////直接从SimpleJpaRepository复制过来的///////////////////////////////


    @Override
    public List<T> findAll() {
        return repositoryHelper.findAll(findAllQL);
    }

    @Override
    public List<T> findAll(final Sort sort) {
        return repositoryHelper.findAll(findAllQL, sort);
    }

    @Override
    public Page<T> findAll(final Pageable pageable) {
        return new PageImpl<T>(
                repositoryHelper.<T>findAll(findAllQL, pageable),
                pageable,
                repositoryHelper.count(countAllQL)
        );
    }

    @Override
    public long count() {
        return repositoryHelper.count(countAllQL);
    }


    /////////////////////////////////////////////////
    ///////////////////自定义实现////////////////////
    /////////////////////////////////////////////////

    @Override
    public Page<T> findAll(final Searchable searchable) {
        List<T> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);
        long total = searchable.hasPageable() ? count(searchable) : list.size();
        return new PageImpl<T>(
                list,
                searchable.getPage(),
                total
        );
    }

    @Override
    public long count(final Searchable searchable) {
        return repositoryHelper.count(countAllQL, searchable, searchCallback);
    }

    /**
     * 重写默认的 这样可以走一级/二级缓存
     *
     * @param id
     * @return
     */
    @Override
    public boolean exists(ID id) {
        return findOne(id) != null;
    }

}
