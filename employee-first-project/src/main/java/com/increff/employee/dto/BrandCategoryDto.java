package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;

import static com.increff.employee.util.ConvertFunctions.convert;

@Repository
public class BrandCategoryDto {

	@Autowired
	private BrandCategoryService service;
	@Autowired
	private ProductService productService;

	public void add(BrandCategoryForm form) {
		BrandCategoryPojo p = convert(form);
		service.add(p);
	}

	public void delete(int id) throws ApiException {
		List<ProductPojo>list = productService.getByBrandCategoryID(id);
		if(list.size()>0)
		{
			throw new ApiException("you can't delete this BrandCategory before deleting its products");
		}
		service.delete(id);
	}

	public BrandCategoryData get(int id) throws ApiException {
		BrandCategoryPojo p = service.get(id);
		return convert(p);
	}
	public BrandCategoryPojo get(String brand,String category) throws ApiException {
		return service.get(brand,category);
	}
	public List<BrandCategoryPojo> get(String brand) throws ApiException {
		brand = StringUtil.toLowerCase(brand);
		return service.get(brand);
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
		service.update(id, p);
	}


}

