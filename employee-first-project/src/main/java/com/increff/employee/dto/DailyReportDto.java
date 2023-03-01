package com.increff.employee.dto;

import com.increff.employee.model.DailyReportData;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.pojo.DailyReportPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.DailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DailyReportDto {
    @Autowired
    private OrderDto orderDto;
    @Autowired
    private OrderItemDto orderItemDto;
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
        List<OrderPojo> orderPojoList = orderDto.getByDate(start, end);
        int totalInvoice = 0;
        int totalItems = 0;
        int totalRevenue = 0;
        for (OrderPojo pojo : orderPojoList) {
            if (pojo.getInvoiceGenerated() > 0) {
                totalInvoice += 1;

                    List<OrderItemData> orderItemPojoList = orderItemDto.getAll(pojo.getId());
                    totalItems+= orderItemPojoList.size();
                    for (OrderItemData data : orderItemPojoList) {
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
    public static DailyReportData convert(DailyReportPojo p)
    {
        DailyReportData data = new DailyReportData();
        data.setTotalRevenue(p.getTotalRevenue());
        data.setDate(p.getDate());
        data.setTotalItems(p.getTotalItems());
        data.setTotalInvoice(p.getTotalInvoice());
        return data;
    }
}
