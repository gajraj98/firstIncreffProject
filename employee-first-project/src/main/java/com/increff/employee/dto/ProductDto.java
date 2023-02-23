package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.InventoryService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;

@Repository
public class ProductDto {

	@Autowired
	private ProductService service;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private BrandCategoryDto brandCategoryDto;

	public void add(ProductForm form) throws ApiException {
		normalize(form);
		BrandCategoryPojo brandCategoryPojo = brandCategoryDto.get(form.getBrand(),form.getCategory());
		ProductPojo p = convert(form,brandCategoryPojo);
		int id=service.add(p);
		InventoryPojo p2 = new InventoryPojo();
		p2.setId(id);
		inventoryService.add(p2);
	}

	public void delete(int id) {
		service.delete(id);
	}

	public ProductData get(int id) throws ApiException {
		ProductPojo p = service.get(id);
		if(p== null)
		{
			throw new ApiException("Product not exist in inventory");
		}
		BrandCategoryData brandCategoryData = brandCategoryDto.get(p.getBrandCategoryId());
		return convert(p,brandCategoryData);
	}
	public List<ProductPojo> getByBrandCategoryID(int BrandCategoryId){
		  return service.getByBrandCategoryID(BrandCategoryId);
	}
	public ProductPojo get(String barcode) throws ApiException {
	    ProductPojo p = service.get(barcode);
		if(p==null)
		{
			throw new ApiException("Product not exist in inventory");
		}
		return p;
	}

	public List<ProductData> getAll() throws ApiException {
		List<ProductPojo> list = service.getAll();
		List<ProductData> list2 = new ArrayList<ProductData>();
		for (ProductPojo p : list) {
			BrandCategoryData brandCategoryData = brandCategoryDto.get(p.getBrandCategoryId());
			list2.add(convert(p,brandCategoryData));
		}
		return list2;
	}

	public void update(int id, ProductForm f) throws ApiException {
		normalize(f);
		BrandCategoryPojo brandCategoryPojo = brandCategoryDto.get(f.getBrand(),f.getCategory());
		ProductPojo p = convert(f,brandCategoryPojo);
		service.update(id, p);
	}

	protected static ProductPojo convert(ProductForm f, BrandCategoryPojo brandCategoryPojo) throws ApiException {

		ProductPojo p = new ProductPojo();
		p.setMrp(f.getMrp());
		p.setName(f.getName());
		p.setBarcode(f.getBarcode());
		p.setBrandCategoryId(brandCategoryPojo.getId());
		System.out.println(p);
		return p;
	}

	protected static ProductData convert(ProductPojo p,BrandCategoryData brandCategoryData) {

		ProductData d = new ProductData();
		d.setName(p.getName());
		d.setId(p.getId());
		d.setMrp(p.getMrp());
		d.setBrand(brandCategoryData.getBrand());
		d.setBarcode(p.getBarcode());
		d.setCategory(brandCategoryData.getCategory());
		return d;
	}
	protected static void normalize(ProductForm f) {
		f.setName(StringUtil.toLowerCase(f.getName()));
		f.setBrand(StringUtil.toLowerCase(f.getBrand()));
		f.setCategory(StringUtil.toLowerCase(f.getCategory()));
	}

}
