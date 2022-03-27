package com.smw.common.db.kline;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface  KlineGuessDao {

    String TABLE_NAME = "KlineGuessBean";


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertKlineGuess(KlineGuessBean... guessBeans);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertKlineGuess(KlineGuessBean guessBeans);



    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateKlineGuess(KlineGuessBean... users);

    /**
     * Room会把对应的参数信息更新到数据库里面
     * @param users  操作参数
     * @return    表示更新成功了多少行
     */
    @Update()
    int updateAll(KlineGuessBean... users);

    @Update
    int updateAll(List<KlineGuessBean> user);

    /**
     * Room会把对应的参数信息指定的行删除掉
     * @param users  操作参数
     * @return   表示删除了多少行
     */
    @Delete
    int deleteKlineGuess(KlineGuessBean... users);

    /**
     * Delete all users.
     */
    @Query("DELETE FROM " + TABLE_NAME)
    void deleteAll();


    //------------------------query------------------------
    // 简单sql语句，查询user表所有的column
    @Query("SELECT * FROM " + TABLE_NAME)
    List<KlineGuessBean> loadAllKlineGuess();

    @Query("SELECT * FROM "+ TABLE_NAME+" WHERE guess_date == :date")
    List<KlineGuessBean>  loadGuessByDate(String date);

    @Query("SELECT * FROM "+ TABLE_NAME+" WHERE guess_date == :date AND ts_code == :code")
    List<KlineGuessBean> loadGuessByDateAndCode(String date, String code);

    @Query("SELECT * FROM "+ TABLE_NAME+" WHERE ts_code == :code order by guess_date desc")
    List<KlineGuessBean>  loadGuessByCode(String code);

    @Query("SELECT * FROM "+ TABLE_NAME+" WHERE latest_rets > 0")
    List<KlineGuessBean>  loadGuessSuccess();

    @Query("SELECT * FROM "+ TABLE_NAME+" WHERE latest_rets < 0")
    List<KlineGuessBean>  loadGuessFailed();

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME)
    int loadAllGuessNum();

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME+" WHERE up_or_down == 2")
    int loadGuessUpNum();

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME+" WHERE up_or_down == 1")
    int loadGuessDownNum();

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME+" WHERE guess_result == 2")
    int loadGuessRightNum();

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME+" WHERE guess_result == 1")
    int loadGuessWrongNum();

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME+" WHERE is_finish == 2")
    int loadGuessFinishNum();

    @Query("SELECT COUNT(*) FROM " + TABLE_NAME+" WHERE is_finish == 1")
    int loadGuessNotFinishNum();

//    @Query("SELECT guess_date FROM "+ TABLE_NAME+" order by guess_date limit 1 ")
//    String  loadLatestGuessDate(String date);

    @Query("SELECT * FROM "+ TABLE_NAME+" order by guess_date desc limit 3 ")
    List<KlineGuessBean>  loadLatest5Guess();

}
