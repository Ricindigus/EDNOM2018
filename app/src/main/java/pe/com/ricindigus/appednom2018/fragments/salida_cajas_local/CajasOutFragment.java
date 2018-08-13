package pe.com.ricindigus.appednom2018.fragments.salida_cajas_local;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.ricindigus.appednom2018.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CajasOutFragment extends Fragment {


    public CajasOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cajas_out, container, false);
    }

}
