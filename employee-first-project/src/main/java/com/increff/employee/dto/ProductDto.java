package com.increff.employee.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.BrandCategoryService;
import com.increff.employee.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;

import static com.increff.employee.util.ConvertFunctions.convert;

@Repository
public class ProductDto {

	@Autowired
	private ProductService service;
	@Autowired
	private InventoryService inventoryService;
    @Autowired
	private BrandCategoryService brandCategoryService;
	public void add(ProductForm form) throws ApiException {
		BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(form.getBrand(),form.getCategory());
		ProductPojo p = convert(form,brandCategoryPojo);
		int id=service.add(p);
		InventoryPojo p2 = new InventoryPojo();
		p2.setId(id);
		inventoryService.add(p2);
	}

	public void delete(int id) throws ApiException {
		service.delete(id);
	}

	public ProductData get(int id) throws ApiException {
		ProductPojo p = service.get(id);
		BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(p.getBrandCategoryId());
		return convert(p,brandCategoryPojo);
	}
	public List<ProductPojo> getByBrandCategoryID(int BrandCategoryId){
		  return service.getByBrandCategoryID(BrandCategoryId);
	}

	public ProductData get(String barcode) throws ApiException {
	    ProductPojo p = service.get(barcode);
		BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(p.getBrandCategoryId());
		return convert(p,brandCategoryPojo);
	}

	public List<ProductData> getAll() throws ApiException {
		return conversion(service.getAll());
	}

	public void update(int id, ProductForm f) throws ApiException {
		ProductPojo p = convert(f);
		service.update(id, p);
	}
	public  List<ProductData> conversion(List<ProductPojo> list) throws ApiException {
		List<ProductData> list2 = new ArrayList<ProductData>();
		for (ProductPojo p : list) {
			BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(p.getBrandCategoryId());
			list2.add(convert(p,brandCategoryPojo));
		}
		return list2;
	}

}
