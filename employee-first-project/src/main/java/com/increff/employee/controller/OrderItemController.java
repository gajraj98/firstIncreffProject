package com.increff.employee.controller;

import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/orderItem")
public class OrderItemController {

    @Autowired
    private OrderItemDto dto;
    @ApiOperation(value = "get itemList by id")
    @RequestMapping( method = RequestMethod.POST)
    public void add(@RequestBody OrderForm f) throws ApiException {
       dto.add(f);
    }
    @ApiOperation(value = "get itemList by id")
    @RequestMapping(value="/{orderId}", method = RequestMethod.GET)
    public List<OrderItemData> getAll(@PathVariable int orderId) throws ApiException
    {
        return dto.getAllCheckInvoiceBefore(orderId);
    }
    @ApiOperation(value = "get itemList by id")
    // TODO not correct naming convention
    @RequestMapping(value="/ByOrderId/{id}", method = RequestMethod.GET)
    public OrderItemData get(@PathVariable int id) throws ApiException
    {
        return dto.get(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException
    {
         dto.delete(id);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id,@RequestBody OrderForm form) throws ApiException
    {
        dto.update(id,form);
    }
}
