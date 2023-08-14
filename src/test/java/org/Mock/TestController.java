package org.Mock;

import org.securitypass.annotation.SecurityPass;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @SecurityPass
    @GetMapping(value = "/test/get")
    public String getTest(){
        return "Hellow Egov!";
    }
    @SecurityPass( role = {"ROLE1","ROLE2"} )
    @GetMapping(value = "/test/get2")
    public String getTest2(){
        return "Hellow Egov!";
    }
    @SecurityPass
    @PostMapping(value = "/test/post")
    public String postTest(){
        return "Hellow Egov!";
    }
    @SecurityPass
    @PostMapping(value = "/test/post/all")
    public String postTest2(){
        return "Hellow Egov!";
    }
    @SecurityPass
    @PutMapping(value = "/test/put")
    public String putTest(){
        return "Hellow Egov!";
    }
    @SecurityPass
    @DeleteMapping(value = "/test/delete")
    public String deleteTest1(){
        return "Hellow Egov!";
    }
    @SecurityPass( role = "ROLE1" )
    @DeleteMapping(value = "/test/delete2")
    public String deleteTest2(){
        return "Hellow Egov!";
    }

}
