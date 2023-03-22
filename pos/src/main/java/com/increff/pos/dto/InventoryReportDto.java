package com.increff.pos.dto;

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

import java.util.ArrayList;
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
        List<InventoryReportData> ans = new ArrayList<>();
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(from.getBrand(), from.getCategory());
        List<ProductPojo> productPojoList = productService.getByBrandCategoryID(brandCategoryPojo.getId());
        for (ProductPojo p : productPojoList) {
            InventoryPojo inventoryPojo = inventoryService.get(p.getId());
            InventoryReportData data = new InventoryReportData();
            data.setBarcode(p.getBarcode());
            data.setInventory(inventoryPojo.getInventory());
            data.setName(p.getName());
            data.setCategory(brandCategoryPojo.getCategory());
            data.setBrand(brandCategoryPojo.getBrand());
            ans.add(data);
        }
        return ans;
    }

    // todo use of pageNo?
    public List<InventoryReportData> getAll(Integer pageNo) throws ApiException {
        List<InventoryReportData> ans = new ArrayList<>();
        List<BrandCategoryPojo> brandCategoryPojoList = brandCategoryService.getAll();
        for (BrandCategoryPojo brandCategoryPojo : brandCategoryPojoList) {
            List<ProductPojo> productPojoList = productService.getByBrandCategoryID(brandCategoryPojo.getId());
            for (ProductPojo p : productPojoList) {
                InventoryPojo inventoryPojo = inventoryService.get(p.getId());
                InventoryReportData data = new InventoryReportData();
                data.setBarcode(p.getBarcode());
                data.setInventory(inventoryPojo.getInventory());
                data.setName(p.getName());
                data.setCategory(brandCategoryPojo.getCategory());
                data.setBrand(brandCategoryPojo.getBrand());
                if (inventoryPojo.getInventory() > 0)
                    ans.add(data);
            }
        }
        return ans;
    }

    public Long getTotalNoInventory() {
        return brandCategoryService.getTotalNoBrands();
    }
}
