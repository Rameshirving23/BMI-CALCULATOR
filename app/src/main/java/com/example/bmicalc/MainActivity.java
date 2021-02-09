package com.example.bmicalc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    EditText Name;
    EditText age;
    EditText bloodGroup;
    EditText dateOfbirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    public void clicked(View v){
        Name = (EditText) findViewById(R.id.Nameid);
        age = (EditText) findViewById(R.id.ageId);
        bloodGroup = (EditText) findViewById(R.id.GroupId);
        dateOfbirth = (EditText) findViewById(R.id.DOBid);

        String userName = Name.getText().toString();
        String userAge = age.getText().toString();
        String userBGroup = bloodGroup.getText().toString();
        String userDOB = dateOfbirth.getText().toString();

        if(userDOB.equals("")||userBGroup.equals("")||userAge.equals("")||userName.equals("")){
            Toast.makeText(this,"Enter the required details",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            Bundle b = new Bundle();
            b.putString("1", userName);
            b.putString("2", userAge);
            b.putString("3", userBGroup);
            b.putString("4", userDOB);
            i.putExtras(b);
            startActivity(i);
        }
    }
}


            /*Name = (EditText) findViewById(R.id.Nameid);
            age = (EditText) findViewById(R.id.ageId);
            bloodGroup = (EditText) findViewById(R.id.GroupId);
            dateOfbirth = (EditText) findViewById(R.id.DOBid);

            String userName = Name.getText().toString();
            String userAge = age.getText().toString();
            String userBGroup = bloodGroup.getText().toString();
            String userDOB = dateOfbirth.getText().toString();

            i.putExtra("name",userName);
            i.putExtra("age",userAge);
            i.putExtra("bgroup",userBGroup);
            i.putExtra("dateofbirth",userDOB);*/