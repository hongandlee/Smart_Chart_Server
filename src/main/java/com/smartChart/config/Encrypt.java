package com.smartChart.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


// salt 암호화
public class Encrypt {
    public static String getSalt() {

        // Random, byte 객체 생성
        SecureRandom r = new SecureRandom();
        byte[] salt = new byte[20];

        // 난수 생성
        r.nextBytes(salt);

        //  byte To String (10진수의 문자열로 변경)
        StringBuffer sb = new StringBuffer();

        for(byte b : salt) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }




    public static String getEncrypt(String pwd, String salt) {

        String result = "";


        try {
            //SHA256 알고리즘 객체 생성
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // pwd와 salt 합치기
            md.update((pwd + salt).getBytes());
            byte[] pwdsalt = md.digest();

            // byte To String
            StringBuffer sb = new StringBuffer();

            for(byte b : pwdsalt) {
                sb.append(String.format("%02x", b));
            }

            result = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }
}
