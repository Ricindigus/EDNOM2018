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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import pe.com.ricindigus.appednom2018.adapters.InventarioCuadernilloAdapter;
import pe.com.ricindigus.appednom2018.modelo.Cuadernillo;
import pe.com.ricindigus.appednom2018.modelo.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListInvCuadernilloFragment extends Fragment {
    Context context;
    int nroLocal;
    Spinner spAulas;
    String usuario;
    RecyclerView recyclerView;
    ArrayList<Cuadernillo> cuadernillos;
    ArrayList<Cuadernillo> datosNoEnviados;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    InventarioCuadernilloAdapter inventarioCuadernilloAdapter;
    boolean b = false;

    public ListInvCuadernilloFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListInvCuadernilloFragment(Context context, int nroLocal, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lis_inv_cuadernillo, container, false);
        spAulas = (Spinner) rootView.findViewById(R.id.lista_spAula);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.lista_recycler);
        fabUpLoad = (FloatingActionButton) rootView.findViewById(R.id.lista_btnUpload);
        txtNumero = (TextView) rootView.findViewById(R.id.lista_txtNumero);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Data d =  new Data(context);
        d.open();
        ArrayList<String> aulas =  d.getArrayAulasListado(nroLocal);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aulas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAulas.setAdapter(dataAdapter);
        d.close();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        inventarioCuadernilloAdapter = new InventarioCuadernilloAdapter(cuadernillos,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(inventarioCuadernilloAdapter);

        spAulas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargaData();
                inventarioCuadernilloAdapter = new InventarioCuadernilloAdapter(cuadernillos,context);
                recyclerView.setAdapter(inventarioCuadernilloAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fabUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Subiendo...", Toast.LENGTH_SHORT).show();
                b = false;
                datosNoEnviados = new ArrayList<Cuadernillo>();
                data = new Data(context);
                data.open();
                int seleccion = spAulas.getSelectedItemPosition();
                String aula = spAulas.getSelectedItem().toString();
                int nroAula = 0;
                if(seleccion > 0) nroAula = data.getNumeroAula(aula,nroLocal);
                datosNoEnviados = data.getAllCuadernillosSinEnviar(nroLocal,nroAula);
                data.close();
                if(datosNoEnviados.size() > 0){
                    final int total = datosNoEnviados.size();
                    int i = 0;
                    for (final Cuadernillo cuadernillo : datosNoEnviados){
                        i++;
                        final int j = i;
                        final String c = cuadernillo.getCodigo();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("inventario").document(cuadernillo.getTipo()+""+cuadernillo.getCodigo());
                        batch.update(documentReference, "check_registro", 1);
                        batch.update(documentReference, "fecha_transferencia", FieldValue.serverTimestamp());
                        batch.update(documentReference, "usuario_registro", usuario);
                        batch.update(documentReference, "fecha_registro",
                                new Timestamp(new Date(cuadernillo.getAnio()-1900,cuadernillo.getMes()-1,cuadernillo.getDia(),
                                        cuadernillo.getHora(),cuadernillo.getMin(),cuadernillo.getSeg())));

                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarCuadernilloSubido(c);
                                data.close();
                                if (j == total) {
                                    Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                    cargaData();
                                    inventarioCuadernilloAdapter = new InventarioCuadernilloAdapter(cuadernillos,context);
                                    recyclerView.setAdapter(inventarioCuadernilloAdapter);
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
        cuadernillos = new ArrayList<Cuadernillo>();
        Data d = new Data(context);
        d.open();
        int seleccion = spAulas.getSelectedItemPosition();
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        if(seleccion > 0) nroAula = d.getNumeroAula(aula,nroLocal);
        cuadernillos = d.getAllCuadernillos(nroLocal,nroAula);
        txtNumero.setText("TOTAL REGISTROS: " + cuadernillos.size());
        d.close();
    }
    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}
