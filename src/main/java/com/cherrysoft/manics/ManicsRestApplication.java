package com.cherrysoft.manics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableHypermediaSupport(type = {
    EnableHypermediaSupport.HypermediaType.HAL,
    EnableHypermediaSupport.HypermediaType.HAL_FORMS
})
@SpringBootApplication
public class ManicsRestApplication {

  public static void main(String[] args) {
    SpringApplication.run(ManicsRestApplication.class, args);
  }

}
