package com.increff.pos.controller;

import com.increff.pos.dto.InvoiceDto;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

@Api
@RestController
public class InvoiceController {
    @Autowired
    private InvoiceDto invoiceDto;
    @ApiOperation("returns Base64 encoded string for invoice")
    @RequestMapping(path = "/api/generateInvoice/{orderCode}",method = RequestMethod.GET)
    public void generateInvoice(@PathVariable String orderCode, HttpServletResponse response) throws ApiException, IOException {
         invoiceDto.generateInvoice(orderCode,response);
    }
}
