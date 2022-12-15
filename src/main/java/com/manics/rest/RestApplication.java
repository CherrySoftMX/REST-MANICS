package com.manics.rest;

import com.manics.rest.config.ElasticSearchClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableConfigurationProperties
@SpringBootApplication
public class RestApplication {

  public static void main(String[] args) {
    System.out.println(ElasticSearchClientConfig.class);
    SpringApplication.run(RestApplication.class, args);
  }

}
