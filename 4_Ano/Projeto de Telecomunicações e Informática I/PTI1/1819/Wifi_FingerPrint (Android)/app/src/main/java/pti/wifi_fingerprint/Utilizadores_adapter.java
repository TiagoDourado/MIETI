package pti.wifi_fingerprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Utilizadores_adapter extends ArrayAdapter<element_utilizadores> {
    private Context context;
    private ArrayList<element_utilizadores> aps=new ArrayList<>();

    public Utilizadores_adapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<element_utilizadores> list) {
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
        element_utilizadores element = aps.get(position);

        TextView nome_espaco = (TextView) listItem.findViewById(R.id.nome_expaco);
        nome_espaco.setText("Nome Utilizador:" +element.getNome_utilizador());

        TextView nome_dono = (TextView) listItem.findViewById(R.id.dono);
        nome_dono.setText("Tipo:" +element.getTipo());

        return listItem;
    }
}
