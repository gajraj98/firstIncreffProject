package com.increff.pos.service;


import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.dto.*;
import com.increff.pos.model.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderItemServiceTest extends AbstractUnitTest {
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private OrderItemService service;
    @Autowired
    private OrderItemDto dto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;
    @Autowired
    private InventoryDto inventoryDto;
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
        ProductForm f1 = new ProductForm();
        f1.setCategory(category);
        f1.setBrand(brand);
        f1.setName(name);
        f1.setBarcode("b");
        f1.setMrp(mrp);
        productDto.add(f1);

        List<InventoryData> inventoryDataList = inventoryDto.getAll();
        for(InventoryData data: inventoryDataList)
        {
            InventoryForm form = new InventoryForm();
            form.setBarcode(data.getBarcode());
            form.setId(data.getId());
            form.setInventory(5000);
            form.setName(data.getName());
            inventoryDto.update(data.getId(),form);
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
    }
    @Test
    public void testGetAll() throws ApiException {
        List<OrderData> list = orderDto.getAll();
        for(OrderData data: list)
        {
            List<OrderItemPojo> list1 = service.getAll(data.getId());
            int size = list1.size();
            assertEquals(2,size);
        }
    }
    @Test
    public void testGet() throws ApiException {
        List<OrderData> list = orderDto.getAll();
        for(OrderData data: list)
        {
            List<OrderItemPojo> list1 = service.getAll(data.getId());
            for(OrderItemPojo pojo: list1)
            {
                OrderItemPojo d = service.get(pojo.getId());
                assertEquals(d.getQuantity(),pojo.getQuantity());
                assertEquals(d.getProductId(),pojo.getProductId());
                assertEquals(d.getSellingPrice(),pojo.getSellingPrice(),0.01);
            }
        }
    }
    @Test
    public void testUpdate() throws ApiException {
        List<OrderData> list = orderDto.getAll();
        for (OrderData data : list) {
            List<OrderItemPojo> list1 = service.getAll(data.getId());
            for (OrderItemPojo pojo : list1) {
                OrderItemPojo p = new OrderItemPojo();
                p.setOrderId(data.getId());
                p.setSellingPrice(400);
                p.setQuantity(300);
                p.setOrderId(data.getId());
                service.update(pojo.getId(),p);
            }
        }
        List<OrderData> list2 = orderDto.getAll();
        for (OrderData data : list) {
            List<OrderItemPojo> list1 = service.getAll(data.getId());
            for (OrderItemPojo pojo : list1) {
                assertEquals(400, pojo.getSellingPrice(), 0.01);
                assertEquals(300, pojo.getQuantity());
            }
        }

    }
    @Test
    public void testDeleteItem() throws ApiException {
        List<OrderData> list = orderDto.getAll();
        for (OrderData data : list) {
            List<OrderItemPojo> list1 = service.getAll(data.getId());
            for(OrderItemPojo p : list1)
            {
                dto.deleteItem(p.getId());
            }
        }
        List<OrderData> list2 = orderDto.getAll();
        for (OrderData data : list2) {
            List<OrderItemPojo> list1 = service.getAll(data.getId());
            int size = list1.size();
            assertEquals(0,size);
        }
    }
}
