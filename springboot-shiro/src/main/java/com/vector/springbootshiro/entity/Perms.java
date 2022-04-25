package com.vector.springbootshiro.entity;

import java.io.Serializable;

/**
 * @author WangJiaHui
 * @description: 权限信息
 * @ClassName Perms
 * @date 2022/4/22 17:50
 */
public class Perms implements Serializable {
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Perms{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

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

    public Perms(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Perms() {
    }
}
