package pe.com.ricindigus.appednom2018.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.ricindigus.appednom2018.R;
import pe.com.ricindigus.appednom2018.adapters.ExpandListAdapter;
import pe.com.ricindigus.appednom2018.fragments.consulta_padron_nacional.ConsultaPadronFragment;
import pe.com.ricindigus.appednom2018.fragments.ingreso_cajas_local.CajasInFragment;
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
import pe.com.ricindigus.appednom2018.fragments.salida_cajas_local.CajasOutFragment;
import pe.com.ricindigus.appednom2018.modelo.Data;
import pe.com.ricindigus.appednom2018.modelo.SQLConstantes;
import pe.com.ricindigus.appednom2018.util.TipoFragment;

public class MainActivity extends AppCompatActivity {

    int nroLocal;
    private ArrayList<String> listDataHeader;
    private ExpandableListView expListView;
    private HashMap<String, List<String>> listDataChild;
    private ExpandListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nroLocal = getIntent().getExtras().getInt("nrolocal");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        enableExpandableList();
        setFragment(TipoFragment.CAJAS_IN);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void setFragment(int tipoFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (tipoFragment){
            case TipoFragment.CAJAS_IN:
                CajasInFragment cajasInFragment = new CajasInFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, cajasInFragment);
                break;
            case TipoFragment.REGISTRO_ASISTENCIA_AULA:
                AsistAulaFragment asistAulaFragment = new AsistAulaFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, asistAulaFragment);
                break;
            case TipoFragment.REGISTRO_ASISTENCIA_LOCAL:
                AsistLocalFragment asistLocalFragment = new AsistLocalFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, asistLocalFragment);
                break;
            case TipoFragment.REGISTRO_INVENTARIO_CUADERNILLO:
                InvCuaderFragment invCuaderFragment = new InvCuaderFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, invCuaderFragment);
                break;
            case TipoFragment.REGISTRO_INVENTARIO_FICHA:
                InvFichaFragment invFichaFragment = new InvFichaFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, invFichaFragment);
                break;
            case TipoFragment.REGISTRO_INVENTARIO_LISTA_ASISTENCIA:
                InvListAsisFragment invListAsisFragment = new InvListAsisFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, invListAsisFragment);
                break;
            case TipoFragment.CAJAS_OUT:
                CajasOutFragment cajasOutFragment = new CajasOutFragment(nroLocal,MainActivity.this);
                fragmentTransaction.replace(R.id.fragment_layout, cajasOutFragment);
                break;
            case TipoFragment.REPORTES_LISTADO_INGRESO_CAJAS:
                ListIngresoCajasFragment listIngresoCajasFragment = new ListIngresoCajasFragment();
                fragmentTransaction.replace(R.id.fragment_layout, listIngresoCajasFragment);
                break;
            case TipoFragment.REPORTES_LISTADO_ASISTENCIA_LOCAL:
                ListAsisLocalFragment listAsisLocalFragment = new ListAsisLocalFragment(MainActivity.this,nroLocal);
                fragmentTransaction.replace(R.id.fragment_layout, listAsisLocalFragment);
                break;
            case TipoFragment.REPORTES_LISTADO_ASISTENCIA_AULA:
                ListAsisAulaFragment listAsisAulaFragment = new ListAsisAulaFragment(MainActivity.this,nroLocal);
                fragmentTransaction.replace(R.id.fragment_layout, listAsisAulaFragment);
                break;
            case TipoFragment.REPORTES_LISTADO_INVENTARIO_FICHA:
                ListInvFichaFragment listInvFichaFragment = new ListInvFichaFragment(MainActivity.this,nroLocal);
                fragmentTransaction.replace(R.id.fragment_layout, listInvFichaFragment);
                break;
            case TipoFragment.REPORTES_LISTADO_INVENTARIO_CUADERNILLO:
                ListInvCuadernilloFragment listInvCuadernilloFragment = new ListInvCuadernilloFragment(MainActivity.this,nroLocal);
                fragmentTransaction.replace(R.id.fragment_layout, listInvCuadernilloFragment);
                break;
            case TipoFragment.REPORTES_LISTADO_INVENTARIO_LISTADO_ASISTENCIA:
                ListInvListadoFragment listInvListadoFragment = new ListInvListadoFragment(MainActivity.this,nroLocal);
                fragmentTransaction.replace(R.id.fragment_layout, listInvListadoFragment);
                break;
            case TipoFragment.REPORTES_LISTADO_SALIDA_CAJAS:
                ListSalidaCajasFragment listSalidaCajasFragment = new ListSalidaCajasFragment();
                fragmentTransaction.replace(R.id.fragment_layout, listSalidaCajasFragment);
                break;
            case TipoFragment.REPORTES_RESUMEN_INGRESO_CAJAS:
                CuadroResumenCajasInFragment cuadroResumenCajasInFragment = new CuadroResumenCajasInFragment();
                fragmentTransaction.replace(R.id.fragment_layout, cuadroResumenCajasInFragment);
                break;
            case TipoFragment.REPORTES_RESUMEN_ASISTENCIA:
                CuadroResumenAsistenciaFragment cuadroResumenAsistenciaFragment = new CuadroResumenAsistenciaFragment();
                fragmentTransaction.replace(R.id.fragment_layout, cuadroResumenAsistenciaFragment);
                break;
            case TipoFragment.REPORTES_RESUMEN_INVENTARIO:
                CuadroResumenInventarioFragment cuadroResumenInventarioFragment = new CuadroResumenInventarioFragment();
                fragmentTransaction.replace(R.id.fragment_layout, cuadroResumenInventarioFragment);
                break;
            case TipoFragment.REPORTES_RESUMEN_SALIDA_CAJAS:
                CuadroResumenCajasOutFragment cuadroResumenCajasOutFragment = new CuadroResumenCajasOutFragment();
                fragmentTransaction.replace(R.id.fragment_layout, cuadroResumenCajasOutFragment);
                break;
            case TipoFragment.CONSULTA_PADRON_NACIONAL:
                ConsultaPadronFragment consultaPadronFragment = new ConsultaPadronFragment();
                fragmentTransaction.replace(R.id.fragment_layout, consultaPadronFragment);
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
                switch (groupPosition){
                    case 0:
                        switch (childPosition){
                            case 0:
                                setFragment(TipoFragment.CAJAS_IN);break;
                        }
                        break;
                    case 1:
                        switch (childPosition){
                            case 0:
                                setFragment(TipoFragment.REGISTRO_ASISTENCIA_LOCAL);break;
                            case 1:
                                setFragment(TipoFragment.REGISTRO_ASISTENCIA_AULA);break;
                        }
                        break;
                    case 2:
                        switch (childPosition){
                            case 0:
                                setFragment(TipoFragment.REGISTRO_INVENTARIO_FICHA);break;
                            case 1:
                                setFragment(TipoFragment.REGISTRO_INVENTARIO_CUADERNILLO);break;
                            case 2:
                                setFragment(TipoFragment.REGISTRO_INVENTARIO_LISTA_ASISTENCIA);break;
                        }
                        break;
                    case 3:
                        switch (childPosition){
                            case 0:
                                setFragment(TipoFragment.CAJAS_OUT);break;
                        }
                        break;
                    case 4:
                        switch (childPosition){
                            case 0:
                                setFragment(TipoFragment.REPORTES_LISTADO_INGRESO_CAJAS);break;
                            case 1:
                                setFragment(TipoFragment.REPORTES_LISTADO_ASISTENCIA_LOCAL);break;
                            case 2:
                                setFragment(TipoFragment.REPORTES_LISTADO_ASISTENCIA_AULA);break;
                            case 3:
                                setFragment(TipoFragment.REPORTES_LISTADO_INVENTARIO_FICHA);break;
                            case 4:
                                setFragment(TipoFragment.REPORTES_LISTADO_INVENTARIO_CUADERNILLO);break;
                            case 5:
                                setFragment(TipoFragment.REPORTES_LISTADO_INVENTARIO_LISTADO_ASISTENCIA);break;
                            case 6:
                                setFragment(TipoFragment.REPORTES_LISTADO_SALIDA_CAJAS);break;
                            case 7:
                                setFragment(TipoFragment.REPORTES_RESUMEN_INGRESO_CAJAS);break;
                            case 8:
                                setFragment(TipoFragment.REPORTES_RESUMEN_ASISTENCIA);break;
                            case 9:
                                setFragment(TipoFragment.REPORTES_RESUMEN_INVENTARIO);break;
                            case 10:
                                setFragment(TipoFragment.REPORTES_RESUMEN_SALIDA_CAJAS);break;
                        }
                        break;
                    case 5:
                        switch (childPosition){
                            case 0:
                                setFragment(TipoFragment.CONSULTA_PADRON_NACIONAL);break;
                        }
                        break;
                    case 6:
                        switch (childPosition){
                            case 0:
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("¿Está seguro que desea borrar los datos?")
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
                                                data.deleteAllElementosFromTabla(SQLConstantes.tablaasisaula);
                                                data.deleteAllElementosFromTabla(SQLConstantes.tablaasislocal);
                                                data.deleteAllElementosFromTabla(SQLConstantes.tablafichas);
                                                data.deleteAllElementosFromTabla(SQLConstantes.tablacuadernillos);
                                                data.deleteAllElementosFromTabla(SQLConstantes.tablalistados);
                                                data.close();
//                                        ListadoFragment listadoFragment = new ListadoFragment(sede,MainActivity.this);
//                                        FragmentManager fragmentManage = getSupportFragmentManager();
//                                        FragmentTransaction fragmentTransact = fragmentManage.beginTransaction();
//                                        fragmentTransact.replace(R.id.fragment_layout, listadoFragment);
//                                        fragmentTransact.commit();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                        }
                        break;

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void prepareListData(List<String> listDataHeader, Map<String, List<String>> listDataChild) {

        // Adding child data
        listDataHeader.add("Ingreso de Cajas al Local");
        listDataHeader.add("Registro de Control de Asistencia");
        listDataHeader.add("Registro de Control de Inventario");
        listDataHeader.add("Salida de Cajas del Local");
        listDataHeader.add("Reportes");
        listDataHeader.add("Consulta Padron Nacional");
        listDataHeader.add("Mas Opciones");


        // Adding child data
        List<String> ingresoCajas = new ArrayList<String>();
        ingresoCajas.add("Cajas");

        List<String> registroAsistencia = new ArrayList<String>();
        registroAsistencia.add("Local");
        registroAsistencia.add("Aula");

        List<String> registroInventario = new ArrayList<String>();
        registroInventario.add("Ficha");
        registroInventario.add("Cuadernillo");
        registroInventario.add("Listado de Asistencia");

        List<String> salidaCajas = new ArrayList<String>();
        salidaCajas.add("Cajas");

        List<String> reportes = new ArrayList<String>();
        reportes.add("Listado Ingreso Cajas a Local");
        reportes.add("Listado de Asistencia por Local");
        reportes.add("Listado de Asistencia por Aula");
        reportes.add("Listado de Inventario - Ficha");
        reportes.add("Listado de Inventario - Cuadernillo");
        reportes.add("Listado de Inventario - Listado de Asistencia");
        reportes.add("Listado Salida de Cajas del Local");
        reportes.add("Cuadro Resumen Ingreso Cajas");
        reportes.add("Cuadro Resumen Asistencia");
        reportes.add("Cuadro Resumen Inventario");
        reportes.add("Cuadro Resumen Salida Cajas");

        List<String> consultaPadron = new ArrayList<String>();
        consultaPadron.add("Consulta Padrón Nacional");

        List<String> masOpciones = new ArrayList<String>();
        masOpciones.add("Reset BD");


        listDataChild.put(listDataHeader.get(0), ingresoCajas);// Header, Child data
        listDataChild.put(listDataHeader.get(1), registroAsistencia);
        listDataChild.put(listDataHeader.get(2), registroInventario);
        listDataChild.put(listDataHeader.get(3), salidaCajas);
        listDataChild.put(listDataHeader.get(4), reportes);
        listDataChild.put(listDataHeader.get(5), consultaPadron);
        listDataChild.put(listDataHeader.get(6), masOpciones);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.showHome) {
//            Toast.makeText(this, "showHome", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        int id = item.getItemId();
//
//        if (id == R.id.menu_registro_entrada) {
//            EntradaFragment entradaFragment = new EntradaFragment(sede,MainActivity.this);
//            fragmentTransaction.replace(R.id.fragment_layout, entradaFragment);
//            fragmentTransaction.commit();
//        } else if (id == R.id.menu_registro_salida) {
//            SalidaFragment salidaFragment = new SalidaFragment(sede,MainActivity.this);
//            fragmentTransaction.replace(R.id.fragment_layout, salidaFragment);
//            fragmentTransaction.commit();
//        } else if (id == R.id.menu_listado) {
//            ListadoFragment listadoFragment = new ListadoFragment(sede,MainActivity.this);
//            fragmentTransaction.replace(R.id.fragment_layout, listadoFragment);
//            fragmentTransaction.commit();
//        } else if (id == R.id.menu_nube) {
//            NubeFragment nubeFragment = new NubeFragment(sede,MainActivity.this);
//            fragmentTransaction.replace(R.id.fragment_layout, nubeFragment);
//            fragmentTransaction.commit();
//
//        } else if (id == R.id.menu_reset_bd) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("¿Está seguro que desea borrar los datos?")
//                    .setTitle("Aviso")
//                    .setCancelable(false)
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    })
//                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            try {
//                                Data data = new Data(MainActivity.this);
//                                data.open();
//                                data.deleteAllElementosFromTabla(SQLConstantes.tablaregistro);
//                                data.close();
//                                ListadoFragment listadoFragment = new ListadoFragment(sede,MainActivity.this);
//                                FragmentManager fragmentManage = getSupportFragmentManager();
//                                FragmentTransaction fragmentTransact = fragmentManage.beginTransaction();
//                                fragmentTransact.replace(R.id.fragment_layout, listadoFragment);
//                                fragmentTransact.commit();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//            AlertDialog alert = builder.create();
//            alert.show();
//
//
//        } else if (id == R.id.menu_cerrar_sesion) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage("¿Está seguro que desea cerrar sesión en la aplicación?")
//                    .setTitle("Aviso")
//                    .setCancelable(false)
//                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            })
//                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            });
//            AlertDialog alert = builder.create();
//            alert.show();
//        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
