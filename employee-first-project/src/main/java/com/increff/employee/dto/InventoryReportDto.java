package com.increff.employee.dto;
;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.model.ProductData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class InventoryReportDto {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandCategoryService brandCategoryService;

    public List<InventoryReportData> getAll() throws ApiException {

        List<InventoryPojo> list1 = inventoryService.getAll();
        HashMap<Integer,Integer>inventoryHashMap = new HashMap<Integer, Integer>();
        for(InventoryPojo data:list1)
        {
            inventoryHashMap.put(data.getId(),data.getInventory());
        }


        List<BrandCategoryPojo> brandCategoryPojoList = brandCategoryService.getAll();


        List<InventoryReportData> list2 = new ArrayList<>();
        for(BrandCategoryPojo pojo1 : brandCategoryPojoList)
        {
            List<ProductPojo> productPojoList = productService.getByBrandCategoryID(pojo1.getId());
            int inventory=0;
            InventoryReportData inventoryReportData = new InventoryReportData();
            for(ProductPojo pojo: productPojoList)
            {
                if(inventoryHashMap.containsKey(pojo.getId()))
                {
                    inventory+= inventoryHashMap.get(pojo.getId());
                }
            }
            inventoryReportData.setInventory(inventory);
            inventoryReportData.setBrand(pojo1.getBrand());
            inventoryReportData.setCategory(pojo1.getCategory());
            list2.add(inventoryReportData);
        }
        return list2;
    }
}
