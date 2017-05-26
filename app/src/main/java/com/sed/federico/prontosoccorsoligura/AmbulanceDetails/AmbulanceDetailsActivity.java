package com.sed.federico.prontosoccorsoligura.AmbulanceDetails;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import com.sed.federico.prontosoccorsoligura.Mission.Mission;
import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.Postazione;
import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.PostazioniActivity;
import com.sed.federico.prontosoccorsoligura.QueryUtils;
import com.sed.federico.prontosoccorsoligura.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AmbulanceDetailsActivity extends AppCompatActivity {
    private static final String AMBULANCE_URL = "http://datipsge.azurewebsites.net/api/centrali/mezzi/";
    private static final String AMBULANCE_URL_VERSION = "v1";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;
    private String ambulanceCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_mezzi);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        if (getIntent() != null) {
            // Activity was started and got an Intent with data
            Intent intent = getIntent();
            this.setTitle(intent.getStringExtra(PostazioniActivity.PA_NAME));
            ambulanceCode = intent.getStringExtra(PostazioniActivity.PA_CODE);

        } else if (savedInstanceState != null) {
            // No intent is available, try getting data from savedInstance
            this.setTitle(savedInstanceState.getString(PostazioniActivity.PA_NAME));
            ambulanceCode = savedInstanceState.getString(PostazioniActivity.PA_CODE);
        }


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        String url = getCompleteUrl();
        new AmbulanceAsyncDownloader(this, mRecyclerView).execute(url);

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putString(PostazioniActivity.PA_CODE, ambulanceCode);
            savedInstanceState.putString(PostazioniActivity.PA_NAME, this.getTitle().toString());

        }
        super.onSaveInstanceState(savedInstanceState);
    }

    private String getCompleteUrl(String ambulanceCode) {
        return AMBULANCE_URL + AMBULANCE_URL_VERSION + "/" + ambulanceCode;
    }

    private String getCompleteUrl() {
        return getCompleteUrl(ambulanceCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ambulance_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MezziAdapter adapter = (MezziAdapter) mRecyclerView.getAdapter();

        switch (item.getItemId()) {
            case R.id.sort_by_white:
            case R.id.sort_by_green:
            case R.id.sort_by_yellow:
            case R.id.sort_by_red:
            case R.id.sort_by_ambulance:
            case R.id.sort_by_totals:
                adapter.sortList(item.getItemId());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class MezziAdapter extends RecyclerView.Adapter<MezziViewHolder> {
        SparseArray<Comparator> comparator = new SparseArray<>();
        private AmbulanceDetailsListCustom mPostazioneDetails;
        private Context mContext;

        {
            comparator.put(R.id.sort_by_white, new ComparatorByWhite());
            comparator.put(R.id.sort_by_green, new ComparatorByGreen());
            comparator.put(R.id.sort_by_yellow, new ComparatorByYellow());
            comparator.put(R.id.sort_by_red, new ComparatorByRed());
            comparator.put(R.id.sort_by_missions, new ComparatorByMissions());
            comparator.put(R.id.sort_by_ambulance, new ComparatorByAmbulanceNo());
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MezziAdapter(Context context, AmbulanceDetailsListCustom centrali) {
//            super();
            mPostazioneDetails = centrali;
            mContext = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public AmbulanceDetailsActivity.MezziViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ambulance_element_compact, parent, false);

            AmbulanceDetailsActivity.MezziViewHolder vh =
                    new AmbulanceDetailsActivity.MezziViewHolder(v);
//            MezziViewHolder vh = new MezziViewHolder(v, new MezziViewHolder.onRecyclerViewClickListener() {
//                @Override
//                public void onClick(View caller) {
//                    Toast toast = Toast.makeText(mContext,
//                            mContext.getString(R.string.statistics_to_be_implemented),
//                            Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//
//
//                }
//            });
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MezziViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final AmbulanceDetail ambulanza = mPostazioneDetails.get(position);

            holder.mTotMissions.setText(String.valueOf(ambulanza.getTotMissions()));
            holder.mAmbulanceNo.setText(ambulanza.getCodeAmbulance());
            holder.mTotWhite.setText(String.valueOf(ambulanza.getTotWhite()));
            holder.mTotGreen.setText(String.valueOf(ambulanza.getTotGreen()));
            holder.mTotYellow.setText(String.valueOf(ambulanza.getTotYellow()));
            holder.mTotRed.setText(String.valueOf(ambulanza.getTotRed()));
            holder.mTotDays.setText(String.format(mContext.getString(R.string.worked_real_days),
                    ambulanza.getTotWorkedDays(), ambulanza.getTotDays()));


            holder.setOnRecyclerViewClickListener(new MezziViewHolder.onRecyclerViewClickListener() {
                @Override
                public void onClick(View caller) {

                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mPostazioneDetails.size();
        }

        public void sortList(int menuId) {
            Collections.sort(mPostazioneDetails, comparator.get(menuId));
            notifyDataSetChanged();
        }

        class ComparatorByWhite implements Comparator<AmbulanceDetail> {
            public int compare(AmbulanceDetail p1, AmbulanceDetail p2) {
                if (p1.getTotWhite() < p2.getTotWhite()) return 1;
                if (p1.getTotWhite() > p2.getTotWhite()) return -1;
                return 0;
            }
        }

        class ComparatorByGreen implements Comparator<AmbulanceDetail> {
            public int compare(AmbulanceDetail p1, AmbulanceDetail p2) {
                if (p1.getTotGreen() < p2.getTotGreen()) return 1;
                if (p1.getTotGreen() > p2.getTotGreen()) return -1;
                return 0;
            }
        }

        class ComparatorByYellow implements Comparator<AmbulanceDetail> {
            public int compare(AmbulanceDetail p1, AmbulanceDetail p2) {
                if (p1.getTotYellow() < p2.getTotYellow()) return 1;
                if (p1.getTotYellow() > p2.getTotYellow()) return -1;
                return 0;
            }
        }

        class ComparatorByRed implements Comparator<AmbulanceDetail> {
            public int compare(AmbulanceDetail p1, AmbulanceDetail p2) {
                if (p1.getTotRed() < p2.getTotRed()) return 1;
                if (p1.getTotRed() > p2.getTotRed()) return -1;
                return 0;
            }
        }

        class ComparatorByMissions implements Comparator<AmbulanceDetail> {
            public int compare(AmbulanceDetail p1, AmbulanceDetail p2) {
                if (p1.getTotMissions() < p2.getTotMissions()) return 1;
                if (p1.getTotMissions() > p2.getTotMissions()) return -1;
                return 0;
            }
        }

        class ComparatorByAmbulanceNo implements Comparator<AmbulanceDetail> {
            public int compare(AmbulanceDetail p1, AmbulanceDetail p2) {
                return p1.getCodeAmbulance().compareToIgnoreCase(p2.getCodeAmbulance());
            }
        }

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MezziViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // each data item is just a string in this case
        private TextView mTotMissions;
        private TextView mTotWhite;
        private TextView mTotYellow;
        private TextView mTotGreen;
        private TextView mTotRed;
        private TextView mFromDate;
        private TextView mToDate;
        private MezziViewHolder.onRecyclerViewClickListener mListener;
        private String mPostazioneCode;
        private TextView mAmbulanceNo;
        private TextView mTotDays;

        public MezziViewHolder(View v) {
            this(v, null);
        }

        public MezziViewHolder(View v, MezziViewHolder.onRecyclerViewClickListener listener) {
            super(v);
            mTotMissions = (TextView) v.findViewById(R.id.tot_missions);
            mTotWhite = (TextView) v.findViewById(R.id.tot_white);
            mTotGreen = (TextView) v.findViewById(R.id.tot_green);
            mTotYellow = (TextView) v.findViewById(R.id.tot_yellow);
            mTotRed = (TextView) v.findViewById(R.id.tot_red);
            mAmbulanceNo = (TextView) v.findViewById(R.id.ambulance_no);
            mTotDays = (TextView) v.findViewById(R.id.tot_days);
            v.setOnClickListener(this);

            if (listener != null) {
                mListener = listener;
            }
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

        public void setOnRecyclerViewClickListener(MezziViewHolder.onRecyclerViewClickListener listener) {
            mListener = listener;
        }

        public interface onRecyclerViewClickListener {
            void onClick(View caller);
        }

    }

    public class AmbulanceAsyncDownloader extends AsyncTask<String, Void, AmbulanceDetailsListCustom> {
        AmbulanceDetailsActivity mContext;
        RecyclerView mRecyclerViewAsync;


        public AmbulanceAsyncDownloader(AmbulanceDetailsActivity context, RecyclerView recyclerView) {
            mContext = context;
            mRecyclerViewAsync = recyclerView;
        }

        @Override
        protected AmbulanceDetailsListCustom doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            AmbulanceDetailsListCustom mezzi;

            String url = params[0];
            mezzi = QueryUtils.fetchMezzi(url);
            return mezzi;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(AmbulanceDetailsListCustom ambulances) {
            super.onPostExecute(ambulances);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerViewAsync.setAdapter(new MezziAdapter(mContext, ambulances));
//            if (mProgressDialog.isShowing())
//                mProgressDialog.dismiss();

        }

        class CentraleComparator implements Comparator<Postazione> {
            public int compare(Postazione p1, Postazione p2) {
                return p1.getName().compareToIgnoreCase(p2.getName());
            }
        }
    }
}

