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
import pe.com.ricindigus.appednom2018.adapters.CajasSalidaAdapter;
import pe.com.ricindigus.appednom2018.modelo.CajaIn;
import pe.com.ricindigus.appednom2018.modelo.CajaOut;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListSalidaCajasFragment extends Fragment {


    RecyclerView recyclerView;
    Context context;
    ArrayList<CajaOut> cajaOuts;
    ArrayList<CajaOut> completos;
    ArrayList<CajaOut> transferidos;

    int nroLocal;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    TextView txtCompletos;
    TextView txtTransferidos;
    boolean b = false;
    CajasSalidaAdapter cajasSalidaAdapter;

    public ListSalidaCajasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListSalidaCajasFragment(Context context, int nroLocal) {
        this.context = context;
        this.nroLocal = nroLocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_salida_cajas, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listado_recycler);
        fabUpLoad = (FloatingActionButton) rootView.findViewById(R.id.listado_btnUpload);
        txtNumero = (TextView) rootView.findViewById(R.id.listado_txtNumero);
        txtCompletos = (TextView) rootView.findViewById(R.id.listado_txtCompletos);
        txtTransferidos = (TextView) rootView.findViewById(R.id.listado_txtTransferidos);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        cajasSalidaAdapter = new CajasSalidaAdapter(cajaOuts,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cajasSalidaAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                completos = new ArrayList<>();
                data = new Data(context);
                data.open();
                completos = data.getAllCajasOutCompletos(nroLocal);
                data.close();
                if(completos.size() > 0){
                    final int total = completos.size();
                    int i = 0;
                    for (final CajaOut cajaOut : completos){
                        i++;
                        final int j = i;
                        Data d = new Data(context);
                        d.open();
                        CajaOut cajaOut20 = d.getCajaOut(getCodigo20(cajaOut.getCod_barra_caja()));
                        d.close();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        DocumentReference documentReference20 = FirebaseFirestore.getInstance().collection("cajas").document(cajaOut20.getCod_barra_caja());
                        DocumentReference documentReference1 = FirebaseFirestore.getInstance().collection("cajas").document(cajaOut.getCod_barra_caja());
                        batch.update(documentReference1, "check_reg_ingreso", 1);
                        batch.update(documentReference1, "fech_trans_ingreso", FieldValue.serverTimestamp());
                        batch.update(documentReference1, "fech_reg_ingreso", new Timestamp(new Date(cajaOut.getAnio()-1900,cajaOut.getMes()-1,cajaOut.getDia(),cajaOut.getHora(),cajaOut.getMin(),cajaOut.getSeg())));
                        batch.update(documentReference20, "check_reg_ingreso", 1);
                        batch.update(documentReference20, "fech_trans_ingreso", FieldValue.serverTimestamp());
                        batch.update(documentReference20, "fech_reg_ingreso", new Timestamp(new Date(cajaOut20.getAnio()-1900,cajaOut20.getMes()-1,cajaOut20.getDia(),cajaOut20.getHora(),cajaOut20.getMin(),cajaOut20.getSeg())));

                        final String codigoBarra = cajaOut.getCod_barra_caja();
                        final String codigoBarra20 = cajaOut20.getCod_barra_caja();

                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarCajaOutSubido(codigoBarra);
                                data.actualizarCajaOutSubido(codigoBarra20);
                                data.close();
                                if (j == total) {
                                    Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                    cargaData();
                                    cajasSalidaAdapter = new CajasSalidaAdapter(cajaOuts,context);
                                    recyclerView.setAdapter(cajasSalidaAdapter);
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

    public String getCodigo20(String cod2){
        String cod1 = cod2.substring(0,cod2.length()-2) + "20";
        return cod1;
    }

    public void cargaData(){

        cajaOuts = new ArrayList<CajaOut>();
        completos = new ArrayList<CajaOut>();
        transferidos = new ArrayList<CajaOut>();

        Data data = new Data(context);
        data.open();
        cajaOuts = data.getAllCajaOutListado(nroLocal);
        completos = data.getAllCajasOutCompletos(nroLocal);
        transferidos = data.getAllCajasOutTransferidos(nroLocal);

        txtNumero.setText("Esperados: " + cajaOuts.size());
        txtCompletos.setText("Completos: " + completos.size());
        txtTransferidos.setText("Tranferidos: " + transferidos.size());
        data.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
