package com.increff.pos.dto;

import com.increff.pos.model.BrandCategoryForm;
import com.increff.pos.model.InventoryReportData;
import com.increff.pos.model.InventoryReportForm;
import com.increff.pos.model.ProductForm;
import com.increff.pos.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryReportdtoTest extends AbstractUnitTest {
    private final String brand = "Brand";
    private final String category = "Category";
    private final String name = "pen";
    private final double mrp = 200;
    private final String barcode = "a";
    @Autowired
    private ProductDto productDto;
    @Autowired
    private InventoryReportDto dto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;

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
        f.setBarcode(barcode);
        f.setMrp(mrp);
        productDto.add(f);
    }

    @Test
    public void testGet() throws ApiException {
        InventoryReportForm form = new InventoryReportForm();
        form.setBrand(brand);
        form.setCategory(category);
        List<InventoryReportData> list = dto.get(form);
        int size = list.size();
        assertEquals(1, size);
    }

    @Test
    public void testGetAll() throws ApiException {
        List<InventoryReportData> list = dto.getAll(1);
        int size = 1;
        assertEquals(size, 1);
    }

    @Test
    public void testGetTotal() throws ApiException {
        Long size = dto.getTotalNoInventory();
        Long ans = new Long(1);
        assertEquals(ans, size);
    }
}
