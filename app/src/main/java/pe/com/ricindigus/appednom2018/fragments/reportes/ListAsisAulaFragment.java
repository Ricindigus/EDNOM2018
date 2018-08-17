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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.adapters.AsistenciaAulaAdapter;
import pe.com.ricindigus.appednom2018.adapters.AsistenciaLocalAdapter;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaAula;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaLocal;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAsisAulaFragment extends Fragment {
    Context context;
    int nroLocal;
    Spinner spAulas;
    RecyclerView recyclerView;
    ArrayList<AsistenciaAula> asistenciaAulas;
    ArrayList<AsistenciaAula> datosNoEnviados;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    AsistenciaAulaAdapter asistenciaAulaAdapter;
    boolean b = false;

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
        View rootView = inflater.inflate(R.layout.fragment_list_asis_aula, container, false);
        spAulas = (Spinner) rootView.findViewById(R.id.asistencia_aula_spAula);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listado_recycler);
        fabUpLoad = (FloatingActionButton) rootView.findViewById(R.id.listado_btnUpload);
        txtNumero = (TextView) rootView.findViewById(R.id.listado_txtNumero);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data d =  new Data(context);
        d.open();
        ArrayList<String> aulas =  d.getArrayAulas(nroLocal);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aulas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAulas.setAdapter(dataAdapter);
        d.close();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        asistenciaAulaAdapter = new AsistenciaAulaAdapter(asistenciaAulas,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(asistenciaAulaAdapter);


        spAulas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargaData();
                asistenciaAulaAdapter = new AsistenciaAulaAdapter(asistenciaAulas,context);
                recyclerView.setAdapter(asistenciaAulaAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                datosNoEnviados = new ArrayList<>();
                data = new Data(context);
                data.open();
                String aula = spAulas.getSelectedItem().toString();
                int nroAula = 0;
                nroAula = data.getNumeroAula(aula,nroLocal);
                datosNoEnviados = data.getAllAsistenciaAulaSinEnviar(nroLocal,nroAula);
                data.close();
                if(datosNoEnviados.size() > 0){
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Toast.makeText(context, "Subiendo...", Toast.LENGTH_SHORT).show();
                    for (final AsistenciaAula asistenciaAula : datosNoEnviados){
                        asistenciaAula.setSubido_aula(1);
                        final String c = asistenciaAula.getDni();
                        db.collection(getResources().getString(R.string.nombre_coleccion_asistencia)).document(asistenciaAula.getDni()).set(asistenciaAula)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("FIRESTORE", "DocumentSnapshot successfully written!");
                                        if(!b){
                                            Toast.makeText(context, datosNoEnviados.size() +" registros subidos", Toast.LENGTH_SHORT).show();
                                            b =true;
                                        }
                                        data = new Data(context);
                                        data.open();
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put(SQLConstantes.asistencia_local_subido_local,1);
                                        data.actualizarAsistenciaLocal(c,contentValues);
                                        cargaData();
                                        asistenciaAulaAdapter.notifyDataSetChanged();
                                        data.close();
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
        asistenciaAulas = new ArrayList<AsistenciaAula>();
        Data d = new Data(context);
        d.open();
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        nroAula = d.getNumeroAula(aula,nroLocal);
        asistenciaAulas = d.getAllAsistenciaAula(nroLocal,nroAula);
        txtNumero.setText("Total registros: " + asistenciaAulas.size());
        d.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

//    public void actualizaLista(int nAula){
//        cargaData();
//        final AsistenciaAulaAdapter asistenciaAulaAdapter = new AsistenciaAulaAdapter(asistenciaAulas,context);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(asistenciaAulaAdapter);
//    }
}
