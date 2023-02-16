package com.increff.employee.controller;

import com.increff.employee.dto.SalesReportDto;
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

@Api
@RestController
public class SalesReportController {
    @Autowired
    private SalesReportDto salesReportDto;

    @ApiOperation(value = "get")
    @RequestMapping(path = "/api/salesReport", method = RequestMethod.POST)
    public SalesReportData get(@RequestBody SalesReportForm form) throws ApiException
    {
        System.out.println(form.getStartDate());
        System.out.println(form.getEndDate());
        System.out.println(form.getCategory());
        System.out.println(form.getBrand());
         return salesReportDto.get(form);
    }
}
