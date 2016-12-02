package com.example.iem.project_tub.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.controller.WebServiceManager;
import com.example.iem.project_tub.models.Arret;
import com.example.iem.project_tub.models.ArretManager;
import com.example.iem.project_tub.models.Ligne;
import com.example.iem.project_tub.models.HoraireManager;
import com.example.iem.project_tub.models.LigneManager;

import java.util.List;

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

        ligneList = WebServiceManager.getInstance(this).getLignes();
        arretList = WebServiceManager.getInstance(this).getArrets();

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
                int idAssoc = HoraireManager.getInstance(Recherche.this).getIdAssoc(idLigne, idArret);
                Intent rechercherHoraires = new Intent(Recherche.this, Horaires.class);
                rechercherHoraires.putExtra("ligne", ligneList.get(idLigne - 1).getNumero());
                rechercherHoraires.putExtra("arret", arretList.get(idArret - 1).getNom());
                rechercherHoraires.putExtra("idAssoc", idAssoc);
                startActivity(rechercherHoraires);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODELIGNE){
            if(resultCode == RESULT_OK){
                idLigne = data.getIntExtra("idLigne", 0);
                btLigne.setText(ligneList.get(idLigne - 1).getNumero() + " " + ligneList.get(idLigne - 1).getDirection());
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
