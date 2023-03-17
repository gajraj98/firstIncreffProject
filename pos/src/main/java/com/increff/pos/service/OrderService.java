package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao dao;

    public void add(OrderPojo orderpojo, List<OrderItemPojo> orderItemPojos) throws ApiException {
        dao.insert(orderpojo);
    }

    public void markInvoiceGenerated(int orderId) {
        OrderPojo p = get(orderId);
        p.setInvoiceGenerated(1);
        return;
    }

    public void delete(int id) throws ApiException {
        dao.delete(id);
    }

    public Long getTotalNoOrders() {

        return dao.getTotalNoOrders();
    }

    public void update(int id) {
        OrderPojo p = dao.select(id);
        p.setLastUpdate(java.time.LocalDateTime.now());
    }

    public OrderPojo get(int id) {
        return dao.select(id);
    }

    public List<OrderPojo> getByDate(LocalDateTime start, LocalDateTime end) {
        return dao.selectByDate(start, end);
    }

    public List<OrderPojo> getAll() {
        return dao.selectAll();
    }

    public List<OrderPojo> getLimited(Integer pageNo) {
        return dao.selectLimited(pageNo);
    }


}
