package com.increff.pos.dto;


import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.service.*;
import javafx.util.Pair;
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
        if((salesReportForm.getBrand()==null)&&(salesReportForm.getCategory()==null))
        {
            return getAll();
        }
        else if(salesReportForm.getBrand()==null)
        {
            return getByBrand(salesReportForm);
        }
        else if(salesReportForm.getCategory()==null)
        {
            return getByCategory(salesReportForm);
        }
        else{
            return getByBrandAndCategory(salesReportForm);
        }
    }
    public List<SalesReportData> getByBrandAndCategory(SalesReportForm form) throws ApiException {

        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(form.getBrand(), form.getCategory());
        List<ProductPojo> productPojoList = productService.getByBrandCategoryID(brandCategoryPojo.getId());
        HashMap<Integer , ProductPojo> productPojoHashMap = new HashMap<Integer, ProductPojo>();
        for(ProductPojo productPojo:productPojoList)
        {
            productPojoHashMap.put(productPojo.getId(),productPojo);
        }
        List<OrderPojo> orderPojoList =dateConversion(form.getStartDate(),form.getEndDate());
        int quantity = 0;
        int totalRevenue = 0;
        List<OrderItemData> orderItemDataList = new ArrayList<>();
        for (OrderPojo pojo : orderPojoList) {
            List<OrderItemPojo> orderItemPojoList2 = orderItemService.getAll(pojo.getId());
                for (OrderItemPojo orderItemData : orderItemPojoList2) {
                    if (productPojoHashMap.containsKey(orderItemData.getProductId())) {
                        ProductPojo productPojo = productPojoHashMap.get(orderItemData.getProductId());
                        quantity+= orderItemData.getQuantity();
                        totalRevenue+= orderItemData.getQuantity()*orderItemData.getSellingPrice();
                    }
                }

        }
        List<SalesReportData> list = new ArrayList<>();
        list.add(FormTOData(form,quantity,totalRevenue));
        return list;
    }
    public List<SalesReportData> getByCategory(SalesReportForm reportForm) throws ApiException {
        List<SalesReportData> list = new ArrayList<>();
        List<BrandCategoryPojo> brandCategoryPojoList = brandCategoryService.getByCategory(reportForm.getCategory());
        for (BrandCategoryPojo brandCategoryPojo : brandCategoryPojoList) {
            SalesReportForm salesReportForm = createSalesForm( brandCategoryPojo,reportForm);
            List<SalesReportData> salesReportData = getByBrandAndCategory(salesReportForm);
            for(SalesReportData data:salesReportData)
            {
                list.add(data);
            }

        }
        return list;
    }
    public List<SalesReportData> getByBrand(SalesReportForm reportForm) throws ApiException {
        List<SalesReportData> list = new ArrayList<>();
        List<BrandCategoryPojo> brandCategoryPojoList = brandCategoryService.get(reportForm.getBrand());
        for (BrandCategoryPojo p : brandCategoryPojoList) {
            SalesReportForm salesReportForm = createSalesForm( p,reportForm);
            List<SalesReportData> salesReportData = getByBrandAndCategory(salesReportForm);
            for(SalesReportData data:salesReportData)
            {
                list.add(data);
            }

        }
        return list;
    }

    public  List<SalesReportData> getAll()
    {
        List<OrderPojo> orderPojoList = orderService.getAll();
        HashMap<Integer, Pair<String,String>> productHashMap = getProductHashMap();
        HashMap<String,Pair<SalesReportData,Pair<String,String>>> brandHashMap = new HashMap<>();
        for(OrderPojo orderPojo : orderPojoList)
        {
            List<OrderItemPojo> orderItemPojoList = orderItemService.getAll(orderPojo.getId());
            for(OrderItemPojo orderItemPojo:orderItemPojoList)
            {
                    int productId = orderItemPojo.getProductId();
                    Pair<String, String> brandCategoryPair = productHashMap.get(productId);
                    String brandCategory = brandCategoryPair.getKey() + "/" + brandCategoryPair.getValue();
                    // todo putIfAbsent
                    if (brandHashMap.containsKey(brandCategory) == true) {

                        SalesReportData salesReportData = brandHashMap.get(brandCategory).getKey();
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
                        salesReportData.setBrand(brandCategoryPair.getKey());
                        salesReportData.setCategory(brandCategoryPair.getValue());
                        brandHashMap.put(brandCategory, new Pair<>(salesReportData, new Pair<>(brandCategoryPair.getKey(), brandCategoryPair.getValue())));
                    }
            }
        }
        return createList(brandHashMap);
    }
    public HashMap<Integer, Pair<String,String>> getProductHashMap()
    {
        HashMap<Integer, Pair<String,String>> productHashMap = new HashMap<Integer, Pair<String, String>>();
        List<BrandCategoryPojo> brandCategoryPojoList = brandCategoryService.getAll();
        for(BrandCategoryPojo brandCategoryPojo:brandCategoryPojoList)
        {
            List <ProductPojo> productPojoList = productService.getByBrandCategoryID(brandCategoryPojo.getId());
            for(ProductPojo pojo:productPojoList)
            {
                productHashMap.put(pojo.getId(),new Pair<>(brandCategoryPojo.getBrand(),brandCategoryPojo.getCategory()));
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
    public List<SalesReportData> createList(  HashMap<String,Pair<SalesReportData,Pair<String,String>>> brandHashMap)
    {
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        for (Map.Entry<String,Pair<SalesReportData,Pair<String,String>>> entry : brandHashMap.entrySet()) {
            salesReportDataList.add(entry.getValue().getKey());
        }
        return salesReportDataList;
    }

    public SalesReportForm createSalesForm(BrandCategoryPojo brandCategoryPojo,SalesReportForm reportForm)
    {
        SalesReportForm salesReportForm = new SalesReportForm();
        salesReportForm.setCategory(brandCategoryPojo.getCategory());
        salesReportForm.setBrand(brandCategoryPojo.getBrand());
        salesReportForm.setStartDate(reportForm.getStartDate());
        salesReportForm.setEndDate(reportForm.getEndDate());
        return salesReportForm;
    }


}