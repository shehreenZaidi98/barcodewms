package com.wms1.production;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductionRepo extends CrudRepository<Production,Long> {


    @Modifying
    @Query(value = "insert into production(barcode,name_of_item,per_pcs_weight,packaging," +
            "carton_gross_weight,hsn)values(?1,?2,?3,?4,?5,?6)", nativeQuery = true)
    @Transactional
    int insertData(String barcode,String name_of_item, float per_pcs_weight,int packaging,
                   float carton_gross_weight,String hsn);

    @Modifying
    @Query(value = "update production set sku=?1, batch_no=?2 where barcode=?3", nativeQuery = true)
    @Transactional
    int updateProduction(String sku,String batch_no,String barcode);

    @Query("select sk from Production sk where barcode=?1 ")
    List<Production> getBarcodeList(String barcode);


}
