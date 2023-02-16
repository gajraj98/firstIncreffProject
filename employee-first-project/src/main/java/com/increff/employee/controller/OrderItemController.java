package com.increff.employee.controller;

import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.ProductData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
@Repository
public class OrderItemController {

    @Autowired
    private OrderItemDto dto;
    @ApiOperation(value = "get itemList by id")
    @RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.GET)
    public List<OrderItemData> get(@PathVariable int id) throws ApiException
    {
        return dto.get(id);
    }
}
