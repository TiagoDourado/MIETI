package pti.wifi_fingerprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Espacos_adapter extends ArrayAdapter<element_espaco> {
    private Context context;
    private ArrayList<element_espaco> aps=new ArrayList<>();

    public Espacos_adapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<element_espaco> list) {
        super(context,0,list);
        this.context=context;
        aps=list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.activity_element_espaco, parent, false);
        }
        element_espaco element = aps.get(position);

        TextView nome_espaco = (TextView) listItem.findViewById(R.id.nome_expaco);
        nome_espaco.setText("Nome espaco:" +element.getNome_espaco());

        TextView nome_dono = (TextView) listItem.findViewById(R.id.dono);
        nome_dono.setText("Nome dono:" +element.getNome_dono());

        return listItem;
    }
}
