# SecurityPass

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
### SecurityFilterChain Config
```java
@Autowired
SecurityPassUtils securityPassUtils

@Bean
protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    String[] permitAllUrls = securityPassUtils_test.getUrls();
    
    return http
    .authorizeHttpRequests(authorize -> authorize
        .antMatchers(permitAllUrls).permitAll()
        .anyRequest().authenticated()
    )
    .build();
}
```