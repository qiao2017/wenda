package com.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.wenda.entity.Question;

/**
 * @author 乔莹
 * @version TODO
 * @time  2019年1月10日 下午7:53:19
 * @copyright qiao
 */
@Mapper
public interface QuestionDAO {
    String TABLE_NAME = " question ";
    String INSERT_FIELDS = " title, content, create_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values ("
            + "#{title}, #{content}, #{createDate}, #{userId}, #{commentCount})"})
    int addQuestion(Question question);
    
    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{qid}"})
    Question selectById(int qid);
    
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);
    
    @Update({"update ", TABLE_NAME, " set comment_count = #{count} where id = #{questionId}"})
    int updateCommentCount(@Param("questionId") int questionId,
            @Param("count") int count);
}
