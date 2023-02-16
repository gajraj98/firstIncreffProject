package com.increff.employee.client;

import com.increff.employee.model.InvoiceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InvoiceClient {

    @Value("${invoice.url}")
    private String fopUrl;
    public String generateInvoice(InvoiceDetails invoiceDetails){
        RestTemplate restTemplate = new RestTemplate();
        String response=restTemplate.postForObject(fopUrl,invoiceDetails,String.class);
        return response;
    }
}
