package com.arosyadi.promah;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arosyadi.promah.adapter.ScheduledAdapter;
import com.arosyadi.promah.model.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CreateActivity";

    private final String UNIT_KEY = "unit";
    private final String NAME_KEY = "name";
    private final String EVENT_NAME_KEY = "eventName";
    private final String EVENT_DESC_KEY = "eventDesc";
    private final String EVENT_LOC_KEY = "eventLoc";
    private final String EVENT_SPEAKER_KEY = "eventSpeaker";
    private final String DATE_START_KEY = "dateStart";
    private final String DATE_END_KEY = "dateEnd";

    private Spinner spUnit;
    private EditText edtname, edtEventName, edtEventDesc, edtEventLoc, edtEventSpeaker;
    private Button btnEventStart, btnEventEnd;
    private TextView tvEventStart, tvEventEnd;

    private Calendar calStart, calEnd;
    private Button btnCreateEvent;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String id = "";
    String user ;
    String idUser ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        spUnit = findViewById(R.id.spinner_unit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.unit, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUnit.setAdapter(adapter);

        edtname = findViewById(R.id.edt_name);
        edtEventName = findViewById(R.id.edt_event_name);
        edtEventDesc = findViewById(R.id.edt_event_desc);
        edtEventLoc = findViewById(R.id.edt_event_location);
        edtEventSpeaker = findViewById(R.id.edt_event_speaker);

        btnEventStart = findViewById(R.id.btn_date_start);
        btnEventEnd = findViewById(R.id.btn_date_end);

        tvEventStart = findViewById(R.id.tv_date_start);
        tvEventEnd = findViewById(R.id.tv_date_end);

        btnCreateEvent = findViewById(R.id.btn_add);

        btnEventStart.setOnClickListener(this);
        btnEventEnd.setOnClickListener(this);
        btnCreateEvent.setOnClickListener(this);

        calStart = Calendar.getInstance();
        calEnd = Calendar.getInstance();




        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("updateNoteId");

            ArrayAdapter unitAdapter = (ArrayAdapter) spUnit.getAdapter();
            int unitPosition = unitAdapter.getPosition(bundle.getString("UpdateUnit"));
            spUnit.setSelection(unitPosition);

            edtname.setText(bundle.getString("updateUserName"));
            edtEventName.setText(bundle.getString("updateEventName"));
            edtEventSpeaker.setText(bundle.getString("UpdateEventSpeaker"));
            edtEventLoc.setText(bundle.getString("UpdateEventLoc"));
            edtEventDesc.setText(bundle.getString("UpdateEventDesc"));

            tvEventStart.setText(bundle.getString("dateStart"));
            tvEventEnd.setText(bundle.getString("dateEnd"));
        }


//        addNewContact();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_date_start) {
            final Calendar startDate = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    calStart.set(year, month, day);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                    tvEventStart.setText(dateFormat.format(calStart.getTime()));
                }
            }, startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE)).show();
        } else if (view.getId() == R.id.btn_date_end) {
            final Calendar endDate = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    calEnd.set(year, month, day);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                    tvEventEnd.setText(dateFormat.format(calEnd.getTime()));
                }
            }, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE)).show();
        } else if (view.getId() == R.id.btn_add) {
            Map<String, Object> newEvent = new HashMap<>();
            newEvent.put(UNIT_KEY, spUnit.getSelectedItem().toString());
            newEvent.put(NAME_KEY, edtname.getText().toString());
            newEvent.put(EVENT_NAME_KEY, edtEventName.getText().toString());
            newEvent.put(EVENT_DESC_KEY, edtEventDesc.getText().toString());
            newEvent.put(EVENT_LOC_KEY, edtEventLoc.getText().toString());
            newEvent.put(EVENT_SPEAKER_KEY, edtEventSpeaker.getText().toString());
            newEvent.put(DATE_START_KEY, tvEventStart.getText());
            newEvent.put(DATE_END_KEY, tvEventEnd.getText());

            if (id.length() > 0) {
                updateEvent(id, newEvent);
            } else {

                addEvent(newEvent);
                readUser();
            }

//            if (edtEventName.toString().length() > 0){
//
//            }


            finish();
        }

    }

    private void updateEvent(String id, Map<String, Object> newEvent) {
        db.collection("event")
                .document(id)
                .set(newEvent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e(TAG, "event update successfully");
                        Toast.makeText(getApplicationContext(), "Event has been updated!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding event document : ", e);
                        Toast.makeText(getApplicationContext(), "Event could not be updated!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addEvent(Map<String, Object> newEvent) {
        db.collection("event")
                .add(newEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Event has been added!", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Note document", e);
                        Toast.makeText(getApplicationContext(), "Note could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void addDataUser(Map<String, Object> newEvent){
//        readUser();
        Log.d(TAG, "SSSSSSSSSSS: "+idUser);
        db.collection("users").document(idUser).collection("userEvent")
                .add(newEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(getApplicationContext(), "Event has been added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding Note document", e);
                        Toast.makeText(getApplicationContext(), "Note could not be added!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void readData() {
//        db.collection("user").document("rifki")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            List<Event> eventList = new ArrayList<>();
//
//                            for (DocumentSnapshot doc : task.getResult()) {
//                                Event event = doc.toObject(Event.class);
//                                event.setId(doc.getId());
//                                eventList.add(event);
//                            }
//
//                            mAdapter = new ScheduledAdapter(eventList, getContext(), db);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                            rvScheduled.setLayoutManager(mLayoutManager);
//                            rvScheduled.setAdapter(mAdapter);
//                            progressBar.setVisibility(View.GONE);
//                        } else {
//                            Log.d(TAG, "Error getting document: ", task.getException());
//                        }
//                    }
//                });
//    }

//    private void readUser() {
////        user = edtname.getText().toString();
////        Log.d(TAG, "readUser: "+user);
//        DocumentReference docRef = db.collection("user").document("rifki");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                       String idUser = document.getString("id");
//                        Log.d(TAG, "onComplete: " + idUser);
//                        Log.d(TAG, "RRRRRR: "+document.getData());
//
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
//    }


}
