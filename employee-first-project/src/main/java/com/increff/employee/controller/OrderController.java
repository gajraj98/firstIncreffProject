package com.increff.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@Repository
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderDto dto;
	@ApiOperation(value = "add a Product")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody List<OrderForm> form) throws ApiException
	{
		dto.add(form);
	}

	@ApiOperation(value = "deleting order ")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		 dto.delete(id);
	}

	@ApiOperation(value = "get  order by id details")
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public OrderData get(@PathVariable int id)
	{
		return dto.get(id);
	}

	@ApiOperation(value = "get all order List")
	@RequestMapping(method = RequestMethod.GET)
	public List<OrderData> getAll()
	{
		return dto.getAll();
	}

	@ApiOperation(value = "updating order")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody List<OrderForm> form) throws ApiException
	{
         dto.update(id,form);
	}
	
}
