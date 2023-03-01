package com.increff.employee.dto;

import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryReportdtoTest  extends AbstractUnitTest{
    @Autowired
    private ProductDto productDto;
    @Autowired
    private InventoryReportDto dto;
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
        f.setBarcode(barcode);
        f.setMrp(mrp);
        productDto.add(f);
    }
    @Test
    public void testGetAll() throws ApiException {
        List<InventoryReportData> list =  dto.getAll();
        int size  = list.size();
        assertEquals(1, size);
    }
}
