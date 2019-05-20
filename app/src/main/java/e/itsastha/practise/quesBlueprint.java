package e.itsastha.practise;

import android.content.Intent;
import android.util.Log;

import java.io.Serializable;
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.support.v4.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;


public class quesBlueprint {

    private String question;
    private String description;
    private String uid;
    private  String key;
    private List<quesBlueprint> result = new ArrayList<>();
    private HashMap<String,Reply> reply;
    private String ques_uid;
    public quesBlueprint(){}

    public HashMap<String, Reply> getReply()
    {
        return reply;
    }

    public void setReply(HashMap<String, Reply> reply) {
        this.reply = reply;
        if(reply.keySet()!=null) {
            for (String exKey : reply.keySet())
                Log.d("Hashmap_keys_setReply", "onBindViewHolder: " + exKey);
        }

    }

    public void setResult(List<quesBlueprint> result) {
        this.result = result;
    }

    public String getQues_uid() {
        return ques_uid;
    }

    public void setQues_uid(String ques_uid) {
        this.ques_uid = ques_uid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<quesBlueprint> getResult() {
        return result;
    }

    public  quesBlueprint(String ques, String description, String uid){
        this.question=ques;
        this.description=description;
        this.uid=uid;
    }

    public quesBlueprint(String question,String description,String ques_uid,String uid,HashMap<String,Reply> reply){
        this.question=question;
        this.description=description;
        this.uid=uid;
        this.reply=reply;
        this.ques_uid=ques_uid;
        Log.d("replier_uid", "onDataChange: "+reply.toString());

    }

    public void toSetAdapter(){

    }


    public void sendQuestion(quesBlueprint obj){
        HashMap<String,String> quesMap=new HashMap<>();
        quesMap.put("question",obj.getQuestion());
        quesMap.put("description",obj.getDescription());
        quesMap.put("uid",obj.getUid());
        key=FirebaseDatabase.getInstance().getReference().child("questions").push().getKey();
        quesMap.put("ques_uid",key);
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference().child("questions");
        final Map<String, Object> newQuesMap= new HashMap<>();
        newQuesMap.put(key,quesMap);
        myRef.updateChildren(newQuesMap);

    }

    public static void addReply(Reply reply, String uid){

        HashMap<String,String> replyMap=new HashMap<>();
        replyMap.put("text",reply.getText());
        replyMap.put("uid",reply.getUid());
        DatabaseReference myRef=FirebaseDatabase.getInstance().getReference().child("questions").child(uid).child("reply");
        String replyKey=myRef.push().getKey();
        final Map<String, Object> newReplyMap= new HashMap<>();
        newReplyMap.put(replyKey,replyMap);
        myRef.updateChildren(newReplyMap);

    }
}
