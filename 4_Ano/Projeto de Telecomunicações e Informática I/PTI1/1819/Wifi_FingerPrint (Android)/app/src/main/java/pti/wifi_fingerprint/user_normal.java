package pti.wifi_fingerprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import static android.widget.Toast.LENGTH_LONG;

public class user_normal extends AppCompatActivity {
    private WifiManager wifiManager;
    private ListView listView;
    private Button buttonScan;
    private int size = 0;
    private List<ScanResult> results;
    private ArrayList<Element> ap_list=new ArrayList<>();
    private AccessPoint_adapter adapter;
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private TextView nome_utilizador;
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_normal);

        int delay=5000;
        int intervalo=1000;
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                scanWifi(ap_list);
            }
        },delay,intervalo);

        listView = findViewById(R.id.wifiList);
        adapter=new AccessPoint_adapter(this,ap_list);
        listView.setAdapter(adapter);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

        //Definir o Drawer
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer=(DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle = setupDrawerToggle();

        navigationView = findViewById(R.id.navigationView);
        setupDrawerContent(navigationView);


        ////////////////////////////////////
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WiFi is disabled ... We need to enable it", LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
    }

    private void scanWifi(ArrayList<Element> ap_list) {
        ap_list.clear();
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            results = wifiManager.getScanResults();    //obtencao da lista de todos os AP's
            unregisterReceiver(this);
            for (ScanResult result : results) {
                int intensidade = WifiManager.calculateSignalLevel(wifi.getConnectionInfo().getRssi(), result.level);
                ap_list.add(new Element(result.SSID, intensidade, result.BSSID));
                adapter.notifyDataSetChanged();
            }
        };
    };

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // The action bar home/up action should open or close the drawer.
            switch (item.getItemId()) {
                case android.R.id.home:
                    mDrawer.openDrawer(GravityCompat.START);
                    return true;
            }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()   {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;  //criacao de um fragmento
        Class fragmentClass;
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                Intent intent=new Intent(user_normal.this, espacos_disponiveis.class);  ///////////////////
                startActivity(intent);
                break;

            case R.id.gerir_conta:
                Intent intent2=new Intent(user_normal.this, definicoes.class);  ///////////////////
                startActivity(intent2);
                break;

            default:
            case R.id.nav_third_fragment:
                Intent intent3=new Intent(user_normal.this, MainActivity.class);
                startActivity(intent3);
        }


    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }
}



