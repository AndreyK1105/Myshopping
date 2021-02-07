package com.example.myshopping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myshopping.adapter.ListItem;
import com.example.myshopping.db.MyConstants;
import com.example.myshopping.db.MyDbManager;
import com.example.myshopping.db.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {
    private final int PICK_IMAGE_CODE= 123;
    private ConstraintLayout imageContener;
    private ImageView imNewImage;
    private EditText edText, edDescr;
    private MyDbManager myDbManager;
    private FloatingActionButton fbAddImage;
    private String tempUri= "empty";
    private boolean isEditState= true;
    private DatabaseReference myDataBase;
    private String USER_KEY = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
        getMyIntents();
        myDbManager.openDb();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE_CODE && data!=null){
        imNewImage.setImageURI(data.getData());
        tempUri=data.getData().toString();

        }

    }

    private void init(){
        imNewImage=findViewById(R.id.imNewImage);
        myDbManager =new MyDbManager(this);
        edText=findViewById(R.id.edTitle1);
        edDescr=findViewById(R.id.edDesc);
        imageContener=findViewById(R.id.imageContener);
        fbAddImage=findViewById(R.id.fbAddImage);
        myDataBase= FirebaseDatabase.getInstance().getReference(USER_KEY);
    }
    private void getMyIntents(){
        Intent i=getIntent();
        if(i!=null){
            ListItem item= (ListItem)i.getSerializableExtra(MyConstants.LIST_ITEM_INTENT);
            isEditState=i.getBooleanExtra(MyConstants.EDIT_STATE,true);

        if(!isEditState) {
            edText.setText(item.getTitle());
            edDescr.setText(item.getDesc());
        }
        }
    }
    public void onClickSave (View view){
if(edText.getText().toString().equals("") || edDescr.getText().toString().equals("")) {
    Toast.makeText(this, R.string.text_empty, Toast.LENGTH_SHORT).show();
}
else {
    Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    User newUser = new User(edText.getText().toString(),edDescr.getText().toString());
    myDbManager.insertToDb(edText.getText().toString(), edDescr.getText().toString(), tempUri);
    myDbManager.closeDb();
    myDataBase.push().setValue(newUser);
   //myDataBase.push().setValue(edDescr.getText().toString());
    finish();
}

    }
    public void onClickDeleteImage (View view){
        imageContener.setVisibility(View.GONE);
        fbAddImage.setVisibility(View.VISIBLE);
        imNewImage.setImageResource(R.drawable.ic_image_add_def);
        tempUri= "empty";
    }
    public void onClickAddImage (View view){
        imageContener.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);

    }
    public void onClickChooseImage (View view){
     Intent chooser = new Intent(Intent.ACTION_PICK);
     chooser.setType("image/*");
     startActivityForResult(chooser, PICK_IMAGE_CODE);

    }
}