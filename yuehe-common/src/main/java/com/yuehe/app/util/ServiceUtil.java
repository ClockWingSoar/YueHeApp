package com.yuehe.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yuehe.app.common.FilterDateModel;
import com.yuehe.app.common.PaginationAndSortModel;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;

public class ServiceUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(ServiceUtil.class);
	
	private static SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
	public static void buildSortOrderBeforeDBQuerying(HttpServletRequest request,PaginationAndSortModel paginationAndSortModel) {
		String page = request.getParameter("page");
		if (!StringUtils.isEmpty(page) && !StringUtils.equals("0", page)) {
			paginationAndSortModel.setPageNumber(Integer.parseInt(request.getParameter("page"))-1);
		}

		if (!StringUtils.isEmpty(request.getParameter("size"))) {
			paginationAndSortModel.setPageSize(Integer.parseInt(request.getParameter("size")));
		}
		String sort = "id,asc"; // default sort column is sale id and in ascending order
		if (!StringUtils.isEmpty(request.getParameter("sort"))) {
			sort = request.getParameter("sort");
		}
		String[] sortStr = sort.split(",");
		paginationAndSortModel.setSortProperty(sortStr[0]);
		paginationAndSortModel.setSortDirection(Direction.fromString(sortStr[1]));
	}
	public static void setBackSortOrderAfterDBQuerying(List<Sort.Order> sortOrders, Model model, String sortProperty,
	Direction sortDirection) {

		if (sortOrders != null && !sortOrders.isEmpty()) {
			int sortOrdersLen = sortOrders.size();
			Sort.Order order = sortOrders.get(sortOrdersLen - 1);
			model.addAttribute("sortProperty", order.getProperty());
			model.addAttribute("sortDirectionFlag", order.getDirection() == Sort.Direction.DESC);
		} else {// set back sortProperty and sortDirectionFlag for the column that not in
				// table,like discount, unpaidAmount,etc.
			model.addAttribute("sortProperty", sortProperty);
			model.addAttribute("sortDirectionFlag", sortDirection == Sort.Direction.DESC);

		}
	}
	public static Pageable buildPageableObj(boolean sortByJPA, Sort sort, PaginationAndSortModel paginationAndSortModel){
		Pageable pageable = null;
		Integer pageNumber = paginationAndSortModel.getPageNumber();
		Integer pageSize = paginationAndSortModel.getPageSize();
		if (sortByJPA) {

			pageable = PageRequest.of(pageNumber, pageSize,sort);
		} else {
			pageable = PageRequest.of(pageNumber, pageSize);

		}
		return pageable;
	}
	public static FilterDateModel initializeFilterDate(FilterDateModel filterDateModel){
		
		String startDate =filterDateModel.getStartDate();
		String endDate =filterDateModel.getEndDate();
		//如果不输入开始日期或结束日期，默认设为最初始的日期和今天，则可以用来查询全部记录
		// if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
		// 	return filterDateModel;
		// }else{

			if(StringUtils.isEmpty(startDate)){
				startDate = "2016-01-01";//悦和成立于2016年后半年，所以最早日期不早于2016-01-01
			}else{
				try {
				startDate = simpleDateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(startDate));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			if(StringUtils.isEmpty(endDate)){
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate localDate = LocalDate.now();
				endDate = dtf.format(localDate);
			}else{

				try {
					endDate = simpleDateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(endDate));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
			filterDateModel.setStartDate(startDate);
			filterDateModel.setEndDate(endDate);
		// }
		LOGGER.info("FitlerDateModel {}", filterDateModel);
		 return filterDateModel;
	}

	public static String convertStringArrayToString(String[] stringArray){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < stringArray.length; i++) {
			// push each string into a string builder at the end
			if (i == (stringArray.length - 1)) {
				// do not append a semicolon after the last string
				sb.append(stringArray[i]);
			} else {
				// append the stringArray and a semicolon
				sb.append(stringArray[i]);
				sb.append(";");
			}

		}
		return sb.toString();

	}

}
