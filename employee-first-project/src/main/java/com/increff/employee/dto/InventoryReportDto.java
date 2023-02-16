package com.increff.employee.dto;
;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.model.ProductData;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class InventoryReportDto {

    @Autowired
    private InventoryDto inventoryDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;

    public List<InventoryReportData> getAll() throws ApiException {
//        fetch all inventory and store in hashmap
        List<InventoryData> list1 = inventoryDto.getAll();
        HashMap<Integer,Integer>inventoryHashMap = new HashMap<Integer, Integer>();
        for(InventoryData data:list1)
        {
            inventoryHashMap.put(data.getId(),data.getInventory());
        }

//        fetching all brandCategory
        List<BrandCategoryData> brandCategoryDataList = brandCategoryDto.getAll();

//        fetching products corresponding to a bradCategory
        List<InventoryReportData> list2 = new ArrayList<>();
        for(BrandCategoryData data : brandCategoryDataList)
        {
            List<ProductPojo> productPojoList = productDto.getByBrandCategoryID(data.getId());
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
            inventoryReportData.setBrand(data.getBrand());
            inventoryReportData.setCategory(data.getCategory());
            list2.add(inventoryReportData);
        }
        return list2;
    }
}
