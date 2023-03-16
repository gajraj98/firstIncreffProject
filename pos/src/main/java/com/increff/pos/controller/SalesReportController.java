package com.increff.pos.controller;

import com.increff.pos.dto.SalesReportDto;
import com.increff.pos.model.SalesReportAllCategoryForm;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@RequestMapping("/api")
public class SalesReportController {
    @Autowired
    private SalesReportDto salesReportDto;

    @ApiOperation(value = "get")
    @RequestMapping(value = "/salesReport", method = RequestMethod.POST)
    public List<SalesReportData> get(@RequestBody SalesReportForm form) throws ApiException
    {
        return salesReportDto.get(form);
    }

    @ApiOperation(value = "get")
    @RequestMapping(path = "/salesReport/brand", method = RequestMethod.POST)
    public List<SalesReportData> get(@RequestBody SalesReportAllCategoryForm form) throws ApiException
    {
        return salesReportDto.get(form);
    }
    @ApiOperation(value = "get")
    @RequestMapping(path = "/salesReport", method = RequestMethod.GET)
    public List<SalesReportData> getAll() throws ApiException
    {
        return salesReportDto.getAll();
    }
}
