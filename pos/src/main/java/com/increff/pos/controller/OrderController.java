package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@Repository
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value = "add a Product")
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody List<OrderForm> form) throws ApiException {
        dto.add(form);
    }

    @ApiOperation(value = "deleting order ")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        dto.delete(id);
    }

    @ApiOperation(value = "get total  brands")
    @RequestMapping(value = "/total", method = RequestMethod.GET)
    public Long getTotalNoOrders() {

        return dto.getTotalNoOrders();
    }

    @ApiOperation(value = "get all the brand and its category")
    @RequestMapping(value = "/limited", method = RequestMethod.GET)
    public List<OrderData> getLimited(@RequestParam("pageNo") Integer pageNo) {
        return dto.getLimited(pageNo);
    }

    @ApiOperation(value = "get  order by id details")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable int id) {
        return dto.get(id);
    }

    @ApiOperation(value = "get all order List")
    @RequestMapping(method = RequestMethod.GET)
    public List<OrderData> getAll() {
        return dto.getAll();
    }


}
