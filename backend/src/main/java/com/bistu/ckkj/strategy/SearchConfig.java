package com.bistu.ckkj.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchConfig {

    @Value("${app.search.strategy}")
    private String searchStrategy;

    @Autowired
    private MysqlSearchStrategy mysqlSearchStrategy;

    @Autowired
    private ElasticsearchSearchStrategy elasticsearchSearchStrategy;

    @Bean
    public SearchContext searchContext() {
        SearchContext context = new SearchContext();
        if ("mysql".equalsIgnoreCase(searchStrategy)) {
            context.setSearchStrategy(mysqlSearchStrategy);
        } else if ("elasticsearch".equalsIgnoreCase(searchStrategy)) {
            context.setSearchStrategy(elasticsearchSearchStrategy);
        } else {
            throw new IllegalArgumentException("Invalid search strategy");
        }
        return context;
    }
}