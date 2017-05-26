package com.sed.federico.prontosoccorsoligura.AmbulanceDetails;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.Postazione;
import com.sed.federico.prontosoccorsoligura.PubblicheAssistenze.PostazioniActivity;
import com.sed.federico.prontosoccorsoligura.QueryUtils;
import com.sed.federico.prontosoccorsoligura.R;

import java.util.Comparator;

public class AmbulanceDetailsActivity_Header extends AppCompatActivity {
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

        // specify an adapter (see also next example)
//        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

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
    protected void onStart() {
        super.onStart();
        String url = getCompleteUrl();
        new AmbulanceAsyncDownloader(this, mRecyclerView).execute(url);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class MezziAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int HEADER_VIEW = 1;
        private AmbulanceDetailsListCustom mPostazioneDetails;
        private Context mContext;

        // Provide a suitable constructor (depends on the kind of dataset)
        public MezziAdapter(Context context, AmbulanceDetailsListCustom centrali) {
//            super();
            mPostazioneDetails = centrali;
            mContext = context;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v;

            if (viewType == HEADER_VIEW) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ambulance_element_header, parent, false);

                HeaderViewHolder vh = new HeaderViewHolder(v);

                return vh;
            }

            // create a new view
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ambulance_element_compact, parent, false);

            MezziViewHolder vh =
                    new MezziViewHolder(v);

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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            try {
                if (holder instanceof MezziViewHolder) {
                    MezziViewHolder vh = (MezziViewHolder) holder;
                    final AmbulanceDetail ambulanza = mPostazioneDetails.get(position - 1);
                    vh.bindView(position, ambulanza);
                } else if (holder instanceof HeaderViewHolder) {
                    HeaderViewHolder vh = (HeaderViewHolder) holder;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            holder.mTotMissions.setText(String.valueOf(ambulanza.getTotMissions()));
//            holder.mAmbulanceNo.setText(ambulanza.getCodeAmbulance());
//            holder.mTotWhite.setText(String.valueOf(ambulanza.getTotWhite()));
//            holder.mTotGreen.setText(String.valueOf(ambulanza.getTotGreen()));
//            holder.mTotYellow.setText(String.valueOf(ambulanza.getTotYellow()));
//            holder.mTotRed.setText(String.valueOf(ambulanza.getTotRed()));
//            holder.mWorkedDays.setText(String.valueOf(ambulanza.getTotWorkedDays()));
//            holder.mRealDays.setText(String.valueOf(ambulanza.getTotDays()));
/*
            holder.mTotMissions.setText(String.valueOf(ambulanza.getTotMissions()));
            holder.mAmbulanceNo.setText(String.format(mContext.getString(R.string.ambulance_no),
                    ambulanza.getCodeAmbulance()));
            holder.mTotWhite.setText(String.format(mContext.getString(R.string.avg_white_per_day),
                    ambulanza.getTotWhite(), ambulanza.getAvgWhite()));
            holder.mTotGreen.setText(String.format(mContext.getString(R.string.avg_green_per_day),
                    ambulanza.getTotGreen(), ambulanza.getAvgGreen()));
            holder.mTotYellow.setText(String.format(mContext.getString(R.string.avg_yellow_per_day),
                    ambulanza.getTotYellow(), ambulanza.getAvgYellow()));
            holder.mTotRed.setText(String.format(mContext.getString(R.string.avg_red_per_day),
                    ambulanza.getTotRed(), ambulanza.getAvgRed()));
            holder.mWorkedDays.setText(String.valueOf(ambulanza.getTotWorkedDays()));
            holder.mRealDays.setText(String.valueOf(ambulanza.getTotDays()));
*/


//            holder.setOnRecyclerViewClickListener(new MezziViewHolder.onRecyclerViewClickListener() {
//                @Override
//                public void onClick(View caller) {
//                    Toast toast = Toast.makeText(mContext,
//                            mContext.getString(R.string.statistics_to_be_implemented),
//                            Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();

//                    FirebaseAnalytics firebaseAnalytics;
//                    firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
//                    Bundle bundle = new Bundle();
//                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "AmbulanceDetail");
//                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, ambulanza.getName());
//                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//                }
//            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mPostazioneDetails.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return HEADER_VIEW;
            }
            return super.getItemViewType(position);
        }
// Define a view holder for Footer view

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MezziViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener {

            // each data item is just a string in this case
            public TextView mTotMissions;
            public TextView mTotWhite;
            public TextView mTotYellow;
            public TextView mTotGreen;
            public TextView mTotRed;
            public TextView mFromDate;
            public TextView mToDate;
            MezziViewHolder.onRecyclerViewClickListener mListener;
            private String mPostazioneCode;
            private TextView mAmbulanceNo;
            private TextView mWorkedDays;
            private TextView mRealDays;

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
                mWorkedDays = (TextView) v.findViewById(R.id.worked_days);
                mRealDays = (TextView) v.findViewById(R.id.real_days);
                v.setOnClickListener(this);

                if (listener != null) {
                    mListener = listener;
                }
            }

            private void bindView(int position, AmbulanceDetail ambulanza) {
                mTotMissions.setText(String.valueOf(ambulanza.getTotMissions()));
                mAmbulanceNo.setText(ambulanza.getCodeAmbulance());
                mTotWhite.setText(String.valueOf(ambulanza.getTotWhite()));
                mTotGreen.setText(String.valueOf(ambulanza.getTotGreen()));
                mTotYellow.setText(String.valueOf(ambulanza.getTotYellow()));
                mTotRed.setText(String.valueOf(ambulanza.getTotRed()));
                mWorkedDays.setText(String.valueOf(ambulanza.getTotWorkedDays()));
                mRealDays.setText(String.valueOf(ambulanza.getTotDays()));
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

        public class HeaderViewHolder extends RecyclerView.ViewHolder {
            public HeaderViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Do whatever you want on clicking the item
                    }
                });
            }
        }
    }

    public class AmbulanceAsyncDownloader extends AsyncTask<String, Void, AmbulanceDetailsListCustom> {
        AmbulanceDetailsActivity_Header mContext;
        RecyclerView mRecyclerViewAsync;


        public AmbulanceAsyncDownloader(AmbulanceDetailsActivity_Header context, RecyclerView recyclerView) {
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

