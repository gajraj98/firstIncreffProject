package com.increff.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.BrandCategoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;


@Service
public class ProductService {

	@Autowired
	private ProductDao dao;
	@Autowired
	private BrandCategoryService brandCategoryService;

	@Transactional(rollbackOn = ApiException.class)
	public int  add(ProductPojo p) throws ApiException
	{
	 	BrandCategoryPojo p2 = brandCategoryService.get(p.getBrandCategoryId());
//		 System.out.println(p.getBrand_category());
	 	if(StringUtil.isEmpty(p.getName())) {
			throw new ApiException("Name can't be empty");
		}
	 	else if(StringUtil.isEmpty(p.getBarcode())) {
	 		throw new ApiException("barcode can't be empty");
	 	}
		 return dao.insert(p);
	}
	@Transactional
	public void delete(int id)
	{
		dao.delete(id);
	}

	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(int id) throws ApiException
	{
		 return getCheck(id);
	}
	public List<ProductPojo> getByBrandCategoryID(int BrandCategoryId){
		return dao.selectByBrandCategoryId(BrandCategoryId);
	}
	public ProductPojo get(String barcode) throws ApiException{
		return dao.select(barcode);
	}
	@Transactional
	public List<ProductPojo> getAll()
	{
		return dao.selectAll();
	}
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id,ProductPojo p) throws ApiException
	{
		ProductPojo ex = getCheck(id);
	    ex.setName(p.getName());
	    ex.setBarcode(p.getBarcode());
	    ex.setMrp(p.getMrp());
	    BrandCategoryPojo p2 = brandCategoryService.get(p.getBrandCategoryId());
	    dao.update(ex);
	}
	public ProductPojo getCheck(int id) throws ApiException {
		ProductPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Product doesn't exist");
		}
		return p;
	}
}
