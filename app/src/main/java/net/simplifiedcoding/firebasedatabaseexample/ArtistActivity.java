package net.simplifiedcoding.firebasedatabaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cuboid.cuboidcirclebutton.CuboidButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArtistActivity extends AppCompatActivity {

    CuboidButton leftbutton;
    CuboidButton sharebutton;
    CuboidButton rightbutton;

    TextView textViewRating, textViewArtist;

    int count;
    DatabaseReference databaseTracks;

    List<Artist> artistsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        Intent intent = getIntent();

        /*
        * this line is important
        * this time we are not getting the reference of a direct node
        * but inside the node track we are creating a new child with the artist id
        * and inside that node we will store all the tracks with unique ids
        * */
        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(intent.getStringExtra(MainActivity.ARTIST_ID));

        leftbutton= (CuboidButton)findViewById(R.id.leftbutton);
        sharebutton= (CuboidButton)findViewById(R.id.sharebutton);
        rightbutton= (CuboidButton)findViewById(R.id.rightbutton);
        textViewArtist = (TextView) findViewById(R.id.textViewArtist);

         count=intent.getIntExtra("COUNT",0);
         textViewArtist.setText(intent.getStringExtra(MainActivity.ARTIST_NAME));
         artistsList=intent.getParcelableArrayListExtra(MainActivity.ARTIST_LIST);


       leftbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               count=count-1;
               textViewArtist.setText(artistsList.get(count).getArtistName());
           }
       });

       sharebutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

           }
       });

       rightbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               count=count+1;
               textViewArtist.setText(artistsList.get(count).getArtistName());
           }
       });






    }





   /* @Override*/
   /* protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Track track = postSnapshot.getValue(Track.class);
                    tracks.add(track);
                }
                TrackList trackListAdapter = new TrackList(ArtistActivity.this, tracks);
                listViewTracks.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    /*private void saveTrack() {
        String trackName = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();
        if (!TextUtils.isEmpty(trackName)) {
            String id  = databaseTracks.push().getKey();
            Track track = new Track(id, trackName, rating);
            databaseTracks.child(id).setValue(track);
            Toast.makeText(this, "Track saved", Toast.LENGTH_LONG).show();
            editTextTrackName.setText("");
        } else {
            Toast.makeText(this, "Please enter track name", Toast.LENGTH_LONG).show();
        }
    }*/
}
