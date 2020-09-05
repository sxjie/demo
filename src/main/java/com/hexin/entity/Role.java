package com.hexin.entity;

import java.util.Date;

/**
 * @ClassName Role
 * @Description TODO
 * @Author shenxiaojie
 * @Date 2019-06-04 10:29
 * @Version 1.0
 */
public class Role {

    private Integer id;

    private Integer pid;

    private String roleName;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
