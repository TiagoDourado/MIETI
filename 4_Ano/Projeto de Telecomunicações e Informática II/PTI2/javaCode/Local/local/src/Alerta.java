
import java.io.*;
import java.util.ArrayList;

public class Alerta implements Runnable{
    private PrintWriter out;
    private InputStreamReader reader;
    private BufferedReader br;

    ArrayList<String> mensagem_alerta = new ArrayList<>();

    Alerta(PrintWriter out){
        this.out = out;
    }

    @Override
    public void run() {
        InputStreamReader isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
        String line = "";
        String strLine = "";


        try {
            while (true) {
                // Get the object of DataInputStream
                BufferedReader desc = new BufferedReader(new InputStreamReader(System.in));
                String from_input = desc.readLine();
                String[] trama = from_input.split(" ");

                mensagem_alerta.add("Alerta");
                //Hello hello = new Hello();
                //mensagem_alerta.add(hello.getTrama_hello().get(1));

                for (int i = 0; i < trama.length; i++) {
                    mensagem_alerta.add(trama[i]);
                }
                out.println(mensagem_alerta);
                mensagem_alerta.clear();
                out.flush();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
