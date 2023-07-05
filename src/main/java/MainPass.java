import org.securitypass.SecurityPassUtil;

import java.net.URL;
import java.security.CodeSource;

/**
 * fileName       : Main
 * author         : crlee
 * date           : 2023/07/04
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023/07/04        crlee       최초 생성
 */
public class MainPass {
    public static void main(String[] args){
        CodeSource codeSource = SecurityPassUtil.class.getProtectionDomain().getCodeSource();
        if (codeSource != null) {
            URL jarFileURL = codeSource.getLocation();
        }
    }
}
