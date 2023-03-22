package com.increff.pos.service;

import com.increff.pos.dao.DailyReportDao;
import com.increff.pos.pojo.DailyReportPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class DailyReportService {

    @Autowired
    private DailyReportDao dao;

    public void add(DailyReportPojo p) {
        dao.insert(p);
    }

    public List<DailyReportPojo> getAll() {
        return dao.selectAll();
    }
}
