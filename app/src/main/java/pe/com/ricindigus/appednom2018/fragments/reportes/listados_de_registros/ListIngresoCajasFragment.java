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
import pe.com.ricindigus.appednom2018.adapters.CajasEntradaAdapter;
import pe.com.ricindigus.appednom2018.modelo.CajaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListIngresoCajasFragment extends Fragment {


    RecyclerView recyclerView;
    Context context;
    ArrayList<CajaReg> cajaRegs;
    ArrayList<CajaReg> noEnviados;


    int nroLocal;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    TextView txtNoRegistrados;
    TextView txtIncompletos;
    TextView txtCompletos;
    TextView txtTransferidos;

    String usuario;


    boolean b = false;
    CajasEntradaAdapter cajasEntradaAdapter;

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
        txtNoRegistrados = (TextView) rootView.findViewById(R.id.listado_txtNoRegistrados);
        txtIncompletos = (TextView) rootView.findViewById(R.id.listado_txtIncompletos);
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
        cajasEntradaAdapter = new CajasEntradaAdapter(cajaRegs,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cajasEntradaAdapter);

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                noEnviados = new ArrayList<>();
                data = new Data(context);
                data.open();
                noEnviados = data.getListCajasEntradaCompletas(nroLocal);
                data.close();
                if(noEnviados.size() > 0){
                    final int total = noEnviados.size();
                    int i = 0;
                    for (final CajaReg cajaIn10 : noEnviados){
                        i++;
                        final int j = i;
                        if (cajaIn10.getTipo() != 3){
                            WriteBatch batch = FirebaseFirestore.getInstance().batch();
                            DocumentReference documentReference10 = FirebaseFirestore.getInstance().collection("cajas").document(cajaIn10.getCod_barra_caja());
                            batch.update(documentReference10, "check_registro", 1);
                            batch.update(documentReference10, "fecha_transferencia_ingreso", FieldValue.serverTimestamp());
                            batch.update(documentReference10, "usuario_registro_ingreso", usuario);
                            batch.update(documentReference10, "fecha_registro_ingreso", new Timestamp(new Date(cajaIn10.getAnio_entrada()-1900,cajaIn10.getMes_entrada()-1,cajaIn10.getDia_entrada(),cajaIn10.getHora_entrada(),cajaIn10.getMin_entrada(),cajaIn10.getSeg_entrada())));
                            Data d = new Data(context);
                            d.open();
                            CajaReg cajaIn20 = d.getCajaReg(getCodigo20(cajaIn10.getCod_barra_caja()),nroLocal);
                            d.close();
                            DocumentReference documentReference20 = FirebaseFirestore.getInstance().collection("cajas").document(cajaIn20.getCod_barra_caja());
                            batch.update(documentReference20, "check_registro", 1);
                            batch.update(documentReference20, "fecha_transferencia_ingreso", FieldValue.serverTimestamp());
                            batch.update(documentReference20, "usuario_registro_ingreso", usuario);
                            batch.update(documentReference20, "fecha_registro_ingreso", new Timestamp(new Date(cajaIn20.getAnio_entrada()-1900,cajaIn20.getMes_entrada()-1,cajaIn20.getDia_entrada(),cajaIn20.getHora_entrada(),cajaIn20.getMin_entrada(),cajaIn20.getSeg_entrada())));

                            final String codigoBarra10 = cajaIn10.getCod_barra_caja();
                            final String codigoBarra20 = cajaIn20.getCod_barra_caja();

                            batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Data data = new Data(context);
                                    data.open();
                                    data.actualizarCajaRegSubidoEntrada(codigoBarra10);
                                    data.actualizarCajaRegSubidoEntrada(codigoBarra20);
                                    data.close();
                                    if (j == total) {
                                        Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                        cargaData();
                                        cajasEntradaAdapter = new CajasEntradaAdapter(cajaRegs,context);
                                        recyclerView.setAdapter(cajasEntradaAdapter);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            WriteBatch batch = FirebaseFirestore.getInstance().batch();
                            DocumentReference documentReference10 = FirebaseFirestore.getInstance().collection("cajas").document(cajaIn10.getCod_barra_caja());
                            batch.update(documentReference10, "check_registro", 1);
                            batch.update(documentReference10, "fecha_transferencia_ingreso", FieldValue.serverTimestamp());
                            batch.update(documentReference10, "usuario_registro_ingreso", usuario);
                            batch.update(documentReference10, "fecha_registro_ingreso", new Timestamp(new Date(cajaIn10.getAnio_entrada()-1900,cajaIn10.getMes_entrada()-1,cajaIn10.getDia_entrada(),cajaIn10.getHora_entrada(),cajaIn10.getMin_entrada(),cajaIn10.getSeg_entrada())));
                            final String codigoBarra10 = cajaIn10.getCod_barra_caja();

                            batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Data data = new Data(context);
                                    data.open();
                                    data.actualizarCajaRegSubidoEntrada(codigoBarra10);
                                    data.close();
                                    if (j == total) {
                                        Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                        cargaData();
                                        cajasEntradaAdapter = new CajasEntradaAdapter(cajaRegs,context);
                                        recyclerView.setAdapter(cajasEntradaAdapter);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
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
        cajaRegs = new ArrayList<CajaReg>();
        Data data = new Data(context);
        data.open();
        cajaRegs = data.getListadoCajasEntrada(nroLocal);
        txtNumero.setText("Totales: " + cajaRegs.size());
        txtNoRegistrados.setText("No registrados: " + data.getNroCajasEntradaSinRegistrar(nroLocal));
        txtIncompletos.setText("Incompletos: " + data.getNroCajasEntradaIncompletas(nroLocal));
        txtCompletos.setText("Completos: " + data.getNroCajasEntradaCompletas(nroLocal));
        txtTransferidos.setText("Tranferidos: " + data.getNroCajasEntradaTransferidos(nroLocal));
        data.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
