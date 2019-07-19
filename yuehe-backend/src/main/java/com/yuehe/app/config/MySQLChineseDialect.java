package com.yuehe.app.config;
import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;
 
public class MySQLChineseDialect extends MySQL8Dialect {
 
    public void MySQLLocalDialect(){
        registerFunction("convert",new SQLFunctionTemplate(StringType.INSTANCE, "convert(?1 using ?2)") );
    }
}
