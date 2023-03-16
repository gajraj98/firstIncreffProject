package com.increff.pos.service;

import com.increff.pos.dto.AbstractUnitTest;
import com.increff.pos.dto.UserDto;
import com.increff.pos.model.UserForm;
import com.increff.pos.pojo.UserPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends AbstractUnitTest {
    @Autowired
    private UserDto dto;
    @Autowired
    private UserService service;
    @Before
    public void setUp() throws ApiException {
        UserForm p = new UserForm();
        p.setEmail("abc@gmail.com");
        p.setRole("supervisor");
        p.setPassword("123");
        dto.add(p);
    }
    @Test
    public void testGetAll(){
        List<UserPojo> list = service.getAll();
        int size = list.size();
        assertEquals(1,size);
    }
    @Test
    public void testGet() throws ApiException {
        List<UserPojo> list = service.getAll();
        for(UserPojo p: list)
        {
            UserPojo pojo = service.get(p.getEmail());
            assertEquals(p.getEmail(),pojo.getEmail());
            assertEquals(p.getId(),pojo.getId());
        }
    }
    @Test
    public void testDelete() throws ApiException {
        List<UserPojo> list = service.getAll();
        for(UserPojo p: list)
        {
            service.delete(p.getId());
        }
        List<UserPojo> list1 = service.getAll();
        int size  =  list1.size();
        assertEquals(0,size);
    }
}
