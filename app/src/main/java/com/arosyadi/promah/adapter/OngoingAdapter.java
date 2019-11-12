package com.arosyadi.promah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arosyadi.promah.R;
import com.arosyadi.promah.model.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.OngoingViewHolder> {

    ArrayList<Event> events;
    Context context;

    public OngoingAdapter(ArrayList<Event> events){ this.events = events; }

    public void refill(ArrayList<Event> events) {
        this.events = new ArrayList<>();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OngoingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_ongoing, parent, false);
        return new OngoingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingViewHolder holder, int position) {
        holder.onBind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class OngoingViewHolder extends RecyclerView.ViewHolder{
        private TextView dateEvent;
        private TextView eventName, eventLoc, eventSpeaker;

        public OngoingViewHolder(@NonNull View itemView) {
            super(itemView);

            dateEvent = itemView.findViewById(R.id.event_date);

            eventName = itemView.findViewById(R.id.event_name);
            eventLoc = itemView.findViewById(R.id.event_location);
            eventSpeaker = itemView.findViewById(R.id.event_speaker);
        }

        void onBind(Event item){

            String datestart = item.getDateStart();
            SimpleDateFormat dateStartFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = dateStartFormat.parse(datestart);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("dd MMM yyyy");
                String NewRelease = dateUpdate.format(date);
                dateEvent.setText(NewRelease);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String dateEnd = item.getDateStart();
            SimpleDateFormat dateEndFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = dateStartFormat.parse(dateEnd);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("dd MMM yyyy");
                String NewRelease = dateUpdate.format(date);
                dateEvent.setText(dateEvent + " - " +NewRelease);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            eventName.setText(item.getEventName());
            eventLoc.setText(item.getEventLocation());
            eventSpeaker.setText(item.getEventSpeaker());
        }
    }
}
