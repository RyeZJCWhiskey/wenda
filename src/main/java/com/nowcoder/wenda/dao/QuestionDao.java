package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/*@Mapper注解的的作用:
1:为了把mapper这个DAO交給Spring管理
2:为了不再写mapper映射文件
3:为了给mapper接口自动根据一个添加@Mapper注解的接口生成一个实现类*/
@Mapper
@Repository //这个注解不是必须，不写Test类中的依赖注入会有红波浪线，但不影响正常运行
public interface QuestionDao {
    //为什么用这些字段？
    //因为以后修改表的时候只用修改这些常量字段就可以了，而不用逐一修改下面的sql语句
    //注意空格，防止下面写SQL语句时少加了空格连在一起会出错
    String TABLE_NAME = " question ";

    String INSERT_FIELDS = " title, content, created_date, user_id, comment_count ";

    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME ," ( ", INSERT_FIELDS,
            " ) values(#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id = #{id}"})
    Question selectById(int id);

    //这个函数的sql语句比较复杂，在QuestionDao.xml中
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
