package com.dani.sed.liguriasoccorso.PubblicheAssistenze;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import com.dani.sed.liguriasoccorso.AmbulanceDetails.AmbulanceDetailsActivity;
import com.dani.sed.liguriasoccorso.PostazioneListCustom;
import com.dani.sed.liguriasoccorso.QueryUtils;
import com.dani.sed.liguriasoccorso.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class PostazioniActivity extends AppCompatActivity {

    public static final String PA_CODE = "pa_code";
    public static final String PA_NAME = "pa_name";
    private static final String URL_POSTAZIONI =
            "http://datipsge.azurewebsites.net/api/centrali/";
    private static final String URL_POSTAZIONI_VERSION = "v1";
    private static final String TAG = "PostazioniActivity";
    static ArrayList<String> mNomiCentrali;
    String mJson;
    SparseArray<PostazioneListCustom> mCachePostazioni;
    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
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

        mCachePostazioni = new SparseArray<>();

        if (savedInstanceState != null) {
            // No intent is available, try getting data from savedInstance
            mJson = savedInstanceState.getString("centrali");
        } else if (getIntent() != null) {
            // Activity was started and got an Intent with data
            mJson = getIntent().getStringExtra("centrali");
        }
        if (mJson == null) {
            mJson = "";
        }

        populateTab(mJson);

        // Create the adapter that will return a fragment for each of the sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putString("centrali", mJson);

        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCachePostazioni.clear();
    }

    private void populateTab(String json) {
        if (json.isEmpty()) return;
        mNomiCentrali = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
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
        getMenuInflater().inflate(R.menu.menu_postazioni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        PlaceholderFragment page = (PlaceholderFragment)
                getSupportFragmentManager().findFragmentByTag("android:switcher:" +
                        R.id.container + ":" + String.valueOf(mViewPager.getCurrentItem()));

        PostazioniAdapter adapter = (PostazioniAdapter) page.mRecyclerView.getAdapter();

        switch (item.getItemId()) {
            case R.id.action_sort:
                return super.onOptionsItemSelected(item);
            case R.id.sort_by_name:
            case R.id.sort_by_white_code:
            case R.id.sort_by_green_code:
            case R.id.sort_by_yellow_code:
            case R.id.sort_by_red_code:
            case R.id.sort_by_missions:
                adapter.sortList(item.getItemId());
                break;
        }
        return super.onOptionsItemSelected(item);
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
        public static PostazioneListCustom mCentrali;
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

        public void sortByWhite() {
            Collections.sort(mCentrali, new PostazioneComparatorByWhite());
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

            mRecyclerView = rootView.findViewById(R.id.recycle_view);

            PostazioniActivity mainActivity = (PostazioniActivity) getActivity();

            mProgressBar = rootView.findViewById(R.id.progress_bar);

            int sectionNo = getArguments().getInt(ARG_SECTION_NUMBER);
            // LinearLayoutManager is used here, this will layout the elements in a similar fashion
            // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
            // elements are laid out.
            mLayoutManager = new LinearLayoutManager(getActivity());

            mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

            String fullUrl = QueryUtils.createUrlWithVersion(URL_POSTAZIONI, URL_POSTAZIONI_VERSION);

            new CentraliAsyncDownloader(mainActivity, mRecyclerView,
                    mNomiCentrali.get(sectionNo)).execute(fullUrl, String.valueOf(sectionNo));

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            return rootView;
        }

        private enum LayoutManagerType {
            GRID_LAYOUT_MANAGER,
            LINEAR_LAYOUT_MANAGER
        }

        class PostazioneComparatorByWhite implements Comparator<Postazione> {
            public int compare(Postazione p1, Postazione p2) {
                if (p1.getTotWhite() < p2.getTotWhite()) return 1;
                if (p1.getTotWhite() > p2.getTotWhite()) return -1;
                return 0;
            }
        }

        public class CentraliAsyncDownloader extends AsyncTask<String, Void, PostazioneListCustom> {
            PostazioniActivity mContext;
            RecyclerView mRecyclerViewAsync;
            String mCentrale;


            public CentraliAsyncDownloader(PostazioniActivity context, RecyclerView recyclerView,
                                           String centrale) {
                mContext = context;
                mRecyclerViewAsync = recyclerView;
                mCentrale = centrale.replaceAll(" ", "");
            }

            @Override
            protected PostazioneListCustom doInBackground(String... params) {
                if (params.length == 0) {
                    return null;
                }
                int currentSection = Integer.valueOf(params[1]) - 1;
                PostazioneListCustom centrali;

                centrali = mContext.mCachePostazioni.get(currentSection);
                if (centrali == null) {
                    String url = params[0] + mCentrale;
                    centrali = QueryUtils.fetchCentrali(url);
                    mContext.mCachePostazioni.put(currentSection, centrali);
                }
                Collections.sort(centrali, new PostazioneComparator());

                mCentrali = centrali;
                return centrali;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(PostazioneListCustom centrali) {
                super.onPostExecute(centrali);
                mProgressBar.setVisibility(View.GONE);
                mRecyclerViewAsync.setAdapter(new PostazioniAdapter(mContext, centrali));
            }

            class PostazioneComparator implements Comparator<Postazione> {
                public int compare(Postazione p1, Postazione p2) {
                    return p1.getName().compareToIgnoreCase(p2.getName());
                }
            }
        }

    }

    public static class PostazioniAdapter extends RecyclerView.Adapter<PostazioniViewHolder> {
        SparseArray<Comparator> comparator = new SparseArray<>();
        private PostazioneListCustom mPostazioni;
        private Context mContext;

        {
            comparator.put(R.id.sort_by_name, new SortByName());
            comparator.put(R.id.sort_by_white_code, new SortByWhite());
            comparator.put(R.id.sort_by_green_code, new SortByGreen());
            comparator.put(R.id.sort_by_yellow_code, new SortByYellow());
            comparator.put(R.id.sort_by_red_code, new SortByRed());
            comparator.put(R.id.sort_by_missions, new SortByMissions());
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public PostazioniAdapter(Context context, PostazioneListCustom postazioni) {
//            super();
            mPostazioni = postazioni;
            mContext = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public PostazioniViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.centrale_element, parent, false);

//            PostazioniViewHolder vh = new PostazioniViewHolder(v);

            PostazioniViewHolder vh = new PostazioniViewHolder(v, new PostazioniViewHolder.onRecyclerViewClickListener() {
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
        public void onBindViewHolder(PostazioniViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final Postazione postazione = mPostazioni.get(position);

            String paName = postazione.getName();
            holder.mPaName.setText(paName);

            holder.mTotMissions.setText(QueryUtils.getText(mContext, R.string.tot_missions,
                    postazione.getTotMissions(), postazione.getAvgMission()));

            holder.mTotMezzi.setText(QueryUtils.getText(mContext, R.string.no_of_ambulances,
                    postazione.getTotAmbulance()));
            holder.mAvgWhite.setText(String.format(mContext.getString(R.string.avg_white_per_day),
                    postazione.getTotWhite(), postazione.getAvgWhite()));
            holder.mAvgGreen.setText(String.format(mContext.getString(R.string.avg_green_per_day), postazione.getTotGreen(),
                    postazione.getAvgGreen()));
            holder.mAvgYellow.setText(String.format(mContext.getString(R.string.avg_yellow_per_day), postazione.getTotYellow(),
                    postazione.getAvgYellow()));
            holder.mAvgRed.setText(String.format(mContext.getString(R.string.avg_red_per_day), postazione.getTotRed(),
                    postazione.getAvgRed()));

            holder.mCross.setBackground(ContextCompat.getDrawable(mContext,
                    postazione.getCrossImage()));
            holder.setPostazioneCode(postazione.getCode());

            holder.setOnRecyclerViewClickListener(new PostazioniViewHolder.onRecyclerViewClickListener() {
                @Override
                public void onClick(View caller) {
                    Intent intent = new Intent(mContext, AmbulanceDetailsActivity.class);
                    intent.putExtra(PA_CODE, postazione.getCode());
                    intent.putExtra(PA_NAME, postazione.getName());
                    mContext.startActivity(intent);
//                    sortList();
//                    FirebaseAnalytics firebaseAnalytics;
//                    firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "AmbulanceDetail");
//                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, postazione.getName());
//                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                }
            });

        }

        public void sortList(int menuId) {
            Collections.sort(mPostazioni, comparator.get(menuId));
            this.notifyDataSetChanged();
//            Toast.makeText(mContext, mContext.getString(R.string.sorting_done),
//                    Toast.LENGTH_SHORT).show();
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mPostazioni.size();
        }

        class SortByMissions implements Comparator<Postazione> {
            public int compare(Postazione p1, Postazione p2) {
                if (p1.getTotMissions() < p2.getTotMissions()) return 1;
                if (p1.getTotMissions() > p2.getTotMissions()) return -1;
                return 0;
            }
        }

        class SortByName implements Comparator<Postazione> {
            public int compare(Postazione p1, Postazione p2) {
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        }

        class SortByRed implements Comparator<Postazione> {
            public int compare(Postazione p1, Postazione p2) {
                if (p1.getTotRed() < p2.getTotRed()) return 1;
                if (p1.getTotRed() > p2.getTotRed()) return -1;
                return 0;
            }
        }

        class SortByYellow implements Comparator<Postazione> {
            public int compare(Postazione p1, Postazione p2) {
                if (p1.getTotYellow() < p2.getTotYellow()) return 1;
                if (p1.getTotYellow() > p2.getTotYellow()) return -1;
                return 0;
            }
        }

        class SortByGreen implements Comparator<Postazione> {
            public int compare(Postazione p1, Postazione p2) {
                if (p1.getTotGreen() < p2.getTotGreen()) return 1;
                if (p1.getTotGreen() > p2.getTotGreen()) return -1;
                return 0;
            }
        }

        class SortByWhite implements Comparator<Postazione> {
            public int compare(Postazione p1, Postazione p2) {
                if (p1.getTotWhite() < p2.getTotWhite()) return 1;
                if (p1.getTotWhite() > p2.getTotWhite()) return -1;
                return 0;
            }
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PostazioniViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // each data item is just a string in this case
        public TextView mPaName;
        public TextView mCross;
        public TextView mPostazione;
        public TextView mTotMezzi;
        public TextView mTotMissions;
        public TextView mAvgWhite;
        public TextView mAvgYellow;
        public TextView mAvgGreen;
        public TextView mAvgRed;

        onRecyclerViewClickListener mListener;
        private String mPostazioneCode;

        public PostazioniViewHolder(View v) {
            this(v, null);
        }

        public PostazioniViewHolder(View v, onRecyclerViewClickListener listener) {
            super(v);
            mPaName = (TextView) v.findViewById(R.id.pa_name);
//            mPostazione = (TextView) v.findViewById(R.id.postazione);
            mCross = (TextView) v.findViewById(R.id.cross_icon);
            mTotMezzi = (TextView) v.findViewById(R.id.tot_ambulance);
            mTotMissions = (TextView) v.findViewById(R.id.tot_missions);
            mAvgWhite = (TextView) v.findViewById(R.id.tot_white);
            mAvgGreen = (TextView) v.findViewById(R.id.tot_green);
            mAvgYellow = (TextView) v.findViewById(R.id.tot_yellow);
            mAvgRed = (TextView) v.findViewById(R.id.tot_red);
            v.setOnClickListener(this);

            if (listener != null) {
                mListener = listener;
            }
//            mPaName.setOnClickListener(this);
//            mCross.setOnClickListener(this);
//            mPostazione.setOnClickListener(this);

        }

        public String getPostazioneCode() {
            return mPostazioneCode;
        }

        public void setPostazioneCode(String postazioneCode) {
            this.mPostazioneCode = postazioneCode;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }

        public void setOnRecyclerViewClickListener(onRecyclerViewClickListener listener) {
            mListener = listener;
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

        public HashMap<Integer, PlaceholderFragment> mPageReferenceMap;


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            mPageReferenceMap = new HashMap<>();
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            PlaceholderFragment myFragment = PlaceholderFragment.newInstance(position);
            mPageReferenceMap.put(position, myFragment);
            return myFragment;//PlaceholderFragment.newInstance(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        public PlaceholderFragment getFragment(int key) {
            return mPageReferenceMap.get(key);
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
