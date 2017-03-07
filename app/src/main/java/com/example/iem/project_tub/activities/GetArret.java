package com.example.iem.project_tub.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.adapters.ArretAdapter;
import com.example.iem.project_tub.controller.APIClient;
import com.example.iem.project_tub.models.Arret;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetArret extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_arret);

        Intent intentReceived = getIntent();
        int idLigne = intentReceived.getIntExtra("idLigne", 0);
        int idArretDepart = intentReceived.getIntExtra("idArretDepart", -1);

        TextView tvNomLigne = (TextView) findViewById(R.id.get_arret_activity_tv_nom_ligne);
        tvNomLigne.setText(intentReceived.getStringExtra("nomLigne"));

        final ListView arretListView = (ListView) findViewById(R.id.get_arret_activity_lv_arrets);
        final ArretAdapter arretAdapter = new ArretAdapter(this, new ArrayList<Arret>());
        arretListView.setAdapter(arretAdapter);

        // Si on a pas reçu d'arret de départ on récupère tous les arrets d'une ligne
        if (idArretDepart == -1) {
            Call<List<Arret>> listArretCall = APIClient.getApiInterface().getArretsFromLigne(idLigne);
            listArretCall.enqueue(new Callback<List<Arret>>() {
                @Override
                public void onResponse(Call<List<Arret>> call, Response<List<Arret>> response) {
                    if (response.body() == null) {
                        Log.d("COUCOU", "ListLigne est null");
                        // Toast
                    } else {
                        arretAdapter.swapItems(response.body());
                        arretAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Arret>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erreur lors du chargement des arrets", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Sinon on récupère tous les arrets de la ligne APRES l'arret donné
            Call<List<Arret>> listArretCall = APIClient.getApiInterface().getArretsFromLigneNext(idLigne, idArretDepart);
            listArretCall.enqueue(new Callback<List<Arret>>() {
                @Override
                public void onResponse(Call<List<Arret>> call, Response<List<Arret>> response) {
                    if (response.body() == null) {
                        Log.d("COUCOU", "ListLigne est null");
                        // Toast
                    } else {
                        arretAdapter.swapItems(response.body());
                        arretAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<Arret>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erreur lors du chargement des arrets", Toast.LENGTH_SHORT).show();
                }
            });
        }



        arretListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentReturn = new Intent();
                intentReturn.putExtra("idArret", arretAdapter.getItem(position).getId());
                setResult(Activity.RESULT_OK, intentReturn);
                finish();
            }
        });
    }
}
