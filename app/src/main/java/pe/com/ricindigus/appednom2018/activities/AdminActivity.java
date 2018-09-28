package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.activities.admin.AdmHorarioActivity;
import pe.com.ricindigus.appednom2018.activities.admin.AdmMarcoActivity;
import pe.com.ricindigus.appednom2018.modelo.Asistencia;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaRa;
import pe.com.ricindigus.appednom2018.modelo.Caja;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.Inventario;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;
import pe.com.ricindigus.appednom2018.util.FileChooser;

public class AdminActivity extends AppCompatActivity {

    Button btnCargarMarco;
    Button btnHorarioAsistencia;
    Button btnSalir;
    Button btnExportarBD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnCargarMarco = (Button) findViewById(R.id.btnCargarMarco);
        btnExportarBD = (Button) findViewById(R.id.btnExportaBD);

        btnHorarioAsistencia = (Button) findViewById(R.id.btnHorarioAsistencia);
        btnSalir = (Button) findViewById(R.id.btnSalir);

        btnCargarMarco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser fileChooser = new FileChooser(AdminActivity.this);
                fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
                    @Override
                    public void fileSelected(File file) {
                        String filename = file.getAbsolutePath();
                        if(filename.substring(filename.length()-7,filename.length()).toLowerCase().equals(".sqlite")){
                            Intent intent = new Intent(AdminActivity.this, AdmMarcoActivity.class);
                            intent.putExtra("filename",filename);
                            intent.putExtra("tipo_carga",1);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(AdminActivity.this, "archivo de tipo incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                fileChooser.showDialog();

            }
        });

        btnExportarBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdmMarcoActivity.class);
                intent.putExtra("tipo_carga",2);
                startActivity(intent);
            }
        });

//        btnHorarioAsistencia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(AdminActivity.this, AdmHorarioActivity.class);
//                startActivity(intent);
//            }
//        });

        btnHorarioAsistencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Data data = new Data(AdminActivity.this);
                data.open();
//                ArrayList<AsistenciaRa> asistenciaRas = data.getAllAsistenciaRaxLocal(1);
//                WriteBatch batch = db.batch();
//                for (AsistenciaRa asistenciaRa : asistenciaRas){
//                    DocumentReference documentReference = db.collection("asistencia_ra").document(asistenciaRa.getDni());
//                    batch.set(documentReference,asistenciaRaToMap(asistenciaRa));
//                }
//                batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(AdminActivity.this, "SUBIDO ASISTENCIA RA", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(AdminActivity.this, "NO SE PUDO SUBIR", Toast.LENGTH_SHORT).show();
//                    }
//                });


//                ArrayList<Caja> cajas = data.getAllCajas(1);
//                WriteBatch batchCaja = db.batch();
//                for (Caja caja: cajas){
//                    String codCaja = caja.getCod_barra_caja().substring(0,caja.getCod_barra_caja().length()-2);
//                    batchCaja.set(db.collection("cajas").document(codCaja),cajatoMap(caja));
//                }
//                batchCaja.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(AdminActivity.this, "Subidos correctamente cajas", Toast.LENGTH_SHORT).show();
//                    }
//                });


//                ArrayList<Asistencia> asistencias =  new ArrayList<>();
//                asistencias =  data.getAllAsistencia(1);
//                int n = asistencias.size() / 400;
//                for (int i = 1; i <= n ; i++) {
//                    final int c = i;
//                    WriteBatch batch = db.batch();
//                    for (int j = (i-1)*400; j < i*400 ; j++) {
//                        batch.set(db.collection("asistencia").document(asistencias.get(j).getDni()),asistenciaToMap(asistencias.get(j)));
//                    }
//                    batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(AdminActivity.this, "Subidos correctamente asistencias" + c, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                WriteBatch batch1 = db.batch();
//                for (int i = n*400 ; i < asistencias.size(); i++) {
//                    batch1.set(db.collection("asistencia").document(asistencias.get(i).getDni()),asistenciaToMap(asistencias.get(i)));
//                }
//                batch1.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(AdminActivity.this, "Subidos correctamente asistencias completos", Toast.LENGTH_SHORT).show();
//                    }
//                });

