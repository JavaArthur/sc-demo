## server端
添加如下依赖
```
 <dependency>
      <groupId>de.codecentric</groupId>
      <artifactId>spring-boot-admin-starter-server</artifactId>
    </dependency>
```
启动类上添加`@EnableAdminServer`
## client端
添加如下依赖
```
<dependency>
      <groupId>de.codecentric</groupId>
      <artifactId>spring-boot-admin-starter-client</artifactId>
    </dependency>
```
yml中添加配置
```yaml
spring:
  boot:
    admin:
      client:
        url: http://localhost:7777
        instance:
          prefer-ip: true #显示ip
          service-base-url: http://localhost:${server.port}
```
## 添加安全管理
server端添加
```pom
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
server 的yaml中添加
```yaml
spring:
  security:
    user:
      name: "chen"
      password: "chen"
```
client中添加
```yaml
spring:
  boot:
    admin:
      client:
        url: http://localhost:7777
        instance:
          prefer-ip: true
        username: chen
        password: chen
```
写一个配置类SecuritySecureConfig继承WebSecurityConfigurerAdapter
```java
@Configuration
public  class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
  private final String adminContextPath;

  public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
    this.adminContextPath = adminServerProperties.getContextPath();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    successHandler.setTargetUrlParameter("redirectTo");
    successHandler.setDefaultTargetUrl(adminContextPath + "/");

    http.authorizeRequests()
        .antMatchers(adminContextPath + "/assets/**").permitAll()
        .antMatchers(adminContextPath + "/login").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
        .logout().logoutUrl(adminContextPath + "/logout").and()
        .httpBasic().and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .ignoringAntMatchers(
            adminContextPath + "/instances",
            adminContextPath + "/actuator/**"
        );
    // @formatter:on
  }
}
```
隐藏server端
```yaml
spring:
  boot:
    admin:
      discovery:
        enabled: false
```