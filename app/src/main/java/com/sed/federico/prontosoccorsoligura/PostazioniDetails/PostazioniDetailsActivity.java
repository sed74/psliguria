package com.sed.federico.prontosoccorsoligura.PostazioniDetails;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.Postazione;
import com.sed.federico.prontosoccorsoligura.QueryUtils;
import com.sed.federico.prontosoccorsoligura.R;

import java.util.Comparator;

public class PostazioniDetailsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ViewFlipper vf = (ViewFlipper) findViewById(R.id.vf);
//        vf.setDisplayedChild(2);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class MezziAdapter extends RecyclerView.Adapter<MezziViewHolder> {
        private PostazioneDetailsListCustom mPostazioneDetails;
        private Context mContext;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MezziAdapter(Context context, PostazioneDetailsListCustom centrali) {
//            super();
            mPostazioneDetails = centrali;
            mContext = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public PostazioniDetailsActivity.MezziViewHolder onCreateViewHolder(ViewGroup parent,
                                                                            int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.centrale_element, parent, false);

            PostazioniDetailsActivity.MezziViewHolder vh =
                    new PostazioniDetailsActivity.MezziViewHolder(v);

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
            final PostazioneDetail postazione = mPostazioneDetails.get(position);

//            String paName = postazione.getName();
//            holder.mPaName.setText(paName);
            holder.mTotMissions.setText(String.format(mContext.getString(R.string.avg_mission_per_day),
                    postazione.getTotMissions()));
//            holder.mTotMezzi.setText(String.format(mContext.getString(R.string.no_of_ambulances),
//                    postazione.getTotAmbulance()));
            holder.mTotWhite.setText(String.format(mContext.getString(R.string.avg_white_per_day),
                    postazione.getTotWhite()));
            holder.mTotGreen.setText(String.format(mContext.getString(R.string.avg_green_per_day),
                    postazione.getTotGreen()));
            holder.mTotYellow.setText(String.format(mContext.getString(R.string.avg_yellow_per_day),
                    postazione.getTotYellow()));
            holder.mTotRed.setText(String.format(mContext.getString(R.string.avg_red_per_day),
                    postazione.getTotRed()));
//            holder.mPostazione.setText(mContext.getString(R.string.statistics_coming_soon));

//            holder.mCross.setBackground(getDrawable(paName));
//            holder.mCross.setBackground(ContextCompat.getDrawable(mContext,
//                    postazione.getCrossImage()));
//            holder.setPostazioneCode(postazione.getCode());


            holder.setOnRecyclerViewClickListener(new MezziViewHolder.onRecyclerViewClickListener() {
                @Override
                public void onClick(View caller) {
                    Toast toast = Toast.makeText(mContext,
                            mContext.getString(R.string.statistics_to_be_implemented),
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

//                    FirebaseAnalytics firebaseAnalytics;
//                    firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "PostazioneDetail");
//                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, postazione.getName());
//                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mPostazioneDetails.size();
        }

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MezziViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        // each data item is just a string in this case
        public TextView mPaName;
        public TextView mCross;
        public TextView mPaCode;
        public TextView mTotMezzi;
        public TextView mTotMissions;
        public TextView mTotWhite;
        public TextView mTotYellow;
        public TextView mTotGreen;
        public TextView mTotRed;
        public TextView mFromDate;
        public TextView mToDate;
        public TextView mTotDays;
        public TextView mTotRealDays;
        MezziViewHolder.onRecyclerViewClickListener mListener;
        private String mPostazioneCode;

        public MezziViewHolder(View v) {
            this(v, null);
        }

        public MezziViewHolder(View v, MezziViewHolder.onRecyclerViewClickListener listener) {
            super(v);
//            mPaName = (TextView) v.findViewById(R.id.textView01);
//            mPostazione = (TextView) v.findViewById(R.id.postazione);
//            mCross = (TextView) v.findViewById(R.id.cross_icon);
//            mTotMezzi = (TextView) v.findViewById(R.id.tot);
            mTotMissions = (TextView) v.findViewById(R.id.tot_missions);
            mTotWhite = (TextView) v.findViewById(R.id.tot_white);
            mTotGreen = (TextView) v.findViewById(R.id.tot_green);
            mTotYellow = (TextView) v.findViewById(R.id.tot_yellow);
            mTotRed = (TextView) v.findViewById(R.id.tot_red);
//            mFromDate = (TextView) v.findViewById(R.id.from_date);
//            mToDate = (TextView) v.findViewById(R.id.to_date);
            mTotDays = (TextView) v.findViewById(R.id.worked_days);
            mTotRealDays = (TextView) v.findViewById(R.id.real_days);
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

        public void setOnRecyclerViewClickListener(MezziViewHolder.onRecyclerViewClickListener listener) {
            mListener = listener;
        }

        public interface onRecyclerViewClickListener {
            void onClick(View caller);
        }

    }

    public class MezziAsyncDownloader extends AsyncTask<String, Void, PostazioneDetailsListCustom> {
        PostazioniDetailsActivity mContext;
        RecyclerView mRecyclerViewAsync;
        String mCentrale;


        public MezziAsyncDownloader(PostazioniDetailsActivity context, RecyclerView recyclerView,
                                    String centrale) {
            mContext = context;
            mRecyclerViewAsync = recyclerView;
            mCentrale = centrale.replaceAll(" ", "");
        }

        @Override
        protected PostazioneDetailsListCustom doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

//            int currentSection = Integer.valueOf(params[1]) - 1;
            PostazioneDetailsListCustom mezzi;

//            mezzi = mContext.mCachePostazioni.get(currentSection);
//            if (mezzi == null) {
            String url = params[0] + mCentrale;
            mezzi = QueryUtils.fetchMezzi(url);
//                mContext.mCachePostazioni.put(currentSection, mezzi);
//            }
//            Collections.sort(mezzi, new PostazioniActivity.PlaceholderFragment.CentraliAsyncDownloader.CentraleComparator());
            return mezzi;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(PostazioneDetailsListCustom centrali) {
            super.onPostExecute(centrali);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerViewAsync.setAdapter(new MezziAdapter(mContext, centrali));
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