//                ArrayList<Inventario> inventarios =  new ArrayList<>();
//                inventarios =  data.getAllFichas(1);
//                int n = inventarios.size() / 400;
//                for (int i = 1; i <= n ; i++) {
//                    final int c = i;
//                    WriteBatch batch = db.batch();
//                    for (int j = (i-1)*400; j < i*400 ; j++) {
//                        batch.set(db.collection("inventario").document(inventarios.get(j).getCodigo()),inventarioToMap(inventarios.get(j)));
//                    }
//                    batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(AdminActivity.this, "Subidos correctamente inventarios" + c, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//                WriteBatch batch1 = db.batch();
//                for (int i = n*400 ; i < inventarios.size(); i++) {
//                    batch1.set(db.collection("inventario").document(inventarios.get(i).getCodigo()),inventarioToMap(inventarios.get(i)));
//                }
//                batch1.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(AdminActivity.this, "Subidos correctamente inventarios completos", Toast.LENGTH_SHORT).show();
//                    }
//                });

                data.close();

            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }




    public Map<String, Object> asistenciaRaToMap(AsistenciaRa asistenciaRa){
        Map<String, Object> map = new HashMap<>();
        map.put(SQLConstantes.asistencia_ra_ccdd,asistenciaRa.getCcdd());
        map.put(SQLConstantes.asistencia_ra_idsede,asistenciaRa.getIdsede());
        map.put(SQLConstantes.asistencia_ra_idnacional,asistenciaRa.getIdnacional());
        map.put(SQLConstantes.asistencia_ra_idlocal,asistenciaRa.getIdlocal());
        map.put(SQLConstantes.asistencia_ra_red,asistenciaRa.getRed());
        map.put(SQLConstantes.asistencia_ra_tipo_cargo,asistenciaRa.getTipo_cargo());
        map.put(SQLConstantes.asistencia_ra_nombre_cargo,asistenciaRa.getNombre_cargo());
        map.put(SQLConstantes.asistencia_ra_dni,asistenciaRa.getDni());
        map.put(SQLConstantes.asistencia_ra_nombres_completos,asistenciaRa.getNombres_completos());
        map.put("check_registro",0);
        return map;
    }

    public Map<String, Object> asistenciaToMap(Asistencia asistencia){
        Map<String, Object> map = new HashMap<>();
        map.put(SQLConstantes.asistencia_ape_paterno, asistencia.getApe_paterno());
        map.put(SQLConstantes.asistencia_ape_materno, asistencia.getApe_materno());
        map.put(SQLConstantes.asistencia_nombres, asistencia.getNombres());
        map.put(SQLConstantes.asistencia_prioridad, asistencia.getPrioridad());
        map.put(SQLConstantes.asistencia_naula, asistencia.getNaula());
        map.put(SQLConstantes.asistencia_idnacional, asistencia.getIdnacional());
        map.put(SQLConstantes.asistencia_ccdd, asistencia.getCcdd());
        map.put(SQLConstantes.asistencia_idlocal, asistencia.getIdlocal());
        map.put(SQLConstantes.asistencia_idsede, asistencia.getIdsede());
        map.put("check_registro_local", 0);
        map.put("check_registro_aula", 0);
        return map;
    }

    public Map<String, Object> cajatoMap(Caja caja){
        Map<String, Object> map = new HashMap<>();
        map.put(SQLConstantes.cajas_idnacional, caja.getIdnacional());
        map.put(SQLConstantes.cajas_ccdd, caja.getCcdd());
        map.put(SQLConstantes.cajas_idsede, caja.getIdsede());
        map.put(SQLConstantes.cajas_idlocal, caja.getIdlocal());
        map.put(SQLConstantes.cajas_tipo, caja.getTipo());
        map.put("check_registro",0);
        return map;
    }

    public Map<String, Object> inventarioToMap(Inventario inventario){
        Map<String, Object> map = new HashMap<>();
        map.put(SQLConstantes.inventario_ccdd, inventario.getCcdd());
        map.put(SQLConstantes.inventario_idnacional, inventario.getIdnacional());
        map.put(SQLConstantes.inventario_idsede, inventario.getIdsede());
        map.put(SQLConstantes.inventario_idlocal, inventario.getIdlocal());
        map.put(SQLConstantes.inventario_naula, inventario.getNaula());
        if (inventario.getTipo() == 3){
            map.put(SQLConstantes.inventario_tipo, inventario.getTipo());
            map.put("check_registro_listado", 0);
        } else{
            map.put(SQLConstantes.inventario_codpagina, inventario.getCodpagina());
            map.put(SQLConstantes.inventario_tipo, 12);
            map.put(SQLConstantes.inventario_dni, inventario.getDni());
            map.put("check_registro_ficha", 0);
            map.put("check_registro_cuadernillo", 0);
        }
        return map;
    }
}
