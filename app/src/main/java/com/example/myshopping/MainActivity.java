package com.example.myshopping;
//myshoping-232d2

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myshopping.adapter.MainAdapter;
import com.example.myshopping.db.MyDbManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyDbManager myDbManager;
    private TextView tvTest;
   // private EditText edTitle, edDisc;
    private List<String> list;
    private RecyclerView rcView;
    private MainAdapter mainAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.id_search);
        SearchView sv=(SearchView)item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("MyLog", "Qery"+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("MyLog", "nextText"+newText);
                mainAdapter.updateAdapter(myDbManager.getFromDb(newText));
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        myDbManager = new MyDbManager(this);
       // edDisc = findViewById(R.id.edDisc);
       // edTitle = findViewById(R.id.edTitle);
        rcView = findViewById(R.id.edView);
        mainAdapter= new MainAdapter(this);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(rcView);
        rcView.setAdapter(mainAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        myDbManager.openDb();
        mainAdapter.updateAdapter(myDbManager.getFromDb(""));
      /*  list = new ArrayList<>();
        list = myDbManager.getFromDbDiscr();
        int l = list.size();
        tvTest.setText(Integer.toString(l));

        int i = 0;
        String tit;
        for (String title : myDbManager.getFromDb()) {
            tit = list.get(i);
            tvTest.append(title);
            tvTest.append("=");
            tvTest.append(tit);
            tvTest.append(System.getProperty("line.separator"));
            i = i + 1;

        } */
    }

    public void onClickAdd(View view) {
        Intent i = new Intent(MainActivity.this,EditActivity.class);
        startActivity(i);

    }
    public void onClickRed (View view){

        Intent i = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDbManager.closeDb();
    }
    private ItemTouchHelper getItemTouchHelper (){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.d("MyLog", "pos"+viewHolder.getAdapterPosition());
                mainAdapter.removeItem(viewHolder.getAdapterPosition(),myDbManager);
            }
        });
    }
}