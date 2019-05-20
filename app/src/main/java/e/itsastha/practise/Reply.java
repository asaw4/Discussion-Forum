package e.itsastha.practise;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Reply implements Serializable {

    private String text;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }




    public Reply(String text,String uid) {
        // Required empty public constructor
        this.text = text;
        this.uid = uid;
    }
    public Reply(){

    }

}
