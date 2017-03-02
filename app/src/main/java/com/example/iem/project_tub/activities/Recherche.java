package com.example.iem.project_tub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.controller.APIClient;
import com.example.iem.project_tub.models.Arret;
import com.example.iem.project_tub.models.Ligne;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Recherche extends AppCompatActivity {

    private static final int REQUESTCODELIGNE = 1;
    private static final int REQUESTCODEARRET = 2;

    private Button btLigne;
    private Button btArret;
    private Button btRechercher;

    private List<Ligne> ligneList;
    private List<Arret> arretList;

    private int idLigne;
    private int idArret;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        Call<List<Ligne>> listLigneCall = APIClient.getApiInterface().getListLignes();
        listLigneCall.enqueue(new Callback<List<Ligne>>() {
            @Override
            public void onResponse(Call<List<Ligne>> call, Response<List<Ligne>> response) {
                ligneList = response.body();
            }

            @Override
            public void onFailure(Call<List<Ligne>> call, Throwable t) {
                ligneList = null;
                Toast.makeText(getApplicationContext(), "Erreur lors du chargement des lignes", Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Arret>> listArretCall = APIClient.getApiInterface().getListArrets();
        listArretCall.enqueue(new Callback<List<Arret>>() {
            @Override
            public void onResponse(Call<List<Arret>> call, Response<List<Arret>> response) {
                arretList = response.body();
            }

            @Override
            public void onFailure(Call<List<Arret>> call, Throwable t) {
                arretList = null;
                Toast.makeText(getApplicationContext(), "Erreur lors du chargement des arrets", Toast.LENGTH_SHORT).show();
            }
        });


        btLigne = (Button) findViewById(R.id.main_activity_bt_choisir_ligne);
        btArret = (Button) findViewById(R.id.main_activity_bt_choisir_arret);
        btRechercher = (Button) findViewById(R.id.main_activity_bt_rechercher);

        btLigne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btArret.setText("Choisir arrêt");
                btRechercher.setEnabled(false);
                Intent getLigne = new Intent(Recherche.this, GetLigne.class);
                startActivityForResult(getLigne, REQUESTCODELIGNE);
            }
        });

        btArret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getArret = new Intent(Recherche.this, GetArret.class);
                getArret.putExtra("idLigne", idLigne);
                getArret.putExtra("nomLigne", btLigne.getText());
                startActivityForResult(getArret, REQUESTCODEARRET);
            }
        });

        btRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<Integer> idLineStopCall = APIClient.getApiInterface().getIdLineStop(idLigne, idArret);
                idLineStopCall.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Integer idLineStop = response.body();
                        System.out.println("ligne : " + idLigne + ", stop : " + idArret);
                        System.out.println(Integer.toString(idLineStop));

                        Intent rechercherHoraires = new Intent(Recherche.this, Horaires.class);
                        rechercherHoraires.putExtra("ligne", ligneList.get(idLigne - 1).getNumero());
                        rechercherHoraires.putExtra("arret", arretList.get(idArret - 1).getNom());
                        rechercherHoraires.putExtra("idAssoc", idLineStop);
                        startActivity(rechercherHoraires);
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Erreur lors de la recherche des horaires", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODELIGNE){
            if(resultCode == RESULT_OK){
                idLigne = data.getIntExtra("idLigne", 0);
                btLigne.setText(ligneList.get(idLigne - 1).getNumero() + " " + ligneList.get(idLigne - 1).getTerminus());
                btArret.setEnabled(true);
            }
        } else if (requestCode == REQUESTCODEARRET) {
            if (resultCode == RESULT_OK) {
                idArret = data.getIntExtra("idArret", 0);
                btArret.setText(arretList.get(idArret - 1).getNom());
                btRechercher.setEnabled(true);
            }
        }
    }

}
