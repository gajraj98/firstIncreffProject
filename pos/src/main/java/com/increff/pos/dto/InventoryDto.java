package com.increff.pos.dto;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convert;

@Repository
public class InventoryDto {

    @Autowired
    private InventoryService service;
    @Autowired
    private ProductService productService;

    public InventoryData get(int id) throws ApiException {
        InventoryPojo p = service.get(id);
        ProductPojo productPojo = productService.get(p.getId());
        return convert(p, productPojo);
    }

    public Long getTotalNoInventory() {

        return service.getTotalNoInventory();
    }

    public List<InventoryData> getAll() throws ApiException {

        return createList(service.getAll());
    }

    public List<InventoryData> getLimited(Integer pageNo) throws ApiException {
        return createList(service.getLimited(pageNo));
    }

    public void update(String barcode, InventoryForm inventoryForm) throws ApiException {
        ProductPojo productPojo = productService.get(barcode);
        InventoryPojo p = convert(inventoryForm);
        service.update(productPojo.getId(), p);
    }

    public void addInventory(String barcode, InventoryForm inventoryForm) throws ApiException {
        checkEmpty(inventoryForm);
        ProductPojo data = productService.get(barcode);
        InventoryPojo p = convert(inventoryForm);
        service.addInventory(data.getId(), p);
    }

    public void update(int id, InventoryForm inventoryForm) throws ApiException {
        checkEmpty(inventoryForm);
        InventoryPojo p = convert(inventoryForm);
        service.update(id, p);
    }

    public List<InventoryData> createList(List<InventoryPojo> list) throws ApiException {
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            ProductPojo productPojo = productService.get(p.getId());
            inventoryDataList.add(convert(p, productPojo));
        }
        return inventoryDataList;
    }
    public void checkEmpty(InventoryForm inventoryForm) throws ApiException {
        if (Math.floor(inventoryForm.getInventory()) != inventoryForm.getInventory()) {
            throw new ApiException("Inventory can't be in decimal");
        }
        if (inventoryForm.getInventory() < 0) {
            throw new ApiException("Inventory can't be negative");
        }
    }
}

