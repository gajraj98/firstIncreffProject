package com.increff.employee.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.pojo.BrandCategoryPojo;

@Service
public class BrandCategoryService {

	@Autowired
	private BrandCategoryDao dao;

	// TODO Move it to class level
	@Transactional(rollbackOn = ApiException.class)
	public void add(BrandCategoryPojo p) {
		dao.insert(p);
	}
	@Transactional
    public void delete(int id) {
	 	dao.delete(id);
	}
	@Transactional(rollbackOn = ApiException.class)
    public BrandCategoryPojo get(int id) throws ApiException {
	    return dao.select(id);
    }
	@Transactional(rollbackOn = ApiException.class)
	public BrandCategoryPojo get(String brand,String category) throws ApiException {
		return dao.select(brand,category);
	}
	@Transactional
	public List<BrandCategoryPojo> get(String brand) throws ApiException {
		return dao.select(brand);
	}
	@Transactional
    public List<BrandCategoryPojo> getAll() {
	    return dao.selectAll();
    }
	@Transactional(rollbackOn  = ApiException.class)
    public void update(int id,BrandCategoryPojo p) throws ApiException {
		BrandCategoryPojo ex = dao.select(id);
	    ex.setBrand(p.getBrand());
	    ex.setCategory(p.getCategory());
	    dao.update(ex);
    }


}

