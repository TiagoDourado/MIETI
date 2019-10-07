package pti.wifi_fingerprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class definicoes extends AppCompatActivity {

    private EditText old_pass;
    private EditText new_pass;
    private Button button;
    private String url_users="http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/utilizadores";
    private RequestQueue request;
    private JsonObjectRequest requestObj;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.definicoes);

        old_pass=findViewById(R.id.old_pass);
        new_pass=findViewById(R.id.new_pass);
        button=findViewById(R.id.button_confirmar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirmar();
            }
        });
    }

    public void Confirmar() {
        request = Volley.newRequestQueue(this);
        requestObj = new JsonObjectRequest(Request.Method.GET, url_users, null, new Response.Listener<JSONObject>(){
            String password_old;
            String user;

            @Override
            public void onResponse(JSONObject response) {
                //System.out.println("sim");

                if(old_pass.getText().equals(" ") || new_pass.getText().equals(" ")){
                    Toast.makeText(getApplication(), "Prenchimento obrigatório", Toast.LENGTH_LONG).show();
                }
                try {
                    JSONArray espacos = response.getJSONArray("users");
                    for (int i = 0; i < espacos.length(); i++) {
                        JSONObject obj = espacos.getJSONObject(i);

                        if(obj.getString("name").equals(MainActivity.user_name)) {
                            user=obj.getString("name");
                            password_old = obj.getString("password");
                            alterar(user);
                        }
                        else{
                            Toast.makeText(getApplication(), "O nome de utilizador não coincide com o de login", Toast.LENGTH_LONG).show();
                        }
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

    public void alterar(String user) throws JSONException {
        final JSONObject postparams = new JSONObject();

        postparams.put("password",new_pass.getText());

        request = Volley.newRequestQueue(this);
        requestObj = new JsonObjectRequest(Request.Method.PATCH, url_users, null, new Response.Listener<JSONObject>(){

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray users = response.getJSONArray("users");
                    users.put(postparams);

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
