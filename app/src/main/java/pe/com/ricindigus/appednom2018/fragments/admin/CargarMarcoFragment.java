package pe.com.ricindigus.appednom2018.fragments.admin;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.activities.LoginActivity;
import pe.com.ricindigus.appednom2018.activities.SplashActivity;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.util.FileChooser;

/**
 * A simple {@link Fragment} subclass.
 */
public class CargarMarcoFragment extends Fragment {

    Button btnCargarMarco;
    Context context;

    public CargarMarcoFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CargarMarcoFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_cargar_marco, container, false);
        btnCargarMarco = rootView.findViewById(R.id.btnCargar);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCargarMarco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarMarco();
            }
        });
    }

    public void cargarMarco(){
        FileChooser fileChooser = new FileChooser(getActivity());
        fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                String filename = file.getAbsolutePath();
                Log.d("File", filename);
                Toast.makeText(context, "Cargando..." + filename, Toast.LENGTH_SHORT).show();
                try {
                    Data data = new Data(context,filename);
                    data.open();
                    data.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fileChooser.showDialog();
    }

}
