package e.itsastha.practise;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AskQues extends AppCompatActivity {

    DatabaseReference mRef;
    FirebaseDatabase database;

    EditText ques;
    EditText description;
    Button ask;
    DatabaseReference myRef;
    DatabaseReference childRef;
    DatabaseReference newPostRef;
    String dques;
    String ddescription;
    String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_ques);

        ask=(Button)findViewById(R.id.ask_ques);
        ques=(EditText)findViewById(R.id.question);
        description=(EditText)findViewById(R.id.ques_description);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        database=FirebaseDatabase.getInstance();
        mRef=database.getReference();



        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dq=ques.getText().toString();
                String dd=description.getText().toString();

                if(dq.isEmpty()){
                    Toast.makeText(AskQues.this, "Enter Question", Toast.LENGTH_SHORT).show();
                }
                else if(dd.isEmpty()){
                    Toast.makeText(AskQues.this, "Enter Description", Toast.LENGTH_SHORT).show();
                }
                else{

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    uid = user.getUid();

                    quesBlueprint obj= new quesBlueprint(dq,dd,uid);
                    obj.sendQuestion(obj);
                    startActivity(new Intent(AskQues.this, LoggedIn.class));
                }


            }
        });

    }

}
