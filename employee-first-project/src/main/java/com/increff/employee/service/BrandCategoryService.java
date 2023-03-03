package com.increff.employee.service;

import javax.transaction.Transactional;

import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.pojo.BrandCategoryPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandCategoryService {

	@Autowired
	private BrandCategoryDao dao;
	
	public void add(BrandCategoryPojo p) {
		normalize(p);
		dao.insert(p);
	}
	public void delete(int id) {
	 	dao.delete(id);
	}
	public BrandCategoryPojo get(int id) throws ApiException {
	    return getCheck(id);
    }

	public BrandCategoryPojo get(String brand,String category) throws ApiException {
		BrandCategoryPojo p = new BrandCategoryPojo();
		p.setCategory(category);
		p.setBrand(brand);
		normalize(p);
		return getCheck(p.getBrand(),p.getCategory());
	}
	public List<BrandCategoryPojo> get(String brand) throws ApiException {
		return getCheck(brand);
	}
	public List<BrandCategoryPojo> getAll() {
	    return dao.selectAll();
    }
	public void update(int id,BrandCategoryPojo p) throws ApiException {
		normalize(p);
		BrandCategoryPojo ex = dao.select(id);
	    ex.setBrand(p.getBrand());
	    ex.setCategory(p.getCategory());
	    dao.update(ex);
    }

	protected static void normalize(BrandCategoryPojo p) {
		p.setBrand(StringUtil.toLowerCase(p.getBrand()).trim());
		p.setCategory(StringUtil.toLowerCase(p.getCategory()).trim());
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
	public List<BrandCategoryPojo> getCheck(String brand) throws ApiException {
		List<BrandCategoryPojo> list = dao.select(brand);
		if(list.size()==0) {
			throw new ApiException("No category doesn't exist corresponding to this brand");
		}
		return list;
	}
}

