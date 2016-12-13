package com.appchina.ios.mgnt.controller.model;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Menus {
    private Menu menu;
    @JsonProperty("subMenus")
    private List<Menus> menusList;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Menus> getMenusList() {
        return menusList;
    }

    public void setMenusList(List<Menus> menusList) {
        this.menusList = menusList;
    }
}
