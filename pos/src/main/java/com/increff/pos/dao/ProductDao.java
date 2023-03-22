package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class ProductDao extends AbstractDao {


    private static final String selectId = "select p from ProductPojo p where id=:id";
    private static final String selectBarcode = "select p from ProductPojo p where barcode=:barcode";
    private static final String selectAll = "select p from ProductPojo p order by id desc";
    private static final String getTotalProducts = "select count(p) from ProductPojo p";
    private static final String selectBrandCategoryId = "select p from ProductPojo p where brandCategoryId=:brandCategoryId";

    public int insert(ProductPojo p) {
        em().persist(p);
        return p.getId();
    }

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(selectId, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<ProductPojo> selectByBrandCategoryId(int brandCategoryId) {
        TypedQuery<ProductPojo> query = getQuery(selectBrandCategoryId, ProductPojo.class);
        query.setParameter("brandCategoryId", brandCategoryId);
        return query.getResultList();
    }

    public Long getTotalNoProducts() {
        TypedQuery<Long> query = getQuery(getTotalProducts, Long.class);
        Long rows = getSingle(query);
        return rows;
    }

    @Transactional(readOnly = true)
    public ProductPojo select(String barcode) {
        TypedQuery<ProductPojo> query = getQuery(selectBarcode, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(selectAll, ProductPojo.class);
        return query.getResultList();
    }

    public List<ProductPojo> selectLimited(Integer pageNo) {
        TypedQuery<ProductPojo> query = getQuery(selectAll, ProductPojo.class);
        query.setFirstResult(10 * (pageNo - 1));
        query.setMaxResults(10);
        return query.getResultList();
    }

    public void update(ProductPojo ex) {

    }
}
