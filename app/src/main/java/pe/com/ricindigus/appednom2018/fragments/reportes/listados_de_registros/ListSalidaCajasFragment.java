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
import pe.com.ricindigus.appednom2018.adapters.CajasSalidaAdapter;
import pe.com.ricindigus.appednom2018.modelo.CajaReg;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.util.ActividadInterfaz;
import pe.com.ricindigus.appednom2018.util.TipoFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListSalidaCajasFragment extends Fragment {


    RecyclerView recyclerView;
    Context context;
    ArrayList<CajaReg> cajaRegs;
    ArrayList<CajaReg> noEnviados;
    String usuario;

    int nroLocal;
    Data data;
    FloatingActionButton fabUpLoad;
    FloatingActionButton fabSearch;

    TextView txtNumero;
    TextView txtNoRegistrados;
    TextView txtIncompletos;
    TextView txtCompletos;
    TextView txtTransferidos;
    boolean b = false;
    CajasSalidaAdapter cajasSalidaAdapter;

    String nombreColeccion;

    public ListSalidaCajasFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListSalidaCajasFragment(Context context, int nroLocal,String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list_salida_cajas, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listado_recycler);
        fabUpLoad = (FloatingActionButton) rootView.findViewById(R.id.listado_btnUpload);
        fabSearch = (FloatingActionButton) rootView.findViewById(R.id.listado_btnBuscar);

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
        cajasSalidaAdapter = new CajasSalidaAdapter(cajaRegs,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cajasSalidaAdapter);

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActividadInterfaz actividadInterfaz = (ActividadInterfaz) getActivity();
                actividadInterfaz.irReporte(TipoFragment.CAJAS_OUT);
            }
        });

        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = false;
                noEnviados = new ArrayList<>();
                data = new Data(context);
                data.open();
                noEnviados = data.getListCajasSalidaCompletas(nroLocal);
                if(noEnviados.size() > 0){
                    final int total = noEnviados.size();
                    int i = 0;
                    for (final CajaReg cajaOut10 : noEnviados){
                        i++;
                        final int j = i;
                        if (cajaOut10.getTipo() != 3){
                            CajaReg cajaOut20 = data.getCajaReg(getCodigo20(cajaOut10.getCod_barra_caja()),nroLocal);
                            String codCaja = cajaOut10.getCod_barra_caja().substring(0,cajaOut10.getCod_barra_caja().length()-2);
                            WriteBatch batch = FirebaseFirestore.getInstance().batch();
                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection(nombreColeccion).document(codCaja);
                            batch.update(documentReference, "check_registro", 2);
                            batch.update(documentReference, "fecha_registro_salida_10", new Timestamp(
                                    new Date(cajaOut10.getAnio_salida()-1900,cajaOut10.getMes_salida()-1,cajaOut10.getDia_salida(),cajaOut10.getHora_salida(),cajaOut10.getMin_salida(),cajaOut10.getSeg_salida())));
                            batch.update(documentReference, "fecha_registro_salida_20", new Timestamp(
                                    new Date(cajaOut10.getAnio_salida()-1900,cajaOut20.getMes_salida()-1,cajaOut20.getDia_salida(),cajaOut20.getHora_salida(),cajaOut20.getMin_salida(),cajaOut20.getSeg_salida())));
                            batch.update(documentReference, "fecha_transferencia_salida", FieldValue.serverTimestamp());
                            batch.update(documentReference, "usuario_registro_salida", usuario);
                            final String codigoBarra10 = cajaOut10.getCod_barra_caja();
                            batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Data data = new Data(context);
                                    data.open();
                                    data.actualizarCajaRegSubidoSalida(codigoBarra10);
                                    data.close();
                                    if (j == total) {
                                        Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                        cargaData();
                                        cajasSalidaAdapter = new CajasSalidaAdapter(cajaRegs,context);
                                        recyclerView.setAdapter(cajasSalidaAdapter);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "NO GUARDO", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            String codCaja = cajaOut10.getCod_barra_caja().substring(0,cajaOut10.getCod_barra_caja().length()-2);
                            WriteBatch batch = FirebaseFirestore.getInstance().batch();
                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection(nombreColeccion).document(codCaja);
                            batch.update(documentReference, "check_registro", 1);
                            batch.update(documentReference, "fecha_registro_salida_10", new Timestamp(
                                    new Date(cajaOut10.getAnio_salida()-1900,cajaOut10.getMes_salida()-1,cajaOut10.getDia_salida(),cajaOut10.getHora_salida(),cajaOut10.getMin_salida(),cajaOut10.getSeg_salida())));
                            batch.update(documentReference, "fecha_transferencia_salida", FieldValue.serverTimestamp());
                            batch.update(documentReference, "usuario_registro_salida", usuario);
                            final String codigoBarra10 = cajaOut10.getCod_barra_caja();
                            batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Data data = new Data(context);
                                    data.open();
                                    data.actualizarCajaRegSubidoSalida(codigoBarra10);
                                    data.close();
                                    if (j == total) {
                                        Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                        cargaData();
                                        cajasSalidaAdapter = new CajasSalidaAdapter(cajaRegs,context);
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
                    }
                }else{
                    Toast.makeText(context, "No hay registros nuevos para subir", Toast.LENGTH_SHORT).show();
                }
                data.close();
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
        nombreColeccion = data.getNombreColeccionCajas();
        cajaRegs = data.getListadoCajasSalida(nroLocal);
        txtNumero.setText("Totales: " + cajaRegs.size());
        txtNoRegistrados.setText("No registrados: " + data.getNroCajasSalidaSinRegistrar(nroLocal));
        txtIncompletos.setText("Incompletos: " + data.getNroCajasSalidaIncompletas(nroLocal));
        txtCompletos.setText("Completos: " + data.getNroCajasSalidaCompletas(nroLocal));
        txtTransferidos.setText("Tranferidos: " + data.getNroCajasSalidaTransferidos(nroLocal));
        data.close();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
