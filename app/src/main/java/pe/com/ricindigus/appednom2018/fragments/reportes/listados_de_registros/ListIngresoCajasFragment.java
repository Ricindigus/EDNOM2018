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
import android.util.Log;
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
import pe.com.ricindigus.appednom2018.adapters.CajaIngresoAdapter;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaLocal;
import pe.com.ricindigus.appednom2018.modelo.CajaIn;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListIngresoCajasFragment extends Fragment {


    RecyclerView recyclerView;
    Context context;
    ArrayList<CajaIn> cajaIns;
    ArrayList<CajaIn> datosNoEnviados;
    int nroLocal;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    boolean b = false;
    CajaIngresoAdapter cajaIngresoAdapter;

    public ListIngresoCajasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListIngresoCajasFragment(Context context, int nroLocal) {
        this.context = context;
        this.nroLocal = nroLocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_ingreso_cajas, container, false);
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
        cajaIngresoAdapter = new CajaIngresoAdapter(cajaIns,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cajaIngresoAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                datosNoEnviados = new ArrayList<>();
                data = new Data(context);
                data.open();
                datosNoEnviados = data.getAllCajasIngresadasSinEnviar(nroLocal);
                data.close();
                if(datosNoEnviados.size() > 0){
                    final int total = datosNoEnviados.size();
                    int i = 0;
                    for (final CajaIn cajaIn : datosNoEnviados){
                        i++;
                        final int j = i;
                        final String c = cajaIn.getCod_barra_caja();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("cajas").document(cajaIn.getCod_barra_caja());
                        batch.update(documentReference, "check_reg_ingreso", 1);
                        batch.update(documentReference, "fech_trans_ingreso", FieldValue.serverTimestamp());
                        batch.update(documentReference, "fech_reg_ingreso", new Timestamp(new Date(cajaIn.getAnio()-1900,cajaIn.getMes()-1,cajaIn.getDia(),cajaIn.getHora(),cajaIn.getMin(),cajaIn.getSeg())));
                        final String codigoBarra = cajaIn.getCod_barra_caja();
                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarCajaInSubido(codigoBarra);
                                data.close();
                                if (j == total) {
                                    Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                    cargaData();
                                    cajaIngresoAdapter = new CajaIngresoAdapter(cajaIns,context);
                                    recyclerView.setAdapter(cajaIngresoAdapter);
                                }
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
        cajaIns = new ArrayList<CajaIn>();
        Data data = new Data(context);
        data.open();
        cajaIns = data.getAllCajaIn(nroLocal);
        txtNumero.setText("Total registros: " + cajaIns.size());
        data.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
