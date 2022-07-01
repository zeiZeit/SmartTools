package com.smw.common.contract.message;


import com.jeremyliao.liveeventbus.core.LiveEvent;

public class UpdateUserInfoEvent implements LiveEvent {
    //定义type 1为新增了一个预测 2为修改了一个预测
    public final int type;

    public UpdateUserInfoEvent(int type) {
        this.type = type;
    }
}