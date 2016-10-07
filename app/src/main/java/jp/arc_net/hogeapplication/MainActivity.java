package jp.arc_net.hogeapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase hogeDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HogeSqlLiteOpenHelper hogeHelper = new HogeSqlLiteOpenHelper(getApplicationContext());
        this.hogeDb = hogeHelper.getWritableDatabase();

        Button nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View x) {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        EditText editText = (EditText) findViewById(R.id.editText);
                        String str = editText.getText().toString();
                        intent.putExtra("INPUT_TEXT", str);
                        ContentValues values = new ContentValues();
                        values.put("INPUT_TIME", System.currentTimeMillis());
                        values.put("INPUT_TEXT", str);
                        hogeDb.insert("HOGE_MESSAGE", "NULL", values);
                        startActivity(intent);
                    }
                }
        );

    }
}
