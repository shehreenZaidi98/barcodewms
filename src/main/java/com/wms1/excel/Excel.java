package com.wms1.excel;

import javax.persistence.*;

@Entity
@Table(name = "excel_upload")
public class Excel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name_of_item;
    private float per_pcs_weight;
    private int packaging;
    private float carton_net_weight;
    private float carton_gross_weight;
    private String hsn;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName_of_item() {
        return name_of_item;
    }

    public void setName_of_item(String name_of_item) {
        this.name_of_item = name_of_item;
    }

    public float getPer_pcs_weight() {
        return per_pcs_weight;
    }

    public void setPer_pcs_weight(float per_pcs_weight) {
        this.per_pcs_weight = per_pcs_weight;
    }

    public int getPackaging() {
        return packaging;
    }

    public void setPackaging(int packaging) {
        this.packaging = packaging;
    }

    public float getCarton_net_weight() {
        return carton_net_weight;
    }

    public void setCarton_net_weight(float carton_net_weight) {
        this.carton_net_weight = carton_net_weight;
    }

    public float getCarton_gross_weight() {
        return carton_gross_weight;
    }

    public void setCarton_gross_weight(float carton_gross_weight) {
        this.carton_gross_weight = carton_gross_weight;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public Excel() {
    }

    public Excel(String name_of_item, float per_pcs_weight, int packaging, float carton_net_weight, float carton_gross_weight, String hsn) {
        this.name_of_item = name_of_item;
        this.per_pcs_weight = per_pcs_weight;
        this.packaging = packaging;
        this.carton_net_weight = carton_net_weight;
        this.carton_gross_weight = carton_gross_weight;
        this.hsn = hsn;
    }
}
