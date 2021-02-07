package com.example.myshopping;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myshopping.db.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private List<String> listData;
    private List<User> listTemp;
    private DatabaseReference myDataBase;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_layout);
        init();
        getDataFromDb();
        setOnClickItem();
    }


    private void init(){
        listView = findViewById(R.id.listView);
        listData = new ArrayList<>();
        listTemp = new ArrayList<>();

        arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(arrayAdapter);
        myDataBase= FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
    private void getDataFromDb (){
        Log.d("MyLog", "getData");
        ValueEventListener vListener =new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (listData.size() > 0) listData.clear();
                if (listTemp.size() > 0) listTemp.clear();
                Log.d("MyLog", "snapshot"+listData.size());
                for (DataSnapshot ds:snapshot.getChildren()){

                    User user = ds.getValue(User.class);

                    assert user.title != null;

                    listData.add(user.title);
                    listTemp.add(user);
                    Log.d("MyLog", "user title - "+user.title);
                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MyLog", "error");
            }
        };
        myDataBase.addValueEventListener(vListener);
    }
    private void setOnClickItem (){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listTemp.get(position);
                Intent i = new Intent(ReadActivity.this, ShowActivity.class);
                i.putExtra("user_title",user.title);
                i.putExtra("user_descr",user.descr);
                startActivity(i);
            }
        });
    }
}
