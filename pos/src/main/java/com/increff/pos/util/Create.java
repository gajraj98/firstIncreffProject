package com.increff.pos.util;

import com.increff.pos.model.DailyReportData;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.pojo.DailyReportPojo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.increff.pos.util.ConvertFunctions.convert;

public class Create {
    public static DailyReportPojo creatDailyReportPojo(Integer totalInvoice, Integer totalItems, Integer totalRevenue)
    {
        LocalDate date = LocalDate.now();
        DailyReportPojo dailyReportPojo = new DailyReportPojo();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // or any other desired pattern
        String dateString = date.format(formatter);
        dailyReportPojo.setDate(dateString);
        dailyReportPojo.setTotalInvoice(totalInvoice);
        dailyReportPojo.setTotalItems(totalItems);
        dailyReportPojo.setTotalRevenue(totalRevenue);
        return dailyReportPojo;
    }
    public static List<SalesReportData> createList(HashMap<String, SalesReportData> brandHashMap) {
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        for (Map.Entry<String, SalesReportData> entry : brandHashMap.entrySet()) {
            salesReportDataList.add(entry.getValue());
        }
        return salesReportDataList;
    }
    public static List<DailyReportData> createList(List<DailyReportPojo>dailyReportPojoList)
    {

        List<DailyReportData> dailyReportDataList = new ArrayList<>();
        for (DailyReportPojo p : dailyReportPojoList) {
            dailyReportDataList.add(convert(p));
        }
        return dailyReportDataList;
    }
}
