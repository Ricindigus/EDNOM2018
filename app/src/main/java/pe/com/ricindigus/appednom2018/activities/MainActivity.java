package pe.com.ricindigus.appednom2018.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.adapters.ExpandListAdapter;
import pe.com.ricindigus.appednom2018.fragments.consulta_padron_nacional.ConsultaPadronFragment;
import pe.com.ricindigus.appednom2018.fragments.registro_control_cajas.CajasInFragment;
import pe.com.ricindigus.appednom2018.fragments.registro_control_asistencia.AsistAulaFragment;
import pe.com.ricindigus.appednom2018.fragments.registro_control_asistencia.AsistLocalFragment;
import pe.com.ricindigus.appednom2018.fragments.registro_control_inventario.InvCuaderFragment;
import pe.com.ricindigus.appednom2018.fragments.registro_control_inventario.InvFichaFragment;
import pe.com.ricindigus.appednom2018.fragments.registro_control_inventario.InvListAsisFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.cuadro_resumen.CuadroResumenAsistenciaFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.cuadro_resumen.CuadroResumenCajasInFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.cuadro_resumen.CuadroResumenCajasOutFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.cuadro_resumen.CuadroResumenInventarioFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros.ListIngresoCajasFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros.ListInvCuadernilloFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros.ListAsisAulaFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros.ListAsisLocalFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros.ListInvFichaFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros.ListInvListadoFragment;
import pe.com.ricindigus.appednom2018.fragments.reportes.listados_de_registros.ListSalidaCajasFragment;
import pe.com.ricindigus.appednom2018.fragments.registro_control_cajas.CajasOutFragment;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;
import pe.com.ricindigus.appednom2018.modelo.UsuarioActual;
import pe.com.ricindigus.appednom2018.modelo.UsuarioLocal;
import pe.com.ricindigus.appednom2018.util.TipoFragment;

public class MainActivity extends AppCompatActivity {

    int nroLocal;
    String usuario;
    String temaApp;
    String usuarioLogueado;

    int numeroVersion;
    int rol;
    int tFragment;
    private ArrayList<String> listDataHeader;
    private ExpandableListView expListView;
    private HashMap<String, List<String>> listDataChild;
    private ExpandListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Data data = new Data(MainActivity.this);
        data.open();
        temaApp = data.getNombreApp();
        numeroVersion = data.getNumeroApp();
        String clave = data.getUsuarioActual().getClave();
        UsuarioLocal usuarioLocal = data.getUsuarioLocal(clave);
        nroLocal = usuarioLocal.getIdlocal();

        usuario = usuarioLocal.getClave();
        usuarioLogueado = usuarioLocal.getUsuario();
        rol = usuarioLocal.getRol();

        data.close();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("EVALUACIÓN DOCENTE PARA EL NOMBRAMIENTO");
        getSupportActionBar().setTitle(temaApp);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                ocultarTeclado(drawerView);
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView txtHeaderTitulo = (TextView) headerView.findViewById(R.id.txtTituloHeader);
        TextView txtHeaderUsuario = (TextView) headerView.findViewById(R.id.txtTituloUsuario);
        txtHeaderTitulo.setText(temaApp);
        txtHeaderUsuario.setText(usuarioLogueado);

