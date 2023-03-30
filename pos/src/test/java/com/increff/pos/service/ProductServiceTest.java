package com.increff.pos.service;

import com.increff.pos.dto.AbstractUnitTest;
import com.increff.pos.dto.BrandCategoryDto;
import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.BrandCategoryData;
import com.increff.pos.model.BrandCategoryForm;
import com.increff.pos.pojo.ProductPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.increff.pos.util.Normalise.normalize;
import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends AbstractUnitTest {
    private final String brand = "Brand";
    private final String category = "Category";
    private final String name = "pen";
    private final double mrp = 200;
    private final String barcode = "a";
    @Autowired
    private ProductService service;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandCategoryDto brandCategoryDto;

    @Before
    public void setUp() throws ApiException {
        BrandCategoryForm brandCategoryForm = new BrandCategoryForm();
        brandCategoryForm.setBrand(brand);
        brandCategoryForm.setCategory(category);
        brandCategoryDto.add(brandCategoryForm);

        ProductPojo p = new ProductPojo();
        List<BrandCategoryData> list = brandCategoryDto.getAll();
        for (BrandCategoryData d : list) {
            p.setName(name);
            p.setBarcode(barcode);
            p.setMrp(mrp);
            p.setBrandCategoryId(d.getId());
            service.add(p);
        }
    }

    @Test
    public void testAdd() throws ApiException {
        ProductPojo p = new ProductPojo();
        List<BrandCategoryData> list = brandCategoryDto.getAll();
        for (BrandCategoryData d : list) {
            p.setName(name);
            p.setBarcode("b");
            p.setMrp(mrp);
            p.setBrandCategoryId(d.getId());
            service.add(p);
        }
    }
    @Test
    public void testAddCheckApiException() throws ApiException {
       try {
            ProductPojo p = new ProductPojo();
            List<BrandCategoryData> list = brandCategoryDto.getAll();
            for (BrandCategoryData d : list) {
                p.setName(name);
                p.setBarcode("a");
                p.setMrp(mrp);
                p.setBrandCategoryId(d.getId());
                service.add(p);
            }
        }
       catch (ApiException e)
       {
           assertEquals("This barcode is already used try some different",e.getMessage());
       }
    }

    @Test
    public void testGetAll() {
        List<ProductPojo> list = service.getAll();
        int size = list.size();
        assertEquals(1, size);
    }

    public void testGetLimited() {
        List<ProductPojo> list = service.getLimited(1);
        int size = list.size();
        assertEquals(1, size);
    }

    @Test
    public void testGetTotal() throws ApiException {
        Long size = service.getTotalNoProducts();
        Long ans = new Long(1);
        assertEquals(ans, size);
    }

    @Test
    public void testGetById() throws ApiException {
        List<ProductPojo> list = service.getAll();
        for (ProductPojo p : list) {
            ProductPojo pojo = service.get(p.getId());
            assertEquals(name, pojo.getName());
            assertEquals(barcode, pojo.getBarcode());
            assertEquals(mrp, pojo.getMrp(), 0.001);
            assertEquals(p.getId(), pojo.getId());
        }
    }
    @Test
    public void testGetByIdCheckApiException() throws ApiException {
        try{
            ProductPojo pojo = service.get(22);
        }
        catch (ApiException e)
        {
            assertEquals("Product doesn't exist",e.getMessage());
        }
    }
    @Test
    public void testGetByBarcodeCheckApiException() throws ApiException {
        try{
            ProductPojo pojo = service.get("f");
        }
        catch (ApiException e)
        {
            assertEquals("Product not exist in inventory",e.getMessage());
        }
    }
    @Test
    public void testGetCheckApiException() throws ApiException {
        try{
            ProductPojo pojo = service.getCheck(22);
        }
        catch (ApiException e)
        {
            assertEquals("Product doesn't exist",e.getMessage());
        }
    }
    @Test
    public void testGetByBrandCategoryID() throws ApiException {
        List<ProductPojo> list = service.getAll();
        for (ProductPojo p : list) {
            BrandCategoryData d = brandCategoryDto.get(p.getBrandCategoryId());
            List<ProductPojo> list1 = service.getByBrandCategoryID(d.getId());
            int size = list1.size();
            assertEquals(1, size);
        }
    }

    @Test
    public void testGetByBarcoded() throws ApiException {
        List<ProductPojo> list = service.getAll();
        for (ProductPojo pojo : list) {
            ProductPojo p = service.get(pojo.getBarcode());
            assertEquals(name, p.getName());
            assertEquals(barcode, p.getBarcode());
            assertEquals(mrp, p.getMrp(), 0.001);
        }

    }

    @Test
    public void testUpdate() throws ApiException {
        List<ProductPojo> list = service.getAll();
        for (ProductPojo pojo : list) {
            pojo.setMrp(300);
            pojo.setName("Pencil");
            service.update(pojo.getId(), pojo);
            ProductPojo d = service.get(pojo.getId());
            assertEquals("pencil", d.getName());
            assertEquals(barcode, d.getBarcode());
            assertEquals(300, d.getMrp(), 0.001);
            assertEquals(pojo.getId(), d.getId());
        }

    }

    @Test
    public void testNormalize() throws ApiException {
        ProductPojo f = new ProductPojo();
        f.setName(name);
        normalize(f);
        assertEquals("pen", f.getName());

    }
}
