package com.wms1.todayIn;

import com.wms1.addProduct.AddProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TodayInRepo extends CrudRepository<TodayIn,Long> {




    @Query("select sum(sk.no_of_pcs) from TodayIn sk where name_of_item=?1 and date between ?2 and ?3")
    int sumOfQuantity(String name_of_item,String from ,String to);

    @Query("select sk.name_of_item from TodayIn sk where  date between ?1 and ?2")
    Set<String> getNameOfItem(String from,String to );

    @Query("select sk from TodayIn sk where name_of_item=?1")
    List<TodayIn> getDataWithNameOfItem(String name_of_item);

    @Query("select sum(sk.no_of_pcs) from TodayIn sk ")
    int sumOfQuantity();


    @Query("select sum(sk.no_of_pcs) from TodayIn sk where date=?1")
    int sumOfQuantity(String date);

    @Query("select sk from TodayIn sk where date=?1")
    List<TodayIn> getTodayInProduct(String date);

}
