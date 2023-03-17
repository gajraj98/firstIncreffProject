package com.increff.pos.util;

import com.increff.pos.model.*;
import com.increff.pos.pojo.*;
import com.increff.pos.service.ApiException;

import java.util.ArrayList;
import java.util.List;


public class ConvertFunctions {

    public static BrandCategoryPojo convert(BrandCategoryForm f) {
        BrandCategoryPojo p = new BrandCategoryPojo();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }

    public static BrandCategoryData convert(BrandCategoryPojo p) {
        BrandCategoryData d = new BrandCategoryData();
        d.setBrand(p.getBrand());
        d.setId(p.getId());
        d.setCategory(p.getCategory());
        return d;
    }

    public static InventoryPojo convert(InventoryForm f) {
        InventoryPojo p = new InventoryPojo();
        p.setId(f.getId());
        p.setInventory(f.getInventory());
        return p;
    }

    public static InventoryData convert(InventoryPojo p, ProductPojo productData) {
        InventoryData d = new InventoryData();
        d.setId(p.getId());
        d.setBarcode(productData.getBarcode());
        d.setName(productData.getName());
        d.setInventory(p.getInventory());
        return d;
    }

    public static OrderItemPojo convertToOrderItem1(OrderForm f, Integer id) {
        OrderItemPojo pItem = new OrderItemPojo();
        pItem.setProductId(id);
        pItem.setSellingPrice(f.getMrp());
        pItem.setQuantity(f.getQuantity());
        return pItem;
    }

    public static OrderData convertDataTOForm(OrderPojo p) {
        OrderData d = new OrderData();
        d.setId(p.getId());
        d.setTime(p.getTime());
        d.setIsInvoiceGenerated(p.getInvoiceGenerated());
        if (p.getLastUpdate() == null) p.setLastUpdate(p.getTime());
        d.setLastUpdate(p.getLastUpdate());
        return d;
    }

    public static OrderItemData convertToOrderItemData(OrderItemPojo p, String barcode, String name) throws ApiException {
        OrderItemData data = new OrderItemData();
        data.setId(p.getId());
        data.setName(name);
        data.setQuantity(p.getQuantity());
        data.setSellingPrice(p.getSellingPrice());
        data.setProductId(p.getProductId());
        data.setBarcode(barcode);
        return data;
    }

    public static OrderItemPojo convertToOrderItem(OrderForm f, Integer productId) throws ApiException {
        OrderItemPojo pItem = new OrderItemPojo();
        pItem.setProductId(productId);
        pItem.setSellingPrice(f.getMrp());
        pItem.setQuantity(f.getQuantity());
        pItem.setOrderId(f.getOrderId());
        return pItem;
    }

    public static ProductPojo convert(ProductForm f, BrandCategoryPojo brandCategoryPojo) throws ApiException {

        ProductPojo p = new ProductPojo();
        p.setMrp(f.getMrp());
        p.setName(f.getName());
        p.setBarcode(f.getBarcode());
        p.setBrandCategoryId(brandCategoryPojo.getId());
        return p;
    }

    public static ProductPojo convert(ProductForm f) throws ApiException {

        ProductPojo p = new ProductPojo();
        p.setMrp(f.getMrp());
        p.setName(f.getName());
        return p;
    }

    public static ProductData convert(ProductPojo p, BrandCategoryPojo brandCategoryPojo) {

        ProductData d = new ProductData();
        d.setName(p.getName());
        d.setId(p.getId());
        d.setMrp(p.getMrp());
        d.setBrand(brandCategoryPojo.getBrand());
        d.setBarcode(p.getBarcode());
        d.setCategory(brandCategoryPojo.getCategory());
        return d;
    }

    public static UserData convert(UserPojo p) {
        UserData d = new UserData();
        d.setEmail(p.getEmail());
        d.setRole(p.getRole());
        d.setId(p.getId());
        return d;
    }

    public static UserPojo convert(UserForm f) {
        UserPojo p = new UserPojo();
        p.setEmail(f.getEmail());
        p.setPassword(f.getPassword());
        return p;
    }

    public static List<UserData> conversion(List<UserPojo> list) {
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }

    public static DailyReportData convert(DailyReportPojo p) {
        DailyReportData data = new DailyReportData();
        data.setTotalRevenue(p.getTotalRevenue());
        data.setDate(p.getDate());
        data.setTotalItems(p.getTotalItems());
        data.setTotalInvoice(p.getTotalInvoice());
        return data;
    }

    public static SalesReportData FormTOData(SalesReportForm salesReportForm, int quantity, int totalRevenue) {
        SalesReportData salesReportData = new SalesReportData();
        salesReportData.setCategory(salesReportForm.getCategory());
        salesReportData.setQuantity(quantity);
        salesReportData.setRevenue(totalRevenue);
        salesReportData.setBrand(salesReportForm.getBrand());
        return salesReportData;
    }

}
