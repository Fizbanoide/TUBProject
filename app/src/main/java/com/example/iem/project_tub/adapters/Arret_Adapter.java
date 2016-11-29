package com.example.iem.project_tub.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.models.Arret;

import java.util.List;

/**
 * Created by iem on 04/11/2016.
 */

public class Arret_Adapter extends BaseAdapter {

    private List<Arret> arretList;
    private Activity activity;

    public Arret_Adapter(List<Arret> arretList, Activity activity) {
        this.arretList = arretList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arretList.size();
    }

    @Override
    public Object getItem(int position) {
        return arretList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Arret arret = arretList.get(position);

        LinearLayout layout;

        if (view == null) {
            layout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.item_arret, null);
        } else {
            layout = (LinearLayout) view;
        }

        final TextView tvNom = (TextView) layout.findViewById(R.id.item_arret_tv_nom_arret);

        tvNom.setText(arret.getNom());

        return layout;
    }
}
