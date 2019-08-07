package com.yuehe.app.view;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuehe.app.dto.SaleClientItemSellerDTO;
import com.yuehe.app.property.BaseProperty;

import org.apache.commons.lang3.StringUtils;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

public class CsvView extends AbstractCsvView {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public void buildCsvDocument(Map<String, Object> model, HttpServletRequest request, HttpServletResponse
            response) throws Exception {
        String tableName = new StringBuilder(BaseProperty.TBALE_NAME_SALE).append(BaseProperty.TABLE_EXPORT_DELIMITER).toString(); //default to sale table, other tables like operation, can pass table name here           
        if (!StringUtils.isEmpty(request.getParameter("tableName"))) {
            tableName = request.getParameter("tableName");
        }
        String DateNowString = sdf.format(new Timestamp(System.currentTimeMillis()));
        String fileName =  new StringBuilder(BaseProperty.COMPANY_NAME_SHORT).append(BaseProperty.TABLE_EXPORT_DELIMITER)
                            .append(tableName).append(DateNowString).append(BaseProperty.TABLE_EXPORT_FILETYPE_CSV).toString();  
        fileName = URLEncoder.encode(fileName,"UTF-8");//this fix the fileName showing random code issue for chinese characters, without this, you'll get file name like __-____-20190722150204
        try {
            response.setContentType("text/csv; charset=UTF-8");//you could also use "application/csv"
            // see https://stackoverflow.com/questions/18050718/utf-8-encoding-name-in-downloaded-file
            //*=UTF-8 is necessary for firefox browser, or you'll get fileName like %E6%82%A6%E5%92%8C-%E9%94%80%E5%94%AE%E4%B8%9A%E7%BB%A9-20190722150036.csv
            response.setHeader("Content-Disposition","attachment; filename*=UTF-8''"+ fileName);//this fix the fileName showing random code issue for chinese characters
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+".csv\"");
        // response.setCharacterEncoding("UTF-8");
        @SuppressWarnings("unchecked")
        List<SaleClientItemSellerDTO> objects = (List<SaleClientItemSellerDTO>) model.get("csvObjList");
        String[] header = (String[])model.get("headers");
        
        Writer writer = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);//this fix the content showing random code issue for chinese characters
        writer.write('\uFEFF'); // BOM for UTF-*
        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

        csvWriter.writeHeader(header);

        for (SaleClientItemSellerDTO saleClientItemSellerDTO : objects) {
            csvWriter.write(saleClientItemSellerDTO, header);
        }
        csvWriter.close();

    }
}
