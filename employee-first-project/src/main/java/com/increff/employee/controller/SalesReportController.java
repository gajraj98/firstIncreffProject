package com.increff.employee.controller;

import com.increff.employee.dto.SalesReportAllCategoryDto;
import com.increff.employee.dto.SalesReportDto;
import com.increff.employee.model.SalesReportAllCategoryForm;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
    public SalesReportData get(@RequestBody SalesReportForm form) throws ApiException
    {
         return salesReportDto.get(form);
    }

    @ApiOperation(value = "get")
    @RequestMapping(path = "/salesReportAllCategory", method = RequestMethod.POST)
    public List<SalesReportData> get(@RequestBody SalesReportAllCategoryForm form) throws ApiException
    {
        return salesReportDto.get(form);
    }
}
