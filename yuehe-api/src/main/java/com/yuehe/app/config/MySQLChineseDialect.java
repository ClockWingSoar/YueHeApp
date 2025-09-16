package com.yuehe.app.config;
import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
 
public class MySQLChineseDialect extends MySQL8Dialect {
 
    public MySQLChineseDialect(){
        super();
        registerFunction("convert", new StandardSQLFunction("convert", StandardBasicTypes.STRING));
    }
}
