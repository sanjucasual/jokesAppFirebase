package net.course99.xxxnonveg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String ARTIST_NAME = "net.course99.xxxnonveg.artistname";
    public static final String ARTIST_ID = "net.course99.xxxnonveg.artistid";
    public static final String ARTIST_LIST = "ARTIST_LIST";
    EditText editTextName;
    Spinner spinnerGenre;
    Button buttonAddArtist;
    ListView listViewArtists;
    ArrayList<Artist> artists;
    DatabaseReference databaseArtists;
    private static FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .threshold(3)
                .session(2)
                .title("कृपया मेरी एप्प को रेटिंग दीजिये ! इसमें आपका एक मिनट से ज्यादा समय नहीं लगेगा ! धन्यवाद !")
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                    }
                }).build();

        ratingDialog.show();
        databaseArtists = mDatabase.getReference("artists");
        listViewArtists = (ListView) findViewById(R.id.listViewArtists);
        artists = new ArrayList<Artist>();
        listViewArtists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = artists.get(i);
                Intent intent = new Intent(getApplicationContext(), ArtistActivity.class);
                intent.putExtra(ARTIST_ID, artist.getArtistId());
                intent.putExtra(ARTIST_NAME, artist.getArtistName());
                intent.putExtra("COUNT",i);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(ARTIST_LIST, artists);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artists.clear();
                ProgressBar pgsBar = (ProgressBar)findViewById(R.id.pBar);
                pgsBar.setVisibility(View.VISIBLE);
                ArrayList<Artist> newartists=new ArrayList<Artist>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Artist artist = postSnapshot.getValue(Artist.class);
                    //adding artist to the list
                    newartists.add(artist);
                }
                pgsBar.setVisibility(View.GONE);
                newartists.addAll(artists);
                artists=newartists;

                ArtistList artistAdapter = new ArtistList(MainActivity.this, artists);
                //attaching adapter to the listview
                listViewArtists.setAdapter(artistAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
