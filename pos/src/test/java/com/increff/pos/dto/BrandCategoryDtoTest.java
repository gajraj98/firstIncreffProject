package com.increff.pos.dto;

import com.increff.pos.model.BrandCategoryData;
import com.increff.pos.model.BrandCategoryForm;
import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.service.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convert;
import static org.junit.Assert.assertEquals;


public class BrandCategoryDtoTest extends AbstractUnitTest {

    private final String Brand = "Brand";
    private final String Category = "Category";
    @Autowired
    private BrandCategoryDto dto;

    @Before
    public void setUp() throws ApiException {
        BrandCategoryForm brandCategoryForm = new BrandCategoryForm();
        brandCategoryForm.setBrand(Brand);
        brandCategoryForm.setCategory(Category);
        dto.add(brandCategoryForm);
    }

    @Test
    public void testAdd() throws ApiException {
        BrandCategoryForm brandCategoryForm = new BrandCategoryForm();
        brandCategoryForm.setBrand("and");
        brandCategoryForm.setCategory(Category);
        dto.add(brandCategoryForm);
    }

    @Test
    public void testGetAll() throws ApiException {
        List<BrandCategoryData> list = dto.getAll();
        int size = list.size();
        assertEquals(1, size);
    }

    @Test
    public void testGetLimited() throws ApiException {
        List<BrandCategoryData> list = dto.getLimited(1);
        int size = list.size();
        assertEquals(1, size);
    }

    @Test
    public void testGetTotal() throws ApiException {
        Long size = dto.getTotalNoBrands();
        Long ans = new Long(1);
        assertEquals(ans, size);
    }

    @Test
    public void get() throws ApiException {
        List<BrandCategoryData> list = dto.getAll();
        for (BrandCategoryData data : list) {
            BrandCategoryData data1 = dto.get(data.getId());
            assertEquals("brand", data1.getBrand());
            assertEquals("category", data1.getCategory());
            assertEquals(data.getId(), data1.getId());
        }
    }

    @Test
    public void getByBrandAndCategory() throws ApiException {
        List<BrandCategoryData> list = dto.getAll();
        for (BrandCategoryData data : list) {
            BrandCategoryPojo p = dto.get(data.getBrand(), data.getCategory());
            assertEquals("brand", p.getBrand());
            assertEquals("category", p.getCategory());
        }
    }
    @Test
    public void getByBrand() throws ApiException {
        List<BrandCategoryData> list = dto.getAll();
        for (BrandCategoryData data : list) {
            List<BrandCategoryPojo> p = dto.get(data.getBrand());
            for (BrandCategoryPojo pojo:p) {
                assertEquals("brand", pojo.getBrand());
                assertEquals("category", pojo.getCategory());
            }
        }
    }
    @Test
    public void testCheckEmptyCheckApiException()
    {
        try{
            BrandCategoryPojo p = new BrandCategoryPojo();
            p.setBrand("v");
            dto.checkEmpty(p);
        }
        catch (ApiException e)
        {
            assertEquals("either brand or category is missing",e.getMessage());
        }
    }

    @Test
    public void update() throws ApiException {
        List<BrandCategoryData> list = dto.getAll();
        for (BrandCategoryData data : list) {
            BrandCategoryForm form = new BrandCategoryForm();
            form.setCategory(data.getCategory());
            form.setBrand("Rocky");
            dto.update(data.getId(), form);
            BrandCategoryData updatedData = dto.get(data.getId());
            assertEquals("rocky", updatedData.getBrand());
            assertEquals("category", updatedData.getCategory());
        }
    }



    @Test
    public void convertFormTOPojo() {
        BrandCategoryForm f = new BrandCategoryForm();
        f.setBrand(Brand);
        f.setCategory(Category);
        BrandCategoryPojo p = convert(f);
        assertEquals("Brand", p.getBrand());
        assertEquals("Category", p.getCategory());
    }

    @Test
    public void convertPojoTOData() {
        BrandCategoryPojo p = new BrandCategoryPojo();
        p.setBrand(Brand);
        p.setCategory(Category);
        BrandCategoryData d = convert(p);
        assertEquals("Brand", d.getBrand());
        assertEquals("Category", d.getCategory());
    }


}
