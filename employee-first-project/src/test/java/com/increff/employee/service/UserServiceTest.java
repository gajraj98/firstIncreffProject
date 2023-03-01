package com.increff.employee.service;

import com.increff.employee.dto.AbstractUnitTest;
import com.increff.employee.dto.UserDto;
import com.increff.employee.pojo.UserPojo;
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
        UserPojo p = new UserPojo();
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
    public void testDelete()
    {
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
