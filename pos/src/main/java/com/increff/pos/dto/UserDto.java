package com.increff.pos.dto;

import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.increff.pos.util.ConvertFunctions.conversion;
import static com.increff.pos.util.Normalise.normalize;
import static com.increff.pos.util.ConvertFunctions.convert;

@Component
public class UserDto {

    @Autowired
    private UserService service;
    @Value("${supervisor.list}")
    private String supervisorList;
    public void add(UserForm form) throws ApiException {
        UserPojo p = convert(form);
        service.add(validate(p));
    }

    public UserPojo get(String email) throws ApiException {
        return service.get(email);
    }
    public UserPojo get(int id){
        return service.get(id);
    }
    public List<UserData> getAll() {
         return  conversion(service.getAll());

    }
    public List<UserData> getLimited(Integer pageNo) {
        return  conversion(service.getLimited(pageNo));

    }
    public Long getTotalNoBrands() {

        return service.getTotalNoBrands();
    }

    public void delete(int id) throws ApiException {
        UserPojo p = service.get(id);

        if(p.getRole().equals("supervisor"))
        {
            throw new ApiException("You can't delete a Supervisor");
        }
        service.delete(id);
    }
    public UserPojo validate(UserPojo p)
    {
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
        return p;
    }


}
