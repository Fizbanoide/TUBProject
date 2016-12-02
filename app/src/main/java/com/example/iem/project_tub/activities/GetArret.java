package com.example.iem.project_tub.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.iem.project_tub.R;
import com.example.iem.project_tub.adapters.Arret_Adapter;
import com.example.iem.project_tub.controller.WebServiceManager;
import com.example.iem.project_tub.models.Arret;
import com.example.iem.project_tub.models.ArretManager;

import java.util.List;

public class GetArret extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_arret);

        Intent intentReceived = getIntent();

        int idLigne = intentReceived.getIntExtra("idLigne", 0);
        final List<Arret> arretList = WebServiceManager.getInstance(this).getArretsWithLigne(idLigne);

        TextView tvNomLigne = (TextView) findViewById(R.id.get_arret_activity_tv_nom_ligne);
        tvNomLigne.setText(intentReceived.getStringExtra("nomLigne"));

        final ListView arretListView = (ListView) findViewById(R.id.get_arret_activity_lv_arrets);
        arretListView.setAdapter(new Arret_Adapter(arretList, this));

        arretListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentReturn = new Intent();
                intentReturn.putExtra("idArret", arretList.get(position).getId());
                setResult(Activity.RESULT_OK, intentReturn);
                finish();
            }
        });
    }
}
