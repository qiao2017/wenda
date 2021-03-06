package com.wenda.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wenda.entity.User;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月9日 下午10:25:04
 * @copyright qiao
 */
@Mapper
public interface UserDAO {
    String TABLE_NAME = " user ";
    String INSET_FIELDS = " name, password, salt, head_url, create_date ";
    String SELECT_FIELDS = " id, name, password, salt, head_url, create_date";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{name}, #{password}, #{salt}, #{headUrl}, #{createDate})"})
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password = #{password} where id = #{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
}
