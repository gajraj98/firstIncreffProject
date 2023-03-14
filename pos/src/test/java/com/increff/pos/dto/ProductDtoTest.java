package com.increff.pos.dto;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandCategoryService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convert;
import static org.junit.Assert.assertEquals;

public class ProductDtoTest extends AbstractUnitTest{
    @Autowired
    private ProductDto dto;
    @Autowired
    private BrandCategoryService brandCategoryService;
    private final String brand="Brand";
    private final String category="Category";
    private final String name="pen";
    private final double mrp=200;
    private final String barcode="a";

    @Before
    public void setUp() throws ApiException {
        BrandCategoryPojo brandCategoryForm = new BrandCategoryPojo();
        brandCategoryForm.setBrand(brand);
        brandCategoryForm.setCategory(category);
        brandCategoryService.add(brandCategoryForm);

        ProductForm f = new ProductForm();
        f.setCategory(category);
        f.setBrand(brand);
        f.setName(name);
        f.setBarcode("a");
        f.setMrp(mrp);
        dto.add(f);
    }
    @Test
    public void testAdd() throws ApiException {
        ProductForm f = new ProductForm();
        f.setCategory(category);
        f.setBrand(brand);
        f.setName("pen");
        f.setBarcode("an");
        f.setMrp(mrp);
        dto.add(f);
    }
    @Test
    public void testGetAll() throws ApiException {
        List<ProductData> list= dto.getAll();
        int size = list.size();
        assertEquals(1, size);
    }
    @Test
    public void testGetLimited() throws ApiException {
        List<ProductData> list= dto.getLimited(1);
        int size = list.size();
        assertEquals(1, size);
    }
    @Test
    public void testGetTotal() throws ApiException {
       Long size= dto.getTotalNoProducts();
        Long ans = new Long(1);
        assertEquals(ans, size);
    }
    @Test
    public void testGetById() throws ApiException {
        List<ProductData> list= dto.getAll();
        for(ProductData data:list) {
            ProductData d= dto.get(data.getId());
            assertEquals(name, d.getName());
            assertEquals(barcode, d.getBarcode());
            assertEquals(mrp, d.getMrp(),0.001);
            assertEquals("brand", d.getBrand());
            assertEquals("category", d.getCategory());
        }
    }
    @Test
    public void testGetByBrandCategoryID() throws ApiException {
        List<ProductData> list= dto.getAll();
        for(ProductData data:list) {
            BrandCategoryPojo p = brandCategoryService.get(data.getBrand(),data.getCategory());
            List<ProductPojo> list1 =dto.getByBrandCategoryID(p.getId());
            int size = list1.size();
            assertEquals(1, size);
        }
    }
    @Test
    public void testGetByBarcoded() throws ApiException {
        List<ProductData> list = dto.getAll();
        for (ProductData data : list) {
            ProductData p = dto.get(data.getBarcode());
            assertEquals(name, p.getName());
            assertEquals(barcode, p.getBarcode());
            assertEquals(mrp, p.getMrp(),0.001);
        }

    }
    @Test
    public void testUpdate() throws ApiException {
        List<ProductData> list = dto.getAll();
        for (ProductData data : list) {
            data.setMrp(300);
            data.setName("Pencil");
            dto.update(data.getId(),data);
            ProductData d= dto.get(data.getId());
            assertEquals("pencil", d.getName());
            assertEquals(barcode, d.getBarcode());
            assertEquals(300, d.getMrp(),0.001);
            assertEquals("brand", d.getBrand());
            assertEquals("category", d.getCategory());
        }

    }
    @Test
    public void testConvertFormToPojo() throws ApiException {
        List<ProductData> list = dto.getAll();
        for (ProductData data : list) {
            BrandCategoryPojo p = brandCategoryService.get(data.getBrand(),data.getCategory());
            ProductPojo pojo = convert(data,p);
            assertEquals(name, pojo.getName());
            assertEquals(barcode, pojo.getBarcode());
            assertEquals(mrp, pojo.getMrp(),0.001);
        }
    }
    @Test
    public void testConvertPojoToData() throws ApiException {
        List<ProductData> list = dto.getAll();
        for (ProductData data : list) {
            BrandCategoryPojo p = brandCategoryService.get(data.getBrand(),data.getCategory());
            BrandCategoryPojo d = brandCategoryService.get(p.getId());
            ProductPojo pojo  = new ProductPojo();
            pojo.setName("pen");
            pojo.setBarcode(barcode);
            pojo.setMrp(200);
            ProductData productData = convert(pojo,d);
            assertEquals("pen", productData.getName());
            assertEquals(barcode, productData.getBarcode());
            assertEquals(200, productData.getMrp(),0.001);
            assertEquals("brand", productData.getBrand());
            assertEquals("category", productData.getCategory());
        }
    }

}
