package com.yuehe.app.specification;

import java.util.ArrayList;
import java.util.List;

import com.yuehe.app.common.ClientColumnsEnum;
import com.yuehe.app.common.OperationColumnsEnum;
import com.yuehe.app.common.SaleColumnsEnum;
import com.yuehe.app.common.YueHeEntitiesEnum;
import com.yuehe.app.util.search.SearchOperation;
import com.yuehe.app.util.search.SpecSearchCriteria;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public final class YueHeSpecificationsBuilder<T> {

    private final List<SpecSearchCriteria> params;
    private final YueHeEntitiesEnum entityType;

    public YueHeSpecificationsBuilder(YueHeEntitiesEnum entityType) {
        params = new ArrayList<>();
        this.entityType = entityType;
    }
    public final List<SpecSearchCriteria> getParams()
    {
        return params;
    }

    // API

    public final  YueHeSpecificationsBuilder<T> with(final String key, final String operation, final Object value, final String prefix, final String suffix) {
        return with(null, key, operation, value, prefix, suffix);
    }

    public final YueHeSpecificationsBuilder<T> with(final String orPredicate, final String key, final String operation, final Object value, final String prefix, final String suffix) {
        SearchOperation op = SearchOperation.getSimpleOperation(operation.charAt(0));
        if (op != null) {
            if (op == SearchOperation.CONTAINS) { // the operation may be complex operation
                final boolean startWithAsterisk = prefix != null && prefix.contains(SearchOperation.ZERO_OR_MORE_REGEX);
                final boolean endWithAsterisk = suffix != null && suffix.contains(SearchOperation.ZERO_OR_MORE_REGEX);

                if (startWithAsterisk && endWithAsterisk) {
                    op = SearchOperation.CONTAINS;
                } else if (startWithAsterisk) {
                    op = SearchOperation.ENDS_WITH;
                } else if (endWithAsterisk) {
                    op = SearchOperation.STARTS_WITH;
                }
            }
            params.add(new SpecSearchCriteria(orPredicate, key, op, value));
        }
        return this;
    }

    public Specification<T> build() {

        if (params.size() == 0)
            return null;

        Specification<T> result = null;//new YueHeSpecification<T>(params.get(0));
     
        for (int i = 0; i < params.size(); i++) {
            String key = params.get(i).getKey();
            String keyToEntityColumns = StringUtils.remove(key.toUpperCase(),'.');
            switch(entityType){
                case SALE:
                    SaleColumnsEnum saleColumnsEnum = SaleColumnsEnum.valueOf(keyToEntityColumns);
                    result = getSpecificationResult(saleColumnsEnum, i);
                    break;
                case OPERATION:
                    OperationColumnsEnum operationColumnsEnum = OperationColumnsEnum.valueOf(keyToEntityColumns);
                    result = getSpecificationResult(operationColumnsEnum, i);
                    break;
                case CLIENT:
                    ClientColumnsEnum clientColumnsEnum = ClientColumnsEnum.valueOf(keyToEntityColumns);
                    result = getSpecificationResult(clientColumnsEnum, i);
                    break;
                    //add other entity here
                default:
                break;
           }
            
        }
        return result;
        
    }
    private final  Specification<T> getSpecificationResult(SaleColumnsEnum saleColumnsEnum, int paramIndex){
        Specification<T> result = null;
        switch(saleColumnsEnum){
            case EMPLOYEENAME:
            case CLIENTCOSMETICSHOPNAME:
            case CLIENTNAME:
            case BEAUTIFYSKINITEMNAME:

                result = params.get(paramIndex).isOrPredicate()
                ? Specification.where(result).or(new SaleSpecification<T>(params.get(paramIndex))) 
                : Specification.where(result).and(new SaleSpecification<T>(params.get(paramIndex)));
                break;



            case DISCOUNT:
            case UNPAIDAMOUNT:
            case EARNEDAMOUNT:
            case UNPAIDEARNEDAMOUNT:
            // throw new Exception("cannot search by this column yet!");
                break;

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
                result = params.get(paramIndex).isOrPredicate()
                ? Specification.where(result).or(new YueHeSpecification<T>(params.get(paramIndex))) 
                : Specification.where(result).and(new YueHeSpecification<T>(params.get(paramIndex)));
                break;
            
        }
        return result;
    }
    private final  Specification<T> getSpecificationResult(OperationColumnsEnum operationColumnsEnum, int paramIndex){
        Specification<T> result = null;
        switch(operationColumnsEnum){
            case EMPLOYEENAME:
            case TOOLNAME:
            case OPERATEEXPENSE:
            case SALEID:

                result = params.get(paramIndex).isOrPredicate()
                ? Specification.where(result).or(new OperationSpecification<T>(params.get(paramIndex))) 
                : Specification.where(result).and(new OperationSpecification<T>(params.get(paramIndex)));
                break;

            case ID:
            case DESCRIPTION:
            default:
                result = params.get(paramIndex).isOrPredicate()
                ? Specification.where(result).or(new YueHeSpecification<T>(params.get(paramIndex))) 
                : Specification.where(result).and(new YueHeSpecification<T>(params.get(paramIndex)));
                break;
            
        }
        return result;
    }
    private final  Specification<T> getSpecificationResult(ClientColumnsEnum clientColumnsEnum, int paramIndex){
        Specification<T> result = null;
        switch(clientColumnsEnum){
            case COSMETICSHOPNAME:

                result = params.get(paramIndex).isOrPredicate()
                ? Specification.where(result).or(new ClientSpecification<T>(params.get(paramIndex))) 
                : Specification.where(result).and(new ClientSpecification<T>(params.get(paramIndex)));
                break;

            case ID:
            case NAME:
            case AGE:
            case GENDER:
            case SYMPTOM:
            default:
                result = params.get(paramIndex).isOrPredicate()
                ? Specification.where(result).or(new YueHeSpecification<T>(params.get(paramIndex))) 
                : Specification.where(result).and(new YueHeSpecification<T>(params.get(paramIndex)));
                break;
            
        }
        return result;
    }



    public final  YueHeSpecificationsBuilder<T> with(YueHeSpecification<T> spec) {
        params.add(spec.getCriteria());
        return this;
    }

    public final YueHeSpecificationsBuilder<T> with(SpecSearchCriteria criteria) {
        params.add(criteria);
        return this;
    }
}
