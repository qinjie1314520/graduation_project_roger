package com.article.openfeign;

import com.common.pojo.Result;
import com.common.utils.constant.ConstantUtils;
import com.controller.controller.user.UserCenterUserController;
import com.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 和用户服务的交互
 * @author roger
 * @since  2021/2/22 18:04
 */
@FeignClient(value = ConstantUtils.GRADUATION_PROJECT_ROGER_USER_CENTER,configuration = ServiceFeignConfiguration.class,fallback = AbstractUserCenterFeignServiceFallback.class)
public interface UserCenterFeignService extends UserCenterUserController {
    @Override
    @RequestMapping(value = "/"+ ConstantUtils.GRADUATION_PROJECT_ROGER_USER_CENTER +"/user-center/selUserInfo",method = RequestMethod.GET)
    Result<User> selUserInfo(@RequestHeader(name = "Authorization") String authorization);
}
