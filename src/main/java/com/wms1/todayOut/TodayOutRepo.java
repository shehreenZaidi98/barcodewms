package com.wms1.todayOut;

import com.wms1.todayIn.TodayIn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TodayOutRepo extends CrudRepository<TodayOut,Long> {


    @Query("select sum(sk.no_of_pcs) from TodayOut sk where name_of_item=?1 and date between ?2 and ?3 and cells_no=?4")
    int sumOfQuantity(String name_of_item,String from ,String to,String cells_no);

    @Query("select sum(sk.no_of_pcs) from TodayOut sk where name_of_item=?1 and date between ?2 and ?3")
    int sumOfQuantity(String name_of_item,String from ,String to);

    @Query("select sk.name_of_item from TodayOut sk where date between ?1 and ?2 ")
    Set<String> getNameOfItem(String date,String to);


    @Query("select sk.name_of_item from TodayOut sk where  cells_no=?1 and date between ?2 and ?3 ")
    Set<String> getNameOfItem(String cells_no,String date,String to);

    @Query("select sk from TodayOut sk where name_of_item=?1")
    List<TodayOut> getDataWithNameOfItem(String name_of_item);

    @Query("select sum(sk.no_of_pcs) from TodayOut sk ")
    int sumOfQuantity();


    @Query("select sum(sk.no_of_pcs) from TodayOut sk where date=?1")
    int sumOfQuantity(String date);

    @Query("select sk from TodayOut sk where date=?1")
    List<TodayOut> getTodayInProduct(String date);


}
