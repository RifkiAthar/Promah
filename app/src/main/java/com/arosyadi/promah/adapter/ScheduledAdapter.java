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

public class ScheduledAdapter extends RecyclerView.Adapter<ScheduledAdapter.ScheduledViewHolder> {

    ArrayList<Event> events;
    Context context;

    public ScheduledAdapter(ArrayList<Event> events){ this.events = events; }

    public void refill(ArrayList<Event> events) {
        this.events = new ArrayList<>();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScheduledViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_scheduled, parent, false);
        return new ScheduledViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduledViewHolder holder, int position) {
        holder.onBind(events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class ScheduledViewHolder extends RecyclerView.ViewHolder{
        private TextView startDate, startMonth;
        private TextView endDate, endMonth;
        private TextView eventName, eventLoc, eventSpeaker;

        public ScheduledViewHolder(@NonNull View itemView) {
            super(itemView);

            startDate = itemView.findViewById(R.id.event_date);
            startMonth = itemView.findViewById(R.id.event_month);
            endDate = itemView.findViewById(R.id.event_date_over);
            endMonth = itemView.findViewById(R.id.event_month_over);
            eventName = itemView.findViewById(R.id.event_name);
            eventLoc = itemView.findViewById(R.id.event_location);
            eventSpeaker = itemView.findViewById(R.id.event_speaker);
        }

        void onBind(Event item){

            String datestart = item.getDateStart();
            SimpleDateFormat dateStartFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = dateStartFormat.parse(datestart);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("dd");
                String NewRelease = dateUpdate.format(date);
                startDate.setText(NewRelease);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String dateEnd = item.getDateStart();
            SimpleDateFormat dateEndFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = dateStartFormat.parse(dateEnd);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("dd");
                String NewRelease = dateUpdate.format(date);
                endDate.setText(NewRelease);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String monthstart = item.getDateStart();
            SimpleDateFormat monthStartFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = monthStartFormat.parse(monthstart);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("dd");
                String NewRelease = dateUpdate.format(date);
                startMonth.setText(NewRelease);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String monthEnd = item.getDateStart();
            SimpleDateFormat monthEndFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = monthEndFormat.parse(monthEnd);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("dd");
                String NewRelease = dateUpdate.format(date);
                endMonth.setText(NewRelease);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            eventName.setText(item.getEventName());
            eventLoc.setText(item.getEventLocation());
            eventSpeaker.setText(item.getEventSpeaker());
        }
    }
}
