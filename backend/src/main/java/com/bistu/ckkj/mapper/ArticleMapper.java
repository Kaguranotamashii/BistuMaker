package com.bistu.ckkj.mapper;

import com.bistu.ckkj.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    /*
    查询所有文章
     */
    @Select("select * from articles")
    List<Article> selectAllArticles();
    /*
    查询单个文章
     */
    @Select("select * from articles where id = #{id}")
    Article selectById(Integer id);

    /*
    新增文章
     */
    @Insert("insert into articles(title, content, cover_image,author,create_time,type,synopsis,label,top,visits) values(#{title}, #{content},#{coverImage},#{author},now(),#{type},#{synopsis},#{label},#{top},#{visits})")
    void insert(Article article);

    /*
    修改文章
     */
    @Select("update articles set title=#{title},content=#{content},cover_image=#{coverImage},modify_time=now() where id=#{id}")
    void update(Article article);

    /*
    删除文章
     */
    @Delete("delete from articles where id = #{id}")
    void deleteById(Integer id);


    @Select("select * from articles where type = #{type}")
    List<Article> selectByType(String type);


    @Select("select * from articles where top=1")
    List<Article> selectByTop();


    /*
     * 修改isRead  取反
     */
    @Update("UPDATE articles SET top = NOT top WHERE id = #{id}")
    void updateIsRead(Integer id);

    @Update("update articles set visits=visits+1 where id =#{id}")
    void addVisits(Integer id);

    //根据单词查询文章关键词
    @Select("select * from articles where title like '%${keyword}%' or content like '%${keyword}%'")
    List<Article> selectByTitle(String keyword);
}
