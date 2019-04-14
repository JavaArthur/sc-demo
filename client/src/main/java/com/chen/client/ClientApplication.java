package com.chen.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClientApplication.class, args);
  }
  @Slf4j
  @RestController
  static class TestController {
    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
      log.info("invoked name = " + name);
      return "hello " + name;
    }
  }
  @Configuration
  public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().anyRequest().permitAll()
          .and().csrf().disable();
    }
  }

}
