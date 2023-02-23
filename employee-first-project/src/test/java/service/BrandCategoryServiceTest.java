package service;

import com.increff.employee.dto.AbstractUnitTest;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandCategoryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandCategoryServiceTest extends AbstractUnitTest {
    @Autowired
    private BrandCategoryService service;
    private final String Brand="Brand";
    private final String Category="Category";
    @Before
    public void setUp()
    {
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setBrand(Brand);
        brandCategoryPojo.setCategory(Category);
        service.add(brandCategoryPojo);
    }
    @Test
    public void testAdd()  {
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setBrand("and");
        brandCategoryPojo.setCategory(Category);
        service.add(brandCategoryPojo);
    }
    @Test
    public void testGetAll()  {
        List<BrandCategoryPojo> list= service.getAll();
        int size=list.size();
        assertEquals(1,size);
    }
    @Test
    public void testGet() throws ApiException {
        List<BrandCategoryPojo> list= service.getAll();
        for(BrandCategoryPojo data: list) {
            BrandCategoryPojo data1 = service.get(data.getId());
            assertEquals("Brand", data1.getBrand());
            assertEquals("Category", data1.getCategory());
            assertEquals(data.getId(), data1.getId());
        }
    }
    @Test
    public void testGetByBrandAndCategory() throws ApiException {
        List<BrandCategoryPojo> list= service.getAll();
        for(BrandCategoryPojo data: list) {
            BrandCategoryPojo p = service.get(data.getBrand(),data.getCategory());
            assertEquals("Brand",p.getBrand());
            assertEquals("Category",p.getCategory());
        }
    }
    @Test
    public void update() throws ApiException {
        List<BrandCategoryPojo> list= service.getAll();
        for(BrandCategoryPojo data: list) {
            BrandCategoryPojo p= new BrandCategoryPojo();
            p.setCategory(data.getCategory());
            p.setBrand("Rocky");
            service.update(data.getId(),p);
            BrandCategoryPojo updatedPojo = service.get(data.getId());
            assertEquals("Rocky", updatedPojo.getBrand());
            assertEquals("Category", updatedPojo.getCategory());
        }
    }
    @Test
    public void testDelete()
    {
        List<BrandCategoryPojo> list= service.getAll();
        for(BrandCategoryPojo data: list) {
            service.delete(data.getId());
        }
        List<BrandCategoryPojo>list1= service.getAll();
        int size=list1.size();
        assertEquals(0,size);
    }
}
