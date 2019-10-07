package pti.wifi_fingerprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class criar_conta extends AppCompatActivity implements View.OnClickListener {
    private HashMap<String, String> dados_user = new HashMap<>();
    private HashMap<String, String> enviar = new HashMap<>();
    private Button submeter;
    private String url = "http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/utilizadores";
    private EditText user, pass, pass_again, mail, perguntar, resposta;
    private RequestQueue request;
    private StringRequest StringRequest;
    private JsonObjectRequest requestObj, postObj;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criar_conta);

        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText4);
        pass_again = (EditText) findViewById(R.id.editText5);
        mail = (EditText) findViewById(R.id.editText6);
        perguntar = (EditText) findViewById(R.id.editText7);
        resposta = (EditText) findViewById(R.id.editText8);
        submeter = (Button) findViewById(R.id.submeter);
        submeter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Boolean bool;
        switch (v.getId()) {
            case R.id.submeter:
                try {
                    verificar_dados();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

    public void verificar_dados() throws JSONException {
        String userName_introduzido = user.getText().toString();
        String pass_introduzida = pass.getText().toString();
        String rep_pass = pass_again.getText().toString();
        String Email = mail.getText().toString();
        String perguntaDeSeguranca = perguntar.getText().toString();
        String resposta_pergunta = resposta.getText().toString();

        request = Volley.newRequestQueue(this);
        requestObj = new JsonObjectRequest(Request.Method.GET, url+"/"+userName_introduzido+"/"+pass_introduzida, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    System.out.println("status:" +status );

                    if(status.equals("fail")){
                        Intent intent = new Intent(criar_conta.this, APs_disponiveis.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplication(), "Registo impossível", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        if (userName_introduzido.isEmpty() || pass_introduzida.isEmpty() || rep_pass.isEmpty() || Email.isEmpty() /*|| perguntaDeSeguranca.isEmpty() || resposta_pergunta.isEmpty()*/) {
            Toast.makeText(getApplication(), "Campos de preenchimento obrigatório", Toast.LENGTH_LONG).show();
        } else {
            if (pass_introduzida.equals(rep_pass)) {
                Intent intent = new Intent(criar_conta.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplication(), "As passwords não coincidem", Toast.LENGTH_LONG).show();
            }
        }

        post_data();
    }


    public void post_data() throws JSONException {
        final String userName = user.getText().toString();
        final String pass_introduzida = pass.getText().toString();
        final String Email = mail.getText().toString();

        //JSONObject obj=new JSONObject();

        final JSONObject postparams = new JSONObject();

        postparams.put("name",userName);
        postparams.put("password",pass_introduzida);
        postparams.put("email",Email);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray users = response.getJSONArray("users");
                    users.put(postparams);

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