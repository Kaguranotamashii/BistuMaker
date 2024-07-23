package com.bistu.ckkj.strategy;

import com.bistu.ckkj.config.ArticleRepository;
import com.bistu.ckkj.mapper.ArticleMapper;
import com.bistu.ckkj.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlSearchStrategy implements SearchStrategy {

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> search(String keyword) {
        // 实现MySQL查询逻辑
        return articleMapper.selectByTitle(keyword);
    }
}