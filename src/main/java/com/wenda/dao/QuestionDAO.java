package com.wenda.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    String INSERT_FIELDS = " title, content, create_date, user_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values ("
            + "#{title}, #{content}, #{createDate}, #{userId})"})
    int addQuestion(Question question);
    
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);
}
