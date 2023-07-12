package org;

import org.Mock.Testcontroller;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.securitypass.cors.SecurityPassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * fileName       : SecurityPassUtilsTest
 * author         : crlee
 * date           : 2023/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/12        crlee       최초 생성
 */
@SpringBootTest(classes = {
        Testcontroller.class,
        SecurityPassUtils.class
})
public class SecurityPassUtilsTest {

    @Autowired
    SecurityPassUtils securityPassUtils;

    @Test
    public void getUrlsTest(){
        String urlAll[] = securityPassUtils.getUrls();
        String expectAll[] = {
                "/All"
        };
        check(urlAll,expectAll);

        String urls1[] = securityPassUtils.getUrls("ROLE1");
        String expect1[] = {
                "/ROLE1","/ROLE134"
        };
        check(urls1,expect1);

        String urls2[] = securityPassUtils.getUrls("ROLE2");
        String expect2[] = {
                "/ROLE2"
        };
        check(urls2,expect2);

        String urls3[] = securityPassUtils.getUrls("ROLE3");
        String expect3[] = {
                "/ROLE134"
        };
        check(urls3,expect3);

        String urls4[] = securityPassUtils.getUrls("ROLE4");
        String expect4[] = {
                "/ROLE134"
        };
        check(urls4,expect4);
    }
    public void check(String[] target,String[] expect){
        Assertions.assertThat( target.length ).isEqualTo(expect.length);
        Assertions.assertThat( target ).contains(expect);
    }
}
