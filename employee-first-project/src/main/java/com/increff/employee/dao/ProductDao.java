package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;

// TODO Have @Transactional in all Daos and Service classes
@Repository
public class ProductDao extends AbstractDao{

	private static String delete_id = "delete from ProductPojo p where id=:id";
	private static String select_id = "select p from ProductPojo p where id=:id";
	private static String select_barcode = "select p from ProductPojo p where barcode=:barcode";
	private static String select_all = "select p from ProductPojo p";
	private static String select_brandCategoryId = "select p from ProductPojo p where brandCategoryId=:brandCategoryId";
    @Autowired
    private InventoryDao daoInventory;
	@Autowired
	private InventoryService inventoryService;
	@Transactional
	public int insert(ProductPojo p) {
        em().persist(p);
		return p.getId();
	}

	public void delete(int id) {
        Query query = em().createQuery(delete_id);
        query.setParameter("id", id);
		query.executeUpdate();
		inventoryService.delete(id);
	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(select_id,ProductPojo.class); 
		query.setParameter("id", id);
		return getSingle(query);
	}
	public List<ProductPojo>selectByBrandCategoryId(int brandCategoryId)
	{
		TypedQuery<ProductPojo> query = getQuery(select_brandCategoryId,ProductPojo.class);
		query.setParameter("brandCategoryId", brandCategoryId);
		return query.getResultList();
	}
	public ProductPojo select(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(select_barcode,ProductPojo.class); 
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}
	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(select_all,ProductPojo.class); 
		return query.getResultList();
	}

	public void update(ProductPojo ex) {

	}
}
