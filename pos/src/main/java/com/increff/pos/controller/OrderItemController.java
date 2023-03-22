package com.increff.pos.controller;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.OrderForm;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
/** todo
 * use spinal case i.e. make it order-items
 * Refer this: https://increff.atlassian.net/wiki/spaces/TB/pages/312377489/Java+Class+layering+and+Structure#Conventions.3
 **/
@RequestMapping("/api/orderItems")
public class OrderItemController {

    @Autowired
    private OrderItemDto dto;

    @ApiOperation(value = "get itemList by id")
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody OrderForm f) throws ApiException {
        dto.add(f);
    }

    @ApiOperation(value = "get itemList by id")
    @RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
    public List<OrderItemData> getAll(@PathVariable int orderId) throws ApiException {
        return dto.getAll(orderId);
    }

    @ApiOperation(value = "get itemList by id")
    @RequestMapping(method = RequestMethod.GET)
    public OrderItemData get(@RequestParam("id") int id) throws ApiException {
        return dto.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) throws ApiException {
        dto.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody OrderForm form) throws ApiException {
        dto.update(id, form);
    }
}
