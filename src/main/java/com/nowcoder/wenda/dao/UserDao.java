package com.nowcoder.wenda.dao;

import com.nowcoder.wenda.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/*@Mapper注解的的作用:
1:为了把mapper这个DAO交給Spring管理
2:为了不再写mapper映射文件
3:为了给mapper接口 自动根据一个添加@Mapper注解的接口生成一个实现类*/
@Mapper
@Repository
public interface UserDao {

    //为什么用这些字段？
    //因为以后修改表的时候只用修改这些常量字段就可以了，而不用逐一修改下面的sql语句
    //注意空格，防止下面写SQL语句时少加了空格连在一起会出错
    String TABLE_NAME = " user ";

    String INSERT_FIELDS = " name, password, salt, head_url ";

    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME ," ( ", INSERT_FIELDS,
            " ) values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Update({"update", TABLE_NAME, " set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
