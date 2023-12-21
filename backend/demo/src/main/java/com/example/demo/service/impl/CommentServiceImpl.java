package com.example.demo.service.impl;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentMapper commentMapper;
    @Override
    public void addComment(Comment comment) {
        commentMapper.insert(comment);
    }

    @Override
    public void deleteComment(Integer id) {
    commentMapper.deleteById(id);
    }

    @Override
    public void updateComment(Comment comment) {
        commentMapper.update(comment);
    }

    @Override
    public Comment selectCommentById(Integer id) {
        Comment comment = commentMapper.selectById(id);
        return comment;


    }

    @Override
    public List<Comment> selectAllComment() {
        List<Comment> comments = commentMapper.selectAll();
        return comments;
    }
}
