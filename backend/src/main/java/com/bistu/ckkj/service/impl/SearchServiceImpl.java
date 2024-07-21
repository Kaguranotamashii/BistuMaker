package com.bistu.ckkj.service.impl;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import com.bistu.ckkj.config.ArticleRepository;
import com.bistu.ckkj.mapper.ArticleMapper;
import com.bistu.ckkj.pojo.Article;
import com.bistu.ckkj.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {


    private final ArticleRepository articleRepository;

    private final ArticleMapper articleMapper;



    @Retryable(value = { ElasticsearchException.class }, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    @Override
    public List<Article> searchArticle(String keyword) {
        try {
            // 执行Elasticsearch查询逻辑
            return articleRepository.findByTitleOrContent(keyword, keyword);
        } catch (ElasticsearchException e) {
            // Elasticsearch不可用，从MySQL中查询数据
            return articleMapper.selectByTitle(keyword);
        }
    }
}
