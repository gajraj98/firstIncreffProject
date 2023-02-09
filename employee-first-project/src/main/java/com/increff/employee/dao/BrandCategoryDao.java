package com.increff.employee.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.increff.employee.pojo.BrandCategoryPojo;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BrandCategoryDao extends AbstractDao{

	private static String delete_id = "delete from BrandCategoryPojo p where id=:id";
	private static String select_id = "select p from BrandCategoryPojo p where id=:id";
	private static String select_brandCategory = "select p from BrandCategoryPojo p where brand=:brand and category=:category";
	private static String select_all = "select p from BrandCategoryPojo p";

	

	public void insert(BrandCategoryPojo p) {
        em().persist(p);
	}

	public int delete(int id) {
        Query query = em().createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
	}

	public BrandCategoryPojo select(int id) {
		TypedQuery<BrandCategoryPojo> query = getQuery(select_id,BrandCategoryPojo.class); 
		query.setParameter("id", id);
		return getSingle(query);
	}
	public BrandCategoryPojo select(String brand,String category) {
		TypedQuery<BrandCategoryPojo> query = getQuery(select_brandCategory,BrandCategoryPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		return getSingle(query);
	}
	public List<BrandCategoryPojo> selectAll() {
		TypedQuery<BrandCategoryPojo> query = getQuery(select_all,BrandCategoryPojo.class); 
		return query.getResultList();
	}

	public void update(BrandCategoryPojo ex) {

	}
	
}
