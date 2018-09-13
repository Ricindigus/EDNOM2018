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
import pe.com.ricindigus.appednom2018.adapters.AsistenciaLocalAdapter;
import pe.com.ricindigus.appednom2018.adapters.InventarioFichaAdapter;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Ficha;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListInvFichaFragment extends Fragment {
    Context context;
    int nroLocal;
    String usuario;
    Spinner spAulas;
    RecyclerView recyclerView;
    ArrayList<Ficha> fichas;
    ArrayList<Ficha> datosNoEnviados;
    Data data;
    FloatingActionButton fabUpLoad;
    TextView txtNumero;
    InventarioFichaAdapter inventarioFichaAdapter;
    boolean b = false;

    public ListInvFichaFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ListInvFichaFragment(Context context, int nroLocal, String usuario) {
        this.context = context;
        this.nroLocal = nroLocal;
        this.usuario = usuario;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_inv_ficha, container, false);
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
        ArrayList<String> aulas =  d.getArrayAulas(nroLocal);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, aulas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAulas.setAdapter(dataAdapter);
        d.close();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        cargaData();
        inventarioFichaAdapter = new InventarioFichaAdapter(fichas,context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(inventarioFichaAdapter);

        spAulas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargaData();
                inventarioFichaAdapter = new InventarioFichaAdapter(fichas,context);
                recyclerView.setAdapter(inventarioFichaAdapter);
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
                datosNoEnviados = new ArrayList<>();
                data = new Data(context);
                data.open();
                String aula = spAulas.getSelectedItem().toString();
                int nroAula = 0;
                nroAula = data.getNumeroAula(aula,nroLocal);
                datosNoEnviados = data.getAllFichasSinEnviar(nroLocal,nroAula);
                data.close();
                if(datosNoEnviados.size() > 0){
                    final int total = datosNoEnviados.size();
                    int i = 0;
                    for (final Ficha ficha : datosNoEnviados){
                        i++;
                        final int j = i;
                        final String c = ficha.getCodigo();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("inventario").document(ficha.getTipo()+""+ficha.getCodigo());
                        batch.update(documentReference, "check_registro", 1);
                        batch.update(documentReference, "fecha_transferencia", FieldValue.serverTimestamp());
                        batch.update(documentReference, "usuario_registro", usuario);
                        batch.update(documentReference, "fecha_registro",
                                new Timestamp(new Date(ficha.getAnio()-1900,ficha.getMes()-1,ficha.getDia(),
                                        ficha.getHora(),ficha.getMin(),ficha.getSeg())));
                        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Data data = new Data(context);
                                data.open();
                                data.actualizarFichaSubido(c);
                                data.close();
                                if (j == total) {
                                    Toast.makeText(context, total + " registros subidos", Toast.LENGTH_SHORT).show();
                                    cargaData();
                                    inventarioFichaAdapter = new InventarioFichaAdapter(fichas,context);
                                    recyclerView.setAdapter(inventarioFichaAdapter);
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
        fichas = new ArrayList<Ficha>();
        Data d = new Data(context);
        d.open();
        String aula = spAulas.getSelectedItem().toString();
        int nroAula = 0;
        nroAula = d.getNumeroAula(aula,nroLocal);
        fichas = d.getAllFichas(nroLocal,nroAula);
        txtNumero.setText("Total registros: " + fichas.size());
        d.close();
    }
    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

}
