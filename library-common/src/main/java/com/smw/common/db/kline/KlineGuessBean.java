package com.smw.common.db.kline;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "KlineGuessBean",
        indices = {@Index(value = {"ts_code","guess_date"},unique = true)})
public class KlineGuessBean {

    //设置主键自增， 每个类需要一个主键
    @PrimaryKey(autoGenerate = true)
    public int id;

    //哪一天猜的  格式类似 20220101
    @ColumnInfo(name = "guess_date")
    public String guessDate;

    //结束日期  格式类似 20220101
    @ColumnInfo(name = "end_date")
    public String endDate;

    //股票code
    @ColumnInfo(name = "ts_code")
    public String tsCode;

    //股票名
    @ColumnInfo(name = "name")
    public String name;

    //猜的几天内涨还是跌
    @ColumnInfo(name = "days")
    public int days;

    //猜的是 涨还是跌 0-没猜，1-猜跌, 2-猜涨
    @ColumnInfo(name = "up_or_down")
    public int upOrDown;

    //猜测原因
    @ColumnInfo(name = "guess_reason")
    public String guessReason;

    //入选价格
    @ColumnInfo(name = "select_price")
    public float selectPrice;

    //最新收益
    @ColumnInfo(name = "latest_rets")
    public float latestRets;

    //最新收益
    @ColumnInfo(name = "result_rets")
    public float resultRets;

    //猜的结果-到目前 0-没猜，1-没猜对，2猜对了
    @ColumnInfo(name = "guess_result")
    public int guessResult;

    //猜的结果-到目前 0-当天，1-没到时间，2-周期结束
    @ColumnInfo(name = "is_finish")
    public int isFinish;

}
