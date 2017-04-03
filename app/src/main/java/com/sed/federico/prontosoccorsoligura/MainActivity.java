package com.sed.federico.prontosoccorsoligura;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderCallbacks<HospitalListCustom> {

    public static final String EXTRA_HOSPITAL_NAME = "hospital_name";
    public static final String EXTRA_HOSPITAL_WW = "white_waiting";
    public static final String EXTRA_HOSPITAL_GW = "green_waiting";
    //    private static final int HOSPITAL_FORCE_LOADER_ID = 2;
    public static final String EXTRA_HOSPITAL_YW = "yellow_waiting";
    public static final String EXTRA_HOSPITAL_RW = "red_waiting";
    public static final String EXTRA_HOSPITAL_WR = "white_running";
    public static final String EXTRA_HOSPITAL_GR = "green_running";
    public static final String EXTRA_HOSPITAL_YR = "yellow_running";
    public static final String EXTRA_HOSPITAL_RR = "red_running";
    private static final String DATI_PS_REQUEST_URL =
            "http://datipsge.azurewebsites.net/api/hospital/";
    private static final String DATI_PS_FORCE_REQUEST_URL =
            "http://datipsge.azurewebsites.net/api/hospital/cache/reload";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int HOSPITAL_LOADER_ID = 1;
    /**
     * Adapter for the list of earthquakes
     */

    private HospitalAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find a reference to the {@link ListView} in the layout
        ListView hospitalListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new HospitalAdapter(this, new HospitalListCustom());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        hospitalListView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the loading indicator while new data is being fetched
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.VISIBLE);

                // Restart the loader to requery the USGS as the query settings have been updated
                getLoaderManager().restartLoader(HOSPITAL_LOADER_ID, null, MainActivity.this);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        hospitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent hospitalIntent = new Intent(MainActivity.this, HospitalActivity.class);

                Hospital localHospital = mAdapter.getItem(position);
                hospitalIntent.putExtra(EXTRA_HOSPITAL_NAME, localHospital.getName());
                hospitalIntent.putExtra(EXTRA_HOSPITAL_WW, localHospital.getWhiteWaiting());
                hospitalIntent.putExtra(EXTRA_HOSPITAL_GW, localHospital.getGreenWaiting());
                hospitalIntent.putExtra(EXTRA_HOSPITAL_YW, localHospital.getYellowWaiting());
                hospitalIntent.putExtra(EXTRA_HOSPITAL_RW, localHospital.getRedWaiting());
                startActivity(hospitalIntent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            refreshView();
        } else if (id == R.id.action_force_refresh) {

            QueryUtils.callWebAPI(DATI_PS_FORCE_REQUEST_URL);
            refreshView();
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshView() {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);

        // Restart the loader to requery the USGS as the query settings have been updated
        getLoaderManager().restartLoader(HOSPITAL_LOADER_ID, null, MainActivity.this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.ps_activity) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public android.content.Loader<HospitalListCustom> onCreateLoader(int i, Bundle bundle) {

        String url = DATI_PS_REQUEST_URL;
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();


        return new HospitalLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(android.content.Loader<HospitalListCustom> loader, HospitalListCustom hospitals) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView text = (TextView) findViewById(R.id.text);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (hospitals != null && !hospitals.isEmpty()) {
            mAdapter.addAll(hospitals);
            text.setVisibility(View.GONE);
        } else {
            text.setText("No data to display");
            text.setVisibility(View.VISIBLE);
        }

//        text.setText(Integer.toString(hospitals.getMaxGlobalValue()));
//        text.setVisibility(View.VISIBLE);
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(android.content.Loader<HospitalListCustom> loader) {
        // Loader reset, so we can clear out our existing data.
//        mAdapter.clear();
    }
}
