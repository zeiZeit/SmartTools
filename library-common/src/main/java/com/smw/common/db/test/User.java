package com.smw.common.db.test;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**

 * 说明：Room 测试类
 * Entity注解包含的属性有：
 * tableName：设置表名字。默认是类的名字,不区分大小写。
 * indices：设置索引。
 * inheritSuperIndices：父类的索引是否会自动被当前类继承。
 * primaryKeys：设置主键。
 * foreignKeys：设置外键。
 */
//@Entity(primaryKeys = {"firstName", "lastName"}) 也可以这样设置多个主键
//@Entity(indices = {@Index("firstName"), @Index(value = {"last_name", "address"},unique = true)}) unique 设置是否唯一索引
@Entity(tableName = "UserBean")
public class User {
    @Ignore
    public User(String userName) {
//        id = UUID.randomUUID().toString();
        mUserName = userName;
    }

    public User( ) {

    }

    @Ignore
    public User(int id, String userName) {
        this.id = id;
        this.mUserName = userName;
    }

    //设置主键自增， 每个类需要一个主键
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "username")
    public String mUserName;

    //自定义设置列名
    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    public int age;

    /**
     * 如果有多个相同类型的嵌入字段，则可以设置前缀属性来保持每个列的唯一性。
     * 然后Room会将提供的值添加到嵌入对象中每个列名的开头。
     */
    @Embedded(prefix = "first")
    public Address address;

    @Embedded(prefix = "second")
    public Address address2;

    public String school;

    /**
     * @Ignore 默认每个字段队列数据库中的一列，除非用 @Ignore注解不添加进入数据表中
     *
     */
    @Ignore
    public Bitmap picture;
}



