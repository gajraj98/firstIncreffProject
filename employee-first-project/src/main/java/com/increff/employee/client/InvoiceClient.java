package com.increff.employee.client;

import com.increff.employee.model.InvoiceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class InvoiceClient {

    @Value("${invoice.url}")
    private String fopUrl;
    public String generateInvoice(InvoiceDetails invoiceDetails){
        RestTemplate restTemplate = new RestTemplate();
        String response=restTemplate.postForObject(fopUrl,invoiceDetails,String.class);
        byte[] decodedBytes = Base64.getDecoder().decode(response);
        String path = "./invoice/generateInvoice";
        File file = new File(path);
       try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
