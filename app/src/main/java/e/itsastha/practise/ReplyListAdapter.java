package e.itsastha.practise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ReplyListAdapter extends BaseAdapter implements Serializable {

    Reply reply;
    private final ArrayList mData;
    Context context;
    private TextView view_reply;
    TextView view_reply_uid;

    public ReplyListAdapter(Context context,HashMap<String,Reply> replyList){
        this.context=context;
        mData=new ArrayList();
        mData.addAll(replyList.entrySet());
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Reply> getItem(int position) {

        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_resource, parent, false);

        final Map.Entry<String, Reply> item = getItem(position);
        DatabaseReference myRootRef= FirebaseDatabase.getInstance().getReference().child("user").child(item.getValue().getUid());
        myRootRef.keepSynced(true);
        final View finalConvertView = convertView;
        myRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user_name=dataSnapshot.child("name").getValue(String.class);
                ((TextView) finalConvertView.findViewById(R.id.replier_uid)).setText(user_name);
                ((TextView) finalConvertView.findViewById(R.id.reply)).setText(item.getValue().getText());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        return convertView;
    }
}
