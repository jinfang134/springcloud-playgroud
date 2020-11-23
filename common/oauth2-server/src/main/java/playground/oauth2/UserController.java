package playground.oauth2;

/*作者：MacroZheng
        链接：https://juejin.cn/post/6844903987137740813
        来源：掘金
        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by macro on 2019/9/30.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        return authentication.getPrincipal();
    }
}

