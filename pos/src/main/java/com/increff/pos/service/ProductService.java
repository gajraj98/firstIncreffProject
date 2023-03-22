package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.increff.pos.util.Normalise.normalize;


@Service
@Transactional(rollbackOn = ApiException.class)
public class ProductService {

    @Autowired
    private ProductDao dao;

    // todo remove these services
    @Autowired
    private BrandCategoryService brandCategoryService;
    @Autowired
    private InventoryService inventoryService;

    public int add(ProductPojo p) throws ApiException {
        normalize(p);
        // todo remove
        BrandCategoryPojo p2 = brandCategoryService.get(p.getBrandCategoryId());
        ProductPojo pojo = dao.select(p.getBarcode());
        if (pojo != null) {
            throw new ApiException("This barcode is already used try some different");
        }
        return dao.insert(p);
    }

    // todo remove
    public void delete(int id) throws ApiException {
        InventoryPojo d = inventoryService.get(id);
        if (d.getInventory() > 0) {
            throw new ApiException("You can't delete this product its inventory is not empty");
        }
        dao.delete(id);
    }


    public ProductPojo get(int id) throws ApiException {
        ProductPojo productPojo = getCheck(id);
        if (productPojo == null) {
            throw new ApiException("Product not exist in inventory");
        }
        return productPojo;
    }

    public Long getTotalNoProducts() {
        return dao.getTotalNoProducts();
    }

    // todo variable name should start with small
    public List<ProductPojo> getByBrandCategoryID(int BrandCategoryId) {
        return dao.selectByBrandCategoryId(BrandCategoryId);
    }

    public ProductPojo get(String barcode) throws ApiException {
        ProductPojo p = dao.select(barcode);
        if (p == null) {
            throw new ApiException("Product not exist in inventory");
        }
        return p;
    }

    public List<ProductPojo> getAll() {
        return dao.selectAll();
    }

    public List<ProductPojo> getLimited(Integer pageNo) {
        return dao.selectLimited(pageNo);
    }

    public void update(int id, ProductPojo p) throws ApiException {
        normalize(p);
        ProductPojo ex = getCheck(id);
        ex.setName(p.getName());
        ex.setMrp(p.getMrp());
        dao.update(ex);
    }

    public ProductPojo getCheck(int id) throws ApiException {
        ProductPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Product doesn't exist");
        }
        return p;
    }


}
