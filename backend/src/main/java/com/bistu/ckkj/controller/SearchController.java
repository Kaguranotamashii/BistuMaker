package com.bistu.ckkj.controller;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import com.bistu.ckkj.aop.MyLog;
import com.bistu.ckkj.config.ArticleRepository;
import com.bistu.ckkj.pojo.Article;
import com.bistu.ckkj.pojo.Result;
import com.bistu.ckkj.strategy.SearchContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    @Autowired
    private SearchContext searchContext;

    @PostMapping("/search")
    public Result addArticle(String keyword){
        return Result.success(searchContext.executeSearch(keyword));
    }
}
