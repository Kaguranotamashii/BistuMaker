package com.bistu.ckkj.strategy;

import com.bistu.ckkj.config.ArticleRepository;
import com.bistu.ckkj.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElasticsearchSearchStrategy implements SearchStrategy {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<Article> search(String keyword) {
        // 实现Elasticsearch查询逻辑
        return articleRepository.findByTitleOrContent(keyword, keyword);
    }
}