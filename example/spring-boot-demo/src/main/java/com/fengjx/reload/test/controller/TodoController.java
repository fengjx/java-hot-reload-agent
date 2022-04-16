package com.fengjx.reload.test.controller;

import com.fengjx.reload.test.service.TodoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author FengJianxin
 * @since 2022/4/16
 */
@RestController
public class TodoController {

    @Resource
    private TodoService cityService;

    @RequestMapping("/todo/list")
    public Object find() {
        return cityService.findAllList();
    }

    @RequestMapping("/todo/add")
    public Object add() {
        return cityService.add("task4");
    }


}
