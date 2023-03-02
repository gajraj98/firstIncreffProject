package com.increff.employee.Job;

import com.increff.employee.dto.DailyReportDto;
import com.increff.employee.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;

// TODO package name to be in lower case
@EnableAsync
public class ReportScheduler {
    // TODO Make it private
    @Autowired
    DailyReportDto reportDto;

    @Async
    @Scheduled(cron = "0 0 * * * *")
    public void createDailyReport() throws  ApiException {
        reportDto.generateDailyReport();
    }
}