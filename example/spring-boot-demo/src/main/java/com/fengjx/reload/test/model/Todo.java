package com.fengjx.reload.test.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author FengJianxin
 * @since 2022/4/17
 */
@Data
public class Todo implements Serializable {

    private int id;

    private String task;

    private String status;

    private Date createTime;

}
