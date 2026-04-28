package com.example.contentProviderMouammar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventAdapter extends ArrayAdapter<Event> {

    private final SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private final SimpleDateFormat timeFormat =
            new SimpleDateFormat("HH::mm", Locale.getDefault());

    // constructeur de l'adapter
    public EventAdapter(Context context, List<Event> events){
        super(context, 0, events);
    }

    // Methode appélé pour afficher chaque element de la liste
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }

        Event event = getItem(position); // Récuperation de l'evenement courant

        // Recuperation des evenement graphiques
        TextView title = convertView.findViewById(R.id.txtTitle);
        TextView date = convertView.findViewById(R.id.txtDate);
        TextView time = convertView.findViewById(R.id.txtTime);

        // Affichage des infirmations de l'evenements
        title.setText(event.title);
        date.setText(dateFormat.format(new Date(event.start)));
        time.setText(
                timeFormat.format(new Date(event.start)) +
                        " - " +
                        timeFormat.format(new Date(event.end))
        );

        return convertView;
    }

}
