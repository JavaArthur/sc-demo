package com.chen.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ConsumerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerApplication.class, args);
  }



  @Slf4j
  @RestController
  @RefreshScope
  static class TestController {
    @Autowired
    Client client;

    @Value("${name:}")
    private String name;

//    @Value("${age:}")
//    private String age;

    @GetMapping("/test")
    public String test(String test) {
      String result = client.hello(name+test);
      return "Return : " + result;
    }
    @RequestMapping("/foo")
    public String foo(String foo) {
      return "hello "+foo+"!";
    }
  }
  @FeignClient("client")
  interface Client {
    @GetMapping("/hello")
    String hello(@RequestParam(name = "name") String name);

  }
}
