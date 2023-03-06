package com.increff.pos.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.BrandCategoryDto;
import com.increff.pos.model.BrandCategoryData;
import com.increff.pos.model.BrandCategoryForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/api/brand")
public class BrandCategoryController {
	@Autowired
	private BrandCategoryDto dto;
	@ApiOperation(value = "add a brand and its category")
	@RequestMapping( method = RequestMethod.POST)
	public void add(@RequestBody BrandCategoryForm form) throws ApiException {
		dto.add(form);
	}
	@ApiOperation(value = "delete the brand and its category")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		dto.delete(id);
	}

	@ApiOperation(value = "get the brand and its category with its id")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public BrandCategoryData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}

	@ApiOperation(value = "get all the brand and its category")
	@RequestMapping(method = RequestMethod.GET)
	public List<BrandCategoryData> getAll() {
		return dto.getAll();
	}

	@ApiOperation(value = "update the brand and its category")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id,@RequestBody BrandCategoryForm f) throws ApiException {
		dto.update(id, f);
	}
	
	
}

