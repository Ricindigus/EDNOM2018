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
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAsisLocalFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    String usuario;
    ArrayList<AsistenciaReg> asistenciaLocals;
    ArrayList<AsistenciaReg> noEnviados;
    int nroLocal;
    Data data;
    FloatingActionButton fabUpLoad;
    AsistenciaLocalAdapter asistenciaLocalAdapter;
    boolean b = false;

    TextView txtTotal;
    TextView txtSinRegistro;
    TextView txtRegistrados;
    TextView txtTransferidos;


    public ListAsisLocalFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListAsisLocalFragment(Context context, int nroLocal, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_asis_local, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listado_recycler);
        fabUpLoad = (FloatingActionButton) rootView.findViewById(R.id.listado_btnUpload);
        txtTotal = (TextView) rootView.findViewById(R.id.lista_txtTotales);
        txtSinRegistro = (TextView) rootView.findViewById(R.id.lista_txtSinRegistro);
        txtRegistrados = (TextView) rootView.findViewById(R.id.lista_txtRegistrados);
        txtTransferidos = (TextView) rootView.findViewById(R.id.lista_txtTransferidos);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        asistenciaLocalAdapter = new AsistenciaLocalAdapter(asistenciaLocals,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(asistenciaLocalAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                noEnviados = new ArrayList<>();
                data = new Data(context);
                data.open();
                noEnviados = data.getAsistenciasLocalSinEnviar(nroLocal);
                data.close();
                if(noEnviados.size() > 0){
                    final int total = noEnviados.size();
                    int i = 0;
                    for (final AsistenciaReg asistenciaLocal : noEnviados){
                        i++;
                        final int j = i;
                        final String c = asistenciaLocal.getDni();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("asistencia").document(asistenciaLocal.getDni());
                        batch.update(documentReference, "check_registro", 1);
                        batch.update(documentReference, "fecha_transferencia", FieldValue.serverTimestamp());
                        batch.update(documentReference, "usuario_registro", usuario);
                        batch.update(documentReference, "fecha_registro",
                                new Timestamp(new Date(asistenciaLocal.getAnio_local()-1900,asistenciaLocal.getMes_local()-1,asistenciaLocal.getDia_local(),
                                        asistenciaLocal.getHora_local(),asistenciaLocal.getMin_local(),asistenciaLocal.getSeg_local())));
                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarAsistenciaRegLocalSubido(c);
                                if (j == total) {
                                    Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                    cargaData();
                                    asistenciaLocalAdapter = new AsistenciaLocalAdapter(asistenciaLocals,context);
                                    recyclerView.setAdapter(asistenciaLocalAdapter);
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
        asistenciaLocals = new ArrayList<AsistenciaReg>();
        Data data = new Data(context);
        data.open();
        asistenciaLocals = data.getListadoAsistenciaLocal(nroLocal);
        txtTotal.setText("Total: " + data.getNumeroItemsAsistenciaReg());
//        txtSinRegistro.setText("Sin Registro: " + data.getNroAsistenciasLocalSinRegistro(nroLocal));
        txtRegistrados.setText("Leídos: " + data.getNroAsistenciasLocalLeidas(nroLocal));
        txtTransferidos.setText("Transferidos: " + data.getNroAsistenciasLocalTransferidos(nroLocal));
        data.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
