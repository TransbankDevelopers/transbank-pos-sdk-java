import cl.transbank.pos.utils.TransbankWrap;

public class Test {

    public static void main(String[] args) {
//        System.loadLibrary("serialport");
//        System.loadLibrary("TransbankWrap");
        System.load("/usr/local/lib/libTransbankWrap.dylib");
        String list = TransbankWrap.list_ports();
        System.out.println("list: " + list);
    }
}
