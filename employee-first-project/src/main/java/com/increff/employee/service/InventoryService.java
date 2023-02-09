package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

@Service
public class InventoryService {

	@Autowired
	private InventoryDao dao;
	@Autowired
	private ProductService productService;
	@Transactional
	public void add(InventoryPojo p)
	{
		dao.insert(p);
	}
	@Transactional(rollbackOn = ApiException.class)
	public InventoryPojo get(int id) throws ApiException
	{
		 return getCheck(id);
	}
	@Transactional
	public List<InventoryPojo> getAll()
	{
		return dao.selectAll();
	}
	@Transactional(rollbackOn = ApiException.class)
	public void update(String barcode,InventoryPojo p) throws ApiException
	{
		ProductPojo productPojo = productService.get(barcode);
		InventoryPojo ex = getCheck(productPojo.getId());
	    ex.setInventory(p.getInventory());
	    dao.update(ex);
	}
	@Transactional
	public void update(int id,InventoryPojo p) throws ApiException
	{
		InventoryPojo ex = getCheck(id);
		ex.setInventory(p.getInventory());
		dao.update(ex);
	}
	public InventoryPojo getCheck(int id) throws ApiException {
		InventoryPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Product doesn't exist");
		}
		return p;
	}
	@Transactional
	public void delete (int id)
	{
		dao.delete(id);
	}
}
