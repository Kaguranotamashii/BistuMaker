package com.bistu.ckkj.strategy;

import com.bistu.ckkj.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchContext {

    private SearchStrategy searchStrategy;

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public List<Article> executeSearch(String keyword) {
        return searchStrategy.search(keyword);
    }
}