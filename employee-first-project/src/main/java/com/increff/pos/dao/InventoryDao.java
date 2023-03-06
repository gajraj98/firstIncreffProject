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

	private static String select_id = "select p from InventoryPojo p where id=:id";
	private static String select_all = "select p from InventoryPojo p";
	private static String delete_id = "delete from InventoryPojo p where id=:id";

	public void insert(InventoryPojo p) {
        em().persist(p);
	}
	public InventoryPojo select(int id) {
		TypedQuery<InventoryPojo> query = getQuery(select_id,InventoryPojo.class); 
		query.setParameter("id", id);
		return getSingle(query);
	}
	public List<InventoryPojo> selectAll() {
		TypedQuery<InventoryPojo> query = getQuery(select_all,InventoryPojo.class); 
		return query.getResultList();
	}
    public int delete(int id)
	{
		Query query = em().createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	public void update(InventoryPojo ex) {

	}
}
