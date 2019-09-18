package com.yuehe.app.property;

public interface BaseProperty {
	public static final String COMPANY_NAME = "悦和国际";
	public static final String COMPANY_NAME_SHORT = "悦和";
	public static final String COMPANY_NAME_OFFICIAL = "江苏忠翼美健康咨询有限公司";
    public static final String COMPANY_OWNER = "钟悦旻";
    public static final String YUEHE_ROLE_EXPERT = "专家";
    public static final String YUEHE_ROLE_OPERATOR = "操作人";
    
    public static final String TBALE_NAME_SALE = "销售业绩";
    public static final String TBALE_NAME_SALE_SHORT = "销售";
    public static final String TBALE_NAME_OPERATION = "操作详情";
    public static final String TBALE_NAME_OPERATION_SHORT = "操作";
    

    public static final String TABLE_EXPORT_DELIMITER = "-";
    public static final String TABLE_EXPORT_FILETYPE_CSV = ".csv";
    public static final String TABLE_EXPORT_FILETYPE_EXCEL = ".xls";
    public static final String TABLE_EXPORT_FILETYPE_PDF = ".pdf";
    
    public static final String ID_TYPE_PREFIX_SALE = "xs";//销售 - xiao shou - xs
    public static final String ID_TYPE_PREFIX_OPERATION = "cz";//操作 - cao zuo - cz
    public static final String ID_TYPE_PREFIX_TOOL = "gj";//仪器 - gong ju - gj
    public static final String ID_TYPE_PREFIX_CLIENT = "kh";//客户 - ke hu - kh
    public static final String ID_TYPE_PREFIX_COSMETICSHOP = "mr";//美容(院) - mei rong - mr
    public static final String ID_TYPE_PREFIX_EMPLOYEE = "yg";//员工 - yuan gong - yg
    public static final String ID_TYPE_PREFIX_USER = "yh";//用户 - yong hu - yh
    public static final String ID_TYPE_PREFIX_BEAUTIFYSKINITEM = "xm";//(美容)项目 - xiang mu - xm
    public static final String ID_TYPE_PREFIX_ROLE = "js";//角色 - jue se - js
    public static final String ID_TYPE_PREFIX_DUTY = "zz";//职责 - zhi ze - zz

    public static final String REFUND_TYPE_CHANGE_DISCOUNT = "changeDiscount";//更改回款的折扣点
    public static final String REFUND_TYPE_REACH_CERTAIN_AMOUNT = "refundToShopTillCertainAmount";//当总业绩达到一定数额时需回给店家相应的回扣
    public static final String REFUND_TYPE_AWARD_EMPLOYEE_PER_SALE = "awardEmployeePerSale";//每卖出一张销售卡，需给店里员工相应的奖励
    public static final String REFUND_TYPE_AWARD_EMPLOYEE_PER_TRYOUT = "awardEmployeePerTryout";//每卖出一张体验卡，需给店里员工相应的奖励
    public static final String REFUND_TYPE_EARNED_AMOUNT_PER_TRYOUT = "earnedAmountPerTryout";//每卖出一张体验卡，需给悦和回的款
    

}
