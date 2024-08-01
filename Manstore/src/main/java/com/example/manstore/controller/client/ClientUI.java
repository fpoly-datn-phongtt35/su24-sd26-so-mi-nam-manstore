package com.example.manstore.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ClientUI {
    @RequestMapping("")
    public String home() {
        return "client/index";
    }

    @RequestMapping("/email-black-friday")
    public String emailBlackFriday() {
        return "client/email/email-black-friday";
    }

    @RequestMapping("/email-flash-sale")
    public String emailFlashSale() {
        return "client/email/email-flash-sale";
    }

    @RequestMapping("/email-order-success")
    public String emailOrderSuccess() {
        return "client/email/email-order-success";
    }

    @RequestMapping("/email-order-success-2")
    public String emailOrderSuccess2() {
        return "client/email/email-order-success-2";
    }

    @RequestMapping("/product-grid")
    public String productGrid() {
        return "client/pages/products/grid/product-grid";
    }

    @RequestMapping("/product-grid-defualt")
    public String productGridDefualt() {
        return "client/pages/products/grid/product-grid-defualt";
    }

    @RequestMapping("/product-grid-right")
    public String productGridRight() {
        return "client/pages/products/grid/product-grid-right";
    }

    @RequestMapping("/product-grid-sidebar-banner")
    public String productGridSidebarBanner() {
        return "client/pages/products/grid/product-grid-sidebar-banner";
    }

    @RequestMapping("/product-list")
    public String productList() {
        return "client/pages/products/list/product-list";
    }

    @RequestMapping("/product-list-defualt")
    public String productListDefualt() {
        return "client/pages/products/list/product-list-defualt";
    }

    @RequestMapping("/product-list-left")
    public String productListLeft() {
        return "client/pages/products/list/product-list-left";
    }

    @RequestMapping("/product-list-right")
    public String productListRight() {
        return "client/pages/products/list/product-list-right";
    }

    @RequestMapping("/product-list-sidebar-banner")
    public String productListSidebarBanner() {
        return "client/pages/products/list/product-list-sidebar-banner";
    }

    @RequestMapping("/product-details")
    public String productDetails() {
        return "client/pages/products/product-details";
    }

    @RequestMapping("/account")
    public String account() {
        return "client/pages/users/account";
    }

    @RequestMapping("/address")
    public String address() {
        return "client/shop/address";
    }

    @RequestMapping("/order")
    public String checkout() {
        return "client/shop/checkout";
    }

    @RequestMapping("/confirmation")
    public String confirmation() {
        return "client/shop/confirmation";
    }

    @RequestMapping("/order-history")
    public String orderHistory() {
        return "client/shop/order-history";
    }

    @RequestMapping("/payment")
    public String payment() {
        return "client/shop/payment";
    }

    @RequestMapping("/review")
    public String review() {
        return "client/shop/review";
    }

    @RequestMapping("/shop-cart")
    public String shopCart() {
        return "client/shop/shop-cart";
    }

    @RequestMapping("/track-order")
    public String trackOrder() {
        return "client/shop/track-order";
    }

    @RequestMapping("/wishlist")
    public String wishlist() {
        return "client/shop/wishlist";
    }

    // Khánh
    @RequestMapping("/about-us")
    public String aboutUs() {
        return "client/pages/about-us";
    }

    @RequestMapping("/faq")
    public String faq() {
        return "client/ecommerce-faq";
    }

    @RequestMapping("/contact-us")
    public String contactUs() {
        return "client/contact-us";
    }
}
