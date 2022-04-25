package com.vector.springbootshiro.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author WangJiaHui
 * @description: 角色信息
 * @ClassName Role
 * @date 2022/4/22 17:50
 */
public class Role implements Serializable {
    private int id;
    private String name;
    private List<Perms> perms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", perms=" + perms +
                '}';
    }

    public List<Perms> getPerms() {
        return perms;
    }

    public void setPerms(List<Perms> perms) {
        this.perms = perms;
    }

    public Role(int id, String name, List<Perms> perms) {
        this.id = id;
        this.name = name;
        this.perms = perms;
    }

    public Role() {
    }
}
