package com.increff.pos.service;

import com.increff.pos.dto.AbstractUnitTest;
import com.increff.pos.pojo.BrandCategoryPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.Normalise.normalize;
import static org.junit.Assert.assertEquals;

public class BrandCategoryServiceTest extends AbstractUnitTest {
    private final String Brand = "Brand";
    private final String Category = "Category";
    @Autowired
    private BrandCategoryService service;

    @Before
    public void setUp() throws ApiException {
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setBrand(Brand);
        brandCategoryPojo.setCategory(Category);
        service.add(brandCategoryPojo);
    }

    @Test
    public void testAdd() throws ApiException {
        BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
        brandCategoryPojo.setBrand("and");
        brandCategoryPojo.setCategory(Category);
        service.add(brandCategoryPojo);
    }
    @Test
    public void testAddCheckException() throws ApiException {
        try{
            BrandCategoryPojo brandCategoryPojo = new BrandCategoryPojo();
            brandCategoryPojo.setBrand(Brand);
            brandCategoryPojo.setCategory(Category);
            service.add(brandCategoryPojo);
        }
        catch (ApiException e)
        {
            assertEquals("brand category already exist",e.getMessage());
        }
    }

    @Test
    public void testGetAll() {
        List<BrandCategoryPojo> list = service.getAll();
        int size = list.size();
        assertEquals(1, size);
    }
    @Test
    public void testGetCheckCategory() throws ApiException {
        List<BrandCategoryPojo> list = service.getAll();
        for (BrandCategoryPojo pojo:list)
        {
            service.getCheckCategory(pojo.getCategory());
        }
    }
    @Test
    public void testGetCheckCategoryApiException() throws ApiException {
        try {
            service.getCheckCategory("d");
        }
        catch (ApiException e)
        {
            assertEquals("No brands exist corresponding to this category",e.getMessage());
        }
    }
    @Test
    public void testGetCheckBrandApiException() throws ApiException {
        try {
            service.getCheckByBrand("d");
        }
        catch (ApiException e)
        {
            assertEquals("No category exist corresponding to this brand",e.getMessage());
        }
    }
    @Test
    public void testGetCheckApiException() throws ApiException {
        try {
            service.getCheck("v","s");
        }
        catch (ApiException e)
        {
            assertEquals("Brand and category doesn't exist",e.getMessage());
        }
    }
    @Test
    public void testGetCheckByIdApiException() throws ApiException {
        try {
            List<BrandCategoryPojo> list = service.getAll();
            for (BrandCategoryPojo pojo : list) {
                service.getCheck(23);
            }
        }
        catch (ApiException e)
        {
            assertEquals("Brand and category doesn't exist",e.getMessage());
        }
    }
    @Test
    public void testGetCheckBrand() throws ApiException {
        List<BrandCategoryPojo> list = service.getAll();
        for (BrandCategoryPojo pojo:list)
        {
            service.getCheckByBrand(pojo.getBrand());
        }
    }
    @Test
    public void testGetCheckBYId() throws ApiException {
        List<BrandCategoryPojo> list = service.getAll();
        for (BrandCategoryPojo pojo:list)
        {
            service.getCheck(pojo.getId());
        }
    }
    @Test
    public void testGetTotal() throws ApiException {
        Long size = service.getTotalNoBrands();
        Long ans = new Long(1);
        assertEquals(ans, size);
    }

    @Test
    public void testGetLimited() throws ApiException {
        List<BrandCategoryPojo> list = service.getLimited(1);
        int size = list.size();
        assertEquals(1, size);
    }

    @Test
    public void testGet() throws ApiException {
        List<BrandCategoryPojo> list = service.getAll();
        for (BrandCategoryPojo data : list) {
            BrandCategoryPojo data1 = service.get(data.getId());
            assertEquals("brand", data1.getBrand());
            assertEquals("category", data1.getCategory());
            assertEquals(data.getId(), data1.getId());
        }
    }

    @Test
    public void testGetByBrandAndCategory() throws ApiException {
        List<BrandCategoryPojo> list = service.getAll();
        for (BrandCategoryPojo data : list) {
            BrandCategoryPojo p = service.get(data.getBrand(), data.getCategory());
            assertEquals("brand", p.getBrand());
            assertEquals("category", p.getCategory());
        }
    }
    @Test
    public void testGetByCategory() throws ApiException {
        List<BrandCategoryPojo> list = service.getAll();
        for (BrandCategoryPojo data : list) {
            List<BrandCategoryPojo> p = service.getByCategory(data.getCategory());
            for (BrandCategoryPojo pojo:p) {
                assertEquals("category", pojo.getCategory());
            }
        }
    }
    @Test
    public void update() throws ApiException {
        List<BrandCategoryPojo> list = service.getAll();
        for (BrandCategoryPojo data : list) {
            BrandCategoryPojo p = new BrandCategoryPojo();
            p.setCategory(data.getCategory());
            p.setBrand("Rocky");
            service.update(data.getId(), p);
            BrandCategoryPojo updatedPojo = service.get(data.getId());
            assertEquals("rocky", updatedPojo.getBrand());
            assertEquals("category", updatedPojo.getCategory());
        }
    }
    @Test
    public void updateApiException() throws ApiException {
        try{
            List<BrandCategoryPojo> list = service.getAll();
            for (BrandCategoryPojo data : list) {
                BrandCategoryPojo p = new BrandCategoryPojo();
                p.setCategory(data.getCategory());
                p.setBrand(Brand);
                service.update(data.getId(), p);
                BrandCategoryPojo updatedPojo = service.get(data.getId());
                assertEquals("rocky", updatedPojo.getBrand());
                assertEquals("category", updatedPojo.getCategory());
            }
        }
        catch (ApiException e)
        {
            assertEquals( "Brand Category Already exist",e.getMessage());
        }
    }


    @Test
    public void testNormalize() {
        BrandCategoryPojo p = new BrandCategoryPojo();
        p.setBrand(Brand);
        p.setCategory(Category);
        normalize(p);
        assertEquals("brand", p.getBrand());
        assertEquals("category", p.getCategory());
    }
}
