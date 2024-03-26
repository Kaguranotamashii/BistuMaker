package com.example.demo.service.impl;

import com.example.demo.mapper.TokenMapper;
import com.example.demo.pojo.Token;
import com.example.demo.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {
        private final TokenMapper tokenMapper;

    @Override
    public void addToken(Token token) {
        tokenMapper.insertToken(token);
    }

    @Override
    public void deleteToken(String token) {
        tokenMapper.deleteToken(token);
    }

    @Override
    public List<Token> selectAllToken() {
        return tokenMapper.selectAllToken();
    }

    @Override
    public Token selectTokenById(String token) {
        Token token1 = tokenMapper.selectTokenById(token);
        return token1;
    }

    @Override
    public void updateTokenById(Token token) {
        tokenMapper.updateToken(token);
    }

    @Override
    public Token selectTokenByUser(String user) {
        return tokenMapper.selectTokenByUser(user);
    }
}
