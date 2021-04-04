package com.wms1.addProduct;

import com.wms1.production.Production;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Repository
public interface AddProductRepo extends CrudRepository<AddProduct,Long> {

    @Modifying
    @Query(value = "insert into add_product(barcode,name_of_item,no_of_pcs,per_pcs_weight,packaging," +
            "carton_gross_weight,hsn,date)values(?1,?2,?3,?4,?5,?6,?7,?8)", nativeQuery = true)
    @Transactional
    int insertData(String barcode,String name_of_item,int no_of_pcs,float per_pcs_weight,int packaging,
                   float carton_gross_weight,String hsn,String date);


    @Query("select sk from AddProduct sk where barcode=?1 ")
    List<AddProduct> getBarcodeList(String barcode);

    @Modifying
    @Query(value = "update add_product set no_of_pcs=?1 where barcode=?2", nativeQuery = true)
    @Transactional
    int updateProduction(int no_of_pcs,String barcode);

    @Query("select sk.name_of_item from AddProduct sk")
    Set<String>getNameOfItem();

    @Query("select sum(sk.no_of_pcs) from AddProduct sk where name_of_item=?1")
    int sumOfQuantity(String name_of_item);

    @Query("select sk from AddProduct sk where name_of_item=?1")
    List<AddProduct> getDataWithNameOfItem(String name_of_item);

    @Query("select sum(sk.no_of_pcs) from AddProduct sk ")
    int sumOfQuantity();

}
