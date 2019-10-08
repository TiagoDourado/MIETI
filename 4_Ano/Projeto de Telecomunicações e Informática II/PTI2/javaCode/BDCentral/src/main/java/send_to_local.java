import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class send_to_local{
    String ip;
    PrintWriter out;
    String payload;
    ArrayList<String> ips_hello = new ArrayList<>();
    ArrayList<PrintWriter> printwriters = new ArrayList<>();
    Map<String, PrintWriter> ip_with_printwriter = new HashMap<String, PrintWriter>();

    send_to_local(PrintWriter out, String payload) {   //recebe o ip proveniente do administrador
        System.out.println("send to local");
        this.out=out;
        this.payload=payload;

        out.println(payload);

        Single_universal single = Single_universal.getInstance();
        PrintWriter nn = single.getPrintWriter(ip);

    }

}

