package com.increff.pos.service;


import com.increff.pos.dto.*;
import com.increff.pos.model.*;
import com.increff.pos.pojo.DailyReportPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DailyReportServceTest extends AbstractUnitTest {
    private final String brand = "Brand";
    private final String category = "Category";
    private final String name = "pen";
    private final double mrp = 200;
    private final String barcode = "a";
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private DailyReportDto dto;
    @Autowired
    private DailyReportService service;
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
        orderDto.add(order);
        List<OrderData> list = orderDto.getAll();
        for (OrderData data : list) {
            orderDto.markInvoiceGenerated(data.getId());
        }
        dto.generateDailyReport();
    }

    @Test
    public void testGetAll() {
        List<DailyReportPojo> list = service.getAll();
        int size = list.size();
        assertEquals(1, size);
    }
}
