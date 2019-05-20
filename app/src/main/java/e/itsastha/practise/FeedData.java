package e.itsastha.practise;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.BreakIterator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;
import static android.widget.Toast.LENGTH_SHORT;


public class FeedData extends RecyclerView.Adapter<FeedData.FeedViewHolder> {

    private List<quesBlueprint> listdata;
    private Context context;
    RecyclerView recyclerView;
    FeedData feedData;



    public FeedData(Context context,List<quesBlueprint> listdata) {
        // Required empty public constructor
        this.listdata=listdata;
        this.context=context;
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder{

        public TextView uid;
        public TextView ques;
        public TextView description;


        public FeedViewHolder(@NonNull View itemView) {
            super(itemView);

            uid=(TextView) itemView.findViewById(R.id.uid_view);
            ques=(TextView) itemView.findViewById(R.id.question_view);
            description=(TextView) itemView.findViewById(R.id.description_view);

        }
    }
    //sub-class ends



    @NonNull
    @Override
    public FeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.resource,viewGroup,false);
        FeedViewHolder feedViewHolder=new FeedViewHolder(view);
        return feedViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FeedViewHolder feedViewHolder, int i) {
        final quesBlueprint myListData = listdata.get(i);
        feedViewHolder.uid.setText(myListData.getUid());
        feedViewHolder.ques.setText(myListData.getQuestion());
        feedViewHolder.description.setText(myListData.getDescription());
        final String ques_uid=myListData.getQues_uid();

        final String[] name = new String[1];
        Log.d("FeedData_uid", "onBindViewHolder: "+ name[0]);
        DatabaseReference myref=FirebaseDatabase.getInstance().getReference().child("user").child(myListData.getUid());
        myref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name[0] =dataSnapshot.child("name").getValue(String.class);
                Log.d("ques_user_name", "onDataChange: "+name[0]);
                feedViewHolder.uid.setText(name[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        Log.d("FeedData_ques", "onBindViewHolder: "+(myListData.getQuestion()));
        Log.d("FeedData_des", "onBindViewHolder: "+myListData.getDescription());





        feedViewHolder.ques.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewQues.class);

                intent.putExtra("ques_user_name", name[0]);
                intent.putExtra("ques", myListData.getQuestion());
                intent.putExtra("ques_des", myListData.getDescription());
                intent.putExtra("ques_uid", ques_uid);
                intent.putExtra("reply_map", myListData.getReply());
                context.startActivity(intent);
                if (myListData.getReply() != null) {
                    for (String exKey : myListData.getReply().keySet())
                        Log.d("Hashmap_keys_FeedData", "onBindViewHolder: " + exKey);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}
