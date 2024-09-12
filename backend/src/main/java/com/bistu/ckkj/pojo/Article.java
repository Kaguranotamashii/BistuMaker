package com.bistu.ckkj.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Data
@Document(indexName = "articles")
public class Article {

    @Id
    private Integer id; // ID

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title; // 文章标题

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content; // 文章内容

    @Field(type = FieldType.Keyword)
    private String coverImage; // 文章封面

    @Field(type = FieldType.Keyword)
    private String author; // 作者

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date)
    private LocalDateTime createTime; // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date)
    private LocalDateTime modifyTime; // 修改时间

    @Field(type = FieldType.Keyword)
    private String type; // 类型

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String synopsis; // 简介

    @Field(type = FieldType.Keyword)
    private String label; // 标签

    @Field(type = FieldType.Integer)
    private Integer top; // 置顶

    @Field(type = FieldType.Integer)
    private Integer visits; // 访问量

    // Getters and Setters
}
