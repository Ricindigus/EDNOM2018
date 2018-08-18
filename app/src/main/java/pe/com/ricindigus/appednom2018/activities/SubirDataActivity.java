package pe.com.ricindigus.appednom2018.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Nacional;
import pe.com.ricindigus.appednom2018.modelo.UsuarioLocal;

public class SubirDataActivity extends AppCompatActivity {

    EditText edtClave;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_data);

        edtClave = (EditText) findViewById(R.id.subir_edtClave);
        btnEnviar = (Button) findViewById(R.id.subir_btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data data = new Data(SubirDataActivity.this);
                data.open();
                UsuarioLocal usuarioLocal = data.getUsuarioLocal(edtClave.getText().toString());
                int numeroLocal = usuarioLocal.getNro_local();

                ArrayList<Nacional> listasNacional = data.getListasDelLocal(numeroLocal);
                int i = 0;
                for (Nacional nacional : listasNacional){
//                    i++;
                    Map<String, Object> asistencia = new HashMap<>();
                    asistencia.put("sede", nacional.getSede());
                    asistencia.put("id_local", nacional.getId_local());
                    asistencia.put("local", nacional.getLocal_aplicacion());
                    asistencia.put("aula", nacional.getAula());
                    asistencia.put("lista_asis", nacional.getCodigo_pagina());
                    asistencia.put("lista_dia", 0);
                    asistencia.put("lista_mes", 0);
                    asistencia.put("lista_anio", 0);
                    asistencia.put("lista_hora", 0);
                    asistencia.put("lista_minuto", 0);
                    if(nacional.getCodigo_pagina() != null){
                        if (!nacional.getCodigo_pagina().equals("")){
                            FirebaseFirestore.getInstance().collection("inventario").document(nacional.getCodigo_pagina())
                                    .set(asistencia)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("FIREBASE", "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("FIREBASE", "Error writing document", e);
                                        }
                                    });
                        }
                    }
                }

                ArrayList<Nacional> nacionals = data.getNacionalxNroLocal(numeroLocal);
                for (Nacional nacional : nacionals){
                    Map<String, Object> asistencia = new HashMap<>();
                    asistencia.put("_id", nacional.get_id());
                    asistencia.put("dni", nacional.getIns_numdoc());
                    asistencia.put("nombres", nacional.getNombres());
                    asistencia.put("apepat", nacional.getApepat());
                    asistencia.put("apemat", nacional.getApemat());
                    asistencia.put("sede", nacional.getSede());
                    asistencia.put("id_local", nacional.getId_local());
                    asistencia.put("local", nacional.getLocal_aplicacion());
                    asistencia.put("aula", nacional.getAula());
                    asistencia.put("local_dia", 0);
                    asistencia.put("local_mes", 0);
                    asistencia.put("local_anio", 0);
                    asistencia.put("local_hora", 0);
                    asistencia.put("local_minuto", 0);
                    asistencia.put("aula_dia", 0);
                    asistencia.put("aula_mes", 0);
                    asistencia.put("aula_anio", 0);
                    asistencia.put("aula_hora", 0);
                    asistencia.put("aula_minuto", 0);

                    if(nacional.getIns_numdoc() != null){
                        if (!nacional.getIns_numdoc().equals("")){
                            FirebaseFirestore.getInstance().collection("asistencia").document(nacional.getIns_numdoc())
                                    .set(asistencia)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("FIREBASE", "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("FIREBASE", "Error writing document", e);
                                        }
                                    });
                        }
                    }


                    Map<String, Object> inventario_ficha_cuad = new HashMap<>();
                    asistencia.put("cod_ficha", nacional.getCodficha());
                    asistencia.put("cod_cartilla", nacional.getCodcartilla());
                    asistencia.put("reg_ficha_dia", 0);
                    asistencia.put("reg_ficha_mes", 0);
                    asistencia.put("reg_ficha_anio", 0);
                    asistencia.put("reg_ficha_hora", 0);
                    asistencia.put("reg_ficha_minuto", 0);
                    asistencia.put("reg_cuader_dia", 0);
                    asistencia.put("reg_cuader_mes", 0);
                    asistencia.put("reg_cuader_anio", 0);
                    asistencia.put("reg_cuader_hora", 0);
                    asistencia.put("reg_cuader_minuto", 0);
                    if(nacional.getCodigo_pagina() != null && nacional.getCodficha() != null){
                        if (!nacional.getCodigo_pagina().equals("") && !nacional.getCodficha().equals("")){
                            FirebaseFirestore.getInstance()
                                    .collection("inventario").document(nacional.getCodigo_pagina())
                                    .collection("cartilla_ficha").document(nacional.getCodficha())
                                    .set(inventario_ficha_cuad)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("FIREBASE", "DocumentSnapshot successfully written!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("FIREBASE", "Error writing document", e);
                                        }
                                    });
                        }

                    }
                }
                data.close();
            }
        });
    }
}
