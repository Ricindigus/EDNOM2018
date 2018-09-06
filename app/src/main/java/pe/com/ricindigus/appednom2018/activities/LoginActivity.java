package pe.com.ricindigus.appednom2018.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import pe.com.ricindigus.appednom2018.modelo.CajaIn;
import pe.com.ricindigus.appednom2018.modelo.CajaOut;
import pe.com.ricindigus.appednom2018.util.FileChooser;
import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.UsuarioLocal;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText edtClave;
    TextView txtAquiMarco;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtClave = (TextInputEditText) findViewById(R.id.login_edtClave);
        btnIngresar = (Button) findViewById(R.id.login_btnIngresar);
        txtAquiMarco = (TextView) findViewById(R.id.login_txtAquiMarco);

        edtClave.setText("VOE5XM");

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresar(edtClave.getText().toString());
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
        Data data = null;
        try {
            data = new Data(LoginActivity.this,0);
            data.open();
            UsuarioLocal usuarioLocal = data.getUsuarioLocal(clave);
            data.close();
            if (usuarioLocal != null){
                Data d = new Data(LoginActivity.this);
                d.open();
                if (d.getNumeroItemsCajasIn() == 0){
                    ArrayList<CajaIn> cajaIns = d.getCopiaCajasInxLocal(usuarioLocal.getNro_local());
                    ArrayList<CajaOut> cajaOuts = d.getCopiaCajasOutxLocal(usuarioLocal.getNro_local());

                    for (CajaIn caja : cajaIns){
                        d.insertarCajaIn(caja);
                    }
                    for (CajaOut caja : cajaOuts){
                        d.insertarCajaOut(caja);
                    }
                }
                d.close();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("nrolocal", usuarioLocal.getNro_local());
                intent.putExtra("usuario", usuarioLocal.getClave());
                startActivity(intent);
            }else {
                Toast.makeText(this, "CLAVE INCORRECTA", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        fileChooser.showDialog();
    }
}
