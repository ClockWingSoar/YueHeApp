package com.yuehe.app.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.yuehe.app.common.ClientColumnsEnum;
import com.yuehe.app.entity.Client;
import com.yuehe.app.entity.CosmeticShop;
import com.yuehe.app.util.search.SpecSearchCriteria;

import org.apache.commons.lang3.StringUtils;
public class ClientSpecification<T> extends YueHeSpecification<T> {


	private static final long serialVersionUID = -6146331518496942868L;
	

	@Override
	public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
		SpecSearchCriteria criteria = super.getCriteria();
		String key = criteria.getKey();
		ClientColumnsEnum columnsNotInClientTableSearchBy = ClientColumnsEnum.valueOf(StringUtils.remove(key.toUpperCase(),'.'));
		Join<Client,CosmeticShop> clientJoinCosmecitShop = root.join("cosmeticShop");
		Predicate predicate = null;
		switch(columnsNotInClientTableSearchBy){
			case COSMETICSHOPNAME:

			switch (criteria.getOperation()) {
				case EQUALITY:
					predicate = builder.equal(clientJoinCosmecitShop.get("name").as(String.class), criteria.getValue());
					break;
				case NEGATION:
					predicate = builder.notEqual(clientJoinCosmecitShop.get("name").as(String.class), criteria.getValue());
					break;
				case GREATER_THAN:
					predicate = builder.greaterThan(clientJoinCosmecitShop.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LESS_THAN:
					predicate = builder.lessThan(clientJoinCosmecitShop.get("name").as(String.class), criteria.getValue().toString());
					break;
				case LIKE:
					predicate =  builder.like(clientJoinCosmecitShop.get("name").as(String.class), criteria.getValue().toString());
					break;
				case STARTS_WITH:
					predicate =  builder.like(clientJoinCosmecitShop.get("name").as(String.class), criteria.getValue() + "%");
					break;
				case ENDS_WITH:
					predicate =  builder.like(clientJoinCosmecitShop.get("name").as(String.class), "%" + criteria.getValue());
					break;
				case CONTAINS:
					predicate =  builder.like(clientJoinCosmecitShop.get("name").as(String.class), "%" + criteria.getValue() + "%");
					break;
				default:
					predicate =  null;
					break;
				}
				break;

			case ID:
			case NAME:
			case AGE:
			case GENDER:
			case SYMPTOM:
			default:
				break;
			}
			return predicate;
	}

	public ClientSpecification(SpecSearchCriteria criteria) {
		super(criteria);
	}

}
