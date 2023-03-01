package com.increff.employee.dto;

import com.increff.employee.dao.UserDao;
import com.increff.employee.model.UserData;
import com.increff.employee.model.UserForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class UserDto {
    @Autowired
    private UserDao dao;
    @Autowired
    private UserService service;
    @Value("${supervisor.list}")
    private String supervisorList;
    public void add(UserForm form) throws ApiException {
        UserPojo p = convert(form);
        List<String> stringList = Arrays.asList(supervisorList.split(","));
        p.setRole("operator");
        normalize(p);
        for(String s:stringList)
        {
            String s1=p.getEmail();
            if(s1.equals(s))
            {
                p.setRole("supervisor");
            }
        }
        service.add(p);
    }

    public UserPojo get(String email) throws ApiException {
        return service.get(email);
    }

    public List<UserData> getAll() {
        List<UserPojo> list = service.getAll();
        List<UserData> list2 = new ArrayList<UserData>();
        for (UserPojo p : list) {
            list2.add(convert(p));
        }
      return list2;
    }

    public void delete(int id) {
        service.delete(id);
    }

    protected static void normalize(UserPojo p) {
        p.setEmail(p.getEmail().toLowerCase().trim());
        p.setRole(p.getRole().toLowerCase().trim());
    }
    private static UserData convert(UserPojo p) {
        UserData d = new UserData();
        d.setEmail(p.getEmail());
        d.setRole(p.getRole());
        d.setId(p.getId());
        return d;
    }

    private static UserPojo convert(UserForm f) {
        UserPojo p = new UserPojo();
        p.setEmail(f.getEmail());
        p.setPassword(f.getPassword());
        return p;
    }
}
