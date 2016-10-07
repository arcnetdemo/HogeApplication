package jp.arc_net.hogeapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by akira on 2016/10/07.
 */
public class HogeSqlLiteOpenHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_TABLE = "CREATE TABLE HOGE_MESSAGE (PK INTEGER PRIMARY KEY AUTOINCREMENT," +
            " INPUT_TIME INTEGER NOT NULL," +
            " INPUT_TEXT TEXT NOT NULL);";

    public HogeSqlLiteOpenHelper(Context context) {
        super(context, "hoge_sqllite.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        sqLiteDatabase.execSQL("DROP TABLE HOGE_MESSAGE;");
        onCreate(sqLiteDatabase);
    }
}
