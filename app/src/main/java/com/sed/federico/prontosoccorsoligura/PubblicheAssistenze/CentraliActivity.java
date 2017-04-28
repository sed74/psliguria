package com.sed.federico.prontosoccorsoligura.PubblicheAssistenze;

import android.app.ActivityOptions;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sed.federico.prontosoccorsoligura.AsyncString;
import com.sed.federico.prontosoccorsoligura.Centrale;
import com.sed.federico.prontosoccorsoligura.CentraleListCustom;
import com.sed.federico.prontosoccorsoligura.CharlieCodeActivity;
import com.sed.federico.prontosoccorsoligura.FragmentMission.MissionFragment;
import com.sed.federico.prontosoccorsoligura.LegendActivity;
import com.sed.federico.prontosoccorsoligura.MainActivity;
import com.sed.federico.prontosoccorsoligura.Mission.MissionActivity;
import com.sed.federico.prontosoccorsoligura.QueryUtils;
import com.sed.federico.prontosoccorsoligura.R;
import com.sed.federico.prontosoccorsoligura.SettingsActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CentraliActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String URL_PA =
            "http://datipsge.azurewebsites.net/api/anagrafiche/comitato/all";
    static ArrayList<String> mNomiCentrali;
    String mJson;
    SparseArray<CentraleListCustom> mCacheCentrali;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_centrali);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCacheCentrali = new SparseArray<>();

        mJson = getIntent().getStringExtra("centrali");
        populateTab(mJson);
        // Create the adapter that will return a fragment for each of the sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        mCacheCentrali.clear();
    }

    private void populateTab(String jason) {
        mNomiCentrali = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jason);
            for (int i = 0; i < jsonArray.length(); i++) {
                String name = jsonArray.getString(i).replaceAll("(\\p{Ll})(\\p{Lu})", "$1 $2");
                ;
//                mNomiCentrali.add(name.toLowerCase().contains("laspezia") ?
//                        name.substring(0, 2) + " " + name.substring(2) : name);
                mNomiCentrali.add(name);

            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_centrali, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.ps_activity) {
            // Handle the camera action
        } else if (id == R.id.nav_settings) {
            Intent settingsActivity = new Intent(CentraliActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
        } else if (id == R.id.nav_charlie_code_legend) {
            Intent settingsActivity = new Intent(CentraliActivity.this, CharlieCodeActivity.class);
            startActivity(settingsActivity);
        } else if (id == R.id.nav_emergency_code_legend) {
            Intent settingsActivity = new Intent(CentraliActivity.this, LegendActivity.class);
            startActivity(settingsActivity);
        } else if (id == R.id.nav_centrali) {
            new AsyncString(CentraliActivity.this).execute(MainActivity.URL_CENTRALI);
        } else if (id == R.id.nav_about) {
//            android.app.Fragment newFragment;
//            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            newFragment = new MissionFragment();
//            transaction.replace(R.id.tabs, newFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();
//            break;
//            Intent settingsActivity = new Intent(CentraliActivity.this, SettingsActivity.class);
//            startActivity(settingsActivity);
        } else if (id == R.id.nav_genova || id == R.id.nav_imperia || id == R.id.nav_la_spezia ||
                id == R.id.nav_lavagna || id == R.id.nav_savona) {
            Intent missionIntent = new Intent(CentraliActivity.this, MissionActivity.class);
            switch (id) {
                case R.id.nav_genova:
                    missionIntent.putExtra(MainActivity.EXTRA_HOSPITAL_NAME, "Genova");
                    break;
                case R.id.nav_imperia:
                    missionIntent.putExtra(MainActivity.EXTRA_HOSPITAL_NAME, "Imperia");
                    break;
                case R.id.nav_la_spezia:
                    missionIntent.putExtra(MainActivity.EXTRA_HOSPITAL_NAME, "LaSpezia");
                    break;
                case R.id.nav_lavagna:
                    missionIntent.putExtra(MainActivity.EXTRA_HOSPITAL_NAME, "Lavagna");
                    break;
                case R.id.nav_savona:
                    missionIntent.putExtra(MainActivity.EXTRA_HOSPITAL_NAME, "Savona");
                    break;
            }
            View view = findViewById(R.id.recycle_view);
            ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                    0, view.getWidth(), view.getHeight());
            startActivity(missionIntent, options.toBundle());
        }
        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        public static final String ARG_SECTION_NUMBER = "section_number";
        protected static CentraleListCustom mCentrali;
        protected RecyclerView.LayoutManager mLayoutManager;
        protected LayoutManagerType mCurrentLayoutManagerType;
        protected RecyclerView mRecyclerView;
        private ProgressBar mProgressBar;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int temp = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_centrali, container, false);

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view);

            CentraliActivity mainActivity = (CentraliActivity) getActivity();

            mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

            int sectionNo = getArguments().getInt(ARG_SECTION_NUMBER);
            // LinearLayoutManager is used here, this will layout the elements in a similar fashion
            // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
            // elements are laid out.
            mLayoutManager = new LinearLayoutManager(getActivity());

            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;


            new CentraliAsyncDownloader(mainActivity, mRecyclerView,
                    mNomiCentrali.get(sectionNo - 1)).execute(URL_PA, String.valueOf(sectionNo));

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            return rootView;
        }

        private enum LayoutManagerType {
            GRID_LAYOUT_MANAGER,
            LINEAR_LAYOUT_MANAGER
        }

        public class CentraliAsyncDownloader extends AsyncTask<String, Void, CentraleListCustom> {
            CentraliActivity mContext;
            RecyclerView mRecyclerViewAsync;
            String mCentrale;


            public CentraliAsyncDownloader(CentraliActivity context, RecyclerView recyclerView,
                                           String centrale) {
                mContext = context;
                mRecyclerViewAsync = recyclerView;
                mCentrale = centrale.replaceAll(" ", "");
            }

            @Override
            protected CentraleListCustom doInBackground(String... params) {
                if (params.length == 0) {
                    return null;
                }

                int currentSection = Integer.valueOf(params[1]) - 1;
                CentraleListCustom centrali;

                centrali = mContext.mCacheCentrali.get(currentSection);
                if (centrali == null) {
                    centrali = new CentraleListCustom();

                    String url = params[0];
                    CentraleListCustom temp = QueryUtils.fetchCentrali(url);
                    for (Centrale c :
                            temp) {
                        if (c.getCentrale().equalsIgnoreCase(mCentrale)) {
                            centrali.add(c);
                        }
                    }
                    mContext.mCacheCentrali.put(currentSection, centrali);
                }
                Collections.sort(centrali, new CentraleComparator());
                return centrali;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected void onPostExecute(CentraleListCustom centrali) {
                super.onPostExecute(centrali);
                mProgressBar.setVisibility(View.GONE);
                mRecyclerViewAsync.setAdapter(new CentraliAdapter(mContext, centrali));
//            if (mProgressDialog.isShowing())
//                mProgressDialog.dismiss();

            }

            class CentraleComparator implements Comparator<Centrale> {
                public int compare(Centrale p1, Centrale p2) {
                    return p1.getDescrizione().compareToIgnoreCase(p2.getDescrizione());
                }
            }
        }
    }


    public static class CentraliAdapter extends RecyclerView.Adapter<CentraliViewHolder> {
        private CentraleListCustom mCentrali;
        private Context mContext;

        // Provide a suitable constructor (depends on the kind of dataset)
        public CentraliAdapter(Context context, CentraleListCustom centrali) {
//            super();
            mCentrali = centrali;
            mContext = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public CentraliViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.centrale_element, parent, false);

            CentraliViewHolder vh = new CentraliViewHolder(v, new CentraliViewHolder.onRecyclerViewClickListener() {
                @Override
                public void onClick(View caller) {
                    Toast toast = Toast.makeText(mContext,
                            mContext.getString(R.string.statistics_to_be_implemented),
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(CentraliViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Centrale centrale = mCentrali.get(position);

            String paName = QueryUtils.getPostazioneName(centrale.getCodice());
            holder.mPaName.setText(paName);
//            holder.mCentrale.setText(String.format(mContext.getString(R.string.belong_to_centrale),
//                    centrale.getCentrale()));
            holder.mCentrale.setText(mContext.getString(R.string.statistics_coming_soon));

            holder.mCross.setBackground(getDrawable(paName));

        }

        private Drawable getDrawable(String centrale) {
            int resource;
            String cent = centrale.toLowerCase();
            if (cent.contains("oro")) {
                resource = R.drawable.cross_gold;
            } else if (cent.contains("bianca") || cent.contains("cb")) {
                resource = R.drawable.cross_white_no_circle;
            } else if (cent.contains("blu")) {
                resource = R.drawable.cross_blue;
            } else if (cent.contains("celeste")) {
                resource = R.drawable.cross_celeste;
            } else if (cent.contains("verde") || cent.contains(" cv ")) {
                resource = R.drawable.cross_green;
            } else if (cent.contains("azzurra")) {
                resource = R.drawable.cross_light_blue;
            } else if (cent.contains("rosa")) {
                resource = R.drawable.cross_rose;
            } else if (cent.contains("rossa") || cent.contains("cri ") || cent.contains(" cr ")) {
                resource = R.drawable.cross_red;
            } else if (cent.contains("gialla")) {
                resource = R.drawable.cross_yellow;
            } else if (cent.contains("elisoccorso")) {
                resource = R.drawable.cross_eli;
            } else {
                resource = R.drawable.cross;
            }
            return ContextCompat.getDrawable(mContext, resource);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mCentrali.size();
        }

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CentraliViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // each data item is just a string in this case
        public TextView mPaName;
        public TextView mCross;
        public TextView mCentrale;
        onRecyclerViewClickListener mListener;


        public CentraliViewHolder(View v) {
            super(v);
            mPaName = (TextView) v.findViewById(R.id.pa_label);
            mCentrale = (TextView) v.findViewById(R.id.centrale);
            mCross = (TextView) v.findViewById(R.id.cross_icon);
        }

        public CentraliViewHolder(View v, onRecyclerViewClickListener listener) {
            super(v);
            mPaName = (TextView) v.findViewById(R.id.pa_label);
            mCentrale = (TextView) v.findViewById(R.id.centrale);
            mCross = (TextView) v.findViewById(R.id.cross_icon);
            mListener = listener;

            v.setOnClickListener(this);
//            mPaName.setOnClickListener(this);
//            mCross.setOnClickListener(this);
//            mCentrale.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        public interface onRecyclerViewClickListener {
            void onClick(View caller);
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mNomiCentrali.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return mNomiCentrali.get(position);
        }
    }

}
