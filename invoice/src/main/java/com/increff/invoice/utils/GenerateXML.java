package com.increff.invoice.utils;

import com.increff.invoice.model.InvoiceDetails;
import com.increff.invoice.model.InvoiceItems;
import com.mysql.cj.util.TimeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class GenerateXML {

      public static void createXml(InvoiceDetails invoiceDetails) throws ParserConfigurationException {
        String xmlFilePath = Constants.INVOICE_XML_PATH;

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("bill");
        document.appendChild(root);
        double totalBillAmount = 0;

        Element date = document.createElement("date");
        document.appendChild(document.createTextNode(TimeUtils.getCurrentDate()));
        root.appendChild(date);

        Element time = document.createElement("time");
        document.appendChild((document.createTextNode((TimeUtils.getCurrentTime()))));
        root.appendChild(time);

        int i=0;
        for(InvoiceItems orderItem : invoiceDetails.getItems()){
              Element item = document.createElement("item");
              root.appendChild(item);

              Element id =document.createElement("id");
              id.appendChild(document.createTextNode(String.valueOf(++i)));
              item.appendChild(id);

              Element name = document.createElement("name");
              name.appendChild(document.createTextNode(orderItem.getName()));
              item.appendChild(name);
              double totalItemCost = orderItem.getPrice()*orderItem.getQuantity();
              totalBillAmount+= totalItemCost;
        }
    }

}
