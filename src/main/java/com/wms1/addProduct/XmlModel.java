package com.wms1.addProduct;

public class XmlModel {
    private String name_of_item;
    private int no_of_pcs;

    public String getName_of_item() {
        return name_of_item;
    }

    public void setName_of_item(String name_of_item) {
        this.name_of_item = name_of_item;
    }

    public int getNo_of_pcs() {
        return no_of_pcs;
    }

    public void setNo_of_pcs(int no_of_pcs) {
        this.no_of_pcs = no_of_pcs;
    }

    public XmlModel(String name_of_item, int no_of_pcs) {
        this.name_of_item = name_of_item;
        this.no_of_pcs = no_of_pcs;
    }
}
