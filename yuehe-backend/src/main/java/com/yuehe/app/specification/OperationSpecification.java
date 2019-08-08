package com.yuehe.app.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.yuehe.app.common.OperationColumnsEnum;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Operation;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.entity.Tool;
import com.yuehe.app.util.search.SpecSearchCriteria;

import org.apache.commons.lang3.StringUtils;
public class OperationSpecification<T> extends YueHeSpecification<T> {


	private static final long serialVersionUID = -6146331518496942868L;
	

	@Override
	public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
		SpecSearchCriteria criteria = super.getCriteria();
		String key = criteria.getKey();
		OperationColumnsEnum columnsNotInOperationTableSearchBy = OperationColumnsEnum.valueOf(StringUtils.remove(key.toUpperCase(),'.'));
		Join<Operation,Employee> operationJoinEmployee = root.join("employee");
		Join<Operation,Tool> operationJoinTool = root.join("tool");
		Join<Operation,Sale> operationJoinSale = root.join("sale");
		Predicate predicate = null;
		switch(columnsNotInOperationTableSearchBy){
			case SALEID:

			switch (criteria.getOperation()) {
				case EQUALITY:
					predicate = builder.equal(operationJoinSale.get("id").as(String.class), criteria.getValue());
					break;
				case NEGATION:
					predicate = builder.notEqual(operationJoinSale.get("id").as(String.class), criteria.getValue());
					break;
				case GREATER_THAN:
					predicate = builder.greaterThan(operationJoinSale.get("id").as(String.class), criteria.getValue().toString());
					break;
				case LESS_THAN:
					predicate = builder.lessThan(operationJoinSale.get("id").as(String.class), criteria.getValue().toString());
					break;
				case LIKE:
					predicate =  builder.like(operationJoinSale.get("id").as(String.class), criteria.getValue().toString());
					break;
				case STARTS_WITH:
					predicate =  builder.like(operationJoinSale.get("id").as(String.class), criteria.getValue() + "%");
					break;
				case ENDS_WITH:
					predicate =  builder.like(operationJoinSale.get("id").as(String.class), "%" + criteria.getValue());
					break;
				case CONTAINS:
					predicate =  builder.like(operationJoinSale.get("id").as(String.class), "%" + criteria.getValue() + "%");
					break;
				default:
					predicate =  null;
					break;
				}
				break;
			case EMPLOYEENAME:

			switch (criteria.getOperation()) {
				case EQUALITY:
					predicate = builder.equal(operationJoinEmployee.get("name").as(String.class), criteria.getValue());
					break;
				case NEGATION:
					predicate = builder.notEqual(operationJoinEmployee.get("name").as(String.class), criteria.getValue());
					break;
				case GREATER_THAN:
					predicate = builder.greaterThan(operationJoinEmployee.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LESS_THAN:
					predicate = builder.lessThan(operationJoinEmployee.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LIKE:
					predicate =  builder.like(operationJoinEmployee.get("name").as(String.class), criteria.getValue().toString());
					break;
				case STARTS_WITH:
					predicate =  builder.like(operationJoinEmployee.get("name").as(String.class), criteria.getValue() + "%");
					break;
				case ENDS_WITH:
					predicate =  builder.like(operationJoinEmployee.get("name").as(String.class), "%" + criteria.getValue());
					break;
				case CONTAINS:
					predicate =  builder.like(operationJoinEmployee.get("name").as(String.class), "%" + criteria.getValue() + "%");
					break;
				default:
					predicate =  null;
					break;
				}
				break;
			
			case TOOLNAME:
			case OPERATEEXPENSE:

			switch (criteria.getOperation()) {
				case EQUALITY:
					predicate =  builder.equal(operationJoinTool.get("name").as(String.class), criteria.getValue());
					break;
				case NEGATION:
					predicate =  builder.notEqual(operationJoinTool.get("name").as(String.class), criteria.getValue());
					break;
				case GREATER_THAN:
					predicate =  builder.greaterThan(operationJoinTool.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LESS_THAN:
					predicate =  builder.lessThan(operationJoinTool.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LIKE:
					predicate =  builder.like(operationJoinTool.get("name").as(String.class).as(String.class), criteria.getValue().toString());
					break;
				case STARTS_WITH:
					predicate =  builder.like(operationJoinTool.get("name").as(String.class), criteria.getValue() + "%");
					break;
				case ENDS_WITH:
					predicate =  builder.like(operationJoinTool.get("name").as(String.class), "%" + criteria.getValue());
					break;
				case CONTAINS:
					predicate =  builder.like(operationJoinTool.get("name").as(String.class), "%" + criteria.getValue() + "%");
					break;
				default:
					predicate =  null;
					break;
				}	
				break;

			case ID:
			case DESCRIPTION:
			default:
				break;
			}
			return predicate;
	}

	public OperationSpecification(SpecSearchCriteria criteria) {
		super(criteria);
	}

}
