package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.model.BrandCategoryForm;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.ProductForm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convert;
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
            InventoryPojo p = convert(form);
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
            ProductPojo productData = new ProductPojo();
            productData.setBarcode(data.getBarcode());
            InventoryData d = convert(p,productData);
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
