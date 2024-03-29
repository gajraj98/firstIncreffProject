package com.increff.pos.dao;

import com.increff.pos.pojo.DailyReportPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DailyReportDao  extends AbstractDao{
    private static String selectAll = "select p from DailyReportPojo p";
     public void insert(DailyReportPojo p)
     {
         em().persist(p);
     }
     public List<DailyReportPojo> selectAll()
     {
         TypedQuery<DailyReportPojo> query = getQuery(selectAll,DailyReportPojo.class);
         return query.getResultList();
     }
}
