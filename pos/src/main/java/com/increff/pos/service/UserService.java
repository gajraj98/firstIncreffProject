package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.UserDao;
import com.increff.pos.pojo.UserPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class UserService {

	@Autowired
	private UserDao dao;


	public void add(UserPojo p) throws ApiException {
		UserPojo existing = dao.select(p.getEmail());
		if (existing != null) {
			throw new ApiException("User with given email already exists");
		}
		dao.insert(p);
	}


	public UserPojo get(String email) throws ApiException {
		return dao.select(email);
	}

	public Long getTotalNoBrands() {

		return dao.getTotalNoBrands();
	}
	public List<UserPojo> getAll() {
		return dao.selectAll();
	}
	public List<UserPojo> getLimited(Integer pageNo) {
		return dao.selectLimited(pageNo);
	}

	public UserPojo get(int id){
		return dao.select(id);
	}
	public void delete(int id) throws ApiException {

		dao.delete(id);
	}

}
