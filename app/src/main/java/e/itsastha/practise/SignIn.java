package e.itsastha.practise;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Button senddata;
    private EditText email;
    public EditText username;
    private EditText uid;
    private EditText password;
    private EditText conpassword;
    private DatabaseReference myRef;
    private DatabaseReference childRef;
    private DatabaseReference userRef;
    boolean bool;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        senddata=(Button)findViewById(R.id.idsendData);

        username=(EditText)findViewById(R.id.idname);
        email=(EditText)findViewById(R.id.idemail);
        password=(EditText)findViewById(R.id.idpassword);
        conpassword=(EditText)findViewById(R.id.idconpassword);

        myRef=FirebaseDatabase.getInstance().getReference();

        senddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String demail=email.getText().toString();
                String dpassword=password.getText().toString();
                String dconpassword=conpassword.getText().toString();
               final String dname=username.getText().toString();

               if(demail.isEmpty()){
                    Toast.makeText(SignIn.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if(dpassword.isEmpty()){
                    Toast.makeText(SignIn.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(dconpassword.isEmpty()){
                    Toast.makeText(SignIn.this, "Re-enter Password", Toast.LENGTH_SHORT).show();

                }
                else if(!dpassword.equals(dconpassword)){
                    Toast.makeText(SignIn.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }

                else {
                    mAuth.createUserWithEmailAndPassword(demail, dpassword)
                            .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("message", "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //key=childRef.push().getKey();
                                        final HashMap<String,String> userMap=new HashMap<>();

                                         String user_uid=user.getUid();


                                        childRef=myRef.child("user").child(user_uid);

                                        childRef.child("name").setValue(dname);
                                        childRef.child("email" ).setValue((demail));

                                        startActivity(new Intent(SignIn.this, LoggedIn.class));
                                    } else {
                                        // If sign in fails, display a message
                                        // to the user.
                                        Log.w("message", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(SignIn.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }}

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
