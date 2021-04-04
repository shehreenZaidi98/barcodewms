package com.wms1.excel;

import com.wms1.addProduct.AddProduct;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExcelRepo extends CrudRepository<Excel,Long> {

    @Query("select sk from Excel sk where name_of_item=?1 ")
    List<Excel> getNameOfItemList(String name_of_item);
}
