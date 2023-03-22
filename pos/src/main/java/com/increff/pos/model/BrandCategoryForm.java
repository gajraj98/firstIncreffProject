package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandCategoryForm {

    @NotNull
    private String brand;
    @NotNull
    private String category;

}
