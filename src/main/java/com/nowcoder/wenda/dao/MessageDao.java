package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.Message;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
/*@Mapper注解的的作用:
1:为了把mapper这个DAO交給Spring管理
2:为了不再写mapper映射文件
3:为了给mapper接口自动根据一个添加@Mapper注解的接口生成一个实现类*/
@Mapper
@Repository //这个注解不是必须，不写Test类中的依赖注入会有红波浪线，但不影响正常运行
public interface MessageDao {
    //为什么用这些字段？
    //因为以后修改表的时候只用修改这些常量字段就可以了，而不用逐一修改下面的sql语句
    //注意空格，防止下面写SQL语句时少加了空格连在一起会出错
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} " +
            "order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from ", TABLE_NAME, " where has_read=0 and to_id=#{userId} and conversation_id=#{conversationId}"})
    int getConvesationUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    @Select({"select ", INSERT_FIELDS, " ,count(id) as id from ( select * from ", TABLE_NAME, " where from_id=#{userId} " +
            "or to_id=#{userId} order by id desc) tt group by conversation_id  order by created_date desc limit #{offset}, #{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset, @Param("limit") int limit);
}

