package com.smw.common.contract;

import java.io.Serializable;

public class AppConfig  implements Serializable {

    /**
     * 用来区分有没有变化
     */
    private int v;
    private Version version ;
    private Agreement agreement;

    public static AppConfig getInstance(){
        return Holder.INSTANCE;
    }

    public static void init(AppConfig obj){
        Holder.INSTANCE = obj;
    }



    static class Holder {
        static  AppConfig INSTANCE =  new AppConfig();
    }


    public static class Version implements Serializable{
        public int code;
        public String version;
        public String url;
    }

    public static class Agreement implements Serializable{
        public String user;
        public String privacy;

    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Agreement getAgreement() {
        return agreement;
    }

    public void setAgreement(Agreement agreement) {
        this.agreement = agreement;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}
