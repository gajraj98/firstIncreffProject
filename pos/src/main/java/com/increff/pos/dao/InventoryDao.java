package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class InventoryDao extends AbstractDao {

    private static final String selectId = "select p from InventoryPojo p where id=:id";
    private static final String selectAll = "select p from InventoryPojo p order by id desc";
    private static final String getTotalInventory = "select count(p) from InventoryPojo p";

    public void insert(InventoryPojo p) {
        em().persist(p);
    }

    public InventoryPojo select(int id) {
        TypedQuery<InventoryPojo> query = getQuery(selectId, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(selectAll, InventoryPojo.class);
        return query.getResultList();
    }

    public List<InventoryPojo> selectLimited(Integer pageNo) {
        TypedQuery<InventoryPojo> query = getQuery(selectAll, InventoryPojo.class);
        query.setFirstResult(10 * (pageNo - 1));
        query.setMaxResults(10);
        return query.getResultList();
    }

    public Long getTotalNoInventory() {

        TypedQuery<Long> query = getQuery(getTotalInventory, Long.class);
        Long rows = getSingle(query);
        return rows;
    }

    public void update(InventoryPojo ex) {

    }
}
