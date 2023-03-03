package com.increff.employee.service;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.InventoryPojo;
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
@Transactional(rollbackOn = ApiException.class)
public class ProductService {

	@Autowired
	private ProductDao dao;
	@Autowired
	private BrandCategoryService brandCategoryService;
	@Autowired
	private InventoryService inventoryService;

	public int  add(ProductPojo p) throws ApiException
	{
		normalize(p);
	 	BrandCategoryPojo p2 = brandCategoryService.get(p.getBrandCategoryId());
		ProductPojo pojo = dao.select(p.getBarcode());
		if(pojo != null)
		{
			throw  new ApiException("This barcode is already used try some different");
		}
		 return dao.insert(p);
	}
	public void delete(int id) throws ApiException {
		InventoryPojo d = inventoryService.get(id);
		if(d.getInventory()>0)
		{
			throw new ApiException("You can't delete this product its inventory is not empty");
		}
		dao.delete(id);
	}


	public ProductPojo get(int id) throws ApiException
	{
		 ProductPojo p = getCheck(id);
		if(p== null)
		{
			throw new ApiException("Product not exist in inventory");
		}
		return p;
	}
	public List<ProductPojo> getByBrandCategoryID(int BrandCategoryId){
		return dao.selectByBrandCategoryId(BrandCategoryId);
	}
	public ProductPojo get(String barcode) throws ApiException{
		ProductPojo p = dao.select(barcode);
		if(p== null)
		{
			throw new ApiException("Product not exist in inventory");
		}
		return p;
	}
	public List<ProductPojo> getAll()
	{
		return dao.selectAll();
	}
	public void update(int id,ProductPojo p) throws ApiException
	{
		normalize(p);
		ProductPojo ex = getCheck(id);
	    ex.setName(p.getName());
	    ex.setMrp(p.getMrp());
		dao.update(ex);
	}
	public ProductPojo getCheck(int id) throws ApiException {
		ProductPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Product doesn't exist");
		}
		return p;
	}
	protected static void normalize(ProductPojo p) {
		p.setName(StringUtil.toLowerCase(p.getName()));
	}
}
