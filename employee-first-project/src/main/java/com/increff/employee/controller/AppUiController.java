package com.increff.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
// TODO have "@RequestMapping(value = "/ui")" here as it's common for all controllers in this class
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return mav("employee.html");
	}

	@RequestMapping(value = "/ui/supervisor")
	public ModelAndView admin() {
		return mav("user.html");
	}

	// TODO Strictly use spinal case for rest paths. Not camel case. Fix everywhere else as well
	@RequestMapping(value = "/ui/brandCategory")
	public ModelAndView brandCategory() {
		return mav("brandCategory.html");
	}

	@RequestMapping(value = "/ui/product")
	public ModelAndView product() {
		return mav("product.html");
	}
	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}
	@RequestMapping(value = "/ui/order")
	public ModelAndView order() {
		return mav("order.html");
	}
	@RequestMapping(value = "/ui/reports")
	public ModelAndView reports() {
		return mav("reports.html");
	}
	@RequestMapping(value = "/ui/inventoryReport")
	public ModelAndView inventoryReport() {
		return mav("inventoryReport.html");
	}
	@RequestMapping(value = "/ui/brandReport")
	public ModelAndView brandReport() {
		return mav("brandReport.html");
	}
	@RequestMapping(value = "/ui/salesReport")
	public ModelAndView salesReport() {
		return mav("salesReport.html");
	}
	@RequestMapping(value = "/ui/salesReportAllCategory")
	public ModelAndView salesReportAllCategory() {
		return mav("salesReportAllCategory.html");
	}
	@RequestMapping(value = "/ui/dailyReport")
	public ModelAndView dailyReport() {
		return mav("dailyReport.html");
	}

}
