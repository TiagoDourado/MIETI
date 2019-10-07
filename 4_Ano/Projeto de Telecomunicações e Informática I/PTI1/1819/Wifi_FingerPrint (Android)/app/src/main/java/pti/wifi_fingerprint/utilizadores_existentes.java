package pti.wifi_fingerprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class utilizadores_existentes extends AppCompatActivity {
    private ListView listView;
    private ArrayList<element_utilizadores> utilizadores_list=new ArrayList<>();
    private RequestQueue request;
    private JsonObjectRequest requestObj;
    private Utilizadores_adapter adapter;
    private String url_utilizadores = "http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/utilizadores";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_utilizadores_existentes);

        listView=findViewById(R.id.list_utilizadores);
        adapter=new Utilizadores_adapter(this,utilizadores_list);
        listView.setAdapter(adapter);
        obter_utilizadores();
    }

    public void obter_utilizadores(){
        System.out.println("entrou");

        request = Volley.newRequestQueue(this);
        requestObj = new JsonObjectRequest(Request.Method.GET, url_utilizadores, null, new Response.Listener<JSONObject>(){
            String nome_user;
            String tipo;

                @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray utilizadores = response.getJSONArray("users");
                    for (int i = 0; i < utilizadores.length(); i++) {
                        System.out.println("sim espaco");
                        JSONObject obj = utilizadores.getJSONObject(i);

                        if(obj.getString("name").equals("Premium") || obj.getString("name").equals("Normal")) {
                            nome_user = obj.getString("name");
                            tipo = obj.getString("tipo");
                            //defs.add(url_espaco);

                            utilizadores_list.add(new element_utilizadores(nome_user, tipo));
                            adapter.notifyDataSetChanged();
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
}
