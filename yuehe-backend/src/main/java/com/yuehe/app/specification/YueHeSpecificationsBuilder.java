package com.yuehe.app.specification;

import java.util.ArrayList;
import java.util.List;

import com.yuehe.app.util.search.ColumnsNotInSaleTableSearchBy;
import com.yuehe.app.util.search.SearchOperation;
import com.yuehe.app.util.search.SpecSearchCriteria;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public final class YueHeSpecificationsBuilder<T> {

    private final List<SpecSearchCriteria> params;

    public YueHeSpecificationsBuilder() {
        params = new ArrayList<>();
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
            if (op == SearchOperation.EQUALITY) { // the operation may be complex operation
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
            ColumnsNotInSaleTableSearchBy columnsNotInSaleTableSearchBy = ColumnsNotInSaleTableSearchBy.valueOf(StringUtils.remove(key.toUpperCase(),'.'));
            switch(columnsNotInSaleTableSearchBy){
                case EMPLOYEENAME:
                case CLIENTCOSMETICSHOPNAME:
                case CLIENTNAME:
                case BEAUTIFYSKINITEMNAME:

                    result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new SaleSpecification<T>(params.get(i))) 
                    : Specification.where(result).and(new SaleSpecification<T>(params.get(i)));
                    break;

                default:
                    result = params.get(i).isOrPredicate()
                    ? Specification.where(result).or(new YueHeSpecification<T>(params.get(i))) 
                    : Specification.where(result).and(new YueHeSpecification<T>(params.get(i)));
                    break;
                  
            }
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
