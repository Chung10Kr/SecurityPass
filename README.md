# SecurityPass
- There was an inconvenience of managing duplicate APIs based on permissions when using Spring Security
- Using this library helps manage APIs based on permissions.
- SecurityPass provides the @SecurityPass annotation. The @SecurityPass annotation can be used on a controller's methods.
- SecurityPassUtils returns the API URLs of methods with the @SecurityPass annotation attached.
- [Maven Central](https://central.sonatype.com/artifact/io.github.Chung10Kr/SecurityPass/1.0.0/overview)

# AS-IS - 1

- In the controller and spring-security settings, it is inconvenient to manage URLs for each authority redundantly.
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

------------
# AS-IS - 2
- There are annotations for authorization and authentication in each method, but special settings must be made.
- @PreAuthorize("hasRole('ROLE_USER') In this case, it is difficult to check errors caused by typos at runtime.
- When using annotations such as @PermitAll, authorizeHttpRequests() cannot be used in spring-security configuration.
- When using annotations such as @PermitAll, special configuration is required to use authorizeHttpRequests() in spring-security configuration.
```java
@PermitAll
//@Secured({"ROLE_USER","ROLE_ADMIN"})
//@PreAuthorize("hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')")
public HashMap<String, Object> actionLogin() throws Exception {
// Login Action
}
```
```java
@EnableGlobalMethodSecurity(jsr250Enabled = true)
//@EnableMethodSecurity(securedEnabled = true ,  securedEnabled = true)
public class SecurityConfig {
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                //.authorizeHttpRequests(authorize -> authorize
                //        .antMatchers(AUTH_WHITELIST).permitAll()
                //        .anyRequest().authenticated()
                //)
                .build();
    }
}
```
# To-Be
- No special configuration is required to use the @SecurityPass annotation.
- SecurityPassUtils returns the API URLs of methods with the @SecurityPass annotation attached.
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



## Gradle
```bash
implementation group: 'io.github.Chung10Kr', name: 'SecurityPass', version: '1.0.0'
```
## Gradle short
```bash
implementation 'io.github.Chung10Kr:SecurityPass:1.0.0'
```
## Maven
```bash
<dependency>
    <groupId>io.github.Chung10Kr</groupId>
    <artifactId>SecurityPass</artifactId>
    <version>1.0.0</version>
</dependency>
```