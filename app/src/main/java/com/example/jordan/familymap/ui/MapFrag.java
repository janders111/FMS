package com.example.jordan.familymap.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jordan.familymap.R;
import com.example.jordan.familymap.model.MainModel;
import com.example.jordan.familymap.model.SettingsManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import model.Event;
import model.Person;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.fromBitmap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFrag#newInstance} factory method to
 * create an instance of this fragment.
 *
 * implements OnMapReadyCallBack
 */
public class MapFrag extends android.support.v4.app.Fragment implements OnMapReadyCallback{
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    LinearLayout mEventDetails;
    private Event currentEvent = null;
    private Person currentPerson = null;
    private String focusEventID = null;

    // / TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MapFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFrag newInstance(String param1, String param2) {
        MapFrag fragment = new MapFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = this.getArguments();
        if (savedInstanceState != null) {
            focusEventID = savedInstanceState.getString("eventID");
        }
        if(getActivity() instanceof MainActivity) { //only show the menu if this is a main activity
            setHasOptionsMenu(true);
        }
        else {
            setHasOptionsMenu(false);
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        mEventDetails = (LinearLayout) mView.findViewById(R.id.eventInfo);
        mEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPerson != null){
                    Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                    intent.putExtra("currentPerson", currentPerson.getPersonID());
                    startActivity(intent);
                }
            }
        });
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map);
        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        if(mGoogleMap != null){ //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            mGoogleMap.clear();
            onMapReady(mGoogleMap);//fixme I added this to try to refresh map
            // add markers from database to the map
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize((getContext()));
        mGoogleMap = googleMap;
        populateMap(googleMap);
    }

    public void populateMap(GoogleMap googleMap) {
        SettingsManager.getCurrentMapType();
        googleMap.setMapType(SettingsManager.getCurrentMapType());
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker m) {
                Event e = MainModel.getEventIDToEvent().get(m.getTag());
                currentEvent = e;
                showEventDetails(e);
                return false;// If this is false, then the default behavior will occur in addition to your custom behavior.
            }
        });

        for(Event e : MainModel.getEvents()) {
            Person p = MainModel.getPersonIDtoPerson().get(e.getPersonID());
            float hue = MainModel.getEventTypeColors().get(e.getEventType()).getHue();
            Marker marker = googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(e.getLatitude(), e.getLongitude()))
                            .title(e.getEventType())
                            .snippet(p.getFirstName() + " " + p.getLastName())
                            .icon(BitmapDescriptorFactory.defaultMarker(hue))
            );
            marker.setTag(e.getEventID());
        }

        if(focusEventID != null) {
            Event focusEvent = MainModel.getEventIDToEvent().get(focusEventID);
            Person p = MainModel.getPersonIDtoPerson().get(focusEvent.getPersonID());
            CameraPosition c = CameraPosition.builder().target(new LatLng(focusEvent.getLatitude(), focusEvent.getLongitude())).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(c));
            LineMaker.setLines(p, googleMap);
            showEventDetails(focusEvent);
        }
    }

    private void showEventDetails(Event e) {
        currentPerson = MainModel.getPersonIDtoPerson().get(e.getPersonID());
        TextView topTextView = (TextView) getView().findViewById(R.id.topText);
        topTextView.setText(currentPerson.getFirstName() + " " + currentPerson.getLastName());

        TextView bottomTextView = (TextView) getView().findViewById(R.id.bottomText);
        bottomTextView.setText(e.getEventType() + ": " + e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")");

        //set male female icon
        Drawable genderIcon;
        if(currentPerson.getGender().equals("m")) {
            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                    .colorRes(R.color.colorMale).sizeDp(40);
        }
        else {
            genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female)
                    .colorRes(R.color.colorFemale).sizeDp(40);
        }
        ImageView genderIconView = (ImageView) getView().findViewById(R.id.gender_image_view);
        genderIconView.setImageDrawable(genderIcon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.search:
                intent = new Intent(getActivity(), SearchActivity.class);
                //intent.putExtra("currentPerson", currentPerson.getPersonID());
                startActivity(intent);
                return true;
            case R.id.filters:
                intent = new Intent(getActivity(), FiltersActivity.class);
                //intent.putExtra("currentPerson", currentPerson.getPersonID());
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                //intent.putExtra("currentPerson", currentPerson.getPersonID());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
