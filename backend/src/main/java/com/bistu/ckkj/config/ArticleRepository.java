package com.bistu.ckkj.config;

import com.bistu.ckkj.pojo.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
    List<Article> findByTitleOrContent(String keyword, String keyword1);
}
