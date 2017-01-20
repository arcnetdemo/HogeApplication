package jp.arc_net.hogeapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.net.URI;

public class Main2Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;

    private SQLiteDatabase hogeDb;

    private HogeMessageDao messageDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.listView = (ListView) findViewById(R.id.listView);
        this.listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        HogeSqlLiteOpenHelper hogeHelper = new HogeSqlLiteOpenHelper(getApplicationContext());
        this.hogeDb = hogeHelper.getReadableDatabase();

//        Cursor cursor = this.hogeDb.rawQuery("SELECT * FROM HOGE_MESSAGE order by INPUT_TIME DESC", null);
//
//        cursor.moveToFirst();
//        int count = cursor.getCount();
//        if (count >= 10 ) count = 10;
//
//        for (int i=0; i < count; i ++) {
//            String str = cursor.getString(2);
//            this.listViewAdapter.add(str);
//            cursor.moveToNext();
//        }
        messageDao = new HogeMessageDao(this.hogeDb, "SELECT * FROM HOGE_MESSAGE order by INPUT_TIME DESC");

        for (int i = 0; i < messageDao.getSize(); i++) {
            this.listViewAdapter.add(messageDao.getMessage(i));
        }

        //Intent intent = getIntent();
        //String str = intent.getStringExtra("INPUT_TEXT");

        //this.listViewAdapter.add(str); // "test string. 1"
        //this.listViewAdapter.add("test string. 2");
        //this.listViewAdapter.add("test string. 3");
        //this.listViewAdapter.add("test string. 4");
        //this.listViewAdapter.add("test string. 5");
        //this.listViewAdapter.add("test string. 6");
        //this.listViewAdapter.add("test string. 7");
        //this.listViewAdapter.add("test string. 8");
        //this.listViewAdapter.add("test string. 9");
        //this.listViewAdapter.add("test string. 10");
        //this.listViewAdapter.add("test string. 11");
        //this.listViewAdapter.add("test string. 12");
        //this.listViewAdapter.add("test string. 13");

        this.listView.setAdapter(this.listViewAdapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // NG cursor.getCount();
//                Cursor cursor = hogeDb.rawQuery("SELECT LATITUDE, LONGITUDE FROM HOGE_MESSAGE ORDER BY INPUT_TIME DESC;", null);
//                cursor.move(position + 1);
//
//                String url_string = "geo:"+cursor.getDouble(0)+","+cursor.getDouble(1);
                String url_string = "geo:"+messageDao.getLatitude(position+1)+","+messageDao.getLongitude(position+1);

                Uri uri = Uri.parse(url_string);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");

                startActivity(intent);
            }
        });
    }
}
