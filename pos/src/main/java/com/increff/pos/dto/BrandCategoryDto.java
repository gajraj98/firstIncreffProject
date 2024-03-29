package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.increff.pos.model.BrandCategoryData;
import com.increff.pos.model.BrandCategoryForm;
import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandCategoryService;

import static com.increff.pos.util.ConvertFunctions.convert;

@Repository
public class BrandCategoryDto {

	@Autowired
	private BrandCategoryService service;
	@Autowired
	private ProductService productService;

	public void add(BrandCategoryForm form) throws ApiException {
		BrandCategoryPojo brandCategoryPojo = convert(form);
		if(brandCategoryPojo.getBrand().length()==0 || brandCategoryPojo.getCategory().length()==0)
		{
			throw new ApiException("one of the brand or category is null");
		}
		else if(brandCategoryPojo.getBrand().matches(".*[^a-zA-Z0-9 ].*")||brandCategoryPojo.getCategory().matches(".*[^a-zA-Z0-9 ].*"))
		{
			throw new ApiException("You can't use special character");
		}
		service.add(brandCategoryPojo);
	}

	public void delete(int id) throws ApiException {
		List<ProductPojo>list = productService.getByBrandCategoryID(id);
		if(list.size()>0)
		{
			throw new ApiException("you can't delete this BrandCategory before deleting its products");
		}
		service.delete(id);
	}
	public Long getTotalNoBrands() {

		return service.getTotalNoBrands();
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
		List<BrandCategoryPojo> brandCategoryPojoList = service.getAll();
		List<BrandCategoryData> brandCategoryDataList = new ArrayList<BrandCategoryData>();
		for (BrandCategoryPojo p : brandCategoryPojoList) {
			brandCategoryDataList.add(convert(p));
		}
		return brandCategoryDataList;
	}
	public List<BrandCategoryData> getLimited(Integer pageNo) {
		List<BrandCategoryPojo> brandCategoryPojoList = service.getLimited(pageNo);
		List<BrandCategoryData> brandCategoryDataList = new ArrayList<BrandCategoryData>();
		for (BrandCategoryPojo p : brandCategoryPojoList) {
			brandCategoryDataList.add(convert(p));
		}
		return brandCategoryDataList;
	}

	public void update(int id, BrandCategoryForm f) throws ApiException {
		BrandCategoryPojo p = convert(f);
		service.update(id, p);
	}


}

