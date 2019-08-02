package com.yuehe.app.specification;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.yuehe.app.util.search.SearchOperation;
import com.yuehe.app.util.search.SpecSearchCriteria;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationsBuilder<T> {

    public  Specification<T> resolveSpecification(String searchParameters) {

        YueHeSpecificationsBuilder<T> builder = new YueHeSpecificationsBuilder<>();
        String operationSetExper = Joiner.on("|")
            .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+\\.?\\w+)(" + operationSetExper + ")(\\p{Punct}?)(\\p{L}+)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(searchParameters);
        while (matcher.find()) {
            System.err.println(matcher.group(1)+"--"+matcher.group(2)+"--"+matcher.group(3)+"--"+matcher.group(4)+"--"+matcher.group(5)+"--"+matcher.group(6));
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(5), matcher.group(4), matcher.group(6));
        }
        List<SpecSearchCriteria> params = builder.getParams();
        params.forEach(l -> System.out.println(l));
        return builder.build();
    }
}
