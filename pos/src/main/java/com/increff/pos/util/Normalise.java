package com.increff.pos.util;

import com.increff.pos.pojo.BrandCategoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.pojo.UserPojo;

public class Normalise {
    public static void normalize(BrandCategoryPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()).trim());
        p.setCategory(StringUtil.toLowerCase(p.getCategory()).trim());
    }

    // todo recheck
    public static String normalize(String brand) {
        brand = StringUtil.toLowerCase(brand).trim();
        return brand;
    }

    // todo is this used?
    public static void normalize(String brnad, String category) {
        brnad = StringUtil.toLowerCase(brnad).trim();
        category = StringUtil.toLowerCase(category).trim();
    }

    public static void normalize(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()).trim());
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
    }

    public static void normalize(UserPojo p) {
        p.setEmail(p.getEmail().toLowerCase().trim());
        p.setRole(p.getRole().toLowerCase().trim());
    }

}
