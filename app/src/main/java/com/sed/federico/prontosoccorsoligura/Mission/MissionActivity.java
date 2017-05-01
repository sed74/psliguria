package com.sed.federico.prontosoccorsoligura.Mission;

import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.sed.federico.prontosoccorsoligura.AsyncDownloader;
import com.sed.federico.prontosoccorsoligura.MainActivity;
import com.sed.federico.prontosoccorsoligura.QueryUtils;
import com.sed.federico.prontosoccorsoligura.R;

import java.util.Collections;
import java.util.Comparator;

public class MissionActivity extends AppCompatActivity
        implements LoaderCallbacks<MissionListCustom>, AsyncDownloader.onDownloadFinishedListener {

    public static final String MISSION_REQUEST_BASE_URL =
            "http://datipsge.azurewebsites.net/api/hospitalization/";
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int LOADER_ID_SINGLE = 1;
    private static final int LOADER_ID_MULTI = 2;
    private static final String EXTRA_MISSION_POSITION = "position";
    private static final int SORT_BY_MISSION = 1;
    private static final int SORT_BY_POSTAZIONE = 2;
    private static MissionListCustom mMissions;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressDialog mProgressDialog;
    private int mSortedBy = SORT_BY_MISSION;
    /**
     * Adapter for the list of Missions
     */
    private MissionAdapter mAdapter;
    private String mActualURL;
    private String mHospitalName;

    private FirebaseAnalytics mFirebaseAnalytics;

    public static MissionListCustom getHospitals() {
        return mMissions;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        // Find a reference to the {@link ListView} in the layout
        final ListView missionListView = (ListView) findViewById(R.id.list);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        mHospitalName = bundle.getString(MainActivity.EXTRA_HOSPITAL_NAME);
//        if (savedInstanceState == null){
//            Intent intent = getIntent();
//            mHospitalName = intent.getStringExtra(MainActivity.EXTRA_HOSPITAL_NAME);
//        }else{
//            mHospitalName = savedInstanceState.getString(MainActivity.EXTRA_HOSPITAL_NAME);
//        }

        mActualURL = MISSION_REQUEST_BASE_URL + "/" + mHospitalName;
        this.setTitle(mHospitalName);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new MissionAdapter(this, new MissionListCustom());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        missionListView.setAdapter(mAdapter);

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
            loaderManager.initLoader(LOADER_ID_SINGLE, null, this);
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

                if (getResources().getConfiguration().screenWidthDp < QueryUtils.TABLET_MIN_WIDTH) {

                    boolean isSelected = (boolean) view.getTag();

                    ListView lv = missionListView;
                    int childCount = lv.getChildCount();

                    for (int i = 0; i < childCount; i++) {
                        View v = lv.getChildAt(i);
                        setVisibility(v, false);
                    }
                    setVisibility(view, !isSelected);
                }
                Bundle bundle = new Bundle();
//                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,
                        mMissions.get(position).getPubblicaAssistenza());
                bundle.putString(FirebaseAnalytics.Param.ORIGIN, "MissionActivity-" +
                        mMissions.get(position).getCentrale());
                bundle.putString(QueryUtils.FBASE_AMBULANCE_NO,
                        mMissions.get(position).getAmbulanceNo());
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView(false);
            }
        });

    }

    private void setVisibility(View view, boolean isSelected) {
        TextView pickupLocation = (TextView) view.findViewById(R.id.pickup_location);
        TextView charlieCode = (TextView) view.findViewById(R.id.charlie_code);
        TextView indiaCode = (TextView) view.findViewById(R.id.india_code);
        TextView hospital = (TextView) view.findViewById(R.id.hospital);

        TextView text1 = (TextView) view.findViewById(R.id.textView10);
        TextView text2 = (TextView) view.findViewById(R.id.textView11);
        TextView text3 = (TextView) view.findViewById(R.id.textView12);
        TextView text4 = (TextView) view.findViewById(R.id.textView13);

        int visibility = !isSelected ? View.GONE : View.VISIBLE;
        pickupLocation.setVisibility(visibility);
        charlieCode.setVisibility(visibility);
        indiaCode.setVisibility(visibility);
        hospital.setVisibility(visibility);
        text1.setVisibility(visibility);
        text2.setVisibility(visibility);
        text3.setVisibility(visibility);
        text4.setVisibility(visibility);

        view.setTag(isSelected);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(MainActivity.EXTRA_HOSPITAL_NAME, mHospitalName);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

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
        this.setTitle(mHospitalName);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            refreshView(true);

        } else if (id == R.id.action_sort_by_mission) {
            sortByMission();
        } else if (id == R.id.action_sort_by_postazione) {
            sortByPostazione();
        } else if (id == R.id.where_is_drago) {
            whereIsDrago();
        }

        return super.onOptionsItemSelected(item);
    }

    private void sortByPostazione() {
        Collections.sort(mMissions, new PostazioneComparator());
        mAdapter.clear();
        mAdapter.addAll(mMissions);
        mSortedBy = SORT_BY_POSTAZIONE;
    }

    private void sortByMission() {
        Collections.sort(mMissions, new MissionComparator());
        mAdapter.clear();
        mAdapter.addAll(mMissions);
        mSortedBy = SORT_BY_MISSION;
    }

    private void refreshView(boolean showProgress) {
        TextView text = (TextView) findViewById(R.id.text);
        text.setVisibility(View.INVISIBLE);
        if (showProgress) {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.VISIBLE);
        }
        // Restart the loader to requery the USGS as the query settings have been updated
        getLoaderManager().restartLoader(LOADER_ID_SINGLE, null, MissionActivity.this);

    }


    @Override
    public android.content.Loader<MissionListCustom> onCreateLoader(int i, Bundle bundle) {

//        View loadingIndicator = findViewById(R.id.loading_indicator);
//        loadingIndicator.setVisibility(View.VISIBLE);
        switch (i) {
            case LOADER_ID_SINGLE:
                String url = mActualURL;
                Uri baseUri = Uri.parse(url);
                Uri.Builder uriBuilder = baseUri.buildUpon();

                return new MissionLoader(this, uriBuilder.toString());
            case LOADER_ID_MULTI:
                String[] urls = new String[]{MISSION_REQUEST_BASE_URL + "Genova",
                        MISSION_REQUEST_BASE_URL + "Savona", MISSION_REQUEST_BASE_URL + "LaSpezia",
                        MISSION_REQUEST_BASE_URL + "Lavagna", MISSION_REQUEST_BASE_URL + "Imperia"};

                for (String s : urls) {
                    Uri baseUriMulti = Uri.parse(s);
                    Uri.Builder uriBuilderMulti = baseUriMulti.buildUpon();
                    url = uriBuilderMulti.toString();
                }
                return new MissionLoader(this, urls);
            default:
                return null;
        }

    }

    private void whereIsDrago() {

        mProgressDialog = new ProgressDialog(MissionActivity.this);
        mProgressDialog.setMessage(getString(R.string.looking_for_drago));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
//        AsyncTask<String, Void, MissionListCustom> temp =
        new AsyncDownloader(getBaseContext(), this).execute(MISSION_REQUEST_BASE_URL + "Genova",
                MISSION_REQUEST_BASE_URL + "Savona", MISSION_REQUEST_BASE_URL + "LaSpezia",
                MISSION_REQUEST_BASE_URL + "Lavagna", MISSION_REQUEST_BASE_URL + "Imperia");

//        getLoaderManager().initLoader(LOADER_ID_MULTI, null, this);

    }

    @Override
    public void onDownloadFinished(String location) {
        String toastText;
        if (!location.isEmpty()) {
            toastText = String.format(getString(R.string.drago_is), location);
        } else {
            toastText = getString(R.string.drago_is_not_working);
        }
        Toast toast = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

//        View loadingIndicator = findViewById(R.id.loading_indicator);
//        loadingIndicator.setVisibility(View.GONE);
    }

    @Override
    public void onDownloadStarted() {
//        View loadingIndicator = findViewById(R.id.loading_indicator);
//        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadFinished(android.content.Loader<MissionListCustom> loader,
                               MissionListCustom missions) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView text = (TextView) findViewById(R.id.text);
        // Clear the adapter of previous earthquake data
        if (loader.getId() == LOADER_ID_SINGLE) {
            mAdapter.clear();
            // If there is a valid list of {@link missions}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (missions != null && !missions.isEmpty()) {

                switch (mSortedBy) {
                    case SORT_BY_MISSION:
                        Collections.sort(missions, new MissionComparator());
                        break;
                    case SORT_BY_POSTAZIONE:
                        Collections.sort(missions, new PostazioneComparator());
                        break;
                }

                mAdapter.addAll(missions);
                text.setVisibility(View.GONE);
                mMissions = missions;
            } else {
                text.setText(R.string.no_data_display);
                text.setVisibility(View.VISIBLE);
            }
        } else if (loader.getId() == LOADER_ID_MULTI) {
            String toastText;
            if (missions.isIsDragoWorking()) {
                toastText = String.format(getString(R.string.drago_is), missions.getDragoPosition());

            } else {
                toastText = getString(R.string.drago_is_not_working);
            }

            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
            getLoaderManager().destroyLoader(LOADER_ID_MULTI);
        }

        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onLoaderReset(android.content.Loader<MissionListCustom> loader) {
        // Loader reset, so we can clear out our existing data.
//        mAdapter.clear();
    }

}

class PostazioneComparator implements Comparator<Mission> {
    public int compare(Mission p1, Mission p2) {
        return p1.getPubblicaAssistenza().compareToIgnoreCase(p2.getPubblicaAssistenza());
    }
}

class MissionComparator implements Comparator<Mission> {
    public int compare(Mission p1, Mission p2) {
        return p1.getMissionNo().compareToIgnoreCase(p2.getMissionNo());
    }
}

