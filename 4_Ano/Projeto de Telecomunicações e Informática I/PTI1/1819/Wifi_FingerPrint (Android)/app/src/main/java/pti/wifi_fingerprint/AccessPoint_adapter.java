package pti.wifi_fingerprint;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
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

public class AccessPoint_adapter extends ArrayAdapter<Element> {
    private Context context;
    private ArrayList<Element> aps=new ArrayList<Element>();

    public AccessPoint_adapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Element> list) {
        super(context,0,list);
        this.context=context;
        aps=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.activity_element, parent, false);
        }
        Element element = aps.get(position);

        TextView ssid = (TextView) listItem.findViewById(R.id.SSID);
        ssid.setText("SSID:" +element.getSSID());

        TextView sinal = (TextView) listItem.findViewById(R.id.signal);
        sinal.setText("Intensidade Sinal:" +Integer.toString(element.getSinal()));

        TextView mac = (TextView) listItem.findViewById(R.id.mac_address);
        mac.setText("Endereço MAC:" +element.getEndereco_mac());

        return listItem;
    }
}
