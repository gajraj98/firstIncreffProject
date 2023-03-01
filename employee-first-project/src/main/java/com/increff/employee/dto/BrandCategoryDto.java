package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
@Repository
public class BrandCategoryDto {

	@Autowired
	private BrandCategoryService service;
	@Autowired
	private ProductDto productDto;

	public void add(BrandCategoryForm form) {
		BrandCategoryPojo p = convert(form);
		normalize(p);
		service.add(p);
	}

	public void delete(int id) throws ApiException {
		List<ProductPojo>list = productDto.getByBrandCategoryID(id);
		if(list.size()>0)
		{
			throw new ApiException("you can't delete this BrandCategory before deleting its products");
		}
		service.delete(id);
	}

	public BrandCategoryData get(int id) throws ApiException {
		BrandCategoryPojo p = getCheck(id);
		return convert(p);
	}
	public BrandCategoryPojo get(String brand,String category) throws ApiException {
		BrandCategoryPojo p = new BrandCategoryPojo();
		p.setCategory(category);
		p.setBrand(brand);
		normalize(p);
		return getCheck(p.getBrand(),p.getCategory());
	}
	public List<BrandCategoryPojo> get(String brand) throws ApiException {
		brand= StringUtil.toLowerCase(brand);
		return getCheck(brand);
	}
	public List<BrandCategoryData> getAll() {
		List<BrandCategoryPojo> list = service.getAll();
		List<BrandCategoryData> list2 = new ArrayList<BrandCategoryData>();
		for (BrandCategoryPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	public void update(int id, BrandCategoryForm f) throws ApiException {
		BrandCategoryPojo p = convert(f);
		normalize(p);
		service.update(id, p);
	}

	protected static BrandCategoryPojo convert(BrandCategoryForm f) {
		BrandCategoryPojo p = new BrandCategoryPojo();
		p.setBrand(f.getBrand());
		p.setCategory(f.getCategory());
		return p;
	}

	protected static BrandCategoryData convert(BrandCategoryPojo p) {
		BrandCategoryData d = new BrandCategoryData();
		d.setBrand(p.getBrand());
		d.setId(p.getId());
		d.setCategory(p.getCategory());
		return d;
	}
	protected static void normalize(BrandCategoryPojo p) {
		p.setBrand(StringUtil.toLowerCase(p.getBrand()));
		p.setCategory(StringUtil.toLowerCase(p.getCategory()));
	}
	public BrandCategoryPojo getCheck(int id) throws ApiException {
		BrandCategoryPojo p = service.get(id);
		if(p == null) {
			throw new ApiException("Brand and category doesn't exist");
		}
		return p;
	}
	public BrandCategoryPojo getCheck(String brand,String category) throws ApiException {
		BrandCategoryPojo p = service.get(brand,category);
		if(p == null) {
			throw new ApiException("Brand and category doesn't exist");
		}
		return p;
	}
	public List<BrandCategoryPojo> getCheck(String brand) throws ApiException {
		List<BrandCategoryPojo> list = service.get(brand);
		if(list.size()==0) {
			throw new ApiException("No category doesn't exist corresponding to this brand");
		}
		return list;
	}
}

