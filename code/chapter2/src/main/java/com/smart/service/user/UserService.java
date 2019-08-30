package com.smart.service.user;

import com.smart.log.dao.LoginLogDao;
import com.smart.user.dao.UserDao;
import com.smart.user.po.LoginLog;
import com.smart.user.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserService {
    private UserDao userDao;
    private LoginLogDao loginLogDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setLoginLogDao(LoginLogDao loginLogDao) {
        this.loginLogDao = loginLogDao;
    }

    public boolean hasMatchUser(String userName,String password){
       int matchCount =  userDao.getMatchCount(userName, password);
       return matchCount > 0;
    }

    public User findUserByUserName(String userName){
        return userDao.findUserByUserByName(userName);
    }

    @Transactional
    public void loginSuccess(User user){
        user.setCredits(5+user.getCredits());
        LoginLog loginLog = new LoginLog();
        loginLog.setIp(user.getLastIp());
        loginLog.setUserId(user.getUserId());
        loginLog.setLoginDateTime(new Timestamp(new Date().getTime()));
        userDao.updateLoginInfo(user);
        loginLogDao.insertLoginLog(loginLog);
    }


}
