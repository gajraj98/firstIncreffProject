package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

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

	public void add(BrandCategoryForm form) throws ApiException {
		BrandCategoryPojo p = convert(form);
		normalize(p);
		service.add(p);
	}

	public void delete(int id) {
		service.delete(id);
	}

	public BrandCategoryData get(int id) throws ApiException {
		BrandCategoryPojo p = service.get(id);
		return convert(p);
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

	private static BrandCategoryPojo convert(BrandCategoryForm f) {
		BrandCategoryPojo p = new BrandCategoryPojo();
		p.setBrand(f.getBrand());
		p.setCategory(f.getCategory());
		return p;
	}

	private static BrandCategoryData convert(BrandCategoryPojo p) {
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
}

