import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Single_universal {

        static Map<String, PrintWriter> ip_with_printwriter = new HashMap<String, PrintWriter>();
        private Map<PrintWriter,String> printwriter_with_ip = new HashMap<PrintWriter, String>();

        private static Single_universal instance;

        public static Single_universal getInstance(){
            if (instance==null){
                instance = new Single_universal();
            }
            return instance;

        }
        public void put_ip_printwriter (String ip, PrintWriter print) {
            ip_with_printwriter.put(ip, print);
        }

        public Map<String,PrintWriter> getIP_Printwriter(){
            return  this.ip_with_printwriter;
        }

        public void put_printwriter_ip (PrintWriter print, String ip){

            printwriter_with_ip.put(print, ip);
        }


        public PrintWriter getPrintWriter(String ip){
            PrintWriter ip_new = ip_with_printwriter.get(ip);
            return ip_new;
        }
        public String get_ip(PrintWriter print){
            String print_new = printwriter_with_ip.get(print);
            return print_new;
        }


}
