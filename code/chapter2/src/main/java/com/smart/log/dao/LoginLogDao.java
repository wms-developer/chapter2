package com.smart.log.dao;

import com.smart.user.po.LoginLog;
import com.smart.user.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginLogDao {

    private final String INSERT_SQL="INSERT INTO spring.t_login_log (t_login_log.user_id, t_login_log.ip, t_login_log.login_datetime) values (?,?,?)";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertLoginLog(LoginLog loginLog){
        Object[] args = {loginLog.getUserId(),loginLog.getIp(),loginLog.getLoginDateTime()};
        jdbcTemplate.update(INSERT_SQL,args);
    }
}
