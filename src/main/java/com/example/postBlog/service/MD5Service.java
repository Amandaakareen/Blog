package com.example.postBlog.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import org.springframework.stereotype.Service;

@Service
public class MD5Service {
    public String generateMD5(String senha) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(senha.getBytes(), 0, senha.length());
            String md5 = new BigInteger(1, m.digest()).toString(16);
            return md5;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
