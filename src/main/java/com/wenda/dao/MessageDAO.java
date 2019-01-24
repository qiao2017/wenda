package com.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wenda.entity.Message;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月23日 下午6:40:50
 * @copyright qiao
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, create_date, has_read, conversation_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({ "insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values ( #{fromId}, #{toId}, #{content}, #{createDate}, #{hasRead}, #{conversationId})" })
    int addMessage(Message message);
    
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where conversation_id = #{conversationId} order by create_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                @Param("offset") int offset,
                                @Param("limit") int limit);
    /*
     * SELECT *, count(id) as cut from (select * from message order by create_date desc) tt
        GROUP BY conversation_id ORDER BY create_date desc
        limit 0, 10
     */
    @Select({"select ", INSERT_FIELDS, ", count(id) as id from (select * from ", TABLE_NAME, " where from_id = #{userId} "
            + "or to_id = #{userId} order by create_date desc) tt ",
                "GROUP BY conversation_id ORDER BY create_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                        @Param("offset") int offset,
                        @Param("limit") int limit);
    @Select({"select count(id) from ", TABLE_NAME, " where conversation_id = #{conversationId} and to_id = #{userId}"})
    int getConVersationUnReadCount(@Param("userId") int userId,
            @Param("conversationId") String conversationId);
}
