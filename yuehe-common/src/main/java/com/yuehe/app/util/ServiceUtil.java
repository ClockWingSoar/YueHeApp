package com.yuehe.app.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yuehe.app.common.PaginationAndSortModel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;

public class ServiceUtil {
	public static void buildSortOrderBeforeDBQuerying(HttpServletRequest request,PaginationAndSortModel paginationAndSortModel) {

		if (!StringUtils.isEmpty(request.getParameter("page"))) {
			paginationAndSortModel.setPageNumber(Integer.parseInt(request.getParameter("page")));
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

}
