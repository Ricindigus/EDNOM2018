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
import pe.com.ricindigus.appednom2018.adapters.CajaIngresoAdapter;
import pe.com.ricindigus.appednom2018.modelo.CajaIn;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListIngresoCajasFragment extends Fragment {


    RecyclerView recyclerView;
    Context context;
    ArrayList<CajaIn> cajaIns;
    ArrayList<CajaIn> completos;
    ArrayList<CajaIn> transferidos;

    int nroLocal;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    TextView txtCompletos;
    TextView txtTransferidos;
    String usuario;


    boolean b = false;
    CajaIngresoAdapter cajaIngresoAdapter;

    public ListIngresoCajasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListIngresoCajasFragment(Context context, int nroLocal, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_ingreso_cajas, container, false);
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
        cajaIngresoAdapter = new CajaIngresoAdapter(cajaIns,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cajaIngresoAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                completos = new ArrayList<>();
                data = new Data(context);
                data.open();
                completos = data.getAllCajasInCompletas(nroLocal);
                data.close();
                if(completos.size() > 0){
                    final int total = completos.size();
                    int i = 0;
                    for (final CajaIn cajaIn : completos){
                        i++;
                        final int j = i;
                        Data d = new Data(context);
                        d.open();
                        CajaIn cajaIn20 = d.getCajaIn(getCodigo20(cajaIn.getCod_barra_caja()));
                        d.close();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        DocumentReference documentReference20 = FirebaseFirestore.getInstance().collection("cajas").document(cajaIn20.getCod_barra_caja());
                        DocumentReference documentReference1 = FirebaseFirestore.getInstance().collection("cajas").document(cajaIn.getCod_barra_caja());
                        batch.update(documentReference1, "check_registro_ingreso", 1);
                        batch.update(documentReference1, "fecha_transferencia_ingreso", FieldValue.serverTimestamp());
                        batch.update(documentReference1, "usuario_registro_ingreso", usuario);
                        batch.update(documentReference1, "fecha_registro_ingreso", new Timestamp(new Date(cajaIn.getAnio()-1900,cajaIn.getMes()-1,cajaIn.getDia(),cajaIn.getHora(),cajaIn.getMin(),cajaIn.getSeg())));
                        batch.update(documentReference20, "check_registro_ingreso", 1);
                        batch.update(documentReference20, "fecha_transferencia_ingreso", FieldValue.serverTimestamp());
                        batch.update(documentReference20, "usuario_registro_ingreso", usuario);
                        batch.update(documentReference20, "fecha_registro_ingreso", new Timestamp(new Date(cajaIn20.getAnio()-1900,cajaIn20.getMes()-1,cajaIn20.getDia(),cajaIn20.getHora(),cajaIn20.getMin(),cajaIn20.getSeg())));

                        final String codigoBarra = cajaIn.getCod_barra_caja();
                        final String codigoBarra20 = cajaIn20.getCod_barra_caja();

                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarCajaInSubido(codigoBarra);
                                data.actualizarCajaInSubido(codigoBarra20);
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

    public String getCodigo20(String cod2){
        String cod1 = cod2.substring(0,cod2.length()-2) + "20";
        return cod1;
    }

    public void cargaData(){
        cajaIns = new ArrayList<CajaIn>();
        completos = new ArrayList<CajaIn>();
        transferidos = new ArrayList<CajaIn>();

        Data data = new Data(context);
        data.open();
        cajaIns = data.getAllCajaInListado(nroLocal);
        completos = data.getAllCajasInCompletas(nroLocal);
        transferidos = data.getAllCajasInTransferidos(nroLocal);
        txtNumero.setText("Esperados: " + cajaIns.size());
        txtCompletos.setText("Completos: " + completos.size());
        txtTransferidos.setText("Tranferidos: " + transferidos.size());
        data.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
