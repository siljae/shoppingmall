package com.lys.shoppingmall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lys.shoppingmall.mapper")
public class MyBatisConfig {
}
