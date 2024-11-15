package com.lys.shoppingmall;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.jasypt.iv.RandomIvGenerator;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {
    private static final String SECRET_KEY = "SecretKey";

    @Test
    void string_encryption() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(SECRET_KEY);
        config.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        config.setIvGenerator(new RandomIvGenerator());
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

//        String url = "URL";
//        String username = "Username";
//        String password = "Password";

        String url = "URL";
        String username = "Username";
        String password = "Password";

        // 암호화
        String encryptedUrl = encryptor.encrypt(url);
        System.out.println("Encrypted URL ::: ENC(" + encryptedUrl + ")");
        String encryptedUsername = encryptor.encrypt(username);
        System.out.println("Encrypted Username ::: ENC(" + encryptedUsername + ")");
        String encryptedPassword = encryptor.encrypt(password);
        System.out.println("Encrypted Password ::: ENC(" + encryptedPassword + ")");

        // 복호화
        String decryptedUrl = encryptor.decrypt(encryptedUrl);
        System.out.println("Decrypted URL ::: " + decryptedUrl);
        String decryptedUsername = encryptor.decrypt(encryptedUsername);
        System.out.println("Decrypted Username ::: " + decryptedUsername);
        String decryptedPassword = encryptor.decrypt(encryptedPassword);
        System.out.println("Decrypted Password ::: " + decryptedPassword);

        Assertions.assertThat(decryptedUrl).isEqualTo(url);
        Assertions.assertThat(decryptedUsername).isEqualTo(username);
        Assertions.assertThat(decryptedPassword).isEqualTo(password);
    }
}
