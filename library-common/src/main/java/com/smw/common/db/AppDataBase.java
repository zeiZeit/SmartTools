package com.smw.common.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.smw.common.db.kline.KlineGuessBean;
import com.smw.common.db.kline.KlineGuessDao;

/**
 * 使用中 annotationProcessor 'androidx.room:room-compiler:2.2.2'
 * 改为kapt 'androidx.room:room-compiler:2.2.2' ，如果项目中使用了kotlin
 *
 * @author
 * TypeConverters({Converters.class}) TODO 类型转化 有待研究其作用
 */
@Database(entities = {
        KlineGuessBean.class
//        , Book.class
}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

//    public abstract BookDao bookDao();
//    public abstract UserDao userDao();
    public abstract KlineGuessDao klineGuessDao();


    private static volatile AppDataBase INSTANCE;

    /**
     * 关于AppDataBase 的使用：
     * 1）如果database的版本号不变。app操作数据库表的时候会直接crash掉。(错误的做法)
     * 2）如果增加database的版本号。但是不提供Migration。app操作数据库表的时候会直接crash掉。（错误的做法）
     * 3）如果增加database的版本号。同时启用fallbackToDestructiveMigration。这个时候之前的数据会被清空掉。
     * 如下fallbackToDestructiveMigration()设置。(不推荐的做法)
     */
    public static AppDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "android_room_dev.db")
                            // 设置是否允许在主线程做查询操作
                            .allowMainThreadQueries()
                             //设置数据库升级(迁移)的逻辑
//                            .addMigrations(MIGRATION_3_4)
//                            .addMigrations(MIGRATION_4_5)
//                            .addMigrations(MIGRATION_5_6)
                            // setJournalMode(@NonNull JournalMode journalMode)  设置数据库的日志模式
                            // 设置迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                            // .fallbackToDestructiveMigration() 会清理表中的数据 ，不建议这样做
                            //设置从某个版本开始迁移数据库如果发生错误，将会重新创建数据库，而不是发生崩溃
                            //.fallbackToDestructiveMigrationFrom(int... startVersions);
                            .addCallback(new RoomDatabase.Callback() {
                                // 进行数据库的打开和创建监听
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                }
                            })

                            //默认值是FrameworkSQLiteOpenHelperFactory，设置数据库的factory。
                            // 比如我们想改变数据库的存储路径可以通过这个函数来实现
                            // .openHelperFactory(SupportSQLiteOpenHelper.Factory factory);
                            .build();
                }
            }
        }
        return INSTANCE;
    }

/*
    */
/**
     * 数据库版本 3->4
     *//*

    private final static Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE KlineGuessBean ADD COLUMN name TEXT");
            database.execSQL("ALTER TABLE KlineGuessBean ADD COLUMN is_finish INTEGER   NOT NULL DEFAULT 0 ");
        }
    };

    */
/**
     * 数据库版本 4->5
     *//*

    private final static Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE KlineGuessBean ADD COLUMN result_rets float NOT NULL DEFAULT 0 ");
        }
    };


*/

    /*    private final static Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `book` (`uid` INTEGER PRIMARY KEY autoincrement, `name` TEXT , `userId` INTEGER, 'time' INTEGER)");
        }
    };*/
}