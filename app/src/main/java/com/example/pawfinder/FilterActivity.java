package com.example.pawfinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    private String sex ;
    private String species;
    private String shelter;
    private int age = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        Button btnBack = findViewById(R.id.backBtn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        Button petTypeFilterDog = findViewById(R.id.dogfilter);
        petTypeFilterDog.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               if(species.equals(""))
                   species ="Dog";
               else{
                   species+=" Dog";
               }

           }
        });
        Button petTypeFilterCat = findViewById(R.id.catfilter);
        petTypeFilterCat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(species.equals(""))
                    species ="Cat";
                else{
                    species+=" Cat";
                }

            }
        });
        Button petTypeFilterOther = findViewById(R.id.otherfilter);
        petTypeFilterOther.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(species.equals(""))
                species ="Other";
                else{
                    species+=" Other";
                }

            }
        });
        Button petTypeFilterZero = findViewById(R.id.Zero);
        petTypeFilterZero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( age==0){

                }

                else{
                    age=0;
                }

            }
        });
        Button petTypeFilterFive = findViewById(R.id.Zero);
        petTypeFilterFive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( age==5){

                }

                else{
                    age=5;
                }

            }
        });
        Button petTypeFilterTen = findViewById(R.id.Zero);
        petTypeFilterTen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( age==10){

                }

                else{
                    age=10;
                }

            }
        });





//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);





//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public String getSpecies(){
        return species;
    }
    public int getAge(){
        return age;
    }
}