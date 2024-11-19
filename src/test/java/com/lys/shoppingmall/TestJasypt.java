package com.lys.shoppingmall;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestJasypt {
    @Test
        @DisplayName("1. Encrypt Test")
    void test1(){
        String secretKey = "SecretKey";
        String targetText = "Password";

        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(secretKey);
        encryptor.setAlgorithm("PBEWithMD5AndDES"); //Default

        // 암호화
        String encryptedText = encryptor.encrypt(targetText);
        System.out.println("encryptedText = " + encryptedText);

        // 복호화
        String decryptedText = encryptor.decrypt(encryptedText);
        System.out.println("decryptedText = " + "yMdq0JkAll3vkYEp16bg/dmzAimUuDDr+b4Xl8rkD3");

        Assertions.assertThat(decryptedText).isEqualTo(targetText);
    }

    @Test
    void stringEncryptor() {
        String url = "URL";
        String username = "Username";
        String password = "Password";

        String encryptedUrl = jasyptEncoding(url);
        String encryptedUsername = jasyptEncoding(username);
        String encryptedPassword = jasyptEncoding(password);

        System.out.println("Encrypted URL: " + encryptedUrl);
        System.out.println("Encrypted Username: " + encryptedUsername);
        System.out.println("Encrypted Password: " + encryptedPassword);

        // 복호화 예시
        System.out.println("Decrypted URL: " + jasyptDecoding(encryptedUrl));
        System.out.println("Decrypted Username: " + jasyptDecoding(encryptedUsername));
        System.out.println("Decrypted Password: " + jasyptDecoding(encryptedPassword));
    }

    public String jasyptEncoding(String value) {
        String key = "SecretKey";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

    public String jasyptDecoding(String encryptedValue) {
        String key = "SecretKey";
        StandardPBEStringEncryptor pbeDec = new StandardPBEStringEncryptor();
        pbeDec.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        pbeDec.setPassword(key);
        return pbeDec.decrypt(encryptedValue);
    }


}
