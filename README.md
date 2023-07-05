# SecurityPass

# AS-IS

```java
@PostMapping(value = "/user/login.do")
public HashMap<String, Object> actionLogin(@RequestBody LoginVO loginVO, HttpServletRequest request) throws Exception {
    // Login Action
}
```
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
```java

@PostMapping(value = "/user/login.do")
@SecurityPass
public HashMap<String, Object> actionLogin(@RequestBody LoginVO loginVO, HttpServletRequest request) throws Exception {
    //login action
}

```
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