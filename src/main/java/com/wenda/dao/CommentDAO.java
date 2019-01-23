package com.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wenda.entity.Comment;

/**
 * @author 乔莹
 * @version TODO
 * @time 2019年1月23日 下午3:29:59
 * @copyright qiao
 */
@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " user_id, content, create_date, entity_id, entity_type, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values ( #{userId}, #{content}, #{createDate}, #{entityId}, #{entityType}, #{status})" })
    int addComment(Comment comment);
    
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, 
        " where entity_id = #{entityId} and entity_type = #{entityType} and status = 0 order by create_date desc limit #{offset}, #{limit}"})
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId,
            @Param("entityType") int entityType, 
            @Param("offset") int offset, 
            @Param("limit") int limit);
    
    @Select({"select count(id) from", TABLE_NAME, " where entity_id = #{entityId} and entity_type = #{entityType} and status = 0"})
    int getCommentCount(@Param("entityId") int entityId,
            @Param("entityType") int entityType);
    
    @Update({"update ", TABLE_NAME, " set status = #{status} where id = #{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
