package com.example.iem.project_tub.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.adapters.LigneAdapter;
import com.example.iem.project_tub.controller.APIClient;
import com.example.iem.project_tub.models.Ligne;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetLigne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ligne);

        final ListView ligneListView = (ListView) findViewById(R.id.get_ligne_activity_lv_lignes);
        final LigneAdapter ligneAdapter = new LigneAdapter(this, new ArrayList<Ligne>());
        ligneListView.setAdapter(ligneAdapter);

        Call<List<Ligne>> listLigneCall = APIClient.getApiInterface().getAllLignes();
        listLigneCall.enqueue(new Callback<List<Ligne>>() {
            @Override
            public void onResponse(Call<List<Ligne>> call, Response<List<Ligne>> response) {
                if (response.body() == null) {
                    Log.d("COUCOU", "ListLigne est null");
                    // Toast
                } else {
                    ligneAdapter.swapItems(response.body());
                    ligneAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Ligne>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erreur lors du chargement des lignes", Toast.LENGTH_SHORT).show();
            }
        });


        ligneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentReturn = new Intent();
                intentReturn.putExtra("idLigne", ligneAdapter.getItem(position).getId());
                setResult(Activity.RESULT_OK, intentReturn);
                finish();
            }
        });
    }
}
