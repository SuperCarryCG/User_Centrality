package com.wtbu.usercentrality.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wtbu.usercentrality.common.BaseResponse;
import com.wtbu.usercentrality.common.ErrorCode;
import com.wtbu.usercentrality.common.ResultUtils;
import com.wtbu.usercentrality.exception.BusinessException;
import com.wtbu.usercentrality.model.User;
import com.wtbu.usercentrality.model.request.UserLoginRequest;
import com.wtbu.usercentrality.model.request.UserRegisterRequest;
import com.wtbu.usercentrality.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.wtbu.usercentrality.contant.UserConstant.ADMIN_ROLE;
import static com.wtbu.usercentrality.contant.UserConstant.USER_LOGIN_STATE;

/**控制层Control封装请求 @RestController 适用于编写 restful 风格的api，返回值默认为json类型
 * 用户接口
 * <p>
 * author chenguang
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //userRegisterRequest.get 相当于 servlet中的request.getParameter("email"); 拿到前端传来的数据
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        //在service已经校验过一次  这里为什么还要校验一次  控制层Controller封装请求:倾向于对请求参数本身的校验,不涉及业务逻辑(越少越好)
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword,planetCode)){
            return null;
        }
       long result = userService.loginRegister(userAccount, userPassword, checkPassword,planetCode);
//        return  new BaseResponse<>(0,result,"ok");
        return ResultUtils.success(result);
    }


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        User user = userService.userLogin(userAccount, userPassword, request);
//        return new BaseResponse<>(0,user,"ok");
        return ResultUtils.success(user);

    }
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }


    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser ==null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        //TO do 检验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return  ResultUtils.success(safetyUser);
    }
    @PostMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        //仅管理员可查询
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"仅管理员有权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(username)) {
            queryWrapper.like("username", username);//不写就是模糊查询
        }
        List<User> userList = userService.list(queryWrapper);
//        return  userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return  ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if(!isAdmin(request)){
            throw  new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        return userService.removeById(id);
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        //仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        //这里role尽量定义成一个常量 user.getUserRole()!=1
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }



}
