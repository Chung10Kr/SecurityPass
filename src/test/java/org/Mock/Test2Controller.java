package org.Mock;

import org.securitypass.annotation.SecurityPass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Test2Controller {

    @GetMapping(value = "/test2/get")
    public String getTest(){
        return "Hellow Egov!";
    }


    @SecurityPass(role = "ROLE1")
    @PostMapping(value = "/test2/post")
    public String postTest(){
        return "Hellow Egov!";
    }
}
