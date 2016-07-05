package project.mosis.volunteerneeded.bluetoothscanner;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ServiceConfigurationError;
import java.util.UUID;

import project.mosis.volunteerneeded.MainActivity;
import project.mosis.volunteerneeded.R;
import project.mosis.volunteerneeded.bluetoothscanner.Connecting.ConnectThread;
import project.mosis.volunteerneeded.bluetoothscanner.Connecting.ManageConnectThread;
import project.mosis.volunteerneeded.bluetoothscanner.Connecting.ServerConnectThread;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListenerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListenerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListenerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final UUID service_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private ServerConnectThread server;
    private BluetoothAdapter BTAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ListenerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListenerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListenerFragment newInstance(String param1, String param2) {
        ListenerFragment fragment = new ListenerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        BTAdapter = BluetoothAdapter.getDefaultAdapter();

        server = new ServerConnectThread();


        try {
            startListening();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void startListening() throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                server.acceptConnect(BTAdapter,service_UUID);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_listener, container, false);

        Button stop_btn = (Button) view.findViewById(R.id.stop_listening_btn);

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
