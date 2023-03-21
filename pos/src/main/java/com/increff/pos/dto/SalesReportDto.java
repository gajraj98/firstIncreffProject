package com.increff.pos.dto;


import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.service.*;
import com.increff.pos.util.BrandCategoryPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.pos.util.ConvertFunctions.*;
import static com.increff.pos.util.StringUtil.isEmpty;

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

    public List<SalesReportData> check(SalesReportForm salesReportForm) throws ApiException {
        List<OrderPojo> orderPojoList;
        List<BrandCategoryPojo> brandCategoryPojoList;
        if(isEmpty(salesReportForm.getStartDate())||isEmpty(salesReportForm.getEndDate()))
        {
             orderPojoList = orderService.getAll();
        }
        else{
             orderPojoList = dateConversion(salesReportForm.getStartDate(),salesReportForm.getEndDate());
        }
        if(isEmpty(salesReportForm.getBrand())&&isEmpty(salesReportForm.getCategory()))
        {
            brandCategoryPojoList = brandCategoryService.getAll();
            return get(orderPojoList,brandCategoryPojoList);
        }
        else if(isEmpty(salesReportForm.getBrand()))
        {
            brandCategoryPojoList = brandCategoryService.getByCategory(salesReportForm.getCategory());
            return get(orderPojoList,brandCategoryPojoList);
        }
        else if(isEmpty(salesReportForm.getCategory()))
        {
            brandCategoryPojoList = brandCategoryService.get(salesReportForm.getBrand());
            return get(orderPojoList,brandCategoryPojoList);
        }
        else{
           BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(salesReportForm.getBrand(),salesReportForm.getCategory());
           brandCategoryPojoList = new ArrayList<>();
           brandCategoryPojoList.add(brandCategoryPojo);
            return get(orderPojoList,brandCategoryPojoList);
        }

    }

    public  List<SalesReportData> get(List<OrderPojo> orderPojoList, List<BrandCategoryPojo> brandCategoryPojoList)
    {
        HashMap<Integer, BrandCategoryPair> productHashMap = getProductHashMap(brandCategoryPojoList);
        HashMap<String, SalesReportData> brandHashMap = new HashMap<>();
        for(OrderPojo orderPojo : orderPojoList)
        {
            List<OrderItemPojo> orderItemPojoList = orderItemService.getAll(orderPojo.getId());
            for(OrderItemPojo orderItemPojo:orderItemPojoList)
            {
                    int productId = orderItemPojo.getProductId();
                    if(productHashMap.containsKey(productId)) {
                        BrandCategoryPair brandCategoryPair = productHashMap.get(productId);
                        String brandCategory = brandCategoryPair.getBrand() + "/" + brandCategoryPair.getCategory();
                        if (brandHashMap.containsKey(brandCategory) == true) {
                            SalesReportData salesReportData = brandHashMap.get(brandCategory);
                            int quantity = salesReportData.getQuantity() + orderItemPojo.getQuantity();
                            salesReportData.setQuantity(quantity);
                            double revenue = salesReportData.getRevenue() + orderItemPojo.getQuantity() * orderItemPojo.getSellingPrice();
                            salesReportData.setRevenue(revenue);
                        } else {
                            SalesReportData salesReportData = new SalesReportData();
                            int quantity = orderItemPojo.getQuantity();
                            salesReportData.setQuantity(quantity);
                            double revenue = orderItemPojo.getQuantity() * orderItemPojo.getSellingPrice();
                            salesReportData.setRevenue(revenue);
                            salesReportData.setBrand(brandCategoryPair.getBrand());
                            salesReportData.setCategory(brandCategoryPair.getCategory());
                            brandHashMap.put(brandCategory, salesReportData);
                        }
                    }
            }
        }
        return createList(brandHashMap);
    }
    public HashMap<Integer, BrandCategoryPair> getProductHashMap(List<BrandCategoryPojo> brandCategoryPojoList)
    {
        HashMap<Integer, BrandCategoryPair> productHashMap = new HashMap<Integer, BrandCategoryPair>();
        for(BrandCategoryPojo brandCategoryPojo:brandCategoryPojoList)
        {
            List <ProductPojo> productPojoList = productService.getByBrandCategoryID(brandCategoryPojo.getId());
            for(ProductPojo pojo:productPojoList)
            {
                BrandCategoryPair brandCategoryPair = new BrandCategoryPair();
                brandCategoryPair.setBrand(brandCategoryPojo.getBrand());
                brandCategoryPair.setCategory(brandCategoryPojo.getCategory());
                productHashMap.put(pojo.getId(),brandCategoryPair);
            }
        }
        return productHashMap;
    }
    public List<OrderPojo> dateConversion(String startDate,String endDate){
        LocalDate startingDate = convert(startDate);
        LocalDateTime start = startingDate.atStartOfDay();
        LocalDate endingDate = convert(endDate);
        LocalDateTime end = endingDate.atTime(LocalTime.MAX);
        List<OrderPojo> orderPojoList = orderService.getByDate(start, end);
        return orderPojoList;
    }
    public List<SalesReportData> createList(  HashMap<String,SalesReportData> brandHashMap)
    {
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        for (Map.Entry<String,SalesReportData> entry : brandHashMap.entrySet()) {
            salesReportDataList.add(entry.getValue());
        }
        return salesReportDataList;
    }

}