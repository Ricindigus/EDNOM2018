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

import pe.com.ricindigus.appednom2018.modelo.AsistenciaReg;
import pe.com.ricindigus.appednom2018.modelo.CajaReg;
import pe.com.ricindigus.appednom2018.modelo.InventarioReg;
import pe.com.ricindigus.appednom2018.util.FileChooser;
import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.UsuarioLocal;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtClave;
    TextView txtAquiMarco;
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
        txtAquiMarco = (TextView) findViewById(R.id.login_txtAquiMarco);
        TextView txtTitulo = (TextView) findViewById(R.id.login_titulo_encuesta);
        Data data = new Data(LoginActivity.this);
        data.open();
        temaApp = data.getNombreApp();
        data.close();
        txtTitulo.setText(temaApp);
        edtClave.setText("R6VDXC");

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clave = edtClave.getText().toString();
                ingresar(clave);
            }
        });

        txtAquiMarco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarMarco();
            }
        });
    }

    public void ingresar(String clave){
        Data data = new Data(LoginActivity.this);
        data.open();
        usuarioLocal = data.getUsuarioLocal(clave);
        if (usuarioLocal != null){
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
                        startActivity(intent);
                        finish();
                        break;
                    case 3:
                        filtrarMarcoCajas(usuarioLocal.getIdlocal(),clave);
                        break;
                }
            }
        }else {
            Toast.makeText(this, "CLAVE INCORRECTA", Toast.LENGTH_SHORT).show();
        }
        data.close();
    }

    public void filtrarMarcoCajas(int nroLocal, String clave){
        Data data = new Data(LoginActivity.this);
        data.open();
        ArrayList<CajaReg> cajaRegs = data.filtrarMarcoCajas(nroLocal);
        for (CajaReg cajaReg : cajaRegs){
            data.insertarCajaReg(cajaReg);
        }
        data.guardarClave(clave);
        data.insertarHistorialUsuario(clave);
        data.close();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void cargarMarco(){
        FileChooser fileChooser = new FileChooser(LoginActivity.this);
        fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(File file) {
                String filename = file.getAbsolutePath();
                Log.d("File", filename);
                Toast.makeText(LoginActivity.this, "Cargando..." + filename, Toast.LENGTH_SHORT).show();
                try {
                    Data data = new Data(LoginActivity.this,filename);
                    data.open();
                    data.close();
                    Intent intent = new Intent(LoginActivity.this,SplashActivity.class);
                    startActivity(intent);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fileChooser.showDialog();
    }
}
