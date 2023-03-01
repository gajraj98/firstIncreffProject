package com.increff.employee.dto;

import com.increff.employee.model.*;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryDtoTest extends AbstractUnitTest{

    @Autowired
    private ProductDto productDto;
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
        List<InventoryData> list = dto.getAll();
        int size= list.size();
        assertEquals(1, size);
    }
    @Test
    public void testGet() throws ApiException {
        List<InventoryData> list = dto.getAll();
        for(InventoryData data: list)
        {
            InventoryData d = dto.get(data.getId());
            assertEquals(data.getId(), d.getId());
            assertEquals(data.getInventory(), d.getInventory());
        }
    }
    @Test
    public void testUpdateById() throws ApiException {
        List<InventoryData> list = dto.getAll();
        for(InventoryData data: list)
        {
            InventoryForm form = new InventoryForm();
            form.setId(data.getId());
            form.setInventory(20);
            form.setBarcode(data.getBarcode());
            dto.update(data.getId(),form);
            InventoryData d = dto.get(data.getId());
            assertEquals(20, d.getInventory());
        }
    }
    @Test
    public void testUpdateByBarcode() throws ApiException {
        List<InventoryData> list = dto.getAll();
        for(InventoryData data: list)
        {
            InventoryForm form = new InventoryForm();
            form.setId(data.getId());
            form.setInventory(20);
            form.setBarcode(data.getBarcode());
            dto.update(data.getBarcode(),form);
            InventoryData d = dto.get(data.getId());
            assertEquals(20, d.getInventory());
        }
    }
    @Test
    public void testConvertFormToPojo() throws ApiException {
        List<InventoryData> list = dto.getAll();
        for(InventoryData data: list)
        {
            InventoryForm form = new InventoryForm();
            form.setId(data.getId());
            form.setInventory(20);
            form.setBarcode(data.getBarcode());
            InventoryPojo p = dto.convert(form);
            assertEquals(p.getId(), form.getId());
            assertEquals(p.getInventory(), form.getInventory());
        }
    }
    @Test
    public void testConvertPojoToData() throws ApiException {
        List<InventoryData> list = dto.getAll();
        for(InventoryData data: list)
        {
            InventoryPojo p = new InventoryPojo();
            p.setId(data.getId());
            p.setInventory(20);
            ProductData productData = new ProductData();
            productData.setBarcode(data.getBarcode());
            InventoryData d = dto.convert(p,productData);
            assertEquals(d.getId(), p.getId());
            assertEquals(d.getInventory(), p.getInventory());
            assertEquals(d.getBarcode(), productData.getBarcode());
        }
    }
    @Test
    public void testDelete() throws ApiException {
        List<InventoryData> list = dto.getAll();
        for(InventoryData d:list) {
            productDto.delete(d.getId());
            List<InventoryData> list1 = dto.getAll();
            int size = list1.size();
            assertEquals(0, size);
        }
    }
}
