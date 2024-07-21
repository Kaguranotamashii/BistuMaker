package com.bistu.ckkj.controller;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import com.bistu.ckkj.aop.MyLog;
import com.bistu.ckkj.config.ArticleRepository;
import com.bistu.ckkj.pojo.Article;
import com.bistu.ckkj.pojo.Result;
import lombok.RequiredArgsConstructor;
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

    private final ArticleRepository articleRepository;



    @PostMapping("/search")
    public Result addArticle(String keyword){
        articleRepository.findByTitleOrContent(keyword,keyword);
        return new Result(200,"success",articleRepository.findByTitleOrContent(keyword,keyword));

    }
}
