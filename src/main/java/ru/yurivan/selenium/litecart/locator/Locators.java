package ru.yurivan.selenium.litecart.locator;

import org.openqa.selenium.By;

public class Locators {
    // Shop main page.
    public static final By SHOP_MAIN_PAGE_PRODUCTS_LOCATOR_MOST_POPULAR =
            By.cssSelector("#box-most-popular ul[class*=products] li");
    public static final By SHOP_MAIN_PAGE_PRODUCTS_LOCATOR_CAMPAIGNS =
            By.cssSelector("#box-campaigns ul[class*=products] li");
    public static final By SHOP_MAIN_PAGE_PRODUCTS_LOCATOR_LATEST =
            By.cssSelector("#box-latest-products ul[class*=products] li");
    public static final By SHOP_MAIN_PAGE_LOGOUT_LINK_LOCATOR = By.cssSelector("#box-account ul li:nth-child(4) a");
    public static final By SHOP_MAIN_PAGE_LOGIN_LINK_BUTTON =
            By.cssSelector("#box-account-login [name=login_form] table [name=login]");

    // Add new product page.
    public static final By ADD_NEW_PRODUCT_PAGE_TABS_LOCATOR = By.cssSelector("#content .tabs .index");
    public static final By ADD_NEW_PRODUCT_PAGE_INFORMATION_TAB_LOCATOR = By.id("tab-information");
    public static final By ADD_NEW_PRODUCT_PAGE_PRICES_TAB_LOCATOR = By.id("tab-prices");

    // Product page.
    public static final By PRODUCT_PAGE_WAIT_LOCATOR = By.id("box-product");
    public static final By PRODUCT_PAGE_PRODUCT_SIZE_CHOOSER =
            By.cssSelector("#box-product [name=buy_now_form] .options [name='options[Size]']");
    public static final By PRODUCT_PAGE_ADD_TO_CART_BUTTON_LOCATOR =
            By.cssSelector("#box-product [name=buy_now_form] [name=add_cart_product]");

    // Cart.
    public static final By CART_LOCATOR = By.id("cart");
    public static final By CART_NUMBER_OF_ITEMS_LOCATOR = By.cssSelector("#cart .quantity");
    public static final By CART_CHECKOUT_LINK_LOCATOR = By.cssSelector("#cart .link");

    // Cart page.
    public static final By CART_PAGE_WAIT_LOCATOR = By.id("checkout-cart-wrapper");
    public static final By CART_PAGE_ORDER_SUMMARY_TABLE_LOCATOR =
            By.cssSelector("#order_confirmation-wrapper [class*=dataTable]");
    public static final By CART_PAGE_ORDER_SUMMARY_TABLE_ITEMS_LOCATOR =
            By.cssSelector("#order_confirmation-wrapper [class*=dataTable] td.item");

    // Country page.
    public static final By COUNTRY_PAGE_WAIT_LOCATOR = By.id("table-zones");

    public static final By COUNTRY_CELLS_LOCATOR =
            By.cssSelector("[name=countries_form] .dataTable tr.row td:nth-child(5) a");
    public static final By COUNTRIES_WITH_GEO_ZONES_LOCATOR =
            By.xpath(
                    "//*[@name='countries_form']//*[@class='dataTable']//tr[@class='row']/td[6][text()!=0]/../td[5]/a");
    public static final By COUNTRY_ZONE_NAME_CELLS_LOCATOR =
            By.xpath("//*[@id='table-zones']//tbody/tr[not(@*)]/td[3][text()!='']");

    public static final By GEO_ZONES_COUNTRY_NAME_CELLS_LOCATORS =
            By.cssSelector("[name=geo_zones_form] .dataTable tbody tr.row td:nth-child(3) a");
    public static final By ZONE_NAME_SELECTS_LOCATOR = By.cssSelector("#table-zones tbody tr td:nth-child(3) select");

    public static final By ADMIN_PANEL_COUNTRIES_SECTION_WAIT_LOCATOR = By.cssSelector("[name=countries_form]");
    public static final By ADMIN_PANEL_CATALOG_SECTION_WAIT_LOCATOR = By.cssSelector("[name=catalog_form]");
    public static final By ADMIN_PANEL_GEO_ZONES_SECTION_WAIT_LOCATOR = By.cssSelector("[name=geo_zones_form]");

}
