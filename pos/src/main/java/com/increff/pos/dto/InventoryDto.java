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

    public void update(String barcode, InventoryForm f) throws ApiException {
        ProductPojo productPojo = productService.get(barcode);
        InventoryPojo p = convert(f);
        service.update(productPojo.getId(), p);
    }

    public void addInventory(String barcode, InventoryForm f) throws ApiException {
        ProductPojo data = productService.get(barcode);
        // todo you need to use these validations in update method also right?
        if (Math.floor(f.getInventory()) != f.getInventory()) {
            throw new ApiException("Inventory can't be in decimal");
        }
        if (f.getInventory() < 0) {
            throw new ApiException("Inventory can't be negative");
        }
        InventoryPojo p = convert(f);
        service.addInventory(data.getId(), p);
    }

    public void update(int id, InventoryForm f) throws ApiException {
        InventoryPojo p = convert(f);
        service.update(id, p);
    }

    public List<InventoryData> createList(List<InventoryPojo> list) throws ApiException {
        // todo rename list2 and list
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo p : list) {
            ProductPojo productPojo = productService.get(p.getId());
            list2.add(convert(p, productPojo));
        }
        return list2;
    }

}

