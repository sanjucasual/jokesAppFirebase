package net.course99.xxxnonveg;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import static android.content.ContentValues.TAG;

public class MyFireBaseInstanceIDService extends FirebaseInstanceIdService {
    FirebaseInstanceId FirebaseInstanceId;
    DatabaseReference tokenDB;
    @Override
    public void onTokenRefresh() {
        FirebaseMessaging.getInstance().subscribeToTopic("topic");
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String refreshedToken){
        tokenDB = FirebaseDatabase.getInstance().getReference("tokens");
        String id = tokenDB.push().getKey();
        TokenUsers tkn= new TokenUsers(id,refreshedToken );
        tokenDB.child(id).setValue(tkn);
    }

}
