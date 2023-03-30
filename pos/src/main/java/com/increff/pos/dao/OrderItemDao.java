package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class OrderItemDao extends AbstractDao {

    private static final String selectOrderId = "select p from OrderItemPojo p where orderId=:orderId";
    private static final String selectId = "select p from OrderItemPojo p where id=:id";
    private static final String selectProductId = "select p from OrderItemPojo p where orderId=:orderId and productId=:productId";
    private static final String deleteOrderId = "delete from OrderItemPojo p where orderId=:orderId";
    private static final String deleteId = "delete from OrderItemPojo p where id=:id";

    public void insert(OrderItemPojo p) {
        em().persist(p);
    }

    public List<OrderItemPojo> selectAll(int orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(selectOrderId, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    public OrderItemPojo select(int id) {
        TypedQuery<OrderItemPojo> query = getQuery(selectId, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public OrderItemPojo select(int orderId, int productId) {
        TypedQuery<OrderItemPojo> query = getQuery(selectProductId, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        query.setParameter("productId", productId);
        return getSingle(query);
    }

    public void delete(int orderId) {
        Query query = em().createQuery(deleteOrderId);
        query.setParameter("orderId", orderId);
        query.executeUpdate();
    }

    public void deleteItem(int id) {
        Query query = em().createQuery(deleteId);
        query.setParameter("id", id);
        query.executeUpdate();
    }


}
