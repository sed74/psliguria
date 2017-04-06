package com.sed.federico.prontosoccorsoligura.Mission;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sed.federico.prontosoccorsoligura.HospitalActivity;
import com.sed.federico.prontosoccorsoligura.MainActivity;
import com.sed.federico.prontosoccorsoligura.R;
import com.sed.federico.prontosoccorsoligura.SettingsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MissionActivity extends AppCompatActivity
        implements LoaderCallbacks<MissionListCustom> {

    public static final String MISSION_REQUEST_BASE_URL =
            "http://datipsge.azurewebsites.net/api/hospitalization/";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int HOSPITAL_LOADER_ID = 1;
    private static final String EXTRA_MISSION_POSITION = "position";
    private static MissionListCustom mMissions;
    /**
     * Adapter for the list of earthquakes
     */

    private MissionAdapter mAdapter;
    private String mActualURL;

    public static MissionListCustom getHospitals() {
        return mMissions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        // Find a reference to the {@link ListView} in the layout
        ListView missionListView = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();

        mActualURL = MISSION_REQUEST_BASE_URL + "/" + intent.getStringExtra("hospital");
        this.setTitle(intent.getStringExtra("hospital"));

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new MissionAdapter(this, new MissionListCustom());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        missionListView.setAdapter(mAdapter);

        TextView text = (TextView) findViewById(R.id.textView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            android.app.LoaderManager loaderManager = getLoaderManager();


            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(HOSPITAL_LOADER_ID, null, this);
//            loaderManager.initLoader(HOSPITAL_FORCE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
        missionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent missionIntent = new Intent(MissionActivity.this, HospitalActivity.class);

                missionIntent.putExtra(EXTRA_MISSION_POSITION, position);
//                startActivity(missionIntent);

                Toast toast = Toast.makeText(MissionActivity.this, getString(R.string.not_implemented_yet), Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

    }

   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_missions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refreshView();

        } else if (id == R.id.action_sort_by_mission) {
            sortByMission();
        } else if (id == R.id.action_sort_by_postazione) {
            sortByPostazione();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortByPostazione() {
        Collections.sort(mMissions, new PostazioneComparator());
        mAdapter.clear();
        mAdapter.addAll(mMissions);
    }

    private void sortByMission() {
        Collections.sort(mMissions, new MissionComparator());
        mAdapter.clear();
        mAdapter.addAll(mMissions);
    }

    private void refreshView() {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        TextView text = (TextView) findViewById(R.id.text);
        text.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);

        // Restart the loader to requery the USGS as the query settings have been updated
        getLoaderManager().restartLoader(HOSPITAL_LOADER_ID, null, MissionActivity.this);

    }


    @Override
    public android.content.Loader<MissionListCustom> onCreateLoader(int i, Bundle bundle) {

        String url = mActualURL;
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new MissionLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(android.content.Loader<MissionListCustom> loader,
                               MissionListCustom hospitals) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView text = (TextView) findViewById(R.id.text);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (hospitals != null && !hospitals.isEmpty()) {

            Collections.sort(hospitals, new PostazioneComparator());

            mAdapter.addAll(hospitals);
            text.setVisibility(View.GONE);
            mMissions = hospitals;
        } else {
            text.setText(R.string.no_data_display);
            text.setVisibility(View.VISIBLE);
        }

        Collections.sort(hospitals, new Comparator<Mission>() {
            @Override
            public int compare(Mission o1, Mission o2) {
                return o1.getmPostazione().compareToIgnoreCase(o2.getmPostazione());
            }

        });
    }


    @Override
    public void onLoaderReset(android.content.Loader<MissionListCustom> loader) {
        // Loader reset, so we can clear out our existing data.
//        mAdapter.clear();
    }

}

class PostazioneComparator implements Comparator<Mission> {
    public int compare(Mission p1, Mission p2) {
        return p1.getmPostazione().compareToIgnoreCase(p2.getmPostazione());
    }
}

class MissionComparator implements Comparator<Mission> {
    public int compare(Mission p1, Mission p2) {
        return p1.getMissionNo().compareToIgnoreCase(p2.getMissionNo());
    }
}