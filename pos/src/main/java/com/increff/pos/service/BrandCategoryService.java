package com.increff.pos.service;

import javax.transaction.Transactional;

import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.increff.pos.dao.BrandCategoryDao;
import com.increff.pos.pojo.BrandCategoryPojo;

import static com.increff.pos.util.Normalise.normalize;


@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandCategoryService {

	@Autowired
	private BrandCategoryDao dao;
	
	public void add(BrandCategoryPojo p) throws ApiException {
		normalize(p);
		BrandCategoryPojo pojo = dao.select(p.getBrand(),p.getCategory());
		if(pojo!=null)
		{
			throw new ApiException("brand category already exist");
		}
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

		return getCheck(brand,category);
	}
	public List<BrandCategoryPojo> get(String brand) throws ApiException {
		normalize(brand);
		return getCheckByBrand(brand);
	}
	public List<BrandCategoryPojo> getByCategory(String category) throws ApiException {
		normalize(category);
		return getCheckCategory(category);
	}
	public List<BrandCategoryPojo> getAll() {
	    return dao.selectAll();
    }
	public List<BrandCategoryPojo> getLimited(Integer pageNo) {
		return dao.selectLimited(pageNo);
	}
	public void update(int id,BrandCategoryPojo p) throws ApiException {
		normalize(p);
		BrandCategoryPojo pojo = dao.select(p.getBrand(), p.getCategory());
		if(pojo!=null)
		{
			throw new ApiException("Brand Category Already exist");
		}
		BrandCategoryPojo ex = dao.select(id);
	    ex.setBrand(p.getBrand());
	    ex.setCategory(p.getCategory());
	    dao.update(ex);
    }


	public BrandCategoryPojo getCheck(int id) throws ApiException {
		BrandCategoryPojo brandCategoryPojo = dao.select(id);
		if(brandCategoryPojo == null) {
			throw new ApiException("Brand and category doesn't exist");
		}
		return brandCategoryPojo;
	}
	public BrandCategoryPojo getCheck(String brand,String category) throws ApiException {
		BrandCategoryPojo brandCategoryPojo = dao.select(brand,category);
		if(brandCategoryPojo == null) {
			throw new ApiException("Brand and category doesn't exist");
		}
		return brandCategoryPojo;
	}
	public List<BrandCategoryPojo> getCheckByBrand(String brand) throws ApiException {
		List<BrandCategoryPojo> brandCategoryPojoList = dao.select(brand);
		if(brandCategoryPojoList.size()==0) {
			throw new ApiException("No category exist corresponding to this brand");
		}
		return brandCategoryPojoList;
	}
	public List<BrandCategoryPojo> getCheckCategory(String category) throws ApiException {
		List<BrandCategoryPojo> list = dao.selectByCategory(category);
		if(list.size()==0) {
			throw new ApiException("No brands exist corresponding to this category");
		}
		return list;
	}
}

