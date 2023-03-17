//package com.increff.pos.controller;
//
//import com.increff.pos.dto.InventoryReportDto;
//import com.increff.pos.model.InventoryReportData;
//import com.increff.pos.model.InventoryReportForm;
//import com.increff.pos.service.ApiException;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Api
//@RestController
//@RequestMapping("/api")
//public class InventoryReportController {
//
//    @Autowired
//    private InventoryReportDto dto;
//    @ApiOperation(value = "get all product InventoryReport")
//    @RequestMapping(value = "/inventoryReport",method = RequestMethod.POST)
//    public List<InventoryReportData> get(@RequestBody InventoryReportForm form) throws ApiException {
//        return dto.get(form);
//    }
////    @ApiOperation(value = "get total  brands")
//    @RequestMapping( value = "/inventoryReport/total", method = RequestMethod.GET)
//    public Long getTotalNoInventory() {
//        return dto.getTotalNoInventory();
//    }
//    @ApiOperation(value = "get all product InventoryReport")
//    @RequestMapping(value = "/inventoryReport", method = RequestMethod.GET)
//    public List<InventoryReportData> getAll(@RequestParam("pageNo") Integer pageNo) throws ApiException {
//        return dto.getAll(pageNo);
//    }
//}
