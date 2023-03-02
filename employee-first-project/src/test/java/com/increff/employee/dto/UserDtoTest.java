package com.increff.employee.dto;

import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDtoTest extends AbstractUnitTest{
    @Autowired
    private UserDto dto;
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
        List<UserPojo> list = dto.getAll();
        int size = list.size();
        assertEquals(1,size);
    }
    @Test
    public void testGet() throws ApiException {
        List<UserPojo> list = dto.getAll();
        for(UserPojo p: list)
        {
            UserPojo pojo = dto.get(p.getEmail());
            assertEquals(p.getEmail(),pojo.getEmail());
            assertEquals(p.getId(),pojo.getId());
        }
    }
    @Test
    public void testDelete()
    {
        List<UserPojo> list = dto.getAll();
        for(UserPojo p: list)
        {
            dto.delete(p.getId());
        }
        List<UserPojo> list1 = dto.getAll();
       int size  =  list1.size();
       assertEquals(0,size);
    }
    @Test
    public void testNormalize()
    {
        UserPojo p = new UserPojo();
        p.setEmail("Abc@Gmail.com");
        p.setRole("supervisor");
         dto.normalize(p);
        assertEquals("abc@gmail.com",p.getEmail());
        assertEquals("supervisor",p.getRole());
    }
}