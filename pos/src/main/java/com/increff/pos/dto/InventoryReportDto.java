package com.increff.pos.dto;
;
import com.increff.pos.model.InventoryReportData;
import com.increff.pos.model.InventoryReportForm;
import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandCategoryService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

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

    public List<InventoryReportData> get(InventoryReportForm from) throws ApiException {
        List<InventoryPojo> list1 = inventoryService.getAll();
        HashMap<Integer,Integer>inventoryHashMap = new HashMap<Integer, Integer>();
        for(InventoryPojo data:list1)
        {
            inventoryHashMap.put(data.getId(),data.getInventory());
        }
        System.out.println(from.getBrand());

        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(from.getBrand(),from.getCategory());


        List<InventoryReportData> list2 = new ArrayList<>();

            List<ProductPojo> productPojoList = productService.getByBrandCategoryID(brandCategoryPojo.getId());
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
            inventoryReportData.setBrand(brandCategoryPojo.getBrand());
            inventoryReportData.setCategory(brandCategoryPojo.getCategory());
            list2.add(inventoryReportData);

        return list2;
    }
    public List<InventoryReportData> getAll(Integer pageNo) throws ApiException {
        List<InventoryPojo> list1 = inventoryService.getAll();
        HashMap<Integer,Integer>inventoryHashMap = new HashMap<Integer, Integer>();
        for(InventoryPojo data:list1)
        {
            inventoryHashMap.put(data.getId(),data.getInventory());
        }
        List<BrandCategoryPojo> brandCategoryPojolist = brandCategoryService.getLimited(pageNo);


        List<InventoryReportData> list2 = new ArrayList<>();
        for(BrandCategoryPojo brandCategoryPojo:brandCategoryPojolist) {
            List<ProductPojo> productPojoList = productService.getByBrandCategoryID(brandCategoryPojo.getId());
            int inventory = 0;
            InventoryReportData inventoryReportData = new InventoryReportData();
            for (ProductPojo pojo : productPojoList) {
                if (inventoryHashMap.containsKey(pojo.getId())) {
                    inventory += inventoryHashMap.get(pojo.getId());
                }
            }
            inventoryReportData.setInventory(inventory);
            inventoryReportData.setBrand(brandCategoryPojo.getBrand());
            inventoryReportData.setCategory(brandCategoryPojo.getCategory());
            list2.add(inventoryReportData);
        }
        return list2;
    }
    public Long getTotalNoInventory() {
        return brandCategoryService.getTotalNoBrands();
    }
}
