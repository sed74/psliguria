package com.dani.sed.liguriasoccorso;

import android.app.ActivityOptions;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dani.sed.liguriasoccorso.FragmentMission.MissionFragment;
import com.dani.sed.liguriasoccorso.FragmentMission.dummy.DummyContent;
import com.dani.sed.liguriasoccorso.Mission.MissionActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderCallbacks<HospitalListCustom>,
        MissionFragment.OnListFragmentInteractionListener {

    public static final String EXTRA_HOSPITAL_NAME = "hospital_name";
    public static final String EXTRA_CENTRALE_NAME = "centrale_name";
    public static final String EXTRA_HOSPITAL_POSITION = "hospital_position";
    public static final String DATI_PS_REQUEST_URL =
            "http://datipsge.azurewebsites.net/api/hospital/";
    public static final String DATI_PS_FORCE_REQUEST_URL =
            "http://datipsge.azurewebsites.net/api/hospital/cache/reload";
    public static final String URL_CENTRALI =
            "http://datipsge.azurewebsites.net/api/anagrafiche/headquarter/all";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int HOSPITAL_LOADER_ID = 1;
    private static final String TAG = MainActivity.class.getClass().getName();
    SwipeRefreshLayout mSwipeRefreshLayout;
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

//        ViewFlipper vf = (ViewFlipper)findViewById(R.id.vf);
//        vf.setDisplayedChild(1);

//        final Handler handler = new Handler();
//
//        final Runnable r = new Runnable() {
//            public void run() {
//        FirebaseDatabase mPostazioniDB = FirebaseDatabase.getInstance();
////        mPostazioniDB.setPersistenceEnabled(true);
//        final DatabaseReference myRef = mPostazioniDB.getReference("postazione");
//
//        // Read from the mPostazioniDB
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                ArrayList<Postazione> postazioni = new ArrayList<>();
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    // TODO: handle the post
//                    Postazione lPostazione = postSnapshot.getValue(Postazione.class);
//                    postazioni.add(lPostazione);
//                    String lID = postSnapshot.getKey();
////                    myRef.child(lID).child("descrizione").setValue(lPostazione.getName());
//
//                }
////                myRef.setValue(postazioni);
//                Log.d(TAG, "Value is: " + postazioni.size());
//                Toast.makeText(MainActivity.this, "Record scaricati: " + postazioni.size(),
//                        Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//            }
//        };
//
//        handler.postDelayed(r, 10);

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
        setVersionNo(navigationView.getMenu());

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
                hospitalIntent.putExtra(EXTRA_HOSPITAL_POSITION, position);

                startActivity(hospitalIntent);
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView();
            }
        });

        showWhatsNew();
        customStartup();
    }

    private void showWhatsNew() {
        final SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int verCode = pInfo.versionCode;
        final String whatsnewPref = getString(R.string.show_whatsnew_pa, String.valueOf(verCode));
        long showWhatsNew = sharedPref.getInt(whatsnewPref, 0);
        if (showWhatsNew == 0) {
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.whats_new)
                    .setTitle(R.string.whats_new_title);
            builder.setPositiveButton(R.string.dont_show, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt(whatsnewPref, 1);
                    editor.commit();
                }
            });
            builder.setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                }
            });

            // 3. Get the AlertDialog from create()
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }
    private void setVersionNo(Menu menu ) {

        // find MenuItem you want to change
        MenuItem versionItem = menu.findItem(R.id.version_info);

        // set new title to the MenuItem
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.version_no)).append(" ").append(BuildConfig.VERSION_NAME);
        versionItem.setTitle(sb.toString());
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
        getMenuInflater().inflate(R.menu.menu_hospital, menu);
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
        TextView text = (TextView) findViewById(R.id.text);
        text.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);

        // Restart the loader to requery the USGS as the query settings have been updated
        getLoaderManager().restartLoader(HOSPITAL_LOADER_ID, null, MainActivity.this);

    }
    private void customStartup() {
        if (!((StartUp) this.getApplication()).getFirstRun())
            return;
        ((StartUp) this.getApplication()).setFirstRun(false);

            SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        Boolean isCustom = sharedPreferences.getBoolean("custom_startup", false);

        if(isCustom) {
            String startupActivity = sharedPreferences.getString("startup_list", null);
            if (startupActivity != null) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                Menu menu = navigationView.getMenu();
                switch (startupActivity) {
                    case "ps":
                        menu.performIdentifierAction(R.id.ps_activity, 0);
                        break;
                    case "genova":
                        menu.performIdentifierAction(R.id.nav_genova, 0);
                        break;
                    case "imperia":
                        menu.performIdentifierAction(R.id.nav_imperia, 0);
                        break;
                    case "spezia":
                        menu.performIdentifierAction(R.id.nav_la_spezia, 0);
                        break;
                    case "lavagna":
                        menu.performIdentifierAction(R.id.nav_lavagna, 0);
                        break;
                    case "savona":
                        menu.performIdentifierAction(R.id.nav_savona, 0);
                        break;
                    case "postazioni":
                        menu.performIdentifierAction(R.id.nav_centrali, 0);
                        break;
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        item.setChecked(true);
        if (id == R.id.ps_activity) {
            // Handle the camera action
        } else if (id == R.id.nav_invite) {
            Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
        } else if (id == R.id.nav_settings) {
            Intent settingsActivity = new Intent(MainActivity.this, AppSettings.class);
            startActivity(settingsActivity);
        } else if (id == R.id.nav_charlie_code_legend) {
            Intent settingsActivity = new Intent(MainActivity.this, CharlieCodeActivity.class);
            startActivity(settingsActivity);
        } else if (id == R.id.nav_emergency_code_legend) {
            Intent settingsActivity = new Intent(MainActivity.this, LegendActivity.class);
            startActivity(settingsActivity);
        } else if (id == R.id.nav_centrali) {
            new AsyncString(MainActivity.this).execute(URL_CENTRALI);
        } else if (id == R.id.nav_about) {

//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.add(new MissionFragment(), null);
//            ft.commit();

//            android.app.Fragment newFragment;
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            newFragment = new MissionFragment();
//            transaction.add(newFragment, null);
////            transaction.replace(R.id.activity_main, newFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();

//            Intent settingsActivity = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivity(settingsActivity);
        } else if (id == R.id.nav_genova || id == R.id.nav_imperia || id == R.id.nav_la_spezia ||
                id == R.id.nav_lavagna || id == R.id.nav_savona) {
            Intent missionIntent = new Intent(MainActivity.this, MissionActivity.class);
            switch (id) {
                case R.id.nav_genova:
                    missionIntent.putExtra(EXTRA_CENTRALE_NAME, "Genova");
                    break;
                case R.id.nav_imperia:
                    missionIntent.putExtra(EXTRA_CENTRALE_NAME, "Imperia");
                    break;
                case R.id.nav_la_spezia:
                    missionIntent.putExtra(EXTRA_CENTRALE_NAME, "LaSpezia");
                    break;
                case R.id.nav_lavagna:
                    missionIntent.putExtra(EXTRA_CENTRALE_NAME, "Lavagna");
                    break;
                case R.id.nav_savona:
                    missionIntent.putExtra(EXTRA_CENTRALE_NAME, "Savona");
                    break;
            }
            View view = findViewById(R.id.list);
            ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                    0, view.getWidth(), view.getHeight());
            startActivity(missionIntent, options.toBundle());
        }
//                bundle.putString(FirebaseAnalytics.Param.ORIGIN, "MainActivity");

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
            text.setText(R.string.no_data_display);
            text.setVisibility(View.VISIBLE);
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(android.content.Loader<HospitalListCustom> loader) {
        // Loader reset, so we can clear out our existing data.
//        mAdapter.clear();
    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
