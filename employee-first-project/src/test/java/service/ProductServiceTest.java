package service;

import com.increff.employee.dto.AbstractUnitTest;
import com.increff.employee.dto.BrandCategoryDto;
import com.increff.employee.model.BrandCategoryData;
import com.increff.employee.model.BrandCategoryForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandCategoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends AbstractUnitTest {
    @Autowired
    private ProductService service;
    @Autowired
    private BrandCategoryDto brandCategoryDto;
    private final String brand="Brand";
    private final String category="Category";
    private final String name="pen";
    private final double mrp=200;
    private final String barcode="a";

    @Before
    public void setUp() throws ApiException {
        BrandCategoryForm brandCategoryForm = new BrandCategoryForm();
        brandCategoryForm.setBrand(brand);
        brandCategoryForm.setCategory(category);
        brandCategoryDto.add(brandCategoryForm);

        ProductPojo p = new ProductPojo();
        List<BrandCategoryData> list = brandCategoryDto.getAll();
        for(BrandCategoryData d:list ) {
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
        for(BrandCategoryData d:list ) {
            p.setName(name);
            p.setBarcode("b");
            p.setMrp(mrp);
            p.setBrandCategoryId(d.getId());
            service.add(p);
        }
    }
    @Test
    public void testGetAll() {
        List<ProductPojo> list= service.getAll();
        int size = list.size();
        assertEquals(1, size);
    }
    @Test
    public void testGetById() throws ApiException {
        List<ProductPojo> list= service.getAll();
        for(ProductPojo p:list) {
            ProductPojo pojo= service.get(p.getId());
            assertEquals(name, pojo.getName());
            assertEquals(barcode, pojo.getBarcode());
            assertEquals(mrp, pojo.getMrp(),0.001);
            assertEquals(p.getId(), pojo.getId());
        }
    }
    @Test
    public void testGetByBrandCategoryID() throws ApiException {
        List<ProductPojo> list= service.getAll();
        for(ProductPojo p:list) {
            BrandCategoryData d = brandCategoryDto.get(p.getBrandCategoryId());
            List<ProductPojo> list1 =service.getByBrandCategoryID(d.getId());
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
            assertEquals(mrp, p.getMrp(),0.001);
        }

    }
    @Test
    public void testUpdate() throws ApiException {
        List<ProductPojo> list = service.getAll();
        for (ProductPojo pojo : list) {
            pojo.setMrp(300);
            pojo.setName("Pencil");
            service.update(pojo.getId(),pojo);
            ProductPojo d= service.get(pojo.getId());
            assertEquals("Pencil", d.getName());
            assertEquals(barcode, d.getBarcode());
            assertEquals(300, d.getMrp(),0.001);
            assertEquals(pojo.getId(), d.getId());
        }

    }
    @Test
    public void testDelete()
    {
        List<ProductPojo> list = service.getAll();
        for (ProductPojo pojo : list) {
            service.delete(pojo.getId());
            List<ProductPojo> list2= service.getAll();
            int size = list2.size();
            assertEquals(0, size);
        }
    }
}
