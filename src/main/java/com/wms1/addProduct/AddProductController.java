package com.wms1.addProduct;

import com.wms1.todayIn.TodayIn;
import com.wms1.todayIn.TodayInRepo;
import com.wms1.todayOut.TodayOut;
import com.wms1.todayOut.TodayOutRepo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AddProductController {
    @Autowired
    AddProductRepo addProductRepo;
    @Autowired
    TodayOutRepo todayOutRepo;
    @Autowired
    TodayInRepo todayInRepo;
    @PostMapping("insertAddProduct")
    public String insertAddProduct(@RequestBody AddProduct addProduct){
        String message="{\"message\":\"UnSuccessful\"}";
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<AddProduct>addProductList=addProductRepo.getBarcodeList(addProduct.getBarcode());
        if(addProductList.size()>0){
            message  = "{\"message\":\"Already Exist\"}";
        }else {
            int insert = addProductRepo.insertData(addProduct.getBarcode(), addProduct.getName_of_item(), addProduct.getNo_of_pcs(),
                    addProduct.getPer_pcs_weight(), addProduct.getPackaging(),  addProduct.getCarton_gross_weight(),
                    addProduct.getHsn(),sdf.format(date));
            if (insert > 0) {
                message ="{\"message\":\"Successful\"}";
            }
            TodayIn todayIn=new TodayIn();
            todayIn.setBarcode(addProduct.getBarcode());
            todayIn.setName_of_item(addProduct.getName_of_item());
            todayIn.setNo_of_pcs(addProduct.getNo_of_pcs());
            todayIn.setPer_pcs_weight(addProduct.getPer_pcs_weight());
            todayIn.setPackaging(addProduct.getPackaging());
            todayIn.setCarton_gross_weight(addProduct.getCarton_gross_weight());
            todayIn.setHsn(addProduct.getHsn());
            todayIn.setDate(sdf.format(date));
            todayInRepo.save(todayIn);
        }
        return message;
    }


    @GetMapping("getAllList")
    public Map<String, List<AddProduct>> getBarcode(){
        List<AddProduct>addProducts= (List<AddProduct>) addProductRepo.findAll();
        HashMap<String,List<AddProduct>> hMap=new HashMap<>();
        hMap.put("barcodeList",addProducts);
        return hMap;
    }



    @PostMapping("updateStockData")
    public String getBarcodeProduct(@RequestBody AddProduct addProduct,@RequestParam("cells_no")String cells_no){
        String message="{\"message\":\"UnSuccessful\"}";
        Date date = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List<AddProduct>addProducts=addProductRepo.getBarcodeList(addProduct.getBarcode());
        if(addProducts.size()>0){
            int update=addProductRepo.updateProduction(addProducts.get(0).getNo_of_pcs()-addProduct.getNo_of_pcs(),
                    addProduct.getBarcode());
            if(update>0){
                message="{\"message\":\"Updated\"}";
            }
            TodayOut todayOut=new TodayOut();
            todayOut.setBarcode(addProduct.getBarcode());
            todayOut.setName_of_item(addProduct.getName_of_item());
            todayOut.setNo_of_pcs(addProduct.getNo_of_pcs());
            todayOut.setPer_pcs_weight(addProduct.getPer_pcs_weight());
            todayOut.setPackaging(addProduct.getPackaging());
            todayOut.setCarton_gross_weight(addProduct.getCarton_gross_weight());
            todayOut.setHsn(addProduct.getHsn());
            todayOut.setDate(sdf.format(date));
            todayOut.setCells_no(cells_no);
            todayOutRepo.save(todayOut);

        }
        return message;
    }

    @GetMapping("getNameOfItem")
    public Map<String, List<AddProductModel>>getSUmOfQuantity(){
        Set<String> addProductModels=addProductRepo.getNameOfItem();
        List<AddProductModel>addProductModels1=new ArrayList<>();
        for(String nameOfProduct:addProductModels){
            int noOfPcs=addProductRepo.sumOfQuantity(nameOfProduct);
            List<AddProduct> addProduct=addProductRepo.getDataWithNameOfItem(nameOfProduct);
            if(addProduct.size()>0){
            AddProductModel addProductModel=new AddProductModel(addProduct.get(0).getName_of_item(),noOfPcs
            ,addProduct.get(0).getPer_pcs_weight(),addProduct.get(0).getPackaging(),
                    addProduct.get(0).getCarton_gross_weight(),addProduct.get(0).getHsn(),addProduct.get(0).getDate());
            addProductModels1.add(addProductModel);}
        }
        HashMap<String, List<AddProductModel>> hMap=new HashMap<>();
        hMap.put("Stock",addProductModels1);
        return hMap;


    }

    @GetMapping("getTotalInTotalOut")
    public Map<String,List<Map<String,Integer>>>getTotalInOut() {
        int totalOut = todayOutRepo.sumOfQuantity();
        List<Map<String, Integer>> mainList = new ArrayList<>();
        Map<String, Integer> addCount = new HashMap<>();
        addCount.put("TotalOut", totalOut);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int totalIn = todayOutRepo.sumOfQuantity(sdf.format(date));
        addCount.put("totalIn", totalIn);

        int todayIn = todayInRepo.sumOfQuantity();
        addCount.put("todayIn", todayIn);

        int todayTotalIn = todayInRepo.sumOfQuantity(sdf.format(date));
        addCount.put("todayTotalIn", todayTotalIn);

        mainList.add(addCount);
        Map<String, List<Map<String, Integer>>> hMap = new HashMap<>();
        hMap.put("data", mainList);
        return hMap;

    }

    @GetMapping("generateExcelStock")
    public void generateExcelStock(HttpServletResponse response) throws IOException {
        Set<String> addProductModels = addProductRepo.getNameOfItem();
        List<AddProductModel> addProductModels1 = new ArrayList<>();
        for (String nameOfProduct : addProductModels) {
            int noOfPcs = addProductRepo.sumOfQuantity(nameOfProduct);
            List<AddProduct> addProduct = addProductRepo.getDataWithNameOfItem(nameOfProduct);
            if (addProduct.size() > 0) {
                AddProductModel addProductModel = new AddProductModel(addProduct.get(0).getName_of_item(), noOfPcs
                        , addProduct.get(0).getPer_pcs_weight(), addProduct.get(0).getPackaging(),
                        addProduct.get(0).getCarton_gross_weight(),addProduct.get(0).getHsn(), addProduct.get(0).getDate());
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


        Row row0 = sheet1.createRow(0);
        row0.setHeight((short) 600);

        Cell cell0 = row0.createCell(0);
        Cell cell1 = row0.createCell(1);
        Cell cell2 = row0.createCell(2);
        Cell cell3 = row0.createCell(3);
        Cell cell4 = row0.createCell(4);
        Cell cell5 = row0.createCell(5);
        Cell cell6 = row0.createCell(6);


        cell0.setCellStyle(style0);
        cell1.setCellStyle(style0);
        cell2.setCellStyle(style0);
        cell3.setCellStyle(style0);
        cell4.setCellStyle(style0);
        cell5.setCellStyle(style0);
        cell6.setCellStyle(style0);

        cell0.setCellValue("SL NO");
       cell1.setCellValue("NAME OF ITEM");
        cell2.setCellValue("NOM OF PCS");
        cell3.setCellValue("PER PCS WEIGHT ");
        cell4.setCellValue("PACKAGING");
        cell5.setCellValue("CARTOON GROSS WEIGHT");
        cell6.setCellValue("HSN");
        int rowCount = 1;

        for (AddProductModel addProductModel : addProductModels1) {
            Row row1 = sheet1.createRow(rowCount++);
            Cell slNo = row1.createCell(0);
            Cell nameOfItem = row1.createCell(1);
            Cell perPcsWeight = row1.createCell(2);
            Cell noOfPcs = row1.createCell(3);
            Cell packaging = row1.createCell(4);
            Cell cartoonGrossWeight = row1.createCell(5);
            Cell hsn = row1.createCell(6);


            slNo.setCellStyle(style1);
            nameOfItem.setCellStyle(style1);
            noOfPcs.setCellStyle(style1);
            packaging.setCellStyle(style1);
            cartoonGrossWeight.setCellStyle(style1);
            perPcsWeight.setCellStyle(style1);
            hsn.setCellStyle(style1);

            slNo.setCellValue(rowCount - 1);
            nameOfItem.setCellValue(addProductModel.getName_of_item());
            noOfPcs.setCellValue(addProductModel.getNo_of_pcs());
            perPcsWeight.setCellValue(addProductModel.getPer_pcs_weight());
            packaging.setCellValue(addProductModel.getPackaging());
            cartoonGrossWeight.setCellValue(addProductModel.getCarton_gross_weight());
            hsn.setCellValue(addProductModel.getHsn());
        }
        response.setHeader("content-disposition", "attachment;filename=Production Report_"  + ".xls");
        workbook.write(response.getOutputStream());


    }

    @GetMapping("getXmlModelData")
    public Map<String, List<XmlModel>>getXmlData(){
        Set<String> xmlModels=addProductRepo.getNameOfItem();
        List<XmlModel>xmlModels1=new ArrayList<>();
        for(String nameOfProduct:xmlModels){
            int noOfPcs=addProductRepo.sumOfQuantity(nameOfProduct);
            List<AddProduct> addProduct=addProductRepo.getDataWithNameOfItem(nameOfProduct);
            if(addProduct.size()>0){
                XmlModel xmlModel=new XmlModel(addProduct.get(0).getName_of_item(),noOfPcs);

                xmlModels1.add(xmlModel);}
        }
        HashMap<String, List<XmlModel>> hMap=new HashMap<>();
        hMap.put("sum",xmlModels1);
        return hMap;


    }






}
