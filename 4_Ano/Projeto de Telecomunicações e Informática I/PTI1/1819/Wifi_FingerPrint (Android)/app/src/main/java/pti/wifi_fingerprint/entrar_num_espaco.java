package pti.wifi_fingerprint;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.davemorrissey.labs.subscaleview.ImageSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.widget.Toast.LENGTH_LONG;
import static java.lang.Thread.sleep;


public class entrar_num_espaco extends AppCompatActivity{
    private Button button;
    //private Button enviar_rssi;
    private ImageView imageView;

   // private Button button_calculo;
    private String url_rp="http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/fingerprint";

    private RequestQueue request;
    private JsonObjectRequest requestObj;

    private WifiManager wifiManager;
    private int size = 0;

    private List<ScanResult> results;
    private ArrayList<Element> ap_list=new ArrayList<>();
    //private String url="http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/fotos/2019-01-24T01:07:17.063Zreceived_2088636827922516.png";
    private String url;
    private  String url_foto;
    String x,y;
    private String nome_espaco;

    private PinView pinView;

    //private int[] RSSI = new int[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_num_espaco);
        //imageView=findViewById(R.id.)
         pinView = (PinView) findViewById(R.id.imageView);

        //pinView.setImage(ImageSource.bitmap("/storage/emulated/0/WhatsApp/Media/WhatsApp Images/IMG-20190119-WA0000.jpg"));
        Button button = findViewById(R.id.bt_local);
        Button enviar_rssi=findViewById(R.id.bt_online);

       Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            url=bundle.getString("url");
            System.out.println("url é:"+url);
            url_foto="http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/"+url;
            nome_espaco=bundle.getString("nome_espaco");
            System.out.println("url foto:"+url_foto);
            System.out.println("nome espaco:"+nome_espaco);
            getImage();
        }

        enviar_rssi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                scanWifi(ap_list);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obter_localizacao();
            }
        });
    }

    private void getImage(){
        System.out.println("Entrou no get Image");
        ImageRequest imageRequest=new ImageRequest(url_foto,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        //imageView.setImageBitmap(response);
                        pinView.setImage(ImageSource.bitmap(response));
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(entrar_num_espaco.this).addToRequestQueue(imageRequest);
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

            int tam = results.size();
            System.out.println("tam: " +tam);

            if(tam < 10) {
                int x = 0;
                for (ScanResult result : results) {
                    int intensidade = WifiManager.calculateSignalLevel(wifi.getConnectionInfo().getRssi(), result.level);
                    //RSSI[x] = intensidade;
                    //x++;
                    ap_list.add(new Element(result.SSID, intensidade, result.BSSID));
                    try {
                        post_FingerPrint(nome_espaco,result.BSSID,0, 0, intensidade, true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("tamanho: " + tam);
                for (int i = tam; i < 10; i++) {
                    try {
                        post_FingerPrint(nome_espaco, " ", 0, 0,0,true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                //System.out.println("entrou aqui");
                int counter = 0; //apenas 10
                for (ScanResult result : results) {
                    //if(counter < 10) {
                        int intensidade = WifiManager.calculateSignalLevel(wifi.getConnectionInfo().getRssi(), result.level);
                        //RSSI[counter] = intensidade;
                        ap_list.add(new Element(result.SSID, intensidade, result.BSSID));
                        //counter++;
                    try {
                        post_FingerPrint(nome_espaco,result.BSSID,0, 0, intensidade,true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
               /* }
                try {

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        };
    };
    public void post_FingerPrint(String nome_espaco,String mac,int touchX,int touchY,int RSSI,boolean estado) throws JSONException {
        System.out.println("x:"+touchX+",y:"+touchY+ ",RSSI:"+RSSI);
        final JSONObject postparams = new JSONObject();
        //postparams.put("MAC",mac);
        postparams.put("point_x",touchX);
        //System.out.println("x: "  +touchX);
        postparams.put("point_y",touchY);
        postparams.put("nome_espaco",nome_espaco);
        postparams.put("MAC",mac);
        //System.out.println("\ny: " +touchY);
        postparams.put("RSSI",RSSI);
        postparams.put("estado",estado);
        //System.out.println("estado: " +estado);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url_rp, postparams, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray users = response.getJSONArray("");
                    users.put(postparams);


                    //Toast.makeText(entrar_num_espaco.this, status, Toast.LENGTH_SHORT).show();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Failure Callback
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjReq);
    }

    public void obter_localizacao() {

        request = Volley.newRequestQueue(this);
        requestObj = new JsonObjectRequest(Request.Method.GET, url_rp+"/"+nome_espaco, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String longitude = response.getString("longitude");
                    System.out.println("longitude: " +longitude);

                    String latitude = response.getString("latitude");
                    System.out.println("latitude: " +latitude);

                    PointF pointF = new PointF(Float.parseFloat(longitude), Float.parseFloat(latitude));
                    pinView.setPin(pointF);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.add(requestObj);
    }
}

