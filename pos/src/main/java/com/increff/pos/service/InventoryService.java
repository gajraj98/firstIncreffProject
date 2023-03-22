package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    public void add(InventoryPojo inventoryPojo) {
        dao.insert(inventoryPojo);
    }

    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    public Long getTotalNoInventory() {

        return dao.getTotalNoInventory();
    }

    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }

    public List<InventoryPojo> getLimited(Integer pageNo) {

        return dao.selectLimited(pageNo);
    }

    public void update(int id, InventoryPojo inventoryPojo) throws ApiException {
        getCheck(id);
        InventoryPojo ex = dao.select(id);
        ex.setInventory(inventoryPojo.getInventory());
        dao.update(ex);
    }

    public void addInventory(int id, InventoryPojo p) throws ApiException {
        getCheck(id);
        InventoryPojo ex = dao.select(id);
        ex.setInventory(p.getInventory() + ex.getInventory());
        dao.update(ex);
    }

    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product doesn't exist");
        }
        return p;
    }

    public void reduceInventory(int quantity, int productId) throws ApiException {
        InventoryPojo inventoryPojo = get(productId);
        int currentInventory = inventoryPojo.getInventory();
        if (currentInventory < quantity) {
            throw new ApiException("MAX " + currentInventory + " inventory is available you exceed the limit ");
        }
        currentInventory = currentInventory - quantity;
        inventoryPojo.setInventory(currentInventory);
        update(productId, inventoryPojo);
    }

    public void addBackInventory(int quantity, int productId) throws ApiException {
        InventoryPojo inventoryPojo = get(productId);
        int currentInventory = inventoryPojo.getInventory();
        currentInventory = currentInventory + quantity;
        inventoryPojo.setInventory(currentInventory);
        update(productId, inventoryPojo);
    }
}
