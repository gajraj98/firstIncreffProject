package com.increff.pos.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProductDao extends AbstractDao{

	private static String deleteId = "delete from ProductPojo p where id=:id";
	private static String selectId = "select p from ProductPojo p where id=:id";
	private static String selectBarcode = "select p from ProductPojo p where barcode=:barcode";
	private static String selectAll = "select p from ProductPojo p order by id desc";
	private static String getTotalProducts="select count(p) from ProductPojo p";
	private static String selectBrandCategoryId = "select p from ProductPojo p where brandCategoryId=:brandCategoryId";


	@Transactional
	public int insert(ProductPojo p) {
        em().persist(p);
		return p.getId();
	}

	public void delete(int id) {
        Query query = em().createQuery(deleteId);
        query.setParameter("id", id);
		query.executeUpdate();

	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(selectId,ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	public List<ProductPojo>selectByBrandCategoryId(int brandCategoryId)
	{
		TypedQuery<ProductPojo> query = getQuery(selectBrandCategoryId,ProductPojo.class);
		query.setParameter("brandCategoryId", brandCategoryId);
		return query.getResultList();
	}
	public Long getTotalNoProducts() {
		TypedQuery<Long> query = getQuery(getTotalProducts,Long.class);
		Long rows =  getSingle(query);
		return rows;
	}
	@Transactional(readOnly = true)
	public ProductPojo select(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(selectBarcode,ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}
	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query= getQuery(selectAll, ProductPojo.class);
		return query.getResultList();
	}
	public List<ProductPojo> selectLimited(Integer pageNo) {
		TypedQuery<ProductPojo> query= getQuery(selectAll, ProductPojo.class);
		query.setFirstResult(10*(pageNo-1));
		query.setMaxResults(10);
		return query.getResultList();
	}
	public void update(ProductPojo ex) {

	}
}
