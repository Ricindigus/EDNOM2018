package pe.com.ricindigus.appednom2018.fragments.reportes;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.adapters.AsistenciaLocalAdapter;
import pe.com.ricindigus.appednom2018.modelo.Asistencia;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAsisLocalFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    ArrayList<Asistencia> registroAsistencias;
    ArrayList<Asistencia> datosNoEnviados;
    int nroLocal;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    boolean b = false;

    public ListAsisLocalFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListAsisLocalFragment(Context context, int nroLocal) {
        this.context = context;
        this.nroLocal = nroLocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_asis_local, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listado_recycler);
        fabUpLoad = (FloatingActionButton) rootView.findViewById(R.id.listado_btnUpload);
        txtNumero = (TextView) rootView.findViewById(R.id.listado_txtNumero);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        final AsistenciaLocalAdapter asistenciaLocalAdapter = new AsistenciaLocalAdapter(registroAsistencias,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(asistenciaLocalAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                datosNoEnviados = new ArrayList<>();
                try {
                    data = new Data(context);
                    data.open();
                    Calendar calendario = Calendar.getInstance();
                    int yy = calendario.get(Calendar.YEAR);
                    int mm = calendario.get(Calendar.MONTH)+1;
                    int dd = calendario.get(Calendar.DAY_OF_MONTH);
                    datosNoEnviados = data.getAllAsistenciaLocalSinEnviar();
                    data.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(datosNoEnviados.size() > 0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Toast.makeText(context, "Subiendo...", Toast.LENGTH_SHORT).show();
                    for (final Asistencia registroAsistencia : datosNoEnviados){
                        registroAsistencia.setSubido_local(1);
                        final String c = registroAsistencia.getDni();
                        db.collection(getResources().getString(R.string.nombre_coleccion_asistencia)).document(registroAsistencia.getDni()).set(registroAsistencia)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("FIRESTORE", "DocumentSnapshot successfully written!");
                                        if(!b){
                                            Toast.makeText(context, datosNoEnviados.size() +" registros subidos", Toast.LENGTH_SHORT).show();
                                            b =true;
                                        }
                                        try {
                                            data = new Data(context);
                                            data.open();
                                            ContentValues contentValues = new ContentValues();
                                            contentValues.put(SQLConstantes.asistencia_subido_local,1);
                                            data.actualizarAsistencia(c,contentValues);
                                            cargaData();
                                            asistenciaLocalAdapter.notifyDataSetChanged();
                                            data.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("FIRESTORE", "Error writing document", e);
                                    }
                                });
                    }
                }else{
                    Toast.makeText(context, "No hay registros nuevos para subir", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void cargaData(){
        registroAsistencias = new ArrayList<Asistencia>();
        try {
            Data data = new Data(context);
            data.open();
            Calendar calendario = Calendar.getInstance();
            int yy = calendario.get(Calendar.YEAR);
            int mm = calendario.get(Calendar.MONTH)+1;
            int dd = calendario.get(Calendar.DAY_OF_MONTH);
            registroAsistencias = data.getAllAsistenciaLocal(nroLocal);
            txtNumero.setText("Total registros: " + registroAsistencias.size());
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
