package com.example.iem.project_tub.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.adapters.Ligne_Adapter;
import com.example.iem.project_tub.controller.WebServiceManager;
import com.example.iem.project_tub.models.Ligne;
import com.example.iem.project_tub.models.LigneManager;

import java.util.List;

public class GetLigne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ligne);

        final List<Ligne> ligneList = WebServiceManager.getInstance(this).getLignes();

        final ListView ligneListView = (ListView) findViewById(R.id.get_ligne_activity_lv_lignes);
        ligneListView.setAdapter(new Ligne_Adapter(this, ligneList));


        ligneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentReturn = new Intent();
                intentReturn.putExtra("idLigne", ligneList.get(position).getId());
                setResult(Activity.RESULT_OK, intentReturn);
                finish();
            }
        });
    }
}
