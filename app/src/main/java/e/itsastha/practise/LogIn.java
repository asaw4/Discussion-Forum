package e.itsastha.practise;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    private Button sendData;
    private EditText em;
    private EditText pd;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        sendData=(Button)findViewById(R.id.send);
        em=(EditText)findViewById(R.id.email);
        pd=(EditText)findViewById(R.id.password);




        sendData.setOnClickListener(new View.OnClickListener()  {
            public void onClick(View v) {

                String email=em.getText().toString();
                String password=pd.getText().toString();


                mAuth.signInWithEmailAndPassword(email, password)

                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("message", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startActivity(new Intent(LogIn.this,LoggedIn.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("message", "signInWithEmail:failure", task.getException());

                                    Toast.makeText(LogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}
