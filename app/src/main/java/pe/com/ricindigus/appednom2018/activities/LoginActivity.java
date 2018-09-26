package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import pe.com.ricindigus.appednom2018.modelo.AsistenciaRaReg;
import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.CajaReg;
import pe.com.ricindigus.appednom2018.modelo.InventarioReg;
import pe.com.ricindigus.appednom2018.util.FileChooser;
import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.UsuarioLocal;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtClave;
//    TextView txtAquiMarco;
    Button btnIngresar;
    String temaApp;
    UsuarioLocal usuarioLocal;
    String clave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtClave = (TextInputEditText) findViewById(R.id.login_edtClave);
        btnIngresar = (Button) findViewById(R.id.login_btnIngresar);
//        txtAquiMarco = (TextView) findViewById(R.id.login_txtAquiMarco);
        TextView txtTitulo = (TextView) findViewById(R.id.login_titulo_encuesta);
        Data data = new Data(LoginActivity.this);
        data.open();
        temaApp = data.getNombreApp();
        data.close();
        txtTitulo.setText(temaApp);
//        edtClave.setText("R6VDXC");

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clave = edtClave.getText().toString();
                ingresar(clave);
            }
        });


    }

    public void ingresar(String clave){
        Data data = new Data(LoginActivity.this);
        data.open();
        usuarioLocal = data.getUsuarioLocal(clave);
        if (usuarioLocal != null){
            if (usuarioLocal.getRol() == 1){
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }else{
                if(data.existeUsuario(clave)) {
                    data.guardarClave(clave);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    switch (usuarioLocal.getRol()){
                        case 2:
                            Intent intent = new Intent(LoginActivity.this, ProgressActivity.class);
                            intent.putExtra("clave",clave);
                            intent.putExtra("tipo_filtro",2);
                            startActivity(intent);
                            finish();
                            break;
                        case 3:
                            Intent intent1 = new Intent(LoginActivity.this, ProgressActivity.class);
                            intent1.putExtra("clave",clave);
                            intent1.putExtra("tipo_filtro",3);
                            startActivity(intent1);
                            finish();
//                            filtrarMarcoCajasAsistenciaRA(usuarioLocal.getIdlocal(),clave);
                            break;
                    }
                }
            }

        }else {
            Toast.makeText(this, "CLAVE INCORRECTA", Toast.LENGTH_SHORT).show();
        }
        data.close();
    }

    public void filtrarMarcoCajasAsistenciaRA(int nroLocal, String clave){
        Data data = new Data(LoginActivity.this);
        data.open();
        ArrayList<CajaReg> cajaRegs = data.filtrarMarcoCajas(nroLocal);
        for (CajaReg cajaReg : cajaRegs){
            data.insertarCajaReg(cajaReg);
        }

        ArrayList<AsistenciaRaReg> asistenciaRaRegs = data.filtrarMarcoAsistenciaRA(nroLocal);
        for (AsistenciaRaReg asistenciaRaReg : asistenciaRaRegs){
            data.insertarAsistenciaRaReg(asistenciaRaReg);
        }
        data.guardarClave(clave);
        data.insertarHistorialUsuario(clave);
        data.close();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
