package pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Date;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.adapters.AsistenciaLocalAdapter;
import pe.com.ricindigus.appednom2018.adapters.AsistenciaRaAdapter;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaRaReg;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.util.ActividadInterfaz;
import pe.com.ricindigus.appednom2018.util.TipoFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAsisRAFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    String usuario;
    ArrayList<AsistenciaRaReg> asistenciaLocals;
    ArrayList<AsistenciaRaReg> noEnviados;
    int nroLocal;
    Data data;
    ImageButton fabUpLoad;
    ImageButton fabSearch;

    AsistenciaRaAdapter asistenciaRaAdapter;
    boolean b = false;

    TextView txtRegistrados;
    TextView txtTransferidos;
    TextView txtSinRegistro;

    String nombreColeccion;

    public ListAsisRAFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListAsisRAFragment(Context context, int nroLocal, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_asis_ra, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listado_recycler);
        fabUpLoad = (ImageButton) rootView.findViewById(R.id.lista_btnUpload);
        fabSearch = (ImageButton) rootView.findViewById(R.id.lista_btnBuscar);

        txtRegistrados = (TextView) rootView.findViewById(R.id.lista_txtRegistrados);
        txtTransferidos = (TextView) rootView.findViewById(R.id.lista_txtTransferidos);
        txtSinRegistro = (TextView) rootView.findViewById(R.id.lista_txtSinRegistro);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        asistenciaRaAdapter = new AsistenciaRaAdapter(asistenciaLocals,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(asistenciaRaAdapter);

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActividadInterfaz actividadInterfaz = (ActividadInterfaz) getActivity();
                actividadInterfaz.irReporte(TipoFragment.REGISTRO_ASISTENCIA_RA);
            }
        });

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                noEnviados = new ArrayList<>();
                data = new Data(context);
                data.open();
                noEnviados = data.getAsistenciasRASinEnviar(nroLocal);
                data.close();
                if(noEnviados.size() > 0){
                    final int total = noEnviados.size();
                    int i = 0;
                    for (final AsistenciaRaReg asistenciaRaReg : noEnviados){
                        i++;
                        final int j = i;
                        final String c = asistenciaRaReg.getDni();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(nombreColeccion).document(asistenciaRaReg.getDni());
                        batch.update(documentReference, "check_registro", 1);
                        batch.update(documentReference, "fecha_transferencia", FieldValue.serverTimestamp());
                        batch.update(documentReference, "usuario_registro", usuario);
                        batch.update(documentReference, "fecha_registro",
                                new Timestamp(new Date(asistenciaRaReg.getAnio()-1900,asistenciaRaReg.getMes()-1,asistenciaRaReg.getDia(),
                                        asistenciaRaReg.getHora(),asistenciaRaReg.getMin(),asistenciaRaReg.getSeg())));
                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarAsistenciaRaRegSubido(c);
                                if (j == total) {
                                    Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                    cargaData();
                                    asistenciaRaAdapter = new AsistenciaRaAdapter(asistenciaLocals,context);
                                    recyclerView.setAdapter(asistenciaRaAdapter);
                                }
                                data.close();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
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
        asistenciaLocals = new ArrayList<AsistenciaRaReg>();
        Data data = new Data(context);
        data.open();
        nombreColeccion = data.getNombreColeccionAsistenciaRA();
        asistenciaLocals = data.getListadoAsistenciaRA(nroLocal);
        txtSinRegistro.setText("Sin Registro: " + data.getNroAsistenciasRaSinRegistro(nroLocal)+"/"+ asistenciaLocals.size());
        txtRegistrados.setText("Registrados: " + data.getNroAsistenciasRALeidas(nroLocal)+"/"+ asistenciaLocals.size());
        txtTransferidos.setText("Transferidos: " + data.getNroAsistenciasRATransferidos(nroLocal)+"/"+asistenciaLocals.size());
        data.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
