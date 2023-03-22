package com.increff.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/ui")
public class AppUiController extends AbstractUiController {

    @RequestMapping(value = "/home")
    public ModelAndView home() {
        return mav("home.html");
    }

    @RequestMapping(value = "/supervisor")
    public ModelAndView admin() {
        return mav("user.html");
    }

    @RequestMapping(value = "/brandCategory")
    public ModelAndView brandCategory() {
        return mav("brandCategory.html");
    }

    @RequestMapping(value = "/product")
    public ModelAndView product() {
        return mav("product.html");
    }

    @RequestMapping(value = "/inventory")
    public ModelAndView inventory() {
        return mav("inventory.html");
    }

    @RequestMapping(value = "/order")
    public ModelAndView order() {
        return mav("order.html");
    }

    @RequestMapping(value = "/reports")
    public ModelAndView reports() {
        return mav("reports.html");
    }

    @RequestMapping(value = "/inventoryReport")
    public ModelAndView inventoryReport() {
        return mav("inventoryReport.html");
    }

    @RequestMapping(value = "/brandReport")
    public ModelAndView brandReport() {
        return mav("brandReport.html");
    }

    @RequestMapping(value = "/salesReport")
    public ModelAndView salesReport() {
        return mav("salesReport.html");
    }

    @RequestMapping(value = "/salesReportAllCategory")
    public ModelAndView salesReportAllCategory() {
        return mav("salesReportAllCategory.html");
    }

    @RequestMapping(value = "/dailyReport")
    public ModelAndView dailyReport() {
        return mav("dailyReport.html");
    }

}
