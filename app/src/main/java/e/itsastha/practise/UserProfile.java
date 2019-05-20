package e.itsastha.practise;

import android.content.Intent;
import android.os.TestLooperManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class UserProfile extends AppCompatActivity {

    TextView user_name;
    TextView user_email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user_name = (TextView) findViewById(R.id.user_id_name);
        user_email = (TextView) findViewById(R.id.user_email);



        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentFirebaseUser.getUid();

        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
        myRootRef.keepSynced(true);
        myRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                user_name.setText(name);
                user_email.setText(email);

                Log.d("entered_user_extraction","onDataChange: ");
                        Log.d("user_data", "onDataChange: "+name);
                        Log.d("user_data", "onDataChange: "+email);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        final LinearLayout ll=findViewById(R.id.asked_ques);


        myRootRef = FirebaseDatabase.getInstance().getReference().child("questions");
        myRootRef.keepSynced(true);
        myRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quesBlueprint model = dataSnapshot.child("question").getValue(quesBlueprint.class);
                if(model!=null) {
                    if (model.getUid().equals(uid)) {
                        Log.d("retrieved_questions", "onDataChange: "+model.getQuestion());
                        final TextView tt = new TextView(UserProfile.this);
                        tt.setText(model.getQuestion());
                        ll.addView(tt);

                    }
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
}



