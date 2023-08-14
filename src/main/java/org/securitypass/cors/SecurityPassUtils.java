package org.securitypass.cors;

import org.securitypass.annotation.SecurityPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * fileName       : SecurityPassUtils
 * author         : crlee
 * date           : 2023/06/21
 * description    : A feature that returns the values of
 *                  @GetMapping,
 *                  @PostMapping,
 *                  @PutMapping,
 *                  @DeleteMapping,
 *                  @PatchMapping annotations
 *                  for methods with the '@SecurityPass' annotation attached
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/06/21        crlee       최초 생성
 */

public class SecurityPassUtils {

    ApplicationContext applicationContext;
    public SecurityPassUtils(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    private final Map<HttpMethod, Class<? extends Annotation>> httpMethodToAnnotationMap = new HashMap<HttpMethod, Class<? extends Annotation>>(){{
        put(HttpMethod.GET, GetMapping.class);
        put(HttpMethod.POST, PostMapping.class);
        put(HttpMethod.PUT, PutMapping.class);
        put(HttpMethod.DELETE, DeleteMapping.class);
        put(HttpMethod.PATCH, PatchMapping.class);
    }};

    public String[] getUrls(){
        return getUrls(SecurityPassConst.ALL);
    }
    public String[] getUrls(String roleType){
        return searchMappingUrls(new ArrayList<>(httpMethodToAnnotationMap.values()), roleType);
    }
    public String[] getUrlsByMethod(HttpMethod method){
        return getUrlByMethodAndRole(method,SecurityPassConst.ALL);
    }
    public String[] getUrlByMethodAndRole(HttpMethod method,String roleType){
        List<Class<? extends Annotation>> mappingAnnotations = Arrays.asList(httpMethodToAnnotationMap.get(method));
        return searchMappingUrls(mappingAnnotations, roleType);
    }
    private String[] searchMappingUrls(List<Class<? extends Annotation>> mappingAnnotations, String roleType){

        List<String> auth_whitelists = new ArrayList<>();
        try {
            List<Class> classes = findController();
            for (Class clazz : classes) {
                for(Method method : clazz.getMethods()){
                    if (method.isAnnotationPresent(SecurityPass.class)) {
                        String[] roles = (String[]) method.getAnnotation(SecurityPass.class).getClass().getMethod("role").invoke(method.getAnnotation(SecurityPass.class));
                        if( Arrays.asList(roles).contains(roleType) ){
                            for (Class<? extends Annotation> annotationClass : mappingAnnotations) {
                                if( method.isAnnotationPresent(annotationClass) ){
                                    String[] values = (String[]) method.getAnnotation(annotationClass).getClass().getMethod("value").invoke(method.getAnnotation(annotationClass));
                                    auth_whitelists.addAll(Arrays.asList(values));
                                };
                            }
                        }

                    }
                }
            }
        }catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        }
        return auth_whitelists.stream().toArray(String[]::new);
    }

    private List<Class> findController(){
        List<Class> classes = new ArrayList<>();
        Map<String, Object> beansWithControllerAnnotation = applicationContext.getBeansWithAnnotation(Controller.class);
        for(Object controller : beansWithControllerAnnotation.values()) {
            classes.add(controller.getClass());
        }
        return classes;
    }
}

