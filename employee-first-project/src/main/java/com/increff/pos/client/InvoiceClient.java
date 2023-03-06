package com.increff.pos.client;

import com.increff.pos.model.InvoiceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.Base64;

@Service
public class InvoiceClient {

    @Value("${invoice.url}")
    private String fopUrl;
    @Value("${pdf.path}")
    private String path;
    public void generateInvoice(InvoiceDetails invoiceDetails,HttpServletResponse response1) throws IOException {
        String filePath = path + "invoice" + invoiceDetails.getOrderId() + ".pdf";
        File file = new File(filePath);
        if(file.exists())
        {
            String response = CheckInvoice(file);
            downloadPDF(response,response1);
        }
        else {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(fopUrl, invoiceDetails, String.class);
            savePdf(response,invoiceDetails.getOrderId());
            downloadPDF(response,response1);
        }
    }
    public void savePdf(String response,Integer orderId)
    {
        byte[] decodedBytes = Base64.getDecoder().decode(response);
        File file = new File(path + "invoice" + orderId + ".pdf");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(decodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String CheckInvoice(File file) throws FileNotFoundException {
       try(FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis= new BufferedInputStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream())
       {
           byte[] buffer = new byte[1024];
           int bytesRead;
           while ((bytesRead = bis.read(buffer)) != -1) {
               baos.write(buffer, 0, bytesRead);
           }
           byte[] fileBytes = baos.toByteArray();
           return  Base64.getEncoder().encodeToString(fileBytes);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
    public void downloadPDF(String base64, HttpServletResponse response) throws IOException {
        // Step 2: Decode the Base64 string to binary data
        byte[] binaryData = Base64.getDecoder().decode(base64);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Invoice.pdf\"");

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(binaryData);
        outputStream.close();
    }
}
