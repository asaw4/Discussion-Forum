package e.itsastha.practise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ViewQues extends AppCompatActivity implements Serializable {

    TextView ques;
    TextView description;
    TextView name;
    EditText reply;
    String ques_uid;
    Button addreply;
    ListView replyList;
    private HashMap<String, Reply> mReply;

    private LinearLayout replyLL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ques);
        ques = findViewById(R.id.ques_view_ques);
        name = findViewById(R.id.ques_view_name);
        description = findViewById(R.id.ques_view_des);
        reply = findViewById(R.id.ques_view_reply);
        addreply = findViewById(R.id.add_reply);
        // replyList=findViewById(R.id.reply_list_view);

        replyLL = findViewById(R.id.replyLinearLayout);


        Intent intent = getIntent();
        String mName = intent.getStringExtra("ques_user_name");
        String mQues = intent.getStringExtra("ques");
        String mDescription = intent.getStringExtra("ques_des");
        ques_uid = intent.getStringExtra("ques_uid");
        mReply = (HashMap<String, Reply>) intent.getSerializableExtra("reply_map");

        if (mReply != null) {
            for (String exKey : mReply.keySet())
                Log.d("abc", "onBindViewHolder: " + exKey);


            //ReplyListAdapter adapter = new ReplyListAdapter(this, mReply);
            // replyList.setAdapter(adapter);
//
//            Log.d("abc", "onCreate: "+mReply);
//
//            for(int i=0; i<mReply.size(); i++){
//
//                View v = getLayoutInflater().inflate(R.layout.reply_resource,null);
//                LinearLayout l = v.findViewById(R.id.llReply);
//                TextView t1 =(TextView) l.getChildAt(0);
//                TextView display_reply=v.findViewById(R.id.reply);
//                TextView display_uid=v.findViewById(R.id.replier_uid);
//                replyLL.addView(v);
//            }
//
//
//}


            for (Map.Entry<String, Reply> entry : mReply.entrySet()) {
                final Reply obj = entry.getValue();

                View v = getLayoutInflater().inflate(R.layout.reply_resource, null);
                LinearLayout l = v.findViewById(R.id.llReply);
                TextView display_reply = v.findViewById(R.id.reply);
                final TextView display_uid = v.findViewById(R.id.replier_uid);
                display_reply.setText(obj.getText());
                display_uid.setText((obj.getUid()));


                final DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("user").child(obj.getUid());
                myRootRef.keepSynced(true);
                myRootRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        display_uid.setText(name);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                replyLL.addView(v);

            }

        }

            ques.setText(mQues);
            name.setText(mName);
            description.setText(mDescription);

            addreply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    String mReply = reply.getText().toString();
                    reply.setText("");

                    if(mReply.equals("")){
                        Toast.makeText(ViewQues.this, "Enter Reply", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        String user_uid = currentFirebaseUser.getUid();
                        Reply obj = new Reply(mReply, user_uid);
                        quesBlueprint.addReply(obj, ques_uid);


                        v = getLayoutInflater().inflate(R.layout.reply_resource, null);

                        TextView display_reply = v.findViewById(R.id.reply);
                        final TextView display_uid = v.findViewById(R.id.replier_uid);
                        display_reply.setText(mReply);
                        display_uid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());


                        final DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("user").child(obj.getUid());
                        myRootRef.keepSynced(true);
                        myRootRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String name = dataSnapshot.child("name").getValue(String.class);
                                display_uid.setText(name);

                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("The read failed: " + databaseError.getCode());
                            }
                        });

                        replyLL.addView(v);

                    }

                }
            });
        }


    }
