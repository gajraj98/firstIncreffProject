package com.increff.employee.controller;

import com.increff.employee.dto.SalesReportAllCategoryDto;
import com.increff.employee.model.SalesReportAllCategoryForm;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.service.ApiException;
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
public class SalesReportAllCategoryController {

    @Autowired
    private SalesReportAllCategoryDto dto;

    @ApiOperation(value = "get")
    @RequestMapping(path = "/api/salesReportAllCategory", method = RequestMethod.POST)
    public List<SalesReportData> get(@RequestBody SalesReportAllCategoryForm form) throws ApiException
    {
       return dto.get(form);
    }
}
