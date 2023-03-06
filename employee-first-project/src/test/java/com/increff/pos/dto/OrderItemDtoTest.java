package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderItemDtoTest extends AbstractUnitTest{
    @Autowired
    private OrderDto orderDto;
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
            List<OrderItemData> list1 = dto.getAll(data.getId());
            int size = list1.size();
            assertEquals(2,size);
        }
    }
    @Test
    public void testGetAllCheckInvoiceBefore() throws ApiException {
        List<OrderData> list = orderDto.getAll();
        for(OrderData data: list)
        {
            List<OrderItemData> list1 = dto.getAllCheckInvoiceBefore(data.getId());
            int size = list1.size();
            assertEquals(2,size);
        }
    }
    @Test
    public void testGet() throws ApiException {
        List<OrderData> list = orderDto.getAll();
        for(OrderData data: list)
        {
            List<OrderItemData> list1 = dto.getAllCheckInvoiceBefore(data.getId());
            for(OrderItemData data1: list1)
            {
                OrderItemData d = dto.get(data1.getId());
                assertEquals(d.getBarcode(),data1.getBarcode());
                assertEquals(d.getQuantity(),data1.getQuantity());
                assertEquals(d.getName(),data1.getName());
                assertEquals(d.getProductId(),data1.getProductId());
                assertEquals(d.getSellingPrice(),data1.getSellingPrice(),0.01);
            }
        }
    }
    @Test
    public void testUpdate() throws ApiException {
        List<OrderData> list = orderDto.getAll();
        for (OrderData data : list) {
            List<OrderItemData> list1 = dto.getAll(data.getId());
            for (OrderItemData data1 : list1) {
                OrderForm form = new OrderForm();
                form.setOrderId(data.getId());
                form.setMrp(400);
                form.setQuantity(300);
                form.setBarcode(data1.getBarcode());
                dto.update(data1.getId(),form);
            }
        }
        List<OrderData> list2 = orderDto.getAll();
        for (OrderData data : list) {
            List<OrderItemData> list1 = dto.getAll(data.getId());
            for (OrderItemData data1 : list1) {
                assertEquals(400, data1.getSellingPrice(), 0.01);
                assertEquals(300, data1.getQuantity());
            }
        }

    }
    @Test
    public void testDeleteItem() throws ApiException {
        List<OrderData> list = orderDto.getAll();
        for (OrderData data : list) {
           List<OrderItemData> list1 = dto.getAll(data.getId());
           for(OrderItemData d : list1)
           {
               dto.deleteItem(d.getId());
           }
        }
        List<OrderData> list2 = orderDto.getAll();
        for (OrderData data : list2) {
            List<OrderItemData> list1 = dto.getAll(data.getId());
            int size = list1.size();
            assertEquals(0,size);
        }
    }
}
