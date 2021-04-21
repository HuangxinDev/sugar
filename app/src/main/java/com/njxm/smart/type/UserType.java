/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.njxm.smart.type;

/**
 * 用户角色
 */
public enum UserType {
    COMPANY("company"), // 业主
    SAFETY_OFFICER("safety_officer"), //安全员
    WORKER("worker"), // 工人
    CONSTRUCTION("construction"); //施工单位管理员

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    /**
     * 默认为工人角色，根据服务器返回的角色编码，返回具体的角色类型
     * @param type
     * @return 用户匹配不上则返回工人角色
     */
    public static UserType getUserType(String type) {
        for (UserType userType : UserType.values()) {
            if (userType.getUserType().equals(type)) {
                return userType;
            }
        }
        return UserType.WORKER;
    }

    private String getUserType() {
        return this.userType;
    }
}
