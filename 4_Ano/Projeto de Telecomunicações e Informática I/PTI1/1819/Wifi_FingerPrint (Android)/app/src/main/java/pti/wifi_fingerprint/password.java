package pti.wifi_fingerprint;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.HashMap;

public class password extends AppCompatDialogFragment  {
   /* private TextView pergunta;
    private EditText resposta;
    private String url = "http://www.mocky.io/v2/5c002cae3200005000b286f2";
    private String userName_introduzido;
    private HashMap<String,String> dados_user=new HashMap<>();
    private JsonObjectRequest requestObj;
    private RequestQueue request;
    private  int counter=0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.password, null);
        builder.setView(view).setTitle("Recuperar Password").setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        pergunta=view.findViewById(R.id.pergunta);
        resposta=view.findViewById(R.id.resposta);

        //userName_introduzido= MainActivity.getUser();
        //dados_user=MainActivity.getPerguntas();

        for(String s : dados_user.keySet()){
            if(s.equals(userName_introduzido)){
                pergunta.setText(dados_user.get(s));
            }
        }
        return builder.create();
    }  */

}
