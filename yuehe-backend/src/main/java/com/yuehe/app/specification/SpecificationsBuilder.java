package com.yuehe.app.specification;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.yuehe.app.common.YueHeEntitiesEnum;
import com.yuehe.app.util.search.SearchOperation;
import com.yuehe.app.util.search.SpecSearchCriteria;

import org.springframework.data.jpa.domain.Specification;

public final class SpecificationsBuilder<T> {

    public  Specification<T> resolveSpecification(String searchParameters,YueHeEntitiesEnum entityType) {

        YueHeSpecificationsBuilder<T> builder = new YueHeSpecificationsBuilder<>(entityType);
        String operationSetExper = Joiner.on("|")
            .join(SearchOperation.SIMPLE_OPERATION_SET);
            // "(\\p{Punct}?)(\\w+(\\.?\\w+)*)(:|!|>|<|~)(\\p{Punct}?)(\\p{L}+|\\w+)(\\p{Punct}?),"
            //group 1(\\p{Punct}?) -- punctuation characters, like * ~ $ ; 
            //group 2 (\\w+(\\.?\\w+)*)  -- a word or word.word.word... such as itemNumber, client.name, client.cosmeticShop.name
            //group 4 (:|!|>|<|~)  -- operation characters
            //group 5 (\\p{L}+|\\w+) \\p{L}+  match chinese characters, \\w+  match english words
        Pattern pattern = Pattern.compile("(\\p{Punct}?)(\\w+(\\.?\\w+)*)(" + operationSetExper + ")(\\p{Punct}?)(\\p{L}+|\\w+)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(searchParameters);
        while (matcher.find()) {
            System.err.println("1-->"+matcher.group(1)+"--2-->"+matcher.group(2)+"--3-->"+matcher.group(3)+"--4-->"+matcher.group(4)+"--5-->"+matcher.group(5)+"--6-->"+matcher.group(6)+"--7-->"+matcher.group(7));
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(6), matcher.group(5), matcher.group(7));
        }
        List<SpecSearchCriteria> params = builder.getParams();
        params.forEach(l -> System.out.println(l));
        return builder.build();
    }
}
