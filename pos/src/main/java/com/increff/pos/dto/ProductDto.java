package com.increff.pos.dto;

import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandCategoryService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convert;
import static com.increff.pos.util.StringUtil.isEmpty;
import static com.increff.pos.util.StringUtil.roundOff;

@Repository
public class ProductDto {

    @Autowired
    private ProductService service;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BrandCategoryService brandCategoryService;

    public void add(ProductForm productForm) throws ApiException {
        if (productForm.getMrp() < 0) {
            throw new ApiException("Mrp can't be less then zero");
        }
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(productForm.getBrand(), productForm.getCategory());
        ProductPojo productPojo = convert(productForm, brandCategoryPojo);
        productPojo.setMrp(roundOff(productPojo.getMrp()));
        if (isEmpty(productPojo.getName()) || isEmpty(productPojo.getBarcode())) {
            throw new ApiException("is either Name or barcode is empty");
        }
        int id = service.add(productPojo);
        InventoryPojo inventoryPojo = new InventoryPojo();
        inventoryPojo.setId(id);
        inventoryService.add(inventoryPojo);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo productPojo = service.get(id);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(productPojo.getBrandCategoryId());
        return convert(productPojo, brandCategoryPojo);
    }

    public Long getTotalNoProducts() {
        return service.getTotalNoProducts();
    }

    public List<ProductPojo> getByBrandCategoryID(int BrandCategoryId) {
        return service.getByBrandCategoryID(BrandCategoryId);
    }

    public ProductData get(String barcode) throws ApiException {
        ProductPojo productPojo = service.get(barcode);
        BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(productPojo.getBrandCategoryId());
        return convert(productPojo, brandCategoryPojo);
    }

    public List<ProductData> getAll() throws ApiException {
        return createList(service.getAll());
    }

    public List<ProductData> getLimited(Integer pageNo) throws ApiException {
        return createList(service.getLimited(pageNo));
    }

    public void update(int id, ProductForm productForm) throws ApiException {
        ProductPojo productPojo = convert(productForm);
        service.update(id, productPojo);
    }

    public List<ProductData> createList(List<ProductPojo> productPojoList) throws ApiException {
        List<ProductData> productDataList = new ArrayList<ProductData>();
        for (ProductPojo p : productPojoList) {
            BrandCategoryPojo brandCategoryPojo = brandCategoryService.get(p.getBrandCategoryId());
            productDataList.add(convert(p, brandCategoryPojo));
        }
        return productDataList;
    }

}
