package com.increff.employee.service;

import com.increff.employee.dto.AbstractUnitTest;
import com.increff.employee.dto.BrandCategoryDto;
import com.increff.employee.dto.InventoryDto;
import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.*;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryServiceTest extends AbstractUnitTest {
    @Autowired
    private ProductDto productDto;
    @Autowired
    private InventoryService service;
    @Autowired
    private InventoryDto dto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;
    private final String brand="Brand";
    private final String category="Category";
    private final String name="pen";
    private final double mrp=200;
    private final String barcode="a";
    @Before
    public void setUp() throws ApiException {
        BrandCategoryForm brandCategoryForm = new BrandCategoryForm();
        brandCategoryForm.setBrand(brand);
        brandCategoryForm.setCategory(category);
        brandCategoryDto.add(brandCategoryForm);

        ProductForm f = new ProductForm();
        f.setCategory(category);
        f.setBrand(brand);
        f.setName(name);
        f.setBarcode("a");
        f.setMrp(mrp);
        productDto.add(f);
    }
    @Test
    public void  testGetAll() throws ApiException {
        List<InventoryPojo> list = service.getAll();
        int size= list.size();
        assertEquals(1, size);
    }
    @Test
    public void testGet() throws ApiException {
        List<InventoryPojo> list = service.getAll();
        for(InventoryPojo pojo: list)
        {
            InventoryData d = dto.get(pojo.getId());
            assertEquals(pojo.getId(), d.getId());
            assertEquals(pojo.getInventory(), d.getInventory());
        }
    }
    @Test
    public void testUpdateById() throws ApiException {
        List<InventoryPojo> list = service.getAll();
        for(InventoryPojo pojo: list)
        {
            pojo.setInventory(20);
            service.update(pojo.getId(),pojo);
            InventoryPojo d = service.get(pojo.getId());
            assertEquals(20, d.getInventory());
        }
    }
    @Test
    public void testUpdateByBarcode() throws ApiException {
        List<InventoryPojo> list = service.getAll();
        for(InventoryPojo pojo: list)
        {
             pojo.setInventory(20);
             InventoryData data = dto.get(pojo.getId());
            service.update(data.getBarcode(),pojo);
            InventoryPojo d = service.get(data.getId());
            assertEquals(20, d.getInventory());
        }
    }
    @Test
    public void testDelete()
    {
        List<InventoryPojo> list = service.getAll();
        for(InventoryPojo pojo: list)
        {
            service.delete(pojo.getId());
            List<InventoryPojo> list2 = service.getAll();
            int size= list2.size();
            assertEquals(0, size);
        }
    }
}
