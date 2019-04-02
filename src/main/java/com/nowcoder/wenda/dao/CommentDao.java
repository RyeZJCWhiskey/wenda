package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Comment;
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
public interface CommentDao {
    //为什么用这些字段？
    //因为以后修改表的时候只用修改这些常量字段就可以了，而不用逐一修改下面的sql语句
    //注意空格，防止下面写SQL语句时少加了空格连在一起会出错
    String TABLE_NAME = " comment ";

    String INSERT_FIELDS = " user_id, content, created_date, entity_id, entity_type, status ";

    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME ," ( ", INSERT_FIELDS,
            " ) values(#{userId},#{content},#{createdDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Comment getCommentById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where entity_id = #{entityId} " +
            "and entity_type = #{entityType} order by created_date desc"})//desc降序排列，由大到小，创建时间由近到远
    List<Comment> selectCommentByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    //count是mysql的自带的聚合函数，返回id的数量
    @Select({"select count(id) from ", TABLE_NAME, " where entity_id=#{entityId} and entity_type=#{entityType} "})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({" update ", TABLE_NAME, " set status = #{status} where id = {id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
