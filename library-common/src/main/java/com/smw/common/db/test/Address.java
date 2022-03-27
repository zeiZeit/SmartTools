package com.smw.common.db.test;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

/**
 * onDelete即当parent中有删除操作时，onUpdate即当parent中有更新操作时，
 * <p>
 * child对应响应的动作有四种：
 * 1. NO_ACTION：当parent中的key有变化的时候child不做任何动作,默认动作
 * 2. RESTRICT：当parent中的key有依赖的时候禁止对parent做动作，做动作就会报错。
 * 3. SET_NULL：当paren中的key有变化的时候child中依赖的key会设置为NULL。
 * 4. SET_DEFAULT：当parent中的key有变化的时候child中依赖的key会设置为默认值。
 * 5. CASCADE：当parent中的key有变化的时候child中依赖的key会跟着变化
 *
 * deferred：默认值false，在事务完成之前，是否应该推迟外键约束。
 * 比如当我们启动一个事务插入很多数据的时候，事务还没完成之前，parent引起key变化的时候。
 * 可以设置deferred为ture,让key立即改变。为false时，事务完成后child的key才会统一进行相应处理
 *
 */
@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "user_id",
        onDelete = CASCADE,
        onUpdate = ForeignKey.NO_ACTION,
        deferred = false
))

public class Address {
    public String street;
    public String state;
    public String city;

    @ColumnInfo(name = "post_code")
    public int postCode;
}