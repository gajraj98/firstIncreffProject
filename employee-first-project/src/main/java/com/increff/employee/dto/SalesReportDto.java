package com.increff.employee.dto;

import com.increff.employee.model.*;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesReportDto {
    @Autowired
    private BrandCategoryDto brandCategoryDto;
    @Autowired
    private ProductDto productDto;
    @Autowired OrderDto orderDto;
    @Autowired
    private OrderItemDto orderItemDto;

    public SalesReportData get(SalesReportForm form) throws ApiException {

        form.setBrand(StringUtil.toLowerCase(form.getBrand()));
        form.setCategory(StringUtil.toLowerCase(form.getCategory()));

//            fetching brandCategoryPojo for brandCategoryID
        BrandCategoryPojo brandCategoryPojo = brandCategoryDto.get(form.getBrand(),form.getCategory());
        List<ProductPojo> productPojoList = productDto.getByBrandCategoryID(brandCategoryPojo.getId());

//        fetch all orders between start to end date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(form.getStartDate(), formatter);
        LocalDateTime start = startDate.atStartOfDay();
        LocalDate endDate = LocalDate.parse(form.getEndDate(), formatter);
        LocalDateTime end =endDate.atTime(LocalTime.MAX);
        List<OrderPojo> orderPojoList = orderDto.getByDate(start,end);

//        fetching all orderItems and storing them in list
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for(OrderPojo pojo:orderPojoList)
        {
            List<OrderItemData> orderItemDataList2 = orderItemDto.getAll(pojo.getId());
            for(ProductPojo productPojo : productPojoList)
            {
                for(OrderItemData orderItemData: orderItemDataList2)
                {
                    if(productPojo.getId() == orderItemData.getProductId())
                    {
                        orderItemDataList.add(orderItemData);
                    }
                }
            }
        }

//        calculate revenue corresponding to category
        int quantity=0;
         int totalRevenue=0;
         for(OrderItemData data: orderItemDataList)
         {
             quantity+= data.getQuantity();
             totalRevenue+= data.getSellingPrice()*data.getQuantity();
         }
         SalesReportData salesReportData = new SalesReportData();
         salesReportData.setCategory(form.getCategory());
         salesReportData.setQuantity(quantity);
         salesReportData.setRevenue(totalRevenue);
        return salesReportData;
    }
}
