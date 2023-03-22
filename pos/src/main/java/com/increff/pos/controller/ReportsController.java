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

    @ApiOperation(value = "get salesReport")
    @RequestMapping(value = "/sales-reports", method = RequestMethod.POST)
    public List<SalesReportData> get(@RequestBody SalesReportForm form) throws ApiException {
        return salesReportDto.check(form);
    }

    @ApiOperation(value = "get all salesReports")
    @RequestMapping(value = "/sales-reports", method = RequestMethod.GET)
    public List<SalesReportData> getAll() throws ApiException {
        SalesReportForm salesReportForm = new SalesReportForm();
        return salesReportDto.check(salesReportForm);
    }

    @ApiOperation(value = "get dailyReport")
    @RequestMapping(value = "/daily-reports", method = RequestMethod.GET)
    public List<DailyReportData> get() throws ApiException {
        return dailyReportDto.getAll();
    }

    @ApiOperation(value = "get InventoryReport of specific product")
    @RequestMapping(value = "/inventory-reports", method = RequestMethod.POST)
    public List<InventoryReportData> get(@RequestBody InventoryReportForm form) throws ApiException {
        return dto.get(form);
    }
    @ApiOperation(value = "get all product InventoryReport")
    @RequestMapping(value = "/inventory-reports", method = RequestMethod.GET)
    public List<InventoryReportData> getAllInventory() throws ApiException {
        return dto.getAll();
    }
}
