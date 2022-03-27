package com.smw.common.db.test;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * dao  在编译期就会自动报错，强大的一匹
 * Data Access Object for the users table.
 *
 */
@Dao
public interface UserDao {

    /**
     * 当DAO里面的某个方法添加了@Insert注解。Room会生成一个实现，将所有参数插入到数据库中的一个单个事务。
     * <p>
     * onConflict：表示当插入有冲突的时候的处理策略。OnConflictStrategy封装了Room解决冲突的相关策略：
     * OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务。
     * OnConflictStrategy.ROLLBACK：冲突策略是回滚事务。
     * OnConflictStrategy.ABORT：冲突策略是终止事务。默认策略
     * OnConflictStrategy.FAIL：冲突策略是事务失败。
     * OnConflictStrategy.IGNORE：冲突策略是忽略冲突。
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertUsers(User... users);

    /**
     * 当参数只有一个时，返回值只可以是long
     *
     * @param user 参数
     * @return 表示插入的rowId
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    /**
     * 有多个参数时，返回值可以是long[]或者List<long>，
     *
     * @param users 参数
     * @return long表示插入的rowId
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertUser(User... users);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateUsers(User... users);

    /**
     * Room会把对应的参数信息更新到数据库里面
     * @param users  操作参数
     * @return    表示更新成功了多少行
     */
    @Update()
    int updateAll(User... users);

    @Update
    int updateAll(List<User> user);

    /**
     * Room会把对应的参数信息指定的行删除掉
     * @param users  操作参数
     * @return   表示删除了多少行
     */
    @Delete
    int deleteUsers(User... users);

    /**
     * Delete all users.
     */
    @Query("DELETE FROM " + TABLE_NAME)
    void deleteAll();

    //所有的CURD根据primary key进行匹配
    String TABLE_NAME = "UserBean";

    //------------------------query------------------------
    // 简单sql语句，查询user表所有的column
    @Query("SELECT * FROM " + TABLE_NAME)
    List<User> loadAllUsers();

    /**
     * 它允许您对数据库执行读/写操作。@Query在编译的时候会验证准确性，
     * 所以如果查询出现问题在编译的时候就会报错。比如字段名称不匹配，没有该字段
     * @param firstName  条件插叙
     * @return  查询返回的列表
     */
    @Query("SELECT * FROM UserBean WHERE first_name == :firstName")
    User[] loadAllUsersByFirstName(String firstName);

    @Query("SELECT * FROM UserBean WHERE age BETWEEN :minAge AND :maxAge")
    List<User> loadAllUsersBetweenAges(int minAge, int maxAge);

    @Query("SELECT * FROM UserBean WHERE first_name LIKE :search " + "OR last_name LIKE :search")
    List<User> findUserWithName(String search);

//    /**
//     * 只查询特定列信息
//     */
//    @Query("SELECT first_name, last_name FROM UserBean")
//    List<NameTuple> loadFullName();


    /**
     * 传递一组的参数，返回的对象可以用单独的对象接收
     */
    @Query("SELECT first_name, last_name FROM UserBean WHERE school IN (:regions)")
    List<NameTuple> loadUsersFromRegions(List<String> regions);

    /**
     * LiveData
     */
    @Query("SELECT first_name, last_name FROM UserBean WHERE school IN (:regions)")
    LiveData<List<NameTuple>> loadUsersFromRegionsSync(List<String> regions);

    /**
     * Rxjava2 中的 对象
     */
    @Query("SELECT * from UserBean")
    Flowable<List<User>> loadUser();

    /**
     * 直接返回cursor
     */
    @Query("SELECT * FROM userbean WHERE age > :minAge LIMIT 5")
    Cursor loadRawUsersOlderThan(int minAge);

    /*
     * 多表联查
     */
//    @Query("SELECT UserBean.* FROM book "
//            + "INNER JOIN loan ON loan.bookId = book.bookId "
//            + "INNER JOIN UserBean ON userbean.id = loan.userId "
//            + "WHERE userbean.last_name LIKE :lastName")
//    List<Book> findBooksBorrowedByNameSync(String lastName);


//    @Query("SELECT userbean.last_name AS userName, book.name AS bookName "
//            + "FROM userbean, book "
//            + "WHERE :userId = book.user_id")
//    LiveData<List<LendingBook>> loadUserAndPetNames(int userId);

    /**
     * 返回第一个用户
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM " + TABLE_NAME + " LIMIT 1")
    Flowable<User> getUser();

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param user the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUserSingle(User user);


}