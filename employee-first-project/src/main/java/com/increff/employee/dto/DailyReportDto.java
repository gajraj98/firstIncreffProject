package com.increff.employee.dto;

import com.increff.employee.model.DailyReportData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.pojo.DailyReportPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.DailyReportService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.util.ConvertFunctions.convert;

@Repository
public class DailyReportDto {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private DailyReportService service;
    public void add(DailyReportPojo p)
    {
        service.add(p);
    }
    public List<DailyReportData> getAll()
    {
        List<DailyReportPojo>list= service.getAll();
        List<DailyReportData>list2 = new ArrayList<>();
        for(DailyReportPojo p:list)
        {
            list2.add(convert(p));
        }
        return list2;
    }
    public void generateDailyReport() throws ApiException {
        LocalDate date = LocalDate.now();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        List<OrderPojo> orderPojoList = orderService.getByDate(start, end);
        int totalInvoice = 0;
        int totalItems = 0;
        int totalRevenue = 0;
        for (OrderPojo pojo : orderPojoList) {
            if (pojo.getInvoiceGenerated() > 0) {
                totalInvoice += 1;

                    List<OrderItemPojo> orderItemPojoList = orderItemService.getAll(pojo.getId());
                    totalItems+= orderItemPojoList.size();
                    for (OrderItemPojo data : orderItemPojoList) {
                        totalRevenue += data.getSellingPrice() * data.getQuantity();
                    }

                }
            }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // or any other desired pattern
        String dateString = date.format(formatter);
        DailyReportPojo dailyReportPojo = new DailyReportPojo();
        dailyReportPojo.setDate(dateString);
        dailyReportPojo.setTotalInvoice(totalInvoice);
        dailyReportPojo.setTotalItems(totalItems);
        dailyReportPojo.setTotalRevenue(totalRevenue);
        add(dailyReportPojo);

    }

}
