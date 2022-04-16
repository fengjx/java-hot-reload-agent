package com.fengjx.reload.test.dao;

import com.fengjx.reload.test.model.Todo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author FengJianxin
 */
@Mapper
public interface TodoMapper {

    @Select("select * from todo")
    List<Todo> findAllList();

    @Select("select * from todo where status = #{status}")
    List<Todo> findList(@Param("status") String status);

    @Insert("insert into todo (task, status, createTime) values (#{task}, 'pre', now() )")
    int add(@Param("task") String task);

}
