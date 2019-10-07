package pti.wifi_fingerprint;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.*;
import static android.widget.Toast.LENGTH_LONG;

public class calibrar extends AppCompatActivity {

    int imageX = 0, imageY = 0;
    int[] location = new int[2];
    Bitmap bitmap;

    private String url_espaco = "http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/espaco";
    private String url_rp="http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/fingerprint";
    //private String url_ap = "http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/AP";

    ///////////////////

    private WifiManager wifiManager;
    private int size = 0;

    private List<ScanResult> results;
    private ArrayList<Element> ap_list=new ArrayList<>();
    private AccessPoint_adapter adapter;
    private String xxx;
    private String y;
    private String url;
    private  String url_foto;
    private String nome_espaco;
    //private PinView imageView;
    PinView imageView;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibrar);
        imageView= (PinView) findViewById(R.id.imageView);
////////////////////////////////////////////////
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            url=bundle.getString("url");
            System.out.println("url é:"+url);
            nome_espaco=bundle.getString("nome_espaco");
            url_foto="http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/"+url;
            System.out.println("url foto:"+url_foto);
            getImage();
        }

        final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (imageView.isReady()) {

                    wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

                    PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());

                    imageView.setPin(sCoord);

                    int xx = (int)sCoord.x;
                    int yy = (int)sCoord.y;

                    xxx = Integer.toString(xx);
                    y   = Integer.toString(yy);

                    System.out.println("xxx: " +xxx +" y: " +y);

                    String text = "Coords: " + sCoord;
                    Toast.makeText(calibrar.this, text, Toast.LENGTH_SHORT).show();

                    scanWifi(ap_list);
                }

                if (!wifiManager.isWifiEnabled()) {
                    Toast.makeText(calibrar.this, "WiFi is disabled ... We need to enable it", LENGTH_LONG).show();
                    wifiManager.setWifiEnabled(true);
                }

                return true;
            }
        });
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    private void getImage(){
        System.out.println("Entrou no get Image");
        ImageRequest imageRequest=new ImageRequest(url_foto,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImage(ImageSource.bitmap(response));
                    }
                }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(calibrar.this).addToRequestQueue(imageRequest);


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
                //int x = 0;
                for (ScanResult result : results) {
                    int intensidade = WifiManager.calculateSignalLevel(wifi.getConnectionInfo().getRssi(), result.level);
                    //System.out.println("Intensidade: " +intensidade);
                    //RSSI[x] = intensidade;
                    //x++;
                    ap_list.add(new Element(result.SSID, intensidade, result.BSSID));
                    try {
                        System.out.println("x: " +Integer.parseInt(xxx) + " y: " + Integer.parseInt(y));
                        post_FingerPrint(nome_espaco,result.BSSID, Integer.parseInt(xxx), Integer.parseInt(y), intensidade, false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("tamanho: " + tam);
                for (int i = tam; i < 10; i++) {
               //     RSSI[i] = 0;
                    try {
                        System.out.println("X: " +Integer.parseInt(xxx) + "y: " + Integer.parseInt(y));
                        post_FingerPrint(nome_espaco," ", Integer.parseInt(xxx), Integer.parseInt(y), 0,false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                //System.out.println("entrou aqui");
                int counter = 0; //apenas 10
                for (ScanResult result : results) {
                    if(counter < 10) {
                        int intensidade = WifiManager.calculateSignalLevel(wifi.getConnectionInfo().getRssi(), result.level);
                        //RSSI[counter] = intensidade;
                        ap_list.add(new Element(result.SSID, intensidade, result.BSSID));
                        try {
                            //System.out.println(Arrays.toString(RSSI));
                            System.out.println("X: " +Integer.parseInt(xxx) + "y: " + Integer.parseInt(y));
                            post_FingerPrint(nome_espaco," ", Integer.parseInt(xxx), Integer.parseInt(y), 0, false);
                            counter++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    };

    int post = 0;

    public void post_FingerPrint(String nome_espaco,String mac, int touchX,int touchY,int RSSI,boolean estado) throws JSONException {
        System.out.println("Fiz umn post: " +post);
        post++;
        System.out.println("x: "+touchX+" y: "+touchY+ " RSSI: "+ RSSI);
        final JSONObject postparams = new JSONObject();
        postparams.put("MAC",mac);
        postparams.put("point_x",touchX);
        //System.out.println("x: "  +touchX);
        postparams.put("point_y",touchY);
        //System.out.println("\ny: " +touchY);
        postparams.put("RSSI", RSSI);
        postparams.put("estado",estado);

        postparams.put("nome_espaco",nome_espaco);
        //System.out.println("estado: " +estado);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url_rp, postparams, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //JSONArray users = response.getJSONArray("fingerprints");
                    //users.put(postparams);

                    String status = response.getString("status");
                    System.out.println("Status: " +status);
                    Toast.makeText(calibrar.this, status, Toast.LENGTH_SHORT).show();

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
}

