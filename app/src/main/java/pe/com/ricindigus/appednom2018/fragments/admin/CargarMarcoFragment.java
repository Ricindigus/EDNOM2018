package pe.com.ricindigus.appednom2018.fragments.admin;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.activities.LoginActivity;
import pe.com.ricindigus.appednom2018.activities.MainActivity;
import pe.com.ricindigus.appednom2018.activities.ProgressActivity;
import pe.com.ricindigus.appednom2018.activities.SplashActivity;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.InventarioReg;
import pe.com.ricindigus.appednom2018.util.FileChooser;

/**
 * A simple {@link Fragment} subclass.
 */
public class CargarMarcoFragment extends Fragment {

    Button btnCargarMarco;
    Context context;
    ProgressBar progressBar;
    TextView txtMensaje;
    TextView txtArchivo;
    String filename = "";

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
        btnCargarMarco = (Button) rootView.findViewById(R.id.btnCargar);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progreso_admin);
        txtMensaje = (TextView) rootView.findViewById(R.id.mensaje_admin);
        txtArchivo = (TextView) rootView.findViewById(R.id.txtArchivo);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtArchivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarArchivo();
            }
        });

        btnCargarMarco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filename.equals("")) Toast.makeText(context, "debe seleccionar archivo", Toast.LENGTH_SHORT).show();
                else new MyAsyncTask().execute();
            }
        });

    }

    public void cargarArchivo(){
        FileChooser fileChooser = new FileChooser(getActivity());
        fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                filename = file.getAbsolutePath();
                if(filename.substring(filename.length()-7,filename.length()).toLowerCase().equals(".sqlite")){
                    txtArchivo.setText(filename);
                }else{
                    filename="";
                    Toast.makeText(context, "archivo de tipo incorrecto", Toast.LENGTH_SHORT).show();
                }

//                Log.d("File", filename);
//                Toast.makeText(context, "Cargando..." + filename, Toast.LENGTH_SHORT).show();
            }
        });
        fileChooser.showDialog();
    }

    public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            publishProgress(1);
            try {
                Data data = new Data(context,filename);
                data.open();
                data.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "LISTO, EL NUEVO MARCO SE CARGO";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0] == 1) txtMensaje.setText("CARGANDO MARCO...");
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtMensaje.setText(mensaje);
            filename = "";
            txtArchivo.setText("Seleccionar archivo");
            progressBar.setVisibility(View.GONE);
        }
    }
}
