package com.increff.pos.dto;

import java.util.ArrayList;
import java.util.List;

import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;

import static com.increff.pos.util.ConvertFunctions.convert;

@Repository
public class InventoryDto {

	@Autowired
	private InventoryService service;
	@Autowired
	private ProductService productService;

	public InventoryData get(int id) throws ApiException {
		InventoryPojo p = service.get(id);
		ProductPojo productPojo = productService.get(p.getId());
		return convert(p,productPojo);
	}
	public Long getTotalNoInventory() {

		return service.getTotalNoInventory();
	}
	public List<InventoryData> getAll() throws ApiException {
		return  conversion(service.getAll());
	}
	public List<InventoryData> getLimited(Integer pageNo) throws ApiException {
		return  conversion(service.getLimited(pageNo));
	}

	public void update(String barcode, InventoryForm f) throws ApiException {
		ProductPojo data = productService.get(barcode);
		InventoryPojo p = convert(f);
		service.update(data.getId(), p);
	}
	public void update(int id, InventoryForm f) throws ApiException {
		InventoryPojo p = convert(f);
		service.update(id, p);
	}
    public List<InventoryData> conversion(List<InventoryPojo> list) throws ApiException {
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for (InventoryPojo p : list) {
			ProductPojo productPojo = productService.get(p.getId());
			list2.add(convert(p,productPojo));
		}
		return list2;
	}

}

