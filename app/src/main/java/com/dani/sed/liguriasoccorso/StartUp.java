package com.dani.sed.liguriasoccorso;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import android.util.SparseArray;
import android.widget.Toast;

import com.dani.sed.liguriasoccorso.PubblicheAssistenze.Postazione;

/**
 * Created by federico.marchesi on 05/05/2017.
 */

public class StartUp extends MultiDexApplication {

    private Boolean isFirstRun = true;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public Boolean getFirstRun() {
        return isFirstRun;
    }

    public void setFirstRun(Boolean firstRun) {
        isFirstRun = firstRun;
    }

//    private void loadPostazione() {
//        FirebaseDatabase mPostazioniDB = FirebaseDatabase.getInstance();
//        mPostazioniDB.setPersistenceEnabled(true);
//        final DatabaseReference myRef = mPostazioniDB.getReference("postazione");
//        globalPostazioni = new SparseArray<>();
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                ArrayList<Postazione> postazioni = new ArrayList<>();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    // TODO: handle the post
//                    Postazione lPostazione = postSnapshot.getValue(Postazione.class);
////                    int lID = Integer.valueOf(postSnapshot.getKey());
////                    globalPostazioni.put(lID, lPostazione);
////                    myRef.child(lID).child("descrizione").setValue(lPostazione.getName());
//
//                }
//
////                myRef.setValue(postazioni);
//                Log.d("StartUp", "Value is: " + globalPostazioni.size());
////                Toast.makeText(MainActivity.this, "Record scaricati: " + postazioni.size(),
////                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
////                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//
//    }


}