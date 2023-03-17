package com.increff.pos.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.UserPojo;

@Repository
public class UserDao extends AbstractDao {

	private static String deleteId = "delete from UserPojo p where id=:id";
	private static String selectId = "select p from UserPojo p where id=:id";
	private static String selectEmail = "select p from UserPojo p where email=:email";
	private static String selectAll = "select p from UserPojo p order by id desc";
	private static String getTotalUsers="select count(p) from UserPojo p";
	
	@Transactional
	public void insert(UserPojo p) {
		em().persist(p);
	}

	public int delete(int id) {
		Query query = em().createQuery(deleteId);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public UserPojo select(int id) {
		TypedQuery<UserPojo> query = getQuery(selectId, UserPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public UserPojo select(String email) {
		TypedQuery<UserPojo> query = getQuery(selectEmail, UserPojo.class);
		query.setParameter("email", email);
		return getSingle(query);
	}
	public Long getTotalNoBrands() {
		TypedQuery<Long> query = getQuery(getTotalUsers,Long.class);
		Long rows =  getSingle(query);
		return rows;
	}
	public List<UserPojo> selectAll() {
		TypedQuery<UserPojo> query = getQuery(selectAll, UserPojo.class);
		return query.getResultList();
	}
	public List<UserPojo> selectLimited(Integer pageNo) {
		TypedQuery<UserPojo> query = getQuery(selectAll, UserPojo.class);
		query.setFirstResult(10*(pageNo-1));
		query.setMaxResults(10);
		return query.getResultList();
	}

	public void update(UserPojo p) {
	}


}
