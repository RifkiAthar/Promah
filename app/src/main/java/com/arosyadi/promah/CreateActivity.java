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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CreateActivity";

    private final String UNIT_KEY = "UNIT";
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

    private FirebaseFirestore db;
    String id = "";

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

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("updateNoteId");

            spUnit.setPrompt(bundle.getString("UpdateUnit"));

            edtname.setText(bundle.getString("updateUserName"));
            edtEventName.setText(bundle.getString("UpdateEventName"));
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
                updateEvent(newEvent);
            } else {
                addEvent(newEvent);
            }

            finish();
        }

    }

    private void updateEvent(Map<String, Object> newEvent) {
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
                        Toast.makeText(getApplicationContext(), "Note has been added!", Toast.LENGTH_SHORT).show();
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


}
