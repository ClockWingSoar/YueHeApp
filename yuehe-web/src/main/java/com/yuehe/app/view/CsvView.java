package com.yuehe.app.view;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.util.StringUtils;
import com.yuehe.app.dto.SaleClientItemSellerDTO;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class CsvView extends AbstractCsvView {


    @Override
    public void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse
            response) throws Exception {
        String tableName = "sales";            
        if (!StringUtils.isNullOrEmpty(request.getParameter("tableName"))) {
            tableName = request.getParameter("tableName");
        }
        String fileName =  "yuehe-"+tableName+"-csv";       
        if (!StringUtils.isNullOrEmpty(request.getParameter("fileName"))) {
            fileName = request.getParameter("fileName");
        }
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+".csv\"");

        List<SaleClientItemSellerDTO> objects = (List<SaleClientItemSellerDTO>) model.get("csvObjList");
        String[] header = (String[])model.get("headers");
        
        Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
        writer.write('\uFEFF'); // BOM for UTF-*
        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

        csvWriter.writeHeader(header);

        for (SaleClientItemSellerDTO saleClientItemSellerDTO : objects) {
            csvWriter.write(saleClientItemSellerDTO, header);
        }
        csvWriter.close();

    }
}
