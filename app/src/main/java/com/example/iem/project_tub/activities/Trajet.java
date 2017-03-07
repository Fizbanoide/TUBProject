package com.example.iem.project_tub.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.controller.APIClient;
import com.example.iem.project_tub.controller.APIInterface;
import com.example.iem.project_tub.models.Arret;
import com.example.iem.project_tub.models.Horaire;
import com.example.iem.project_tub.models.HoraireTrajet;
import com.example.iem.project_tub.models.Ligne;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trajet extends AppCompatActivity {

    private static final int REQUESTCODELIGNE = 1;
    private static final int REQUESTCODEARRETDEPART = 2;
    private static final int REQUESTCODEARRETARRIVEE = 3;

    private Button btLigne;
    private Button btArretDepart;
    private Button btArretArrivee;
    private Button btHeure;
    private Button btRechercher;
    private TextView tvResultat;

    private List<Ligne> ligneList;
    private List<Arret> arretList;

    private Ligne ligne;
    private Arret arretDepart;
    private Arret arretArrivee;
    private String heure;
    private HoraireTrajet horaireTrajet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trajet);

        Call<List<Ligne>> listLigneCall = APIClient.getApiInterface().getAllLignes();
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

        Call<List<Arret>> listArretCall = APIClient.getApiInterface().getAllArrets();
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


        btLigne = (Button) findViewById(R.id.activity_trajet_bt_choisir_ligne);
        btArretDepart = (Button) findViewById(R.id.activity_trajet_bt_choisir_arret_depart);
        btArretArrivee = (Button) findViewById(R.id.activity_trajet_bt_choisir_arret_arrivee);
        btHeure = (Button) findViewById(R.id.activity_trajet_bt_choisir_heure);
        btRechercher = (Button) findViewById(R.id.main_activity_bt_rechercher);
        tvResultat = (TextView) findViewById(R.id.activity_trajet_tv_resultat);


        btLigne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getLigne = new Intent(Trajet.this, GetLigne.class);
                startActivityForResult(getLigne, REQUESTCODELIGNE);
            }
        });

        btArretDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getArret = new Intent(Trajet.this, GetArret.class);
                getArret.putExtra("idLigne", ligne.getId());
                getArret.putExtra("nomLigne", btLigne.getText());
                startActivityForResult(getArret, REQUESTCODEARRETDEPART);
            }
        });

        btArretArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getArret = new Intent(Trajet.this, GetArret.class);
                getArret.putExtra("idLigne", ligne.getId());
                getArret.putExtra("nomLigne", btLigne.getText());
                getArret.putExtra("idArretDepart", arretDepart.getId());
                startActivityForResult(getArret, REQUESTCODEARRETARRIVEE);
            }
        });

        // Gestion de la récupération de l'heure par popup
        btHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Trajet.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        heure = String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute);
                        btHeure.setText(heure);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
                btRechercher.setEnabled(true);
            }
        });

        btRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Call<HoraireTrajet> horaireTrajetCall = APIClient.getApiInterface().getHoraireTrajet(ligne.getId(), arretDepart.getId(), arretArrivee.getId(), heure);
                horaireTrajetCall.enqueue(new Callback<HoraireTrajet>() {
                    @Override
                    public void onResponse(Call<HoraireTrajet> call, Response<HoraireTrajet> response) {
                        horaireTrajet = response.body();
                        tvResultat.setText("Heure de départ : " + horaireTrajet.getDepart() + "\nHeure d'arrivee : " + horaireTrajet.getArrivee());
                    }

                    @Override
                    public void onFailure(Call<HoraireTrajet> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Erreur lors de la récupération du trajet", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUESTCODELIGNE){
            if(resultCode == RESULT_OK){
                ligne = ligneList.get(data.getIntExtra("idLigne", 0) - 1);
                btLigne.setText(ligne.getNumero() + " " + ligne.getTerminus());
                btArretDepart.setText("Choisir arret");
                btArretDepart.setEnabled(true);

                btArretArrivee.setText("Choisir arret");
                btArretArrivee.setEnabled(false);
                btHeure.setText("Choisir heure");
                btHeure.setEnabled(false);
                btRechercher.setEnabled(false);
            }
        } else if (requestCode == REQUESTCODEARRETDEPART) {
            if (resultCode == RESULT_OK) {
                arretDepart = arretList.get(data.getIntExtra("idArret", 0) - 1);
                btArretDepart.setText(arretDepart.getNom());
                btArretArrivee.setText("Choisir arret");
                btArretArrivee.setEnabled(true);

                btHeure.setText("Choisir heure");
                btHeure.setEnabled(false);
                btRechercher.setEnabled(false);
            }
        } else if (requestCode == REQUESTCODEARRETARRIVEE) {
            if (resultCode == RESULT_OK) {
                arretArrivee = arretList.get(data.getIntExtra("idArret", 0) - 1);
                btArretArrivee.setText(arretArrivee.getNom());
                btHeure.setEnabled(true);

                btRechercher.setEnabled(false);
            }
        }
    }
}
