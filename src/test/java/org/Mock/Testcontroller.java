package org.Mock;

import org.securitypass.annotation.SecurityPass;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

/**
 * fileName       : Testcontroller
 * author         : crlee
 * date           : 2023/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/12        crlee       최초 생성
 */
@Controller
public class Testcontroller {
    @PostMapping(value = "/All")
    @SecurityPass
    public String testMethod() throws Exception {
        return "Test";
    }
    @PostMapping(value = "/ROLE1")
    @SecurityPass(role = "ROLE1")
    public String testMethod2() throws Exception {
        return "Test";
    }
    @PostMapping(value = "/ROLE2")
    @SecurityPass(role = "ROLE2")
    public String testMethod3() throws Exception {
        return "Test";
    }
    @PostMapping(value = "/ROLE134")
    @SecurityPass(role = {"ROLE1","ROLE3","ROLE4"})
    public String testMethod4() throws Exception {
        return "Test";
    }
}
