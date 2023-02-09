package com.increff.employee.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.EmployeePojo;
import com.increff.employee.util.StringUtil;

@Service
public class BrandCategoryService {

	@Autowired
	private BrandCategoryDao dao;
	
	@Transactional(rollbackOn = ApiException.class)
	public void add(BrandCategoryPojo p) throws ApiException {
		if(StringUtil.isEmpty(p.getBrand())) {
			throw new ApiException("Brand can't be empty");
		}
		else if(StringUtil.isEmpty(p.getCategory())){
			throw new ApiException("Category can't be empty");
		}
		dao.insert(p);
	}
	@Transactional
    public void delete(int id) {
	 	dao.delete(id);
	}
	@Transactional(rollbackOn = ApiException.class)
    public BrandCategoryPojo get(int id) throws ApiException {
	    return getCheck(id);
    }
	@Transactional(rollbackOn = ApiException.class)
	public BrandCategoryPojo get(String brand,String category) throws ApiException {
		return getCheck(brand,category);
	}
	@Transactional
    public List<BrandCategoryPojo> getAll() {
	    return dao.selectAll();
    }
	@Transactional(rollbackOn  = ApiException.class)
    public void update(int id,BrandCategoryPojo p) throws ApiException {
		BrandCategoryPojo ex = getCheck(id);
	    ex.setBrand(p.getBrand());
	    ex.setCategory(p.getCategory());
	    dao.update(ex);
    }
	public BrandCategoryPojo getCheck(int id) throws ApiException {
		BrandCategoryPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Brand and category doesn't exist");
		}
		return p;
	}
	public BrandCategoryPojo getCheck(String brand,String category) throws ApiException {
		BrandCategoryPojo p = dao.select(brand,category);
		if(p == null) {
			throw new ApiException("Brand and category doesn't exist");
		}
		return p;
	}
}

