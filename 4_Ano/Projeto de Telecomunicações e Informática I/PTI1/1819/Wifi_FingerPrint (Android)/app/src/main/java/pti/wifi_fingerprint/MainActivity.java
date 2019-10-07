package pti.wifi_fingerprint;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_LONG;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private EditText user, pass;
    private Button Login, nova_conta;
    private RequestQueue request;
    private JsonObjectRequest requestObj;
    private String url = "http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/utilizadores";
    private HashMap<String, String> dados_user = new HashMap<>();

    public static String user_name;

    private static HashMap<String, String> com_pergunta = new HashMap<>();

    private int counter = 0;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        Login = (Button) findViewById(R.id.start);
        nova_conta = (Button) findViewById(R.id.create);
        //obter_pass=(Button) findViewById(R.id.forgotten);

        Login.setOnClickListener(this);
        nova_conta.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Iniciar sessão");
        //obter_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                Confirmar_acesso();
                break;

            case R.id.create:
                Intent intent2 = new Intent(MainActivity.this, criar_conta.class);
                startActivity(intent2);
                break;
        }
    }

    public void recovery_password() {
        password psw = new password();
        psw.show(getSupportFragmentManager(), "password");
    }

    public void setUser(String user) {
        this.user_name = user;
    }

    public void Confirmar_acesso() {
        System.out.println("entrou");
        final String userName_introduzido = user.getText().toString();
        final String pass_introduzida = pass.getText().toString();

        request = Volley.newRequestQueue(this);
        requestObj = new JsonObjectRequest(Request.Method.GET, url + "/" + userName_introduzido + "/" + pass_introduzida, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    System.out.println("status:" + status);

                    if (status.equals("premium")) {
                        System.out.println("sou o maior");
                        progressDialog.show();
                        new Iniciar().execute();
                        //setTipoUser("premium");
                        //System.out.println(tipoUser);
                        Intent intent = new Intent(MainActivity.this, user_normal.class);
                        intent.putExtra("nome_utilizador", userName_introduzido);
                        startActivity(intent);
                    }

                    if (status.equals("ok normal")) {

                        progressDialog.show();
                        new Iniciar().execute();
                        Intent intent = new Intent(MainActivity.this, user_normal.class);
                        intent.putExtra("nome_utilizador", userName_introduzido);
                        startActivity(intent);
                    }
                    if (status.equals("ok dono")) {

                        progressDialog.show();
                        new Iniciar().execute();
                        setUser(userName_introduzido);
                        System.out.println("nome Introduzido:" + user_name);
                        Intent intent = new Intent(MainActivity.this, Dono.class);
                        intent.putExtra("nome_utilizador", userName_introduzido);
                        startActivity(intent);
                    }
                    if (status.equals("fail")) {
                        Toast.makeText(getApplication(), "Dados incorretos", Toast.LENGTH_LONG).show();
                    }
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


    private class Iniciar extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //progressDialog.cancel();
        }
    }

}




