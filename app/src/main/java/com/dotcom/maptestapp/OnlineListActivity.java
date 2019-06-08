package com.dotcom.maptestapp;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OnlineListActivity extends AppCompatActivity {

    DatabaseReference onlineref,currentuserref,counterref;
    FirebaseRecyclerAdapter<com.dotcom.maptestapp.User,ListofOnlineViewHolder>adapter;
    RecyclerView listOnline;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_list);


        listOnline=findViewById(R.id.onlineList);
        listOnline.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        listOnline.setLayoutManager(layoutManager);

        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle("Online");
        setSupportActionBar(toolbar);

        onlineref = FirebaseDatabase.getInstance().getReference().child(".info/Connected");
        counterref=FirebaseDatabase.getInstance().getReference("LastOnline");
        currentuserref=FirebaseDatabase.getInstance().getReference("LastOnline")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        setupSystem();
        updateList();


    }

    private void updateList() {
        adapter = new FirebaseRecyclerAdapter<com.dotcom.maptestapp.User, ListofOnlineViewHolder>(
                com.dotcom.maptestapp.User.class,
                R.layout.user_layout,
                ListofOnlineViewHolder.class,
                counterref
        ) {

            @Override
            protected void populateViewHolder(ListofOnlineViewHolder viewHolder, com.dotcom.maptestapp.User model, int position) {
                viewHolder.text_mail.setText(model.getEmails());
            }

        };
        adapter.notifyDataSetChanged();
        listOnline.setAdapter(adapter);
    }

    private void setupSystem() {
        onlineref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(Boolean.class))
                {
                    currentuserref.onDisconnect().removeValue();
                    counterref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(new com.dotcom.maptestapp.User(FirebaseAuth.getInstance().getCurrentUser().getEmail(),("Online")));
                            adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        currentuserref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    com.dotcom.maptestapp.User user=postSnapshot.getValue(com.dotcom.maptestapp.User.class);
                    Log.d("Log",""+user.getEmails()+"is"+user.getStatus());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
       MenuInflater inflater =getMenuInflater();
       inflater.inflate(R.menu.menuonline, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
     switch (item.getItemId())
        {
            case  R.id.action_join:
                counterref.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(new User(FirebaseAuth.getInstance().getCurrentUser().getEmail(), "Online"));
                break;
            case R.id.logout:
                currentuserref.removeValue();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
