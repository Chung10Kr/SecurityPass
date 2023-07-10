# SecurityPass
- There was an inconvenience of managing duplicate APIs based on permissions when using Spring Security
- Using this library helps manage APIs based on permissions.
- SecurityPass provides the @SecurityPass annotation. The @SecurityPass annotation can be used on a controller's methods.
- SecurityPassUtils returns the API URLs of methods with the @SecurityPass annotation attached.
- [Maven Central](https://central.sonatype.com/artifact/io.github.Chung10Kr/SecurityPass/1.0.0/overview)

# AS-IS

### Controller method
```java
@PostMapping(value = "/user/login.do")
public HashMap<String, Object> actionLogin() throws Exception {
    // Login Action
}
```

### SecurityFilterChain Config
```java
private String[] AUTH_WHITELIST = {
        "/",
        "/user/login.do",
};

@Bean
protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
    .authorizeHttpRequests(authorize -> authorize
        .antMatchers(AUTH_WHITELIST).permitAll()
        .anyRequest().authenticated()
    )
    .build();
}
```

# To-Be
### Controller method
```java

@PostMapping(value = "/user/login.do")
@SecurityPass
public HashMap<String, Object> actionLogin() throws Exception {
    //login action
}

```

### SecurityPassConfig
```java
@Configuration
public class SecurityPassConfig {

    @Bean
    protected SecurityPassUtils securityPassUtils(){
        return new SecurityPassUtils();
    }
}
```
### SecurityFilterChain Config
```java
@Autowired
SecurityPassUtils securityPassUtils

@Bean
protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    String[] permitAllUrls = securityPassUtils.getUrls(); // [ "/user/login.do" ]
        
    return http
    .authorizeHttpRequests(authorize -> authorize
        .antMatchers(permitAllUrls).permitAll()
        .anyRequest().authenticated()
    )
    .build();
}
```


# Sample Code
```java
@SecurityPass
@SecurityPass(role="admin")
@SecurityPass(role="user")
@SecurityPass(role={"user","admin"})
```
```java
String[] permitAllUrls = securityPassUtils.getUrls();
String[] permitAdminrls = securityPassUtils.getUrls("admin");
String[] permitUserUrls = securityPassUtils.getUrls("user");
```