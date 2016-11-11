package jp.arc_net.hogeapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Main2Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> listViewAdapter;

    private SQLiteDatabase hogeDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.listView = (ListView) findViewById(R.id.listView);
        this.listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        HogeSqlLiteOpenHelper hogeHelper = new HogeSqlLiteOpenHelper(getApplicationContext());
        this.hogeDb = hogeHelper.getReadableDatabase();

        Cursor cursor = this.hogeDb.rawQuery("SELECT * FROM HOGE_MESSAGE order by INPUT_TIME DESC", null);

        cursor.moveToFirst();
        int count = cursor.getCount();
        if (count >= 10 ) count = 10;

        for (int i=0; i < count; i ++) {
            String str = cursor.getString(2);
            this.listViewAdapter.add(str);
            cursor.moveToNext();
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
    }
}
