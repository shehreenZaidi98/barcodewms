package com.wms1.excel;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Action;
import java.util.*;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ExcelController {
    @Autowired
    ExcelRepo excelRepo;

    @PostMapping("uploadExcel")
    public String uploadExcel(@RequestParam("file") MultipartFile multipartFile) {
        Workbook workbook = null;
        try {
            workbook = (Workbook) new XSSFWorkbook(multipartFile.getInputStream());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        XSSFSheet sheet = (XSSFSheet) workbook.getSheetAt(0);
        XSSFSheet sheet2 = (XSSFSheet) workbook.getSheetAt(1);
        String response = "{\"message\":\"Unsuccessful\"}";

        for (int i = 1; i <= sheet.getLastRowNum(); ++i) {
            try {
                if (sheet.getRow(i).getCell(0).getCellType() != CellType.BLANK) {
                    try {

                        Excel excel = new Excel(sheet.getRow(i).getCell(0).getStringCellValue().trim(),
                                (float)sheet.getRow(i).getCell(1).getNumericCellValue(),
                                (int) sheet.getRow(i).getCell(2).getNumericCellValue(),
                                (float) sheet.getRow(i).getCell(3).getNumericCellValue(),
                                (float) sheet.getRow(i).getCell(4).getNumericCellValue(),
                                String.valueOf((int) sheet.getRow(i).getCell(5).getNumericCellValue()));
                        int count=sheet.getRow(i).getPhysicalNumberOfCells();
                        System.out.println(count);
                        Excel excel1 = excelRepo.save(excel);


                        if (excel.getId()!=0) {
                            response = "{\"message\":\"Successful\"}";
                        }

                    } catch (Exception e2) {
                        System.out.println("Exception :  " + e2.getMessage());
                    }
                }
            }catch (Exception e3){
                System.out.println("Exception"+e3.getMessage());
            }
        }
        return response;
    }


    @GetMapping("getNameDetails")
    public Map<String, List<Excel>>getAllList(@RequestParam("name_of_item")String name_of_item){
        List<Excel>excels=excelRepo.getNameOfItemList(name_of_item);
        HashMap<String,List<Excel>>hMap=new HashMap<>();
        hMap.put("itemDetails",excels);
        return hMap;
    }
    @GetMapping("getNameItemList")
    public Map<String,Set<String>>getNameItemList(){
        Set<String>temp=new HashSet<>();
        List<Excel>excelList= (List<Excel>) excelRepo.findAll();
        for (Excel excel:excelList){
            temp.add(excel.getName_of_item());
        }
    HashMap<String,Set<String>>hMap=new HashMap<>();
        hMap.put("ItemList",temp);
        return hMap;
    }

    @GetMapping("getAllData")
    public Map<String,List<Excel>>getExcel(){
        HashMap<String,List<Excel>>hMap=new HashMap<>();
        hMap.put("product", (List<Excel>) excelRepo.findAll());
        return hMap;
    }

}
