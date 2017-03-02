package com.example.iem.project_tub.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.iem.project_tub.R;
import com.example.iem.project_tub.models.Ligne;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iem on 18/10/2016.
 */

public class LigneAdapter extends BaseAdapter {

    private List<Ligne> ligneList;
    private Activity activity;

    public LigneAdapter(Activity activity, List<Ligne> ligneList) {
        this.ligneList = ligneList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return ligneList.size();
    }

    @Override
    public Ligne getItem(int position) {
        return ligneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void swapItems(List<Ligne> items) {
        this.ligneList = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final Ligne ligne = this.getItem(position);

        LinearLayout layout;

        if(view == null) {
            layout = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.item_ligne, null);
        } else {
            layout = (LinearLayout)view;
        }

        final TextView tvNumero = (TextView) layout.findViewById(R.id.item_ligne_tv_numero_ligne);
        final TextView tvDirection = (TextView) layout.findViewById(R.id.item_ligne_tv_direction_ligne);

        tvNumero.setText(Integer.toString(ligne.getNumero()));
        tvDirection.setText(ligne.getTerminus());

        return layout;
    }
}
