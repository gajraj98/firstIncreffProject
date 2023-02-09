package com.increff.employee.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.service.ApiException;

import java.util.List;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@Repository
public class ProductController {

	@Autowired
	private ProductDto dto;
	@ApiOperation(value = "add a Product")
	@RequestMapping(path = "/api/product", method = RequestMethod.POST)
	public void add(@RequestBody ProductForm form) throws ApiException
	{
         dto.add(form);
	}
	@ApiOperation(value = "delete product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id)
	{
		dto.delete(id);
	}
	@ApiOperation(value = "get product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException
	{
	      return dto.get(id);	
	}
	@ApiOperation(value = "get all product details")
	@RequestMapping(path = "/api/product", method = RequestMethod.GET)
	public List<ProductData> getAll()
	{
		return dto.getAll();
	}
	@ApiOperation(value = "Update product details")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id,@RequestBody ProductForm form) throws ApiException
	{
		dto.update(id, form);
	}
}