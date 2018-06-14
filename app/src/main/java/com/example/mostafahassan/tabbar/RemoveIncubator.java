package com.example.mostafahassan.tabbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RemoveIncubator extends AppCompatActivity {

    private EditText  incNum;
    private Button submitBtn;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_inc);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Incubators");

        incNum = (EditText) findViewById(R.id.IncNum_Edite);
        submitBtn = (Button) findViewById(R.id.submitButton);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String incNum_val = incNum.getText().toString();

                if (!TextUtils.isEmpty(incNum_val)) {
                    checkInc();
                } else {
                    if (incNum_val.isEmpty()) {
                        incNum.setError("Invalid IncNum");
                    }
                }
            }
        });

    }

    void checkInc(){

        final String incNum_val = incNum.getText().toString().trim();

        final Query firebaseSearchQuery = databaseReference.orderByChild("IncNum").startAt(incNum_val).endAt(incNum_val + "\uf8ff");

        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    databaseReference.child(incNum_val).removeValue();
                    finish();
                }
                else {
                    incNum.setError("This Inc Not Found");
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}

