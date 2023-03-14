package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.pojo.InventoryPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

	@Autowired
	private InventoryDao dao;
	@Autowired
	private ProductService productService;

	public void add(InventoryPojo p)
	{
		dao.insert(p);
	}
	public InventoryPojo get(int id) throws ApiException
	{
		InventoryPojo p = getCheck(id);
		 return p;
	}
	public Long getTotalNoInventory() {

		return dao.getTotalNoInventory();
	}
	public List<InventoryPojo> getAll()
	{
		return dao.selectAll();
	}
	public List<InventoryPojo> getLimited(Integer pageNo)
	{
		return dao.selectLimited(pageNo);
	}

	public void update(String barcode,InventoryPojo p) throws ApiException
	{
		ProductPojo productPojo = productService.get(barcode);
		InventoryPojo ex = dao.select(productPojo.getId());
	    ex.setInventory(p.getInventory());
	    dao.update(ex);
	}

	public void update(int id,InventoryPojo p) throws ApiException
	{
		getCheck(id);
		InventoryPojo ex = dao.select(id);
		ex.setInventory(p.getInventory());
		dao.update(ex);
	}

	public void delete (int id)
	{
		dao.delete(id);
	}
	public InventoryPojo getCheck(int id) throws ApiException {
		InventoryPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Product doesn't exist");
		}
		return p;
	}
}
