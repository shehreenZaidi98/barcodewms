package com.wms1.todayOut;

import com.wms1.addProduct.AddProductModel;
import com.wms1.todayIn.TodayIn;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class TodayOutController {
    @Autowired
    TodayOutRepo todayOutRepo;

    @GetMapping("getTodayOutNameOfItem")
    public Map<String, List<AddProductModel>> getSUmOfQuantity(@RequestParam("date")String date, @RequestParam("to")String to){

        Set<String> addProductModels=todayOutRepo.getNameOfItem(date,to);
        List<AddProductModel>addProductModels1=new ArrayList<>();
        for(String nameOfProduct:addProductModels){
            int noOfPcs=todayOutRepo.sumOfQuantity(nameOfProduct,date,to);
            List<TodayOut> addProduct=todayOutRepo.getDataWithNameOfItem(nameOfProduct);
            if(addProduct.size()>0){
                AddProductModel addProductModel=new AddProductModel(addProduct.get(0).getName_of_item(),noOfPcs
                        ,addProduct.get(0).getPer_pcs_weight(),addProduct.get(0).getPackaging(),
                        addProduct.get(0).getCarton_gross_weight(),addProduct.get(0).getHsn(),addProduct.get(0).getDate());
                addProductModels1.add(addProductModel);}
        }
        HashMap<String, List<AddProductModel>> hMap=new HashMap<>();
        hMap.put("sum",addProductModels1);
        return hMap;


    }


    @GetMapping("getTodayOutProduct")
    public Map<String,List<TodayOut>>getTodayInProduct(@RequestParam("date")String date){
        List<TodayOut>todayIns=todayOutRepo.getTodayInProduct(date);
        HashMap<String,List<TodayOut>>hMap=new HashMap<>();
        hMap.put("todayIn",todayIns);
        return  hMap;
    }


    @GetMapping("getTodayOutReport")
    public void getSUmOfQuantity(HttpServletResponse response,@RequestParam("cells_no")String cells_no,
                                 @RequestParam("date")String date, @RequestParam("to")String to
                                 ) throws IOException {

        Set<String> addProductModels = todayOutRepo.getNameOfItem( cells_no,date, to);
        List<AddProductModel> addProductModels1 = new ArrayList<>();
        for (String nameOfProduct : addProductModels) {
            int noOfPcs = todayOutRepo.sumOfQuantity(nameOfProduct, date, to,cells_no);
            List<TodayOut> addProduct = todayOutRepo.getDataWithNameOfItem(nameOfProduct);
            if (addProduct.size() > 0) {
                AddProductModel addProductModel = new AddProductModel(addProduct.get(0).getName_of_item(), noOfPcs
                        , addProduct.get(0).getPer_pcs_weight(), addProduct.get(0).getPackaging(),
                        addProduct.get(0).getCarton_gross_weight(), addProduct.get(0).getHsn(), addProduct.get(0).getDate());
                addProductModels1.add(addProductModel);
            }
        }

    Workbook workbook = new HSSFWorkbook();
    HSSFCellStyle style1 = (HSSFCellStyle) workbook.createCellStyle();
    CellStyle style0 = workbook.createCellStyle();

        style0.setVerticalAlignment(VerticalAlignment.CENTER);
        style0.setAlignment(HorizontalAlignment.CENTER);

        style0.setBorderBottom(BorderStyle.THIN);
        style0.setBorderTop(BorderStyle.THIN);
        style0.setBorderLeft(BorderStyle.THIN);
        style0.setBorderRight(BorderStyle.THIN);

        style0.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style0.setFillPattern(FillPatternType.SOLID_FOREGROUND);



        style1.setAlignment(HorizontalAlignment.CENTER);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);

    org.apache.poi.ss.usermodel.Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);

    org.apache.poi.ss.usermodel.Font font1 = workbook.createFont();
        font1.setFontHeightInPoints((short) 10);

        style0.setFont((org.apache.poi.ss.usermodel.Font) font);
        style0.setWrapText(true);

        style1.setFont((org.apache.poi.ss.usermodel.Font) font1);
        style1.setWrapText(true);
    Sheet sheet1 = workbook.createSheet("Today's Data");

        sheet1.setColumnWidth(0, 5000);
        sheet1.setColumnWidth(5, 5000);
        sheet1.setColumnWidth(4, 5000);
        sheet1.setColumnWidth(3, 5000);
        sheet1.setColumnWidth(2, 5000);
        sheet1.setColumnWidth(1, 5000);
        sheet1.setColumnWidth(6, 5000);
         sheet1.setColumnWidth(7, 5000);


    Row row0 = sheet1.createRow(0);
        row0.setHeight((short) 600);

    Cell cell0 = row0.createCell(0);
    Cell cell1 = row0.createCell(1);
    Cell cell2 = row0.createCell(2);
    Cell cell3 = row0.createCell(3);
    Cell cell4 = row0.createCell(4);
    Cell cell5 = row0.createCell(5);
    Cell cell6 = row0.createCell(6);
     Cell cell7 = row0.createCell(7);



        cell0.setCellStyle(style0);
        cell1.setCellStyle(style0);
        cell2.setCellStyle(style0);
        cell3.setCellStyle(style0);
        cell4.setCellStyle(style0);
        cell5.setCellStyle(style0);
        cell6.setCellStyle(style0);
        cell7.setCellStyle(style0);


        cell0.setCellValue("SL NO");
    //  cell1.setCellValue("BARCODE");
        cell1.setCellValue("NAME OF ITEM");
        cell2.setCellValue("NOM OF PCS");
        cell3.setCellValue("PER PCS WEIGHT ");
        cell4.setCellValue("PACKAGING");
        cell5.setCellValue("CARTOON GROSS WEIGHT");
        cell6.setCellValue("HSN");
        cell7.setCellValue("CELL NO");
         int rowCount = 1;
       for (AddProductModel addProductModel : addProductModels1) {
        Row row1 = sheet1.createRow(rowCount++);
        Cell slNo = row1.createCell(0);
        //  Cell barcode = row1.createCell(1);
        Cell nameOfItem = row1.createCell(1);
        Cell perPcsWeight = row1.createCell(2);
        Cell noOfPcs = row1.createCell(3);
        Cell packaging = row1.createCell(4);
        Cell cartoonGrossWeight = row1.createCell(5);
        Cell hsn = row1.createCell(6);
        Cell cellno = row1.createCell(7);



        slNo.setCellStyle(style1);
        // barcode.setCellStyle(style1);
        nameOfItem.setCellStyle(style1);
        noOfPcs.setCellStyle(style1);
        packaging.setCellStyle(style1);
        cartoonGrossWeight.setCellStyle(style1);
        perPcsWeight.setCellStyle(style1);
        hsn.setCellStyle(style1);
           cellno.setCellStyle(style1);


        slNo.setCellValue(rowCount - 1);
        nameOfItem.setCellValue(addProductModel.getName_of_item());
        noOfPcs.setCellValue(addProductModel.getNo_of_pcs());
        perPcsWeight.setCellValue(addProductModel.getPer_pcs_weight());
        packaging.setCellValue(addProductModel.getPackaging());
        cartoonGrossWeight.setCellValue(addProductModel.getCarton_gross_weight());
        hsn.setCellValue(addProductModel.getHsn());

    }
        response.setHeader("content-disposition", "attachment;filename=Production Report_"+ date + cells_no+".xls");
        workbook.write(response.getOutputStream());


}








}
