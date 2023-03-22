package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SalesReportDtoTest extends AbstractUnitTest {
    private final String brand = "Brand";
    private final String category = "Category";
    private final String name = "pen";
    private final double mrp = 200;
    private final String barcode = "a";
    @Autowired
    private OrderDto dto;
    @Autowired
    private SalesReportDto salesReportDto;
    @Autowired
    private OrderItemDto orderItemDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;
    @Autowired
    private InventoryDto inventoryDto;

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

        ProductForm f1 = new ProductForm();
        f1.setCategory(category);
        f1.setBrand(brand);
        f1.setName(name);
        f1.setBarcode("b");
        f1.setMrp(mrp);
        productDto.add(f1);

        List<InventoryData> inventoryDataList = inventoryDto.getAll();
        for (InventoryData data : inventoryDataList) {
            InventoryForm form = new InventoryForm();
            form.setBarcode(data.getBarcode());
            form.setId(data.getId());
            form.setInventory(5000);
            form.setName(data.getName());
            inventoryDto.update(data.getId(), form);
        }

        List<OrderForm> order = new ArrayList<>();
        OrderForm form1 = new OrderForm();
        form1.setBarcode("a");
        form1.setMrp(200);
        form1.setQuantity(300);
        order.add(form1);
        OrderForm form2 = new OrderForm();
        form2.setBarcode("b");
        form2.setMrp(20);
        form2.setQuantity(30);
        order.add(form2);
        dto.add(order);
    }

    @Test
    public void testGet() throws ApiException {
        SalesReportForm form = new SalesReportForm();
        form.setStartDate(LocalDate.now().toString());
        form.setEndDate(LocalDate.now().toString());
        form.setCategory(category);
        form.setBrand(brand);
        List<SalesReportData> salesReportData = salesReportDto.check(form);
        int revenue = 200 * 300 + 20 * 30;
        for (SalesReportData data : salesReportData) {
            assertEquals(revenue, data.getRevenue(), 0.01);
        }
    }

    @Test
    public void testGetByBrand() throws ApiException {
        SalesReportForm form = new SalesReportForm();
        form.setStartDate(LocalDate.now().toString());
        form.setEndDate(LocalDate.now().toString());
        form.setBrand(brand);
        List<SalesReportData> list = salesReportDto.check(form);
        int revenue = 200 * 300 + 20 * 30;
        for (SalesReportData data : list) {
            assertEquals(revenue, data.getRevenue(), 0.01);
        }
    }
}
