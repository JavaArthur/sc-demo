//package com.chen.gateway.config;
//
//import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import reactor.core.publisher.Mono;
//
///**
// * @author chen
// * @Description TODO
// * @date 2019/3/31 11:00
// **/
//@Configuration
//public class RateLimiterConfiguration {
//
//  @Bean(value = "remoteAddrKeyResolver")
//  public KeyResolver remoteAddrKeyResolver() {
//    return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
//  }
//
//}
//
