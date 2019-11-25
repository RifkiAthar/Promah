package com.arosyadi.medicaleuser.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arosyadi.medicaleuser.R;
import com.arosyadi.medicaleuser.adapter.ScheduledAdapter;
import com.arosyadi.medicaleuser.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ScheduledFragment extends Fragment {

    public ScheduledFragment(){}

    private static final String TAG = "scheduleFragment";

    private Context context;

    private RecyclerView rvScheduled;
    private ScheduledAdapter mAdapter;

    private FirebaseFirestore db;
    private ListenerRegistration dbListener;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scheduled, container, false);
        context = view.getContext();

        rvScheduled = view.findViewById(R.id.rv_scheduled);
        db = FirebaseFirestore.getInstance();

        progressBar = view.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        loadDataList();

        dbListener = db.collection("event")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                        if (e != null){
                            Log.e(TAG, "Listen Failed!");
                            return;
                        }

                        List<Event> eventList = new ArrayList<>();

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            Event event = doc.toObject(Event.class);
                            event.setId(doc.getId());
                            eventList.add(event);
                        }

                        mAdapter = new ScheduledAdapter(eventList, getContext(), db);
                        rvScheduled.setAdapter(mAdapter);
                    }
                });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        dbListener.remove();
    }


    private void loadDataList() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("event")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            List<Event> eventList = new ArrayList<>();

                            for (DocumentSnapshot doc : task.getResult()) {
                                Event event = doc.toObject(Event.class);
                                event.setId(doc.getId());
                                eventList.add(event);
                            }

                            mAdapter = new ScheduledAdapter(eventList, getContext(), db);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                            rvScheduled.setLayoutManager(mLayoutManager);
                            rvScheduled.setAdapter(mAdapter);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d(TAG, "Error getting document: ", task.getException());
                        }
                    }
                });
    }
}

