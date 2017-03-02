package com.example.iem.project_tub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.controller.APIClient;
import com.example.iem.project_tub.models.Horaire;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Horaires extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaires);

        Intent intent = getIntent();
        int numeroLigne = intent.getIntExtra("ligne", 0);
        String nomArret = intent.getStringExtra("arret");
        int idAssoc = intent.getIntExtra("idAssoc", 0);

        TextView detailsArret = (TextView) findViewById(R.id.horaire_activity_tv_details_arret);
        detailsArret.setText("Ligne " + numeroLigne + " : " + nomArret);

        Call<List<Horaire>> horairesCall = APIClient.getApiInterface().getHoraires(idAssoc);
        horairesCall.enqueue(new Callback<List<Horaire>>() {
            @Override
            public void onResponse(Call<List<Horaire>> call, Response<List<Horaire>> response) {
                loadHours(response.body());
            }

            @Override
            public void onFailure(Call<List<Horaire>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erreur lors de la récupération des horaires", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadHours(List<Horaire> horaireList){
        String horairesTxt = "";

        for (Horaire horaire : horaireList){
            horairesTxt = horairesTxt + " " + horaire.getHeure();
        }

        TextView tvHoraires = (TextView) findViewById(R.id.horaire_activity_tv_horaires);
        tvHoraires.setText(horairesTxt);
    }
}
