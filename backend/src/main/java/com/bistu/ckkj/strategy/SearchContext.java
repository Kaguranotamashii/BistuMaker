package com.bistu.ckkj.strategy;

import com.bistu.ckkj.pojo.Article;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Setter
@Service
public class SearchContext {

    private SearchStrategy searchStrategy;

    public List<Article> executeSearch(String keyword) {
        return searchStrategy.search(keyword);
    }
}