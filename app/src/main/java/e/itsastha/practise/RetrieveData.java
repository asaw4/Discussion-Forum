package e.itsastha.practise;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class RetrieveData implements Serializable {
    private FeedData feedData;
    RecyclerView rv;
    RecyclerView rrv;
    private Context context;

    public RetrieveData(Context context, RecyclerView rv) {
        this.context = context;
        this.rv = rv;
        this.feedData = feedData;
    }
    public RetrieveData(RecyclerView rrv){
        this.rrv=rrv;

    }

    public void retrieveData() {

        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference().child("questions");
        myRootRef.keepSynced(true);
        myRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<quesBlueprint> result = new ArrayList<>();

                //result.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    quesBlueprint model = postSnapshot.getValue(quesBlueprint.class);
                    result.add(model);
                    Log.d("uid", "onDataChange: " + model.getUid());
                    Log.d("question_disp", "onDataChange: " + model.getQuestion());
                    Log.d("description", "onDataChange: " + model.getDescription());
                    Log.d("result length inside", "onDataChange: " + result.size());
                    Log.d("ques_uid", "onDataChange: " + model.getQues_uid());

                }

                feedData = new FeedData(context, result);


                rv.setAdapter(feedData);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}

