package com.increff.pos.dto;

import com.increff.pos.model.BrandCategoryData;
import com.increff.pos.model.BrandCategoryForm;
import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandCategoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.increff.pos.util.ConvertFunctions.convert;
import static com.increff.pos.util.Normalise.normalize;
import static com.increff.pos.util.StringUtil.isEmpty;

@Repository
public class BrandCategoryDto {

    @Autowired
    private BrandCategoryService service;
    @Autowired
    private ProductService productService;

    public void add(BrandCategoryForm form) throws ApiException {
        BrandCategoryPojo brandCategoryPojo = convert(form);
        if (isEmpty(brandCategoryPojo.getBrand()) || isEmpty(brandCategoryPojo.getCategory())) {
            throw new ApiException("either brand or category is missing");
        }
        service.add(brandCategoryPojo);
    }

    public Long getTotalNoBrands() {
        return service.getTotalNoBrands();
    }

    public BrandCategoryData get(int id) throws ApiException {
        BrandCategoryPojo p = service.get(id);
        return convert(p);
    }

    public BrandCategoryPojo get(String brand, String category) throws ApiException {
        return service.get(brand, category);
    }

    public List<BrandCategoryPojo> get(String brand) throws ApiException {
        brand = normalize(brand);
        return service.get(brand);
    }

    public List<BrandCategoryData> getAll() {
        List<BrandCategoryPojo> brandCategoryPojoList = service.getAll();
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<BrandCategoryData>();
        for (BrandCategoryPojo p : brandCategoryPojoList) {
            brandCategoryDataList.add(convert(p));
        }
        return brandCategoryDataList;
    }

    public List<BrandCategoryData> getLimited(Integer pageNo) {
        List<BrandCategoryPojo> brandCategoryPojoList = service.getLimited(pageNo);
        List<BrandCategoryData> brandCategoryDataList = new ArrayList<BrandCategoryData>();
        for (BrandCategoryPojo p : brandCategoryPojoList) {
            brandCategoryDataList.add(convert(p));
        }
        return brandCategoryDataList;
    }

    public void update(int id, BrandCategoryForm f) throws ApiException {
        BrandCategoryPojo p = convert(f);
        service.update(id, p);
    }


}

