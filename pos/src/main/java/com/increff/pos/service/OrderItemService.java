package com.increff.pos.service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;

    public void add(OrderItemPojo p) throws ApiException {
        OrderItemPojo pojo = dao.select(p.getOrderId(), p.getProductId());
        if (pojo != null) {
            if (pojo.getSellingPrice() != p.getSellingPrice()) {
                throw new ApiException("selling price can't be different to existing order");
            }
            pojo.setQuantity(p.getQuantity() + pojo.getQuantity());

        } else {
            dao.insert(p);
        }

    }

    public void delete(int orderId) throws ApiException {
        dao.delete(orderId);
    }

    public void deleteItem(int id) throws ApiException {
        dao.deleteItem(id);
    }

    public OrderItemPojo get(int orderId, int productId) {
        return dao.select(orderId, productId);
    }

    public List<OrderItemPojo> getAll(int orderId) {
        return dao.selectAll(orderId);
    }

    public OrderItemPojo get(int id) {
        return dao.select(id);
    }

    public void update(int id, OrderItemPojo pojo) throws ApiException {
        OrderItemPojo p = dao.select(id);
        p.setSellingPrice(pojo.getSellingPrice());
        p.setQuantity(pojo.getQuantity());
    }

}
