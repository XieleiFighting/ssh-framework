package com.hades.ssh.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.aop.framework.AopContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.google.common.collect.Maps;
import com.hades.ssh.common.dao.RepositoryHelper;
import com.hades.ssh.common.entity.BaseEntity;
import com.hades.ssh.common.entity.search.Searchable;
import com.hades.ssh.common.plugin.entity.Movable;

public abstract class BaseMovableServiceImpl<T extends BaseEntity<ID> & Movable, ID extends Serializable>
		extends BaseServiceImpl<T, ID> implements BaseMovableService<T, ID> {
	
	//权重的步长
    private final Integer stepLength;

    /**
     * 默认步长1000
     */
    protected BaseMovableServiceImpl() {
        this(1000);
    }

    protected BaseMovableServiceImpl(Integer stepLength) {
        this.stepLength = stepLength;
    }

    /**
     * 权重的步长 默认1000
     *
     * @return
     */
    public Integer getStepLength() {
        return stepLength;
    }
    
    @Override
    public T save(T entity) {
        if (entity.getWeight() == null) {
        	entity.setWeight(findNextWeight());
        }
        return super.save(entity);
    }
    
    @Override
    public void down(ID fromId, ID toId) {
    	T from = findOne(fromId);
    	T to = findOne(toId);
    	if (from == null || to == null || from.equals(to)) {
            return;
        }
    	Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();
        
        T nextTo = findNextByWeight(toWeight);
        
        //如果toId的下一个是fromId 则直接交换顺序即可
        if (from.equals(nextTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }
        
        int minWeight = Math.min(fromWeight, toWeight);
        int maxWeight = Math.max(fromWeight, toWeight);
        //作为一个优化，减少不连续的weight
        int count = countByBetween(minWeight, maxWeight).intValue();
        if (count > 0 && count < 20) {
            List<T> moves = findByBetweenAndAsc(minWeight, maxWeight);
            if (fromWeight < toWeight) {
                Integer swapInteger = moves.get(count - 2).getWeight();
                for (int i = count - 2; i >= 1; i--) {
                    //最后一个的weight = toWeight;
                    moves.get(i).setWeight(moves.get(i - 1).getWeight());
                }
                moves.get(0).setWeight(swapInteger);
            } else {
                for (int i = 0; i <= count - 2; i++) {
                    moves.get(i).setWeight(moves.get(i + 1).getWeight());
                }
                moves.get(count - 1).setWeight(minWeight);
            }
            return;
        }
        
        T preTo = findPreByWeight(toWeight);
    	
        //计算新的权重
        int newWeight = 0;
        if (preTo == null) {
            newWeight = toWeight / 2;
        } else {
            newWeight = toWeight - (toWeight - preTo.getWeight()) / 2;

        }

        if (Math.abs(newWeight - toWeight) <= 1) {
            throw new IllegalStateException(String.format("up error, no enough weight :fromId:%d, toId:%d", fromId, toId));
        }
        from.setWeight(newWeight);
    }
    
    @Override
    public void up(ID fromId, ID toId) {
    	T from = findOne(fromId);
        T to = findOne(toId);
        if (from == null || to == null || from.equals(to)) {
            return;
        }
        Integer fromWeight = from.getWeight();
        Integer toWeight = to.getWeight();


        T preTo = findPreByWeight(toWeight);
        //如果toId的下一个是fromId 则直接交换顺序即可
        if (from.equals(preTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }

        int minWeight = Math.min(fromWeight, toWeight);
        int maxWeight = Math.max(fromWeight, toWeight);
        //作为一个优化，减少不连续的weight
        int count = countByBetween(minWeight, maxWeight).intValue();
        if (count > 0 && count < 20) {
            List<T> moves = findByBetweenAndDesc(minWeight, maxWeight);
            //123/124
            //5000 4000 3000

            if (fromWeight > toWeight) {
                Integer swapInteger = moves.get(count - 2).getWeight();
                for (int i = count - 2; i >= 1; i--) {
                    //最后一个的weight = toWeight;
                    moves.get(i).setWeight(moves.get(i - 1).getWeight());
                }
                moves.get(0).setWeight(swapInteger);
            } else {
                for (int i = 0; i <= count - 2; i++) {
                    moves.get(i).setWeight(moves.get(i + 1).getWeight());
                }
                moves.get(count - 1).setWeight(maxWeight);
            }
            return;
        }

        //如果toId的下一个是fromId 则直接交换顺序即可
        if (from.equals(preTo)) {
            from.setWeight(toWeight);
            to.setWeight(fromWeight);
            return;
        }
        T nextTo = findNextByWeight(toWeight);

        //计算新的权重
        int newWeight = 0;
        if (nextTo == null) {
            newWeight = toWeight + stepLength;
        } else {
            newWeight = toWeight + (nextTo.getWeight() - toWeight) / 2;
        }

        if (Math.abs(newWeight - toWeight) <= 1) {
            throw new IllegalStateException(String.format("down error, no enough weight :fromId:%d, toId:%d", fromId, toId));
        }
        from.setWeight(newWeight);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void reweight() {
    	int batchSize = 100;
        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        Pageable pageable = new PageRequest(0, batchSize, sort);
        Page<T> page = findAll(pageable);
        do {
            //doReweight需要requiresNew事务
            ((BaseMovableService) AopContext.currentProxy()).doReweight(page);

            RepositoryHelper.clear();

//            if (page.isLastPage()) {
            if (page.isLast()) {
                break;
            }

            pageable = new PageRequest((pageable.getPageNumber() + 1) * batchSize, batchSize, sort);

            page = findAll(pageable);

        } while (true);
    }
    
    @Override
    public void doReweight(Page<T> page) {
        int totalElements = (int) page.getTotalElements();
        List<T> moves = page.getContent();

        int firstElement = page.getNumber() * page.getSize();

        for (int i = 0; i < moves.size(); i++) {
            T move = moves.get(i);
            move.setWeight((totalElements - firstElement - i) * stepLength);
            update(move);
        }
    }
    
    @Override
    public T findPreByWeight(Integer weight) {
        Pageable pageable = new PageRequest(0, 1);
        Map<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("weight_lt", weight);
        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        Page<T> page = findAll(Searchable.newSearchable(searchParams).addSort(sort).setPage(pageable));

        if (page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }

    @Override
    public T findNextByWeight(Integer weight) {
        Pageable pageable = new PageRequest(0, 1);

        Map<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("weight_gt", weight);
        Sort sort = new Sort(Sort.Direction.ASC, "weight");
        Page<T> page = findAll(Searchable.newSearchable(searchParams).addSort(sort).setPage(pageable));

        if (page.hasContent()) {
            return page.getContent().get(0);
        }
        return null;
    }
    
    private Integer findNextWeight() {
        Searchable searchable = Searchable.newSearchable().setPage(0, 1).addSort(Sort.Direction.DESC, "weight");
        Page<T> page = findAll(searchable);

        if (!page.hasContent()) {
            return stepLength;
        }

        return page.getContent().get(0).getWeight() + stepLength;
    }
    
    private Long countByBetween(Integer minWeight, Integer maxWeight) {
        Map<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("weight_gte", minWeight);
        searchParams.put("weight_lte", maxWeight);
        return count(Searchable.newSearchable(searchParams));
    }
    
  //@Query(value = "from Move m where m.weight>=?1 and m.weight <= ?2 order by m.weight asc")
    List<T> findByBetweenAndAsc(Integer minWeight, Integer maxWeight) {
        Map<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("weight_gte", minWeight);
        searchParams.put("weight_lte", maxWeight);

        return findAllWithSort(Searchable.newSearchable(searchParams).addSort(Sort.Direction.ASC, "weight"));
    }


    List<T> findByBetweenAndDesc(Integer minWeight, Integer maxWeight) {
        Map<String, Object> searchParams = Maps.newHashMap();
        searchParams.put("weight_gte", minWeight);
        searchParams.put("weight_lte", maxWeight);

        Sort sort = new Sort(Sort.Direction.DESC, "weight");
        return findAllWithSort(Searchable.newSearchable(searchParams).addSort(sort));
    }
    
}
