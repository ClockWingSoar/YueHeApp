package com.yuehe.app.util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yuehe.app.property.BaseProperty;

public class YueHeUtil {
	 private final static Logger LOGGER = LoggerFactory.getLogger(YueHeUtil.class);
	private static int commonIdLen1 = 5;//for table BeautifySkinItem, cosmeticShop,employee, role,tool
	private static int commonIdLen2 = 7;//for table client, sale
	private static int commonIdLen3 = 8;//for table operation
	private static String zero = "0";// to build the mid part of the id
	
	public static String getId(IdType idType, int idNumInDb) {
		// IdType idType = IdType.values()[idTypeIndex];//by the index find out the id Type
		String id = "";
		//all yuehe table ids start with two characters, like "xm" for BeautifySkinItem table ,
		//"kh" for Client table,"mr" for cosmeticShop table, "yg" for employee table,"js" for role table
		//"cz" for Operation table, "xs" for sale table, "gj" for tool table
		int idFinalPart = idNumInDb+1;//get the incremented id part.
		LOGGER.debug("GetId-"+"-"+idType+"-"+idNumInDb);
		switch(idType) {
		case BEAUTIFY_SKIN_ITEM:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_BEAUTIFYSKINITEM,calculateMidZeros(commonIdLen1,idFinalPart),idFinalPart);
			break;
		case CLIENT:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_CLIENT,calculateMidZeros(commonIdLen2,idFinalPart),idFinalPart);
			break;
		case COSMETIC_SHOP:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_COSMETICSHOP,calculateMidZeros(commonIdLen1,idFinalPart),idFinalPart);
			break;
		case EMPLOYEE:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_EMPLOYEE,calculateMidZeros(commonIdLen1,idFinalPart),idFinalPart);
			break;
		case OPERATION:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_OPERATION,calculateMidZeros(commonIdLen3,idFinalPart),idFinalPart);
			break;
		case ROLE:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_ROLE,calculateMidZeros(commonIdLen1,idFinalPart),idFinalPart);
			break;
		case SALE:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_SALE,calculateMidZeros(commonIdLen2,idFinalPart),idFinalPart);
			break;
		case TOOL:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_TOOL,calculateMidZeros(commonIdLen1,idFinalPart),idFinalPart);
			break;
		case USER:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_USER,calculateMidZeros(commonIdLen1,idFinalPart),idFinalPart);
			break;
		case DUTY:
			id = buildId(BaseProperty.ID_TYPE_PREFIX_DUTY,calculateMidZeros(commonIdLen1,idFinalPart),idFinalPart);
			break;
		default:
			break;
			
			
			
		}
		return id;
		
	}
	private static String buildId(String idTypeStr, int idMidPart, int idFinalPart)
	{
		LOGGER.debug("buildId-"+"-"+idTypeStr+"-"+idMidPart+"-"+idFinalPart);
		return idTypeStr.concat(getZeros(idMidPart)).concat(Integer.valueOf(idFinalPart).toString());
	}
	private static int calculateMidZeros(int commonIdLen, int idFinalPart) {
		int idFinalPartLen = String.valueOf(idFinalPart).length();
		return commonIdLen-2-idFinalPartLen;
	}
	private static String getZeros(int num) {
		
		StringBuilder zeros=new StringBuilder();
		LOGGER.debug("zero-"+zero);
		LOGGER.debug("zeros-"+zeros);
		for(int i = 0; i < num; i++) {
			zeros.append(zero);
		}
		LOGGER.debug("getZeros-"+num+"-"+zeros);
		return zeros.toString();
	}
	/**
	 * to extract the digital number of a String id, to be able to increase it based on it
	 * @param id
	 * @return
	 */
	public static int extractIdDigitalNumber(String id){
		int IdNum = new Integer(id.substring(2));
		return IdNum;
	}
	
	public static final String START_ARRAY = "[";
	public static final String END_ARRAY = "]";
	private static String[] randomValues = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

	/**
	 * 
	 * 生成游戏批次号工具类
	 */
	public static String bacthNo() {
		Date nowTime = new Date();
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		return time.format(nowTime);
	}

	public static String getSixRandom() {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			Double number = Math.random() * (randomValues.length - 1);
			str.append(randomValues[number.intValue()]);
		}

		return str.toString();
	}

	/**
	 * 根据requet获取ip地址.
	 *
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@SuppressWarnings("unchecked")
	public static final <T> List<T> json2listT(String jsonStr, Class<T> tC) {
		// json字符串不能为空
		if (StringUtils.isBlank(jsonStr))
			return null;
		// json字符串必须为数组节点类型
		if (!(jsonStr.startsWith(START_ARRAY) && jsonStr.endsWith(END_ARRAY)))
			return null;
		List<T> listT = null;
		try {
			// 创建泛型对象
			T t = tC.newInstance();
			// 利用类加载加载泛型的具体类型
			Class<T> classT = (Class<T>) Class.forName(t.getClass().getName());
			List<Object> listObj = new ArrayList<Object>();
			// 将数组节点中json字符串转换为object对象到Object的list集合
			listObj = new GsonBuilder().create().fromJson(jsonStr, new TypeToken<List<Object>>() {
			}.getType());
			// 转换未成功
			if (listObj == null || listObj.isEmpty())
				return null;
			listT = new ArrayList<T>();
			// 将Object的list中的的每一个元素中的json字符串转换为泛型代表的类型加入泛型代表的list集合返回
			for (Object obj : listObj) {
				T perT = new GsonBuilder().create().fromJson(obj.toString(), classT);
				listT.add(perT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listT;
	}
	public static String dateToStr(Date dateDate) {
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		   String dateString = formatter.format(dateDate);
		   return dateString;
}
	
	
}
