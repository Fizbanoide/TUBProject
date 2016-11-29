package com.example.iem.project_tub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iem.project_tub.R;

/**
 * Created by iem on 29/11/2016.
 */

public class MainActivity extends AppCompatActivity {

    Button btCarte, btRecherche;
    TextView tvCarte, tvRecherche;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCarte = (Button) findViewById(R.id.btCarte);
        btRecherche = (Button) findViewById(R.id.btRecherche);
        tvCarte = (TextView) findViewById(R.id.tvCarte);
        tvRecherche = (TextView) findViewById(R.id.tvRecherche);

        btRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recherche = new Intent(MainActivity.this, Recherche.class);
                startActivity(recherche);
            }
        });

        btCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent carte = new Intent(MainActivity.this, Carte.class);
                startActivity(carte);
            }
        });


    }


}
