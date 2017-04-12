package com.sed.federico.prontosoccorsoligura;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sed.federico.prontosoccorsoligura.Mission.Mission;
import com.sed.federico.prontosoccorsoligura.Mission.MissionAdapter;
import com.sed.federico.prontosoccorsoligura.Mission.MissionListCustom;
import com.sed.federico.prontosoccorsoligura.Mission.MissionLoader;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MissionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionFragment extends Fragment
        /*implements LoaderManager.LoaderCallbacks<MissionListCustom>*/ {

    public static final String MISSION_REQUEST_BASE_URL =
            "http://datipsge.azurewebsites.net/api/hospitalization/";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CENTRALE = "centrale";
    private static final String ARG_PARAM2 = "param2";

    private static final int HOSPITAL_LOADER_ID = 1;
    private static final String EXTRA_MISSION_POSITION = "position";
    private static MissionListCustom mMissions;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String mCentrale;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    /**
     * Adapter for the list of earthquakes
     */

    private MissionAdapter mAdapter;
    private String mActualURL;
    private String mCentraleName;

    public MissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param centraleName Parameter 1.
     * @return A new instance of fragment MissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MissionFragment newInstance(String centraleName) {
        MissionFragment fragment = new MissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CENTRALE, centraleName);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCentraleName = getArguments().getString(ARG_CENTRALE);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        // Find a reference to the {@link ListView} in the layout
        final ListView missionListView = (ListView) container.findViewById(R.id.list);

//        mCentraleName = bundle.getString(MainActivity.EXTRA_HOSPITAL_NAME);

        mActualURL = MISSION_REQUEST_BASE_URL + "/" + mCentraleName;
//        this.setTitle(mCentraleName);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new MissionAdapter(getContext(), new MissionListCustom());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        missionListView.setAdapter(mAdapter);

        TextView text = (TextView) container.findViewById(R.id.textView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();


            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
//            loaderManager.initLoader(HOSPITAL_LOADER_ID, null, MissionFragment.this);
//            loaderManager.initLoader(HOSPITAL_FORCE_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = container.findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
        missionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                boolean isSelected = (boolean) view.getTag();

                ListView lv = missionListView;
                int childCount = lv.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    View v = lv.getChildAt(i);
                    setVisibility(v, false);
                }

                setVisibility(view, !isSelected);

            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) container.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshView(container);
            }
        });

        return rootView;
    }

    private void refreshView(ViewGroup container) {
        View loadingIndicator = container.findViewById(R.id.loading_indicator);
        TextView text = (TextView) container.findViewById(R.id.text);
        text.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);

        // Restart the loader to requery the USGS as the query settings have been updated
//        getLoaderManager().restartLoader(HOSPITAL_LOADER_ID, null, this);

    }

    private void setVisibility(View view, boolean isSelected) {
        TextView pickupLocation = (TextView) view.findViewById(R.id.pickup_location);
        TextView charlieCode = (TextView) view.findViewById(R.id.charlie_code);
        TextView indiaCode = (TextView) view.findViewById(R.id.india_code);
        TextView hospital = (TextView) view.findViewById(R.id.hospital);

        int visibility = !isSelected ? View.GONE : View.VISIBLE;
        pickupLocation.setVisibility(visibility);
        charlieCode.setVisibility(visibility);
        indiaCode.setVisibility(visibility);
        hospital.setVisibility(visibility);
        view.setTag(isSelected);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

/*
    @Override
    public Loader<MissionListCustom> onCreateLoader(int i, Bundle bundle) {

        String url = mActualURL;
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        return new MissionLoader(getActivity(), uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader<MissionListCustom> loader, MissionListCustom hospitals) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = getActivity().findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        TextView text = (TextView) getActivity().findViewById(R.id.text);

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

//        if (hospitals != null && !hospitals.isEmpty()) {
//            Collections.sort(hospitals, new Comparator<Mission>() {
//                @Override
//                public int compare(Mission o1, Mission o2) {
//                    return o1.getmPostazione().compareToIgnoreCase(o2.getmPostazione());
//                }
//
//            });
//        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<MissionListCustom> loader) {
        // Loader reset, so we can clear out our existing data.
//        mAdapter.clear();
    }
*/


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
