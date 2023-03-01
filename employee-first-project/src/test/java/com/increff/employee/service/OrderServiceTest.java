package com.increff.employee.service;

import com.increff.employee.dto.*;
import com.increff.employee.model.*;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderServiceTest extends AbstractUnitTest {
    @Autowired
    private OrderDto dto;
    @Autowired
    private OrderService service;
    @Autowired
    private OrderItemDto orderItemDto;
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

        List<OrderItemPojo> orderItem = new ArrayList<>();
        OrderItemPojo p = new OrderItemPojo();
        OrderItemPojo p1 = new OrderItemPojo();
        ProductPojo data = productDto.get("a");
        p.setProductId(data.getId());
        p.setQuantity(300);
        p.setSellingPrice(200);
        orderItem.add(p);
        ProductPojo data1 = productDto.get("b");
        p1.setProductId(data1.getId());
        p1.setQuantity(30);
        p1.setSellingPrice(20);
        orderItem.add(p1);

        OrderPojo pojo = new OrderPojo();
        pojo.setTime(java.time.LocalDateTime.now());
        service.add(pojo,orderItem);
    }
    @Test
    public void testGetAll()
    {
        List<OrderPojo> list = service.getAll();
        int size = list.size();
        assertEquals(1,size);
    }
    @Test
    public void testGetById()
    {
        List<OrderPojo> list = service.getAll();
        for (OrderPojo pojo:list)
        {
            OrderPojo p = service.get(pojo.getId());
            assertEquals(p.getId(),pojo.getId());
            assertEquals(p.getTime(),p.getTime());
        }
    }
    @Test
    public void testGetByDate()
    {
        List<OrderPojo> list = service.getAll();
        for (OrderPojo pojo:list)
        {
            List<OrderPojo> list1 = service.getByDate(pojo.getTime(),pojo.getTime());
            int size = list1.size();
            assertEquals(1,size);
        }
    }

    public void testGetByTime()
    {
        List<OrderPojo> list = service.getAll();
        for (OrderPojo pojo:list)
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = pojo.getTime().format(formatter);
            OrderPojo d = service.get(formattedDateTime);
            assertEquals(d.getId(),pojo.getId());
            assertEquals(d.getTime(),pojo.getTime());
        }
    }
    @Test(expected = ApiException.class)
    public void testMarkInvoiceGenerated() throws ApiException {
        List<OrderPojo> list = service.getAll();
        for (OrderPojo pojo:list)
        {
            service.markInvoiceGenerated(pojo.getId());
            List<OrderPojo> list1 = service.getAll();
            for (OrderPojo p:list){
                dto.isInvoiceGenerated(p.getId());
            }
        }
    }
    @Test
    public void testDelete() throws ApiException {
        List<OrderPojo> list = service.getAll();
        for (OrderPojo pojo:list)
        {
            dto.delete(pojo.getId());
            List<OrderPojo> list1 = dto.getByDate(pojo.getTime(),pojo.getTime());
            int size = list1.size();
            assertEquals(0,size);
        }
    }

}
