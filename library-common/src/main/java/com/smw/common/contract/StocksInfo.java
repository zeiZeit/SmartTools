package com.smw.common.contract;

import java.io.Serializable;
import java.util.ArrayList;

public class StocksInfo implements Serializable {
    private ArrayList<String> tradeDate;

    public static StocksInfo getInstance(){
        return StocksInfo.Holder.INSTANCE;
    }

    public static void init(StocksInfo obj){
        StocksInfo.Holder.INSTANCE = obj;
    }

    static class Holder {
        static  StocksInfo INSTANCE =  new StocksInfo();
    }



    public ArrayList<String> getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(ArrayList<String> tradeDate) {
        this.tradeDate = tradeDate;
    }

    public static String getAfterDate(String indexDay,int afterDays){
        if (getInstance().getTradeDate()==null||getInstance().getTradeDate().size()==0){
            return null;
        }else {
            int size = getInstance().getTradeDate().size();
            int index = getInstance().getTradeDate().indexOf(indexDay);
            if (index==-1){
                return null;
            }else {
                if (index+afterDays+1>size){
                    return getInstance().getTradeDate().get(size-1);
                }else {
                    return getInstance().getTradeDate().get(index+afterDays);
                }
            }
        }
    }

}
