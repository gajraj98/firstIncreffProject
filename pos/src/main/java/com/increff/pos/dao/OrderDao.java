package com.increff.pos.dao;

import com.increff.pos.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class OrderDao extends AbstractDao {

    private static final String selectId = "select p from OrderPojo p where id=:id";
    private static final String selectAll = "select p from OrderPojo p order by id desc";
    private static final String selectByDate = "select p from OrderPojo p where time >= :start and time <= :end";
    private static final String deleteId = "delete from OrderPojo p where id=:id";
    private static final String getTotalOrders = "select count(p) from OrderPojo p";

    public void insert(OrderPojo p) {
        em().persist(p);
    }

    public void delete(int id) {
        Query query = em().createQuery(deleteId);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public Long getTotalNoOrders() {
        TypedQuery<Long> query = getQuery(getTotalOrders, Long.class);
        Long rows = getSingle(query);
        return rows;
    }

    public OrderPojo select(int id) {
        TypedQuery<OrderPojo> query = getQuery(selectId, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderPojo> selectByDate(LocalDateTime start, LocalDateTime end) {
        TypedQuery<OrderPojo> query = getQuery(selectByDate, OrderPojo.class);
        query.setParameter("start", start);
        query.setParameter("end", end);
        return query.getResultList();
    }

    public List<OrderPojo> selectAll() {
        TypedQuery<OrderPojo> query = getQuery(selectAll, OrderPojo.class);
        return query.getResultList();
    }

    public List<OrderPojo> selectLimited(Integer pageNo) {
        TypedQuery<OrderPojo> query = getQuery(selectAll, OrderPojo.class);
        query.setFirstResult(10 * (pageNo - 1));
        query.setMaxResults(10);
        return query.getResultList();
    }
}
