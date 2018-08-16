package pe.com.ricindigus.appednom2018.fragments.reportes;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.com.ricindigus.appednom2018.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAsisAulaFragment extends Fragment {
    Context context;
    int nroLocal;

    public ListAsisAulaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListAsisAulaFragment(Context context, int nroLocal) {
        this.context = context;
        this.nroLocal = nroLocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_asis_aula, container, false);
    }

}
