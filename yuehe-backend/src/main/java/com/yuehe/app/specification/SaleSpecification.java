package com.yuehe.app.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.yuehe.app.entity.BeautifySkinItem;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.entity.Employee;
import com.yuehe.app.entity.Sale;
import com.yuehe.app.util.search.SpecSearchCriteria;
import com.yuehe.app.common.SaleColumnsEnum;

import org.apache.commons.lang3.StringUtils;

import ch.qos.logback.core.net.server.Client;
//just trying to parameterized to Sale entity, all the work is done in YueHeSpecification class
public class SaleSpecification<T> extends YueHeSpecification<T> {


	private static final long serialVersionUID = -6146331518496942868L;
	// private SpecSearchCriteria criteria;
	

	@Override
	public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
		SpecSearchCriteria criteria = super.getCriteria();
			// SetJoin join1 = root.join(T.embeddedEntity);
		// predicates.add(builder.equal(join1.get(EmbeddedEntity_.field1), value);
		String key = criteria.getKey();
		SaleColumnsEnum columnsNotInSaleTableSearchBy = SaleColumnsEnum.valueOf(StringUtils.remove(key.toUpperCase(),'.'));
		Join<Sale,Employee> saleJoinEmployee = root.join("employee");
		Join<Sale,Client> saleJoinClient = root.join("client");
		Join<Sale,CosmeticShop> saleJoinCosmeticShop = saleJoinClient.join("cosmeticShop");
		Join<Sale,BeautifySkinItem> saleJoinBeautifySkinItem = root.join("beautifySkinItem");
		Predicate predicate = null;
		switch(columnsNotInSaleTableSearchBy){
			case EMPLOYEENAME:

			switch (criteria.getOperation()) {
				case EQUALITY:
					predicate = builder.equal(saleJoinEmployee.get("name").as(String.class), criteria.getValue());
					break;
				case NEGATION:
					predicate = builder.notEqual(saleJoinEmployee.get("name").as(String.class), criteria.getValue());
					break;
				case GREATER_THAN:
					predicate = builder.greaterThan(saleJoinEmployee.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LESS_THAN:
					predicate = builder.lessThan(saleJoinEmployee.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LIKE:
					predicate =  builder.like(saleJoinEmployee.get("name").as(String.class), criteria.getValue().toString());
					break;
				case STARTS_WITH:
					predicate =  builder.like(saleJoinEmployee.get("name").as(String.class), criteria.getValue() + "%");
					break;
				case ENDS_WITH:
					predicate =  builder.like(saleJoinEmployee.get("name").as(String.class), "%" + criteria.getValue());
					break;
				case CONTAINS:
					predicate =  builder.like(saleJoinEmployee.get("name").as(String.class), "%" + criteria.getValue() + "%");
					break;
				default:
					predicate =  null;
					break;
				}
				break;
			
			case CLIENTCOSMETICSHOPNAME:

			switch (criteria.getOperation()) {
				case EQUALITY:
					predicate =  builder.equal(saleJoinCosmeticShop.get("name").as(String.class), criteria.getValue());
					break;
				case NEGATION:
					predicate =  builder.notEqual(saleJoinCosmeticShop.get("name").as(String.class), criteria.getValue());
					break;
				case GREATER_THAN:
					predicate =  builder.greaterThan(saleJoinCosmeticShop.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LESS_THAN:
					predicate =  builder.lessThan(saleJoinCosmeticShop.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LIKE:
					predicate =  builder.like(saleJoinCosmeticShop.get("name").as(String.class).as(String.class), criteria.getValue().toString());
					break;
				case STARTS_WITH:
					predicate =  builder.like(saleJoinCosmeticShop.get("name").as(String.class), criteria.getValue() + "%");
					break;
				case ENDS_WITH:
					predicate =  builder.like(saleJoinCosmeticShop.get("name").as(String.class), "%" + criteria.getValue());
					break;
				case CONTAINS:
					predicate =  builder.like(saleJoinCosmeticShop.get("name").as(String.class), "%" + criteria.getValue() + "%");
					break;
				default:
					predicate =  null;
					break;
				}	
				break;

			case CLIENTNAME:
			switch (criteria.getOperation()) {
				case EQUALITY:
					predicate =  builder.equal(saleJoinClient.get("name").as(String.class), criteria.getValue());
					break;
				case NEGATION:
					predicate =  builder.notEqual(saleJoinClient.get("name").as(String.class), criteria.getValue());
					break;
				case GREATER_THAN:
					predicate =  builder.greaterThan(saleJoinClient.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LESS_THAN:
					predicate =  builder.lessThan(saleJoinClient.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LIKE:
					predicate =  builder.like(saleJoinClient.get("name").as(String.class), criteria.getValue().toString());
					break;
				case STARTS_WITH:
					predicate =  builder.like(saleJoinClient.get("name").as(String.class), criteria.getValue() + "%");
					break;
				case ENDS_WITH:
					predicate =  builder.like(saleJoinClient.get("name").as(String.class), "%" + criteria.getValue());
					break;
				case CONTAINS:
					predicate =  builder.like(saleJoinClient.get("name").as(String.class), "%" + criteria.getValue() + "%");
					break;
				default:
					predicate =  null;
					break;
				}
				break;
			case BEAUTIFYSKINITEMNAME:

			switch (criteria.getOperation()) {
				case EQUALITY:
					predicate =  builder.equal(saleJoinBeautifySkinItem.get("name").as(String.class), criteria.getValue());
					break;
				case NEGATION:
					predicate =  builder.notEqual(saleJoinBeautifySkinItem.get("name").as(String.class), criteria.getValue());
					break;
				case GREATER_THAN:
					predicate =  builder.greaterThan(saleJoinBeautifySkinItem.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LESS_THAN:
					predicate =  builder.lessThan(saleJoinBeautifySkinItem.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LIKE:
					predicate =  builder.like(saleJoinBeautifySkinItem.get("name").as(String.class), criteria.getValue().toString());
					break;
				case STARTS_WITH:
					predicate =  builder.like(saleJoinBeautifySkinItem.get("name").as(String.class), criteria.getValue() + "%");
					break;
				case ENDS_WITH:
					predicate =  builder.like(saleJoinBeautifySkinItem.get("name").as(String.class), "%" + criteria.getValue());
					break;
				case CONTAINS:
					predicate =  builder.like(saleJoinBeautifySkinItem.get("name").as(String.class), "%" + criteria.getValue() + "%");
					break;
				default:
					predicate =  null;
					break;
				}
				break;
			case DISCOUNT:
			case UNPAIDAMOUNT:
			case EARNEDAMOUNT:
			case UNPAIDEARNEDAMOUNT:
			case ID:
			case CREATECARDDATE:
			case CREATECARDTOTALAMOUNT:
			case ITEMNUMBER:
			case RECEIVEDAMOUNT:
			case RECEIVEDEARNEDAMOUNT:
			case EMPLOYEEPREMIUM:
			case SHOPPREMIUM:
			case DESCRIPTION:
			default:
				break;
			}
			return predicate;
	}

	public SaleSpecification(SpecSearchCriteria criteria) {
		super(criteria);
	}

}
