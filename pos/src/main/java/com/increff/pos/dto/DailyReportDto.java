package com.increff.pos.dto;

import com.increff.pos.model.DailyReportData;
import com.increff.pos.pojo.DailyReportPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.DailyReportService;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convert;

@Repository
public class DailyReportDto {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private DailyReportService dailyReportService;

    public void add(DailyReportPojo p) {
        dailyReportService.add(p);
    }

    public List<DailyReportData> getAll() {
        List<DailyReportPojo> dailyReportPojoList = dailyReportService.getAll();
        List<DailyReportData> dailyReportDataList = new ArrayList<>();
        for (DailyReportPojo p : dailyReportPojoList) {
            dailyReportDataList.add(convert(p));
        }
        return dailyReportDataList;
    }

    public void generateDailyReport(){
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
                totalItems += orderItemPojoList.size();
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
