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


public class BrandCategoryDtoTest extends AbstractUnitTest{

    @Autowired
    private BrandCategoryDto dto;
    private final String Brand="Brand";
    private final String Category="Category";
    @Before
    public void setUp()  {
        BrandCategoryForm brandCategoryForm = new BrandCategoryForm();
        brandCategoryForm.setBrand(Brand);
        brandCategoryForm.setCategory(Category);
        dto.add(brandCategoryForm);
    }
    @Test
    public void testAdd()  {
        BrandCategoryForm brandCategoryForm = new BrandCategoryForm();
        brandCategoryForm.setBrand("and");
        brandCategoryForm.setCategory(Category);
        dto.add(brandCategoryForm);
    }
    @Test
    public void testGetAll() throws ApiException {
        List<BrandCategoryData>list= dto.getAll();
        int size=list.size();
        assertEquals(1,size);
    }
    @Test
    public void get() throws ApiException {
        List<BrandCategoryData> list= dto.getAll();
        for(BrandCategoryData data: list) {
            BrandCategoryData data1 = dto.get(data.getId());
            assertEquals("brand", data1.getBrand());
            assertEquals("category", data1.getCategory());
            assertEquals(data.getId(), data1.getId());
        }
    }
    @Test
    public void getByBrandAndCategory() throws ApiException {
        List<BrandCategoryData> list= dto.getAll();
        for(BrandCategoryData data: list) {
            BrandCategoryPojo p = dto.get(data.getBrand(),data.getCategory());
            assertEquals("brand",p.getBrand());
            assertEquals("category",p.getCategory());
        }
    }
    @Test
    public void update() throws ApiException {
        List<BrandCategoryData> list= dto.getAll();
        for(BrandCategoryData data: list) {
            BrandCategoryForm form= new BrandCategoryForm();
            form.setCategory(data.getCategory());
            form.setBrand("Rocky");
            dto.update(data.getId(),form);
            BrandCategoryData updatedData = dto.get(data.getId());
            assertEquals("rocky", updatedData.getBrand());
            assertEquals("category", updatedData.getCategory());
        }
    }
    @Test
    public void delete() throws ApiException {
        List<BrandCategoryData> list= dto.getAll();
        for(BrandCategoryData data: list) {
            dto.delete(data.getId());
        }
        List<BrandCategoryData>list1= dto.getAll();
        int size=list1.size();
        assertEquals(0,size);
    }

    @Test
    public void convertFormTOPojo(){
        BrandCategoryForm f = new BrandCategoryForm();
        f.setBrand(Brand);
        f.setCategory(Category);
        BrandCategoryPojo p = convert(f);
        assertEquals("Brand",p.getBrand());
        assertEquals("Category",p.getCategory());
    }
    @Test
    public void convertPojoTOData(){
        BrandCategoryPojo p = new BrandCategoryPojo();
        p.setBrand(Brand);
        p.setCategory(Category);
        BrandCategoryData d = convert(p);
        assertEquals("Brand",d.getBrand());
        assertEquals("Category",d.getCategory());
    }


}
