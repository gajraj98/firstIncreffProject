package com.increff.pos.controller;

import java.util.List;

import com.increff.pos.dto.UserDto;
import com.increff.pos.model.BrandCategoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/api/supervisor/user")
public class SupervisorApiController {

	@Autowired
	private UserDto dto;

	@ApiOperation(value = "Adds a user")
	@RequestMapping( method = RequestMethod.POST)
	public void addUser(@RequestBody UserForm form) throws ApiException {
		dto.add(form);
	}

	@ApiOperation(value = "Deletes a user")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable int id) throws ApiException {
		dto.delete(id);
	}

	@ApiOperation(value = "Gets list of all users")
	@RequestMapping( method = RequestMethod.GET)
	public List<UserData> getAllUser() {
		   return dto.getAll();
	}
	@ApiOperation(value = "get total  brands")
	@RequestMapping( value = "/total", method = RequestMethod.GET)
	public Long getTotalNoBrands() {

		return dto.getTotalNoBrands();
	}
	@ApiOperation(value = "get all the brand and its category")
	@RequestMapping(value = "/limited",method = RequestMethod.GET)
	public List<UserData> getLimited(@RequestParam("pageNo") Integer pageNo) {
		return dto.getLimited(pageNo);
	}


}
