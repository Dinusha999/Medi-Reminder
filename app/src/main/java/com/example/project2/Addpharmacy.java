package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Addpharmacy extends AppCompatActivity {
    Button button;
    DatabaseHelper Mydb;
    EditText inputD;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpharmacy);


        Mydb = new DatabaseHelper(this);
        inputD = findViewById(R.id.inputfiledph);
        button=findViewById(R.id.button4);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getInputD = inputD.getText().toString();

                Boolean result = Mydb.addPhar(getInputD);
                if(result){
                    Toast.makeText(getApplicationContext(), "Data has been added to the db", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), PharmacyList.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(), "Data not added to the db", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}