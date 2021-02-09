package com.example.bmicalc;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;


public class Main2Activity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void onClickbutton(View view) {
        //String condition;
        EditText weight = (EditText) findViewById(R.id.weightInput);
        EditText height = (EditText) findViewById(R.id.heightInput);
        TextView output = (TextView) findViewById(R.id.output);
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.secondlayout);

        if(weight.getText().toString().equals("")|| height.getText().toString().equals("")) {
            Toast.makeText(this,"Enter your height and weight",Toast.LENGTH_SHORT).show();
        }
        else {
            double kg = Double.parseDouble(weight.getText().toString());
            double m = Double.parseDouble(height.getText().toString());


            double BMI = kg / (m * m);

            if (BMI > 25.0) {
                //condition = "OVERWEIGHT";
                output.setText(String.format("%.2f", BMI) + "(overweight)");
                Toast.makeText(this, "kindly reduce you weight", Toast.LENGTH_LONG).show();
                //return condition;
                Snackbar.make(layout, "Download your report", Snackbar.LENGTH_INDEFINITE)
                        .setAction("DOWNLOAD", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createPDF();
                            }
                        }).setActionTextColor(Color.BLUE).show();
            } else if (BMI > 18.5 && BMI < 25.0) {
                //condition = "FIT";
                output.setText(String.format("%.2f", BMI) + " (You are FIT !)");
                Toast.makeText(this, "Kudos! you are FIT!", Toast.LENGTH_LONG).show();
                //return condition;
                Snackbar.make(layout, "Download your report", Snackbar.LENGTH_INDEFINITE)
                        .setAction("DOWNLOAD", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createPDF();
                            }
                        }).setActionTextColor(Color.BLUE).show();

            } else if (BMI < 18.5) {
                //condition = "UNDERWEIGHT";
                output.setText(String.format("%.2f", BMI) + " (underweight)");
                Toast.makeText(this, "increase your weight", Toast.LENGTH_LONG).show();
                //return condition;
                Snackbar.make(layout, "Download your report", Snackbar.LENGTH_INDEFINITE)
                        .setAction("DOWNLOAD", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createPDF();
                            }
                        }).setActionTextColor(Color.BLUE).show();

            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.download:
                createPDF();
                break;

            case R.id.share:
                Toast.makeText(this,"Sharing is caring",Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }


    public void createPDF(){
        Intent i = new Intent();
        Bundle a = getIntent().getExtras();
        String name = a.getString("1");
        String age = a.getString("2");
        String Bgroup = a.getString("3");
        String DOB = a.getString("4");
        String[] infoTab = {"Name","Age","Blood Group", "DOB", "Condition"};
        String[] userInfo = {name,age,Bgroup,DOB,"check BMI" };


        for(int k = 0; k< userInfo.length;k++){
            Log.i("abcefgthy", userInfo[k]);
        }



        Bitmap bmp, scaledBmp;
        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.healthy);
        scaledBmp = Bitmap.createScaledBitmap(bmp,250,100,false);

        PdfDocument myPDF = new PdfDocument();
        Paint myPaint = new Paint();

        PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(250,400,1).create();
        PdfDocument.Page myPage1 = myPDF.startPage(myPageInfo1);
        Canvas canvas = myPage1.getCanvas();
        canvas.drawBitmap(scaledBmp,0,5,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(10.0f);
        myPaint.setColor(Color.BLUE);
        canvas.drawText("INFORMATION",10,130,myPaint);

        myPaint.setTextAlign(Paint.Align.LEFT);
        myPaint.setTextSize(8.0f);
        myPaint.setColor(Color.BLACK);
        int xStartPos = 10;
        int xStartPos2 = 115;
        int xEndPos = myPageInfo1.getPageWidth()-10;
        int yStartPos = 160;

        for(int j = 0; j< infoTab.length;j++){
            canvas.drawText(infoTab[j], xStartPos,yStartPos,myPaint);
            canvas.drawText(userInfo[j], xStartPos2,yStartPos,myPaint);
            canvas.drawLine(xStartPos,yStartPos+5,xEndPos,yStartPos+5,myPaint);
            yStartPos+=25;
        }



        canvas.drawLine(100,135,100,265,myPaint);

        myPDF.finishPage(myPage1);
        
        File file = new File(Environment.getExternalStorageDirectory().getPath(),"/firstPDF.pdf");

        try {
            myPDF.writeTo(new FileOutputStream(file));
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();}

        myPDF.close();
        Toast.makeText(this,"Report created", Toast.LENGTH_LONG).show();
    }
}
