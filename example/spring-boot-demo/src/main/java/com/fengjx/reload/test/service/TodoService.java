package com.fengjx.reload.test.service;

import com.fengjx.reload.test.dao.TodoMapper;
import com.fengjx.reload.test.model.Todo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author FengJianxin
 * @since 2022/4/16
 */
@Service
public class TodoService {

    @Resource
    private TodoMapper todoMapper;

    public List<Todo> findList(String status) {
        return todoMapper.findList(status);
    }


    public List<Todo> findAllList() {
        return todoMapper.findAllList();
    }

    public boolean add(String task) {
        return todoMapper.add(task) == 1;
    }


}