        enableExpandableList();
        if (rol == 3) tFragment = TipoFragment.CAJAS_IN;
        else tFragment = TipoFragment.REGISTRO_ASISTENCIA_LOCAL;
        setFragment(tFragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            cerrarSesion();
        }
    }


    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean retornar = super.dispatchKeyEvent(event);
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP){
//            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START);
//            } else {
//                cerrarSesion();
//            }
//            retornar = true;
//        }
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment_actual");
            switch (tFragment){
                case TipoFragment.CAJAS_IN:((CajasInFragment)fragment).clickBoton();break;
                case TipoFragment.CAJAS_OUT:((CajasOutFragment)fragment).clickBoton();break;
                case TipoFragment.REGISTRO_ASISTENCIA_LOCAL:((AsistLocalFragment)fragment).clickBoton();break;
                case TipoFragment.REGISTRO_ASISTENCIA_AULA:((AsistAulaFragment)fragment).clickBoton();break;
                case TipoFragment.REGISTRO_INVENTARIO_FICHA:((InvFichaFragment)fragment).clickBoton();break;
                case TipoFragment.REGISTRO_INVENTARIO_CUADERNILLO:((InvCuaderFragment)fragment).clickBoton();break;
                case TipoFragment.REGISTRO_INVENTARIO_LISTA_ASISTENCIA:((InvListAsisFragment)fragment).clickBoton();break;
                case TipoFragment.CONSULTA_PADRON_NACIONAL:((ConsultaPadronFragment)fragment).clickBoton();break;
            }
        }

        return retornar;
    }

    public void setFragment(int tipoFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (tipoFragment){
            case TipoFragment.CAJAS_IN:
                CajasInFragment cajasInFragment = new CajasInFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, cajasInFragment,"fragment_actual");
                break;
            case TipoFragment.REGISTRO_ASISTENCIA_AULA:
                AsistAulaFragment asistAulaFragment = new AsistAulaFragment(nroLocal,MainActivity.this,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, asistAulaFragment,"fragment_actual");
                break;
            case TipoFragment.REGISTRO_ASISTENCIA_LOCAL:
                AsistLocalFragment asistLocalFragment = new AsistLocalFragment(nroLocal,MainActivity.this,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, asistLocalFragment,"fragment_actual");
                break;
            case TipoFragment.REGISTRO_INVENTARIO_CUADERNILLO:
                InvCuaderFragment invCuaderFragment = new InvCuaderFragment(nroLocal,MainActivity.this,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, invCuaderFragment,"fragment_actual");
                break;
            case TipoFragment.REGISTRO_INVENTARIO_FICHA:
                InvFichaFragment invFichaFragment = new InvFichaFragment(nroLocal,MainActivity.this,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, invFichaFragment,"fragment_actual");
                break;
            case TipoFragment.REGISTRO_INVENTARIO_LISTA_ASISTENCIA:
                InvListAsisFragment invListAsisFragment = new InvListAsisFragment(nroLocal,MainActivity.this,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, invListAsisFragment,"fragment_actual");
                break;
            case TipoFragment.CAJAS_OUT:
                CajasOutFragment cajasOutFragment = new CajasOutFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, cajasOutFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_LISTADO_INGRESO_CAJAS:
                ListIngresoCajasFragment listIngresoCajasFragment = new ListIngresoCajasFragment(MainActivity.this,nroLocal,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, listIngresoCajasFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_LISTADO_ASISTENCIA_LOCAL:
                ListAsisLocalFragment listAsisLocalFragment = new ListAsisLocalFragment(MainActivity.this,nroLocal,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, listAsisLocalFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_LISTADO_ASISTENCIA_AULA:
                ListAsisAulaFragment listAsisAulaFragment = new ListAsisAulaFragment(MainActivity.this,nroLocal,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, listAsisAulaFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_LISTADO_INVENTARIO_FICHA:
                ListInvFichaFragment listInvFichaFragment = new ListInvFichaFragment(MainActivity.this,nroLocal,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, listInvFichaFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_LISTADO_INVENTARIO_CUADERNILLO:
                ListInvCuadernilloFragment listInvCuadernilloFragment = new ListInvCuadernilloFragment(MainActivity.this,nroLocal,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, listInvCuadernilloFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_LISTADO_INVENTARIO_LISTADO_ASISTENCIA:
                ListInvListadoFragment listInvListadoFragment = new ListInvListadoFragment(MainActivity.this,nroLocal,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, listInvListadoFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_LISTADO_SALIDA_CAJAS:
                ListSalidaCajasFragment listSalidaCajasFragment = new ListSalidaCajasFragment(MainActivity.this,nroLocal,usuario);
                fragmentTransaction.replace(R.id.fragment_layout, listSalidaCajasFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_RESUMEN_INGRESO_CAJAS:
                CuadroResumenCajasInFragment cuadroResumenCajasInFragment = new CuadroResumenCajasInFragment(MainActivity.this,nroLocal);
                fragmentTransaction.replace(R.id.fragment_layout, cuadroResumenCajasInFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_RESUMEN_ASISTENCIA:
                CuadroResumenAsistenciaFragment cuadroResumenAsistenciaFragment = new CuadroResumenAsistenciaFragment();
                fragmentTransaction.replace(R.id.fragment_layout, cuadroResumenAsistenciaFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_RESUMEN_INVENTARIO:
                CuadroResumenInventarioFragment cuadroResumenInventarioFragment = new CuadroResumenInventarioFragment();
                fragmentTransaction.replace(R.id.fragment_layout, cuadroResumenInventarioFragment,"fragment_actual");
                break;
            case TipoFragment.REPORTES_RESUMEN_SALIDA_CAJAS:
                CuadroResumenCajasOutFragment cuadroResumenCajasOutFragment = new CuadroResumenCajasOutFragment(MainActivity.this,nroLocal);
                fragmentTransaction.replace(R.id.fragment_layout, cuadroResumenCajasOutFragment,"fragment_actual");
                break;
            case TipoFragment.CONSULTA_PADRON_NACIONAL:
                ConsultaPadronFragment consultaPadronFragment = new ConsultaPadronFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, consultaPadronFragment,"fragment_actual");
                break;
        }
        fragmentTransaction.commit();
    }

    private void enableExpandableList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        expListView = (ExpandableListView) findViewById(R.id.left_drawer);

        prepareListData(listDataHeader, listDataChild);
        listAdapter = new ExpandListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(rol == 3){
                    switch (groupPosition){
                        case 0:
                            switch (childPosition){
                                case 0: setFragment(TipoFragment.CAJAS_IN);tFragment = TipoFragment.CAJAS_IN;break;
                            }break;
                        case 1:
                            switch (childPosition){
                                case 0: setFragment(TipoFragment.CAJAS_OUT);tFragment = TipoFragment.CAJAS_OUT;break;
                            }break;
                        case 2:
                            switch (childPosition){
                                case 0: setFragment(TipoFragment.REPORTES_LISTADO_INGRESO_CAJAS);tFragment = TipoFragment.REPORTES_LISTADO_INGRESO_CAJAS;break;
                                case 1: setFragment(TipoFragment.REPORTES_LISTADO_SALIDA_CAJAS);tFragment = TipoFragment.REPORTES_LISTADO_SALIDA_CAJAS;break;
                                case 2: setFragment(TipoFragment.REPORTES_RESUMEN_INGRESO_CAJAS);tFragment = TipoFragment.REPORTES_RESUMEN_INGRESO_CAJAS;break;
                                case 3: setFragment(TipoFragment.REPORTES_RESUMEN_SALIDA_CAJAS);tFragment = TipoFragment.REPORTES_RESUMEN_SALIDA_CAJAS;break;
                            }break;
                        case 3:
                            if (numeroVersion < 5){
                                switch (childPosition){
                                    case 0: borrarData();setFragment(TipoFragment.CAJAS_IN);tFragment = TipoFragment.CAJAS_IN;tFragment = TipoFragment.CAJAS_IN;break;
                                    case 1: cerrarSesion();break;
                                }break;
                            }else{
                                switch (childPosition){
                                    case 0: cerrarSesion();break;
                                }break;
                            }

                    }
                }else{
                    switch (groupPosition){
                        case 0:
                            switch (childPosition){
                                case 0: setFragment(TipoFragment.REGISTRO_ASISTENCIA_LOCAL);tFragment = TipoFragment.REGISTRO_ASISTENCIA_LOCAL;break;
                                case 1: setFragment(TipoFragment.REGISTRO_ASISTENCIA_AULA);tFragment = TipoFragment.REGISTRO_ASISTENCIA_AULA;break;
                            }
                            break;
                        case 1:
                            switch (childPosition){
                                case 0: setFragment(TipoFragment.REGISTRO_INVENTARIO_FICHA);tFragment = TipoFragment.REGISTRO_INVENTARIO_FICHA;break;
                                case 1: setFragment(TipoFragment.REGISTRO_INVENTARIO_CUADERNILLO);tFragment = TipoFragment.REGISTRO_INVENTARIO_CUADERNILLO;break;
                                case 2: setFragment(TipoFragment.REGISTRO_INVENTARIO_LISTA_ASISTENCIA);tFragment = TipoFragment.REGISTRO_INVENTARIO_LISTA_ASISTENCIA;break;
                            }break;
                        case 2:
                            switch (childPosition){

                                case 0: setFragment(TipoFragment.REPORTES_LISTADO_ASISTENCIA_LOCAL);tFragment = TipoFragment.REPORTES_LISTADO_ASISTENCIA_LOCAL;break;
                                case 1: setFragment(TipoFragment.REPORTES_LISTADO_ASISTENCIA_AULA);tFragment = TipoFragment.REPORTES_LISTADO_ASISTENCIA_AULA;break;
                                case 2: setFragment(TipoFragment.REPORTES_LISTADO_INVENTARIO_FICHA);tFragment = TipoFragment.REPORTES_LISTADO_INVENTARIO_FICHA;break;
                                case 3: setFragment(TipoFragment.REPORTES_LISTADO_INVENTARIO_CUADERNILLO);tFragment = TipoFragment.REPORTES_LISTADO_INVENTARIO_CUADERNILLO;break;
                                case 4: setFragment(TipoFragment.REPORTES_LISTADO_INVENTARIO_LISTADO_ASISTENCIA);tFragment = TipoFragment.REPORTES_LISTADO_INVENTARIO_LISTADO_ASISTENCIA;break;
                                case 5: setFragment(TipoFragment.REPORTES_RESUMEN_ASISTENCIA);tFragment = TipoFragment.REPORTES_RESUMEN_ASISTENCIA;break;
                                case 6: setFragment(TipoFragment.REPORTES_RESUMEN_INVENTARIO);tFragment = TipoFragment.REPORTES_RESUMEN_INVENTARIO;break;
                            }break;
                        case 3:
                            switch (childPosition){
                                case 0: setFragment(TipoFragment.CONSULTA_PADRON_NACIONAL);tFragment = TipoFragment.CONSULTA_PADRON_NACIONAL;break;
                            }break;
                        case 4:
                            if (numeroVersion<5){
                                switch (childPosition) {
                                    case 0: borrarData();setFragment(TipoFragment.REGISTRO_ASISTENCIA_LOCAL);tFragment = TipoFragment.REGISTRO_ASISTENCIA_LOCAL;break;
                                    case 1: cerrarSesion();break;
                                }break;
                            }else{
                                switch (childPosition) {
                                    case 0: cerrarSesion();break;
                                }break;
                            }


                    }
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void prepareListData(List<String> listDataHeader, Map<String, List<String>> listDataChild) {

        if (rol == 3){
            listDataHeader.add("Ingreso de Cajas al Local");
            listDataHeader.add("Salida de Cajas del Local");
            listDataHeader.add("Reportes");

            // Adding child data
            List<String> ingresoCajas = new ArrayList<String>();
            ingresoCajas.add("Entrada de Cajas");

            List<String> salidaCajas = new ArrayList<String>();
            salidaCajas.add("Salida de Cajas");

            List<String> reportes = new ArrayList<String>();
            reportes.add("Listado Ingreso Cajas a Local");
            reportes.add("Listado Salida de Cajas del Local");
            reportes.add("Cuadro Resumen Ingreso Cajas");
            reportes.add("Cuadro Resumen Salida Cajas");
            listDataChild.put(listDataHeader.get(0), ingresoCajas);
            listDataChild.put(listDataHeader.get(1), salidaCajas);
            listDataChild.put(listDataHeader.get(2), reportes);

            listDataHeader.add("Mas Opciones");
            List<String> masOpciones = new ArrayList<String>();
            if (numeroVersion < 5){
                masOpciones.add("Reset BD");
            }
            masOpciones.add("Cerrar Sesión");
            listDataChild.put(listDataHeader.get(3), masOpciones);
        }else if (rol == 2){
            listDataHeader.add("Registro de Control de Asistencia");
            listDataHeader.add("Registro de Control de Inventario");
            listDataHeader.add("Reportes");
            listDataHeader.add("Consulta Padron Nacional");


            List<String> registroAsistencia = new ArrayList<String>();
            registroAsistencia.add("Local");
            registroAsistencia.add("Aula");
            List<String> registroInventario = new ArrayList<String>();
            registroInventario.add("Ficha");
            registroInventario.add("Cuadernillo");
            registroInventario.add("Listado de Asistencia");
            List<String> reportes = new ArrayList<String>();
            reportes.add("Listado de Asistencia por Local");
            reportes.add("Listado de Asistencia por Aula");
            reportes.add("Listado de Inventario - Ficha");
            reportes.add("Listado de Inventario - Cuadernillo");
            reportes.add("Listado de Inventario - Listado de Asistencia");
//        reportes.add("Cuadro Resumen Asistencia");
//        reportes.add("Cuadro Resumen Inventario");
            List<String> consultaPadron = new ArrayList<String>();
            consultaPadron.add("Consulta Padrón Nacional");

            listDataChild.put(listDataHeader.get(0), registroAsistencia);
            listDataChild.put(listDataHeader.get(1), registroInventario);
            listDataChild.put(listDataHeader.get(2), reportes);
            listDataChild.put(listDataHeader.get(3), consultaPadron);

            listDataHeader.add("Mas Opciones");
            List<String> masOpciones = new ArrayList<String>();
            if (numeroVersion < 5){
                masOpciones.add("Reset BD");
            }
            masOpciones.add("Cerrar Sesión");
            listDataChild.put(listDataHeader.get(4), masOpciones);
        }
    }
    public void borrarData(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("¿Está seguro que desea borrar todos los datos, tendrá que volver a iniciar sesión?")
                .setTitle("Aviso").setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Data data = new Data(MainActivity.this);
                        data.open();
                        data.deleteAllElementosFromTabla(SQLConstantes.tablacajasreg);
                        data.deleteAllElementosFromTabla(SQLConstantes.tablaasistenciasreg);
                        data.deleteAllElementosFromTabla(SQLConstantes.tablainventariosreg);
                        data.deleteAllElementosFromTabla(SQLConstantes.tablainventariosreg);
                        data.deleteAllElementosFromTabla(SQLConstantes.tablahistorialusuarios);
                        data.close();
                        CajasInFragment cajasInFragment = new CajasInFragment(nroLocal,MainActivity.this);
                        FragmentManager fragmentManage = getSupportFragmentManager();
                        FragmentTransaction fragmentTransact = fragmentManage.beginTransaction();
                        fragmentTransact.replace(R.id.fragment_layout, cajasInFragment);
                        fragmentTransact.commit();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void cerrarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("¿Está seguro que desea cerrar sesión?")
                .setTitle("Aviso").setCancelable(false)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
