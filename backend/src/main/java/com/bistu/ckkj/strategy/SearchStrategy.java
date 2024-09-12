package com.bistu.ckkj.strategy;

import com.bistu.ckkj.pojo.Article;

import java.util.List;

public interface SearchStrategy {
    List<Article> search(String keyword);
}