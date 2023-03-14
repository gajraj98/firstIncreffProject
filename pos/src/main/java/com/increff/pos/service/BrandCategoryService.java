package com.increff.pos.service;

import javax.transaction.Transactional;

import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.increff.pos.dao.BrandCategoryDao;
import com.increff.pos.pojo.BrandCategoryPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandCategoryService {

	@Autowired
	private BrandCategoryDao dao;
	
	public void add(BrandCategoryPojo p) throws ApiException {
		BrandCategoryPojo pojo = dao.select(p.getBrand(),p.getCategory());
		if(pojo!=null)
		{
			throw new ApiException("brand category already exist");
		}
		normalize(p);
		dao.insert(p);
	}
	public void delete(int id) {
	 	dao.delete(id);
	}
	public Long getTotalNoBrands() {

		return dao.getTotalNoBrands();
	}
	public BrandCategoryPojo get(int id) throws ApiException {
	    return getCheck(id);
    }

	public BrandCategoryPojo get(String brand,String category) throws ApiException {
		normalize(brand,category);
		BrandCategoryPojo p = new BrandCategoryPojo();
		p.setCategory(category);
		p.setBrand(brand);
		normalize(p);
		return getCheck(p.getBrand(),p.getCategory());
	}
	public List<BrandCategoryPojo> get(String brand) throws ApiException {
		normalize(brand);
		return getCheck(brand);
	}
	public List<BrandCategoryPojo> getAll() {
	    return dao.selectAll();
    }
	public List<BrandCategoryPojo> getLimited(Integer pageNo) {
		return dao.selectLimited(pageNo);
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
	protected static void normalize(String s) {
		s=StringUtil.toLowerCase(s).trim();
	}
	protected static void normalize(String a,String b) {
		a=StringUtil.toLowerCase(a).trim();
		b=StringUtil.toLowerCase(b).trim();
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

