package com.increff.pos.dto;


import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.StringUtil;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.SalesReportAllCategoryForm;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convertToOrderItemData;

@Repository
public class SalesReportDto {

    @Autowired
    private BrandCategoryService brandCategoryService;

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;


    public List<SalesReportData> get(SalesReportForm form) throws ApiException {

        if(form.getBrand().length()==0)
        {
            return getAll();
        }
        else if(form.getCategory().length()==0)
        {
            SalesReportAllCategoryForm form1 = new SalesReportAllCategoryForm();
            form1.setBrand(form.getBrand());
            form1.setStartDate(form.getStartDate());
            form1.setEndDate(form.getEndDate());
            return get(form1);
        }
        form.setBrand(StringUtil.toLowerCase(form.getBrand()));
        form.setCategory(StringUtil.toLowerCase(form.getCategory()));

//            fetching brandCategoryPojo for brandCategoryID
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(form.getBrand(), form.getCategory());
        List<ProductPojo> productPojoList = productService.getByBrandCategoryID(brandCategoryPojo.getId());

//        fetch all orders between start to end date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(form.getStartDate(), formatter);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDate endDate = LocalDate.parse(form.getEndDate(), formatter);
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        List<OrderPojo> orderPojoList = orderService.getByDate(start, end);

//        fetching all orderItems and storing them in list
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for (OrderPojo pojo : orderPojoList) {
            List<OrderItemPojo> orderItemPojoList2 = orderItemService.getAll(pojo.getId());
            for (ProductPojo productPojo : productPojoList) {
                for (OrderItemPojo orderItemData : orderItemPojoList2) {
                    if (productPojo.getId() == orderItemData.getProductId()) {
                        ProductPojo productPojo1 = productService.get(orderItemData.getProductId());
                        orderItemDataList.add(convertToOrderItemData(orderItemData, productPojo1.getBarcode(), productPojo1.getName()));
                    }
                }
            }
        }

//        calculate revenue corresponding to category
        int quantity = 0;
        int totalRevenue = 0;
        for (OrderItemData data : orderItemDataList) {
            quantity += data.getQuantity();
            totalRevenue += data.getSellingPrice() * data.getQuantity();
        }
        SalesReportData salesReportData = new SalesReportData();
        salesReportData.setCategory(form.getCategory());
        salesReportData.setQuantity(quantity);
        salesReportData.setRevenue(totalRevenue);
        salesReportData.setBrand(form.getBrand());
        List<SalesReportData> list = new ArrayList<>();
        list.add(salesReportData);
        return list;
    }

    public List<SalesReportData> get(SalesReportAllCategoryForm form) throws ApiException {
        List<SalesReportData> list = new ArrayList<>();
        List<BrandCategoryPojo> brandCategoryPojoList = brandCategoryService.get(form.getBrand());
        for (BrandCategoryPojo p : brandCategoryPojoList) {
            SalesReportForm salesReportForm = new SalesReportForm();
            salesReportForm.setCategory(p.getCategory());
            salesReportForm.setBrand(p.getBrand());
            salesReportForm.setStartDate(form.getStartDate());
            salesReportForm.setEndDate(form.getEndDate());
            List<SalesReportData> salesReportData = get(salesReportForm);
            for(SalesReportData data:salesReportData)
            {
                list.add(data);
            }

        }
        return list;
    }

    public List<SalesReportData> getAll() throws ApiException {
        List<SalesReportData> list = new ArrayList<>();
        List<BrandCategoryPojo> brandCategoryPojoList = brandCategoryService.getAll();
        for (BrandCategoryPojo pojo : brandCategoryPojoList) {
                SalesReportForm salesReportForm = new SalesReportForm();
                salesReportForm.setCategory(pojo.getCategory());
                salesReportForm.setBrand(pojo.getBrand());
                LocalDate currentDate = LocalDate.now();
                String currentDateStr = currentDate.toString();
                String startDate  = "2023-02-03";
                salesReportForm.setStartDate(startDate);
                salesReportForm.setEndDate(currentDateStr);
                List<SalesReportData> salesReportData = get(salesReportForm);
                for(SalesReportData data:salesReportData)
                {
                    if(data.getRevenue()>0)
                    list.add(data);
                }

        }
        return list;
    }
}