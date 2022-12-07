package com.smw.contract.data.model;

public class ContractListItem {
    private String contract_id;
    private String contract_name;
    private String create_time;
    private String update_time;
    private String create_user;
    private String create_user_head;
    private String create_uuid;
    private int enable;
    private int status;


    public ContractListItem() {
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getCreate_user_head() {
        return create_user_head;
    }

    public void setCreate_user_head(String create_user_head) {
        this.create_user_head = create_user_head;
    }

    public String getCreate_uuid() {
        return create_uuid;
    }

    public void setCreate_uuid(String create_uuid) {
        this.create_uuid = create_uuid;
    }

    public int isEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
