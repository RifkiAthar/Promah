package com.arosyadi.promah.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.arosyadi.promah.CreateActivity;
import com.arosyadi.promah.R;
import com.arosyadi.promah.model.Event;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OngoingAdapter extends RecyclerView.Adapter<OngoingAdapter.OngoingViewHolder> {

    List<Event> events;
    Context context;
    FirebaseFirestore db;

    public OngoingAdapter(List<Event> events, Context context, FirebaseFirestore db){
        this.events = events;
        this.context = context;
        this.db = db;
    }

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
        private TextView startDate, startMonth;
        private TextView endDate, endMonth;
        private TextView eventDate;
        private TextView eventName, eventLoc, eventSpeaker;
        private CardView cardView;

        public OngoingViewHolder(@NonNull View itemView) {
            super(itemView);

            startDate = itemView.findViewById(R.id.event_date);
//            startMonth = itemView.findViewById(R.id.event_month);
            endDate = itemView.findViewById(R.id.event_date_over);
//            endMonth = itemView.findViewById(R.id.event_month_over);
            eventName = itemView.findViewById(R.id.event_name);
            eventLoc = itemView.findViewById(R.id.event_location);
            eventSpeaker = itemView.findViewById(R.id.event_speaker);
            cardView = itemView.findViewById(R.id.list_ongoing);

        }

        void onBind(final Event item){

            String datestart = item.getDateStart();
            String newDate = "";
            SimpleDateFormat dateStartFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            try {
                Date date = dateStartFormat.parse(datestart);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("dd");
                newDate = dateUpdate.format(date);
//                startDate.setText(newRelease);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String dateEnd = item.getDateEnd();
            SimpleDateFormat dateEndFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            try {
                Date date = dateEndFormat.parse(dateEnd);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("dd");
                newDate = newDate + " - " + dateUpdate.format(date);
//                startDate.setText(startDate + " - " + newEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String monthstart = item.getDateStart();
            SimpleDateFormat monthStartFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            try {
                Date date = monthStartFormat.parse(monthstart);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("MMM");
//                newDate = newDate + dateUpdate.format(date);
//                startMonth.setText(newMonthStart);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String monthEnd = item.getDateStart();
            SimpleDateFormat monthEndFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            try {
                Date date = monthEndFormat.parse(monthEnd);
                SimpleDateFormat dateUpdate =new SimpleDateFormat("MMM yyyy");
                newDate = newDate + " " + dateUpdate.format(date);
                startDate.setText(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            eventName.setText(item.getEventName());
            eventLoc.setText(item.getEventLoc());
            eventSpeaker.setText(item.getEventSpeaker());
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update(item);
                }
            });
        }

        private void update(Event event) {
            Intent intent = new Intent(context, CreateActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("UpdateEventId", event.getId());
            intent.putExtra("updateStartDate", event.getDateStart());
            intent.putExtra("updateEndDate", event.getDateEnd());
            intent.putExtra("updateEventName", event.getEventName());
            intent.putExtra("updateEventLoc", event.getEventLoc());
            intent.putExtra("updateEventSpeaker", event.getEventSpeaker());
            intent.putExtra("updateEventDesc", event.getEventDesc());
            intent.putExtra("updateUnit", event.getUnit());
            intent.putExtra("updateUserName", event.getName());
            context.startActivity(intent);
        }
    }
}
