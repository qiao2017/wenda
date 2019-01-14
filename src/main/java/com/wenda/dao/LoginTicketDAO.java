package com.wenda.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.wenda.entity.LoginTicket;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月14日 下午7:08:44
 * @copyright qiao
 */
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME = " login_ticket ";
    String INSERT_FIELDS = " user_id, expired, status, ticket ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
    ") values (#{userId}, #{expired}, #{status}, #{ticket})"})
    int adTicket(LoginTicket ticket);
    
    
}
