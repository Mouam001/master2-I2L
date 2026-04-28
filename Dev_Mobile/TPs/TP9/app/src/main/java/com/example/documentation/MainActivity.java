package com.example.documentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    private Button btnShared, btnExternal, btnInternal, btnPref, btnBDDLocal, btnProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnExternal = findViewById(R.id.btnExternal);
        btnInternal = findViewById(R.id.btnInternal);
        btnShared = findViewById(R.id.btnShared);
        btnPref = findViewById(R.id.btnPrefs);
        btnBDDLocal = findViewById(R.id.btnRoom);
        btnProvider = findViewById(R.id.btnProvider);

        btnShared.setOnClickListener( v->
                startActivity(new Intent(this, MediaStoreActivity.class))
                );

        btnInternal.setOnClickListener( v->
                startActivity(new Intent(this, InternalStorageActivity.class))
        );

        btnExternal.setOnClickListener( v->
                startActivity(new Intent(this, ExternalStorageActivity.class))
        );

        btnPref.setOnClickListener( v->
                startActivity(new Intent(this, SharedPreference.class))
        );

        btnBDDLocal.setOnClickListener(v->
                startActivity(new Intent(this, RoomActivity.class)));

        btnProvider.setOnClickListener(v->
                startActivity(new Intent(this, ContentProviderActivity.class)));

    }



}
