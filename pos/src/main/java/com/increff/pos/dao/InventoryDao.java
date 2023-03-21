package com.increff.pos.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.InventoryPojo;

@Repository
@Transactional
public class InventoryDao extends AbstractDao{

	private static String selectId = "select p from InventoryPojo p where id=:id";
	private static String selectAll = "select p from InventoryPojo p order by id desc";
	private static String deleteId = "delete from InventoryPojo p where id=:id";
	private static String getTotalInventory="select count(p) from InventoryPojo p";
	public void insert(InventoryPojo p) {
        em().persist(p);
	}
	public InventoryPojo select(int id) {
		TypedQuery<InventoryPojo> query = getQuery(selectId,InventoryPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	public List<InventoryPojo> selectAll() {
		TypedQuery<InventoryPojo> query = getQuery(selectAll,InventoryPojo.class);
		return query.getResultList();
	}
	public List<InventoryPojo> selectLimited(Integer pageNo) {
		TypedQuery<InventoryPojo> query = getQuery(selectAll,InventoryPojo.class);
		query.setFirstResult(10*(pageNo-1));
		query.setMaxResults(10);
		return query.getResultList();
	}
	public Long getTotalNoInventory() {

		TypedQuery<Long> query = getQuery(getTotalInventory,Long.class);
		Long rows =  getSingle(query);
		return rows;
	}
    public int delete(int id)
	{
		Query query = em().createQuery(deleteId);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	public void update(InventoryPojo ex) {

	}
}
