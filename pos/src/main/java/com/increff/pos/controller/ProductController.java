package com.increff.pos.controller;

import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductDto dto;

    @ApiOperation(value = "add a Product")
    @RequestMapping(method = RequestMethod.POST)
    public void add(@RequestBody ProductForm form) throws ApiException {
        dto.add(form);
    }

    @ApiOperation(value = "get product by id")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "get product by barcode")
    @RequestMapping(value = "/barcode", method = RequestMethod.GET)
    public ProductData get(@RequestParam("barcode") String barcode) throws ApiException {
        return dto.get(barcode);
    }

    @ApiOperation(value = "get total no of product")
    @RequestMapping(value = "/total", method = RequestMethod.GET)
    public Long getTotalNoProducts() {

        return dto.getTotalNoProducts();
    }

    @ApiOperation(value = "get all product details")
    @RequestMapping(method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "get limited product details")
    @RequestMapping(value = "/limited", method = RequestMethod.GET)
    public List<ProductData> getLimited(@RequestParam("pageNo") Integer pageNo) throws ApiException {
        return dto.getLimited(pageNo);
    }

    @ApiOperation(value = "Update product details")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {
        dto.update(id, form);
    }
}
