package com.wms1.todayOut;

import javax.persistence.*;

@Entity
@Table(name = "today_out")
public class TodayOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String barcode;
    private String name_of_item;
    private int no_of_pcs;
    private float per_pcs_weight;
    private int packaging;
    private float carton_gross_weight;
    private String hsn;
    private String date;
    private String cells_no;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCells_no() {
        return cells_no;
    }

    public void setCells_no(String cells_no) {
        this.cells_no = cells_no;
    }
}
