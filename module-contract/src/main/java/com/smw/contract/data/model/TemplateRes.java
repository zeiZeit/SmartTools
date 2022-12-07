package com.smw.contract.data.model;

import com.smw.base.bean.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;

public class TemplateRes extends BaseBean implements Serializable {
    private int current_page;
    private int pages;
    private int per_page;
    private int total;
    private ArrayList<TemplateFile> items;

    public TemplateRes() {
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<TemplateFile> getItems() {
        return items;
    }

    public void setItems(ArrayList<TemplateFile> items) {
        this.items = items;
    }
}
