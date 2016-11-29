package com.example.iem.project_tub.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.models.HoraireManager;

import org.w3c.dom.Text;

import java.util.List;

public class Horaires extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaires);

        Intent intent = getIntent();
        int idAssoc = intent.getIntExtra("idAssoc", 0);

        List<String> horaires = HoraireManager.getInstance(this).getHorairesByAssoc(idAssoc);

        TextView detailsArret = (TextView) findViewById(R.id.horaire_activity_tv_details_arret);
        detailsArret.setText("Ligne " + intent.getStringExtra("ligne") + " : " + intent.getStringExtra("arret"));

        TextView tvHoraires = (TextView) findViewById(R.id.horaire_activity_tv_horaires);
        tvHoraires.setText(horaires.toString());
    }
}
