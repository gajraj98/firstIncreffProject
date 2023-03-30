package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convertDataTOForm;
import static com.increff.pos.util.ConvertFunctions.convertToOrderItem;
import static org.junit.Assert.assertEquals;

public class OrderDtoTest extends AbstractUnitTest {
    private final String brand = "Brand";
    private final String category = "Category";
    private final String name = "pen";
    private final double mrp = 200;
    private final String barcode = "a";
    @Autowired
    private OrderDto dto;
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

        dto.add(order);
    }

    @Test
    public void testAddCheckApiException()
    {
        try{
            List<OrderForm> order = new ArrayList<>();
            OrderForm form1 = new OrderForm();
            form1.setBarcode("a");
            form1.setMrp(200);
            form1.setQuantity(300);
            order.add(form1);
            OrderForm form2 = new OrderForm();
            form2.setBarcode("a");
            form2.setMrp(20);
            form2.setQuantity(30);
            order.add(form2);
            dto.add(order);
        }
        catch (ApiException e)
        {
            assertEquals("Selling price of two same products can't be different",e.getMessage());
        }

    }
    @Test
    public void testAddCheckHashMap()
    {
        try{
            List<OrderForm> order = new ArrayList<>();
            OrderForm form1 = new OrderForm();
            form1.setBarcode("a");
            form1.setMrp(200);
            form1.setQuantity(300);
            order.add(form1);
            OrderForm form2 = new OrderForm();
            form2.setBarcode("a");
            form2.setMrp(200);
            form2.setQuantity(30);
            order.add(form2);
            dto.add(order);
        }
        catch (ApiException e)
        {
            assertEquals("Selling price of two same products can't be different",e.getMessage());
        }

    }
    @Test
    public void testGetAll() {
        List<OrderData> list = dto.getAll();
        int size = list.size();
        assertEquals(1, size);
    }

    @Test
    public void testGetLimited() {
        List<OrderData> list = dto.getLimited(1);
        int size = list.size();
        assertEquals(1, size);
    }

    @Test
    public void testGetTotal() {
        Long ans = dto.getTotalNoOrders();
        Long size = new Long(1);
        assertEquals(size, ans);
    }

    @Test
    public void testGetById() {
        List<OrderData> list = dto.getAll();
        for (OrderData data : list) {
            OrderData d = dto.get(data.getId());
            assertEquals(d.getId(), data.getId());
            assertEquals(d.getTime(), data.getTime());
        }
    }

    @Test
    public void testGetByDate() {
        List<OrderData> list = dto.getAll();
        for (OrderData data : list) {
            List<OrderPojo> list1 = dto.getByDate(data.getTime(), data.getTime());
            int size = list1.size();
            assertEquals(1, size);
        }
    }

    //    @Test
//    public void testGetByTime()
//    {
//        List<OrderData> list = dto.getAll();
//        for (OrderData data:list)
//        {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedDateTime = data.getTime().format(formatter);
//            LocalDateTime dateTime = LocalDateTime.parse(formattedDateTime, formatter);
//            OrderPojo d = dto.get(String.valueOf(dateTime));
////            OrderPojo d = dto.get(formattedDateTime);
//            assertEquals(d.getId(),data.getId());
//            assertEquals(d.getTime(),data.getTime());
//        }
//    }
    @Test(expected = ApiException.class)
    public void testMarkInvoiceGenerated() throws ApiException {
        List<OrderData> list = dto.getAll();
        for (OrderData data : list) {
            dto.markInvoiceGenerated(data.getId());
            List<OrderData> list1 = dto.getAll();
            for (OrderData d : list) {
                dto.checkInvoiceGenerated(d.getId());
            }
        }
    }

    @Test
    public void testDelete() throws ApiException {
        List<OrderData> list = dto.getAll();
        for (OrderData data : list) {
            dto.delete(data.getId());
            List<OrderPojo> list1 = dto.getByDate(data.getTime(), data.getTime());
            int size = list1.size();
            assertEquals(0, size);
        }
    }

    @Test
    public void testConvertPojoToData() {
        List<OrderData> list = dto.getAll();
        for (OrderData data : list) {
            OrderPojo p = new OrderPojo();
            p.setTime(data.getTime());
            p.setId(data.getId());
            OrderData d = convertDataTOForm(p);
            assertEquals(d.getTime(), p.getTime());
            assertEquals(d.getId(), p.getId());
        }
    }

    @Test
    public void testConvertFormToItemPojo() throws ApiException {
        List<OrderData> list = dto.getAll();
        for (OrderData data : list) {
            List<OrderItemData> orderItemData = orderItemDto.getAll(data.getId());
            for (OrderItemData data1 : orderItemData) {
                OrderForm f = new OrderForm();
                f.setQuantity(data1.getQuantity());
                f.setBarcode(data1.getBarcode());
                f.setMrp(data1.getSellingPrice());
                ProductData data2 = productDto.get(barcode);
                OrderItemPojo p = convertToOrderItem(f, data2.getId());
                assertEquals(p.getQuantity(), f.getQuantity());
                assertEquals(p.getSellingPrice(), f.getMrp(), 0.01);
            }
        }
    }


}
