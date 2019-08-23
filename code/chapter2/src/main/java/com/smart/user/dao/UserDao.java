package com.smart.user.dao;

import com.smart.user.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    private final static String QUERY_SQL=" SELECT * FROM  t_user WHERE t_user.user_name=? ";

    private final static String UPDATE_LOGIN_INFO=" UPDATE t_user set last_visit=?,last_ip=?,credits=? WHERE user_id=? ";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMatchCount(String userName,String password){
        String sqlStr ="SELECT COUNT(*) FROM  t_user WHERE t_user.user_name=? AND t_user.user_pass=?";
        return jdbcTemplate.queryForObject(sqlStr,new Object[]{userName,password},Integer.class);
    }

    public User findUseyByUserByName(final String userName){
        final User user = new User();
        jdbcTemplate.query(QUERY_SQL, new Object[]{userName}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
                user.setCredits(resultSet.getInt("credits"));
            }
        });
        return user;
    }


    public int updateLoginInfo(User user){
       return jdbcTemplate.update(UPDATE_LOGIN_INFO,new Object[]{user.getLastVisit(),user.getLastIp(),user.getCredits(),user.getUserId()});
    }
}
