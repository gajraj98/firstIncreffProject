//package com.increff.pos.dto;
//
//import com.increff.pos.model.SalesReportAllCategoryForm;
//import com.increff.pos.model.SalesReportData;
//import com.increff.pos.model.SalesReportForm;
//import com.increff.pos.pojo.BrandCategoryPojo;
//import com.increff.pos.service.ApiException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//@Repository
//public class SalesReportAllCategoryDto {
//
//    @Autowired
//    private BrandCategoryDto brandCategoryDto;
//    @Autowired
//    private SalesReportDto salesReportDto;
//
//    public List<SalesReportData> get(SalesReportAllCategoryForm form) throws ApiException {
//
//        List<SalesReportData> list = new ArrayList<>();
//        List<BrandCategoryPojo> brandCategoryPojoList = brandCategoryDto.get(form.getBrand());
//        for(BrandCategoryPojo p: brandCategoryPojoList)
//        {
//            SalesReportForm salesReportForm = new SalesReportForm();
//            salesReportForm.setCategory(p.getCategory());
//            salesReportForm.setBrand(p.getBrand());
//            salesReportForm.setStartDate(form.getStartDate());
//            salesReportForm.setEndDate(form.getEndDate());
//            SalesReportData salesReportData = salesReportDto.get(salesReportForm);
//            list.add(salesReportData);
//        }
//        return list;
//    }
//}
