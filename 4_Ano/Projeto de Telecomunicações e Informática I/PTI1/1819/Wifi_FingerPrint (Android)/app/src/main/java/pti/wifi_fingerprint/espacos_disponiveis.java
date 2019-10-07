package pti.wifi_fingerprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;

public class espacos_disponiveis extends AppCompatActivity {
    private ListView listView;
    private ArrayList<element_espaco> espacos_list=new ArrayList<>();
    private RequestQueue request;
    private JsonObjectRequest requestObj;
    private Espacos_adapter adapter;
    private String url_espacos = "http://ec2-34-207-88-224.compute-1.amazonaws.com:9000/espacos";

    private ArrayList<String> defs=new ArrayList<>();
    private ArrayList<String> nomes_espacos=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_espacos);

        listView = findViewById(R.id.list_espacos);
        adapter=new Espacos_adapter(this,espacos_list);
        listView.setAdapter(adapter);
        obter_espacos();
    }

    public void obter_espacos(){
        System.out.println("entrou");
        final JSONObject postparams = new JSONObject();

        request = Volley.newRequestQueue(this);
        requestObj = new JsonObjectRequest(Request.Method.GET, url_espacos, null, new Response.Listener<JSONObject>(){
        String nome_espaco;
        String nome_dono;
        String url_espaco;

        @Override
        public void onResponse(JSONObject response) {
            System.out.println("sim");
            try {
                JSONArray espacos = response.getJSONArray("espacos");
                for (int i = 0; i < espacos.length(); i++) {
                    System.out.println("sim espaco");
                    JSONObject obj = espacos.getJSONObject(i);

                    nome_espaco = obj.getString("nome_espaco");
                    url_espaco = obj.getString("url_imagem");
                    nome_dono=obj.getString("nome_Dono");

                    System.out.println(nome_espaco);
                    System.out.println(url_espaco);
                    defs.add(url_espaco);

                    nomes_espacos.add(nome_espaco);
                    espacos_list.add(new element_espaco(nome_espaco,nome_dono));
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(espacos_disponiveis.this, entrar_num_espaco.class);
                    System.out.println("url a enviar é:"+defs.get(position));
                    intent.putExtra("url", defs.get(position));
                    intent.putExtra("nome_espaco",nomes_espacos.get(position));
                    startActivity(intent);
                    //Toast.makeText(getApplication(), position, Toast.LENGTH_LONG).show();
                }
            });
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
