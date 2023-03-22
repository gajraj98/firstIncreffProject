package com.increff.pos.controller;

import com.increff.pos.dto.DailyReportDto;
import com.increff.pos.dto.InventoryReportDto;
import com.increff.pos.dto.SalesReportDto;
import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api")
public class ReportsController {
    // todo use only DTO here
    @Autowired
    private SalesReportDto salesReportDto;
    @Autowired
    private InventoryReportDto dto;
    @Autowired
    private DailyReportDto dailyReportDto;

    // todo change the description value for every method
    @ApiOperation(value = "/Post")
    @RequestMapping(value = "/salesReport", method = RequestMethod.POST)
    public List<SalesReportData> get(@RequestBody SalesReportForm form) throws ApiException {
        return salesReportDto.check(form);
    }

    @ApiOperation(value = "/get")
    /** todo
     * use spinal case i.e. make it sales-report and change it in other places also
     * Refer this: https://increff.atlassian.net/wiki/spaces/TB/pages/312377489/Java+Class+layering+and+Structure#Conventions.3
     **/
    @RequestMapping(value = "/salesReport", method = RequestMethod.GET)
    public List<SalesReportData> getAll() throws ApiException {
        SalesReportForm salesReportForm = new SalesReportForm();
        return salesReportDto.check(salesReportForm);
    }

    @ApiOperation(value = "get")
    @RequestMapping(value = "/dailyReport", method = RequestMethod.GET)
    public List<DailyReportData> get() throws ApiException {
        return dailyReportDto.getAll();
    }

    @ApiOperation(value = "get all product InventoryReport")
    @RequestMapping(value = "/inventoryReport", method = RequestMethod.POST)
    public List<InventoryReportData> get(@RequestBody InventoryReportForm form) throws ApiException {
        return dto.get(form);
    }

    @ApiOperation(value = "get total  brands")
    @RequestMapping(value = "/inventoryReport/total", method = RequestMethod.GET)
    public Long getTotalNoInventory() {
        return dto.getTotalNoInventory();
    }

    @ApiOperation(value = "get all product InventoryReport")
    @RequestMapping(value = "/inventoryReport", method = RequestMethod.GET)
    public List<InventoryReportData> getAll(@RequestParam("pageNo") Integer pageNo) throws ApiException {
        return dto.getAll(pageNo);
    }
}
