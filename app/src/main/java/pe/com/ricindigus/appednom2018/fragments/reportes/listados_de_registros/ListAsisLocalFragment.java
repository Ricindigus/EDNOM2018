package pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros;


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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Calendar;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.adapters.AsistenciaLocalAdapter;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaLocal;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAsisLocalFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    ArrayList<AsistenciaLocal> registroAsistenciaLocals;
    ArrayList<AsistenciaLocal> datosNoEnviados;
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
        final AsistenciaLocalAdapter asistenciaLocalAdapter = new AsistenciaLocalAdapter(registroAsistenciaLocals,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(asistenciaLocalAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                datosNoEnviados = new ArrayList<>();
                data = new Data(context);
                data.open();
                datosNoEnviados = data.getAllAsistenciaLocalSinEnviar(nroLocal);
                data.close();
                if(datosNoEnviados.size() > 0){
                    final int total = datosNoEnviados.size();
                    int i = 0;
                    for (final AsistenciaLocal registroAsistenciaLocal : datosNoEnviados){
                        final int j = i++;
                        final String c = registroAsistenciaLocal.getDni();
                        FirebaseFirestore.getInstance().collection("asistencia_local").document(registroAsistenciaLocal.getDni())
                                .set(registroAsistenciaLocal.toMap())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Data data = new Data(context);
                                        data.open();
                                        data.actualizarAsistenciaLocalSubido(c);
                                        data.close();
                                        if (j == total) Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("FIRESTORE", "Error writing document", e);
                                    }
                                });
//                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
//                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(getResources().getString(R.string.nombre_coleccion_asistencia)).document(registroAsistenciaLocal.getDni());
//                        batch.update(documentReference, "local_dia", registroAsistenciaLocal.getLocal_dia());
//                        batch.update(documentReference, "local_mes", registroAsistenciaLocal.getLocal_mes());
//                        batch.update(documentReference, "local_anio", registroAsistenciaLocal.getLocal_anio());
//                        batch.update(documentReference, "local_hora", registroAsistenciaLocal.getLocal_hora());
//                        batch.update(documentReference, "local_minuto", registroAsistenciaLocal.getLocal_minuto());
//                        final String c = registroAsistenciaLocal.getDni();
//                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                data = new Data(context);
//                                data.open();
//                                ContentValues contentValues = new ContentValues();
//                                contentValues.put(SQLConstantes.asistencia_local_subido_local,1);
//                                data.actualizarAsistenciaLocal(c,contentValues);
//                                data.close();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                }else{
                    Toast.makeText(context, "No hay registros nuevos para subir", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void cargaData(){
        registroAsistenciaLocals = new ArrayList<AsistenciaLocal>();
        Data data = new Data(context);
        data.open();
        registroAsistenciaLocals = data.getAllAsistenciaLocal(nroLocal);
        txtNumero.setText("Total registros: " + registroAsistenciaLocals.size());
        data.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
