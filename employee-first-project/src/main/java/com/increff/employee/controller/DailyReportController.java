package com.increff.employee.controller;

import com.increff.employee.dto.DailyReportDto;
import com.increff.employee.model.DailyReportData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class DailyReportController {
    @Autowired
    private DailyReportDto dto;
    @ApiOperation(value = "get")
    @RequestMapping(path = "/api/dailyReport", method = RequestMethod.GET)
    public List<DailyReportData> get() throws ApiException {
        return dto.getAll();
    }

}
