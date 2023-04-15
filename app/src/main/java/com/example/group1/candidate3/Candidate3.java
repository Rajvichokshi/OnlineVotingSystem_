package com.example.group1.candidate3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.group1.R;
import com.example.group1.VoteSuccess;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class Candidate3 extends AppCompatActivity {
    Button rn;
    private long backPressedTime;
    Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate3);

        FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

        rn=findViewById(R.id.rn);
        rn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.runTransaction(new Transaction.Function<Object>() {
                    @Nullable
                    @Override
                    public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                        DocumentReference documentReference=firebaseFirestore.collection("Candidate")
                                .document("Prathamesh Telmasre");
                        DocumentSnapshot documentSnapshot=transaction.get(documentReference);
                        long newVote=documentSnapshot.getLong("Vote")+1;
                        transaction.update(documentReference,"Vote",newVote);
                        return newVote;
                    }
                });

                startActivity(new Intent(getApplicationContext(), VoteSuccess.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 1 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Can't go back", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}