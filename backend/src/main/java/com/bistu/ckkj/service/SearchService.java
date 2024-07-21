package com.bistu.ckkj.service;

import com.bistu.ckkj.pojo.Article;

import java.util.List;

public interface SearchService {

    //查询文章
    List<Article> searchArticle(String keyword);
}
