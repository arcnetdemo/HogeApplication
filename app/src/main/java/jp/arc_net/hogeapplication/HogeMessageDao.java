package jp.arc_net.hogeapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;

import java.util.ArrayList;

/**
 * Created by akira on 2017/01/20.
 */
public class HogeMessageDao {

    private final String query;
    private SQLiteDatabase hogeDb;

    private ArrayList<Entity> entityList;

    public HogeMessageDao(SQLiteDatabase hogeDb, String query) {
        super();
        this.hogeDb = hogeDb;
        this.query = query;
        //
        this.entityList = new ArrayList<>();

        updateEntityList();
    }

    private void updateEntityList() {
        Cursor cursor = this.hogeDb.rawQuery(this.query, null);

        cursor.moveToFirst();
        int count = cursor.getCount();
        if (count >= 10 ) count = 10;

        for (int i=0; i < count; i ++) {
            Entity entity = new Entity();
            entity.setTime(cursor.getLong(1));
            entity.setMessage(cursor.getString(2));
            entity.setLatitude(cursor.getDouble(3));
            entity.setLongitude(cursor.getDouble(4));

            //this.listViewAdapter.add(str);
            entityList.add(entity);

            cursor.moveToNext();
        }
    }

    public void insert(String str, Double latitude, Double longitude) {
        ContentValues values = new ContentValues();
        values.put("INPUT_TIME", System.currentTimeMillis());
        values.put("INPUT_TEXT", str);

        values.put("LATITUDE", latitude);
        values.put("LONGITUDE", longitude);
        values.put("ALTITUDE", new Double(0));

        hogeDb.insert("HOGE_MESSAGE", "NULL", values);

        updateEntityList();
    }

    public String getMessage(int index) {
        return entityList.get(index).getMessage();
    }

    public Double getLatitude(int index) {
        return entityList.get(index).getLatitude();
    }

    public Double getLongitude(int index) {
        return entityList.get(index).getLongitude();
    }

    public int getSize() {
        return entityList.size();
    }

    // Entity とは レコードをJavaのオブジェクトで定義するためのクラスにつけられる事が多い
    public class Entity {
        private long time;
        private String message;
        private Double longitude;
        private Double latitude;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }
    }
}
