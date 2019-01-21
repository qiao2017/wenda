package com.wenda.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    
    @Select({"select ", SELECT_FIELDS, " from ", 
        TABLE_NAME, " where ticket = #{ticket}"})
    LoginTicket selectByTicket(@Param("ticket") String ticket);
    
    @Update({"update ", TABLE_NAME, " set status = '1' where ticket = #{ticket}"})
    void updateStatus(@Param("ticket")String ticket);
}
