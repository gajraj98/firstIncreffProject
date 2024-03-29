package com.increff.pos.dao;

import java.util.List;

import com.increff.pos.pojo.BrandCategoryPojo;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BrandCategoryDao extends AbstractDao{

	private static String deleteId = "delete from BrandCategoryPojo p where id=:id";
	private static String selectId = "select p from BrandCategoryPojo p where id=:id";
	private static String selectBrandCategory = "select p from BrandCategoryPojo p where brand=:brand and category=:category";
	private static String selectAll = "select p from BrandCategoryPojo p order by id desc";
	private static String selectBrand = "select p from BrandCategoryPojo p where brand=:brand";
	private static String selectCategory = "select p from BrandCategoryPojo p where category=:category";
	private static String getTotalBrands="select count(p) from BrandCategoryPojo p";
	

	public void insert(BrandCategoryPojo p) {
        em().persist(p);
	}

	public int delete(int id) {
        Query query = em().createQuery(deleteId);
        query.setParameter("id", id);
        return query.executeUpdate();
	}
	public Long getTotalNoBrands() {
		TypedQuery<Long> query = getQuery(getTotalBrands,Long.class);
		Long rows =  getSingle(query);
		return rows;
	}
	public BrandCategoryPojo select(int id) {
		TypedQuery<BrandCategoryPojo> query = getQuery(selectId,BrandCategoryPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	public BrandCategoryPojo select(String brand,String category) {
		TypedQuery<BrandCategoryPojo> query = getQuery(selectBrandCategory,BrandCategoryPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		return getSingle(query);
	}
	public List<BrandCategoryPojo>select(String brand) {
		TypedQuery<BrandCategoryPojo> query = getQuery(selectBrand,BrandCategoryPojo.class);
		query.setParameter("brand", brand);
		return query.getResultList();
	}
	public List<BrandCategoryPojo>selectByCategory(String category) {
		TypedQuery<BrandCategoryPojo> query = getQuery(selectCategory,BrandCategoryPojo.class);
		query.setParameter("category", category);
		return query.getResultList();
	}
	public List<BrandCategoryPojo> selectAll() {
		TypedQuery<BrandCategoryPojo> query = getQuery(selectAll,BrandCategoryPojo.class);
		return query.getResultList();
	}
	public List<BrandCategoryPojo> selectLimited(Integer pageNo) {
		TypedQuery<BrandCategoryPojo> query = getQuery(selectAll,BrandCategoryPojo.class);
		query.setFirstResult(10*(pageNo-1));
		query.setMaxResults(10);
		return query.getResultList();
	}

	public void update(BrandCategoryPojo ex) {

	}
	
}
