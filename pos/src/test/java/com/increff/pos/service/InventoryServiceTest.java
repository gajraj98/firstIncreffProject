package com.increff.pos.service;

import com.increff.pos.dto.AbstractUnitTest;
import com.increff.pos.dto.BrandCategoryDto;
import com.increff.pos.dto.InventoryDto;
import com.increff.pos.dto.ProductDto;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.model.BrandCategoryForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.ProductForm;
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
    public void  testGetLimited() throws ApiException {
        List<InventoryPojo> list = service.getLimited(1);
        int size= list.size();
        assertEquals(1, size);
    }

    @Test
    public void  testGetTotal() throws ApiException {
        Long ans = service.getTotalNoInventory();
        Long size= new Long(1);
        assertEquals(size,ans);
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


}
