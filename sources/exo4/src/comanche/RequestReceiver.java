package comanche;
import java.io.*;
import java.net.*;
import org.objectweb.fractal.api.control.BindingController;

public class RequestReceiver implements Runnable, BindingController {
  private Scheduler s;
  private RequestHandler rh;
  // configuration aspect
  public String[] listFc () { return new String[] { "s", "rh" }; }
  public Object lookupFc (String itfName) {
    if (itfName.equals("s")) { return s; }
    else if (itfName.equals("rh")) { return rh; }
    else return null;
  }
  public void bindFc (String itfName, Object itfValue) {
    if (itfName.equals("s")) { s = (Scheduler)itfValue; }
    else if (itfName.equals("rh")) { rh = (RequestHandler)itfValue; }
  }
  public void unbindFc (String itfName) {
    if (itfName.equals("s")) { s = null; }
    else if (itfName.equals("rh")) { rh = null; }
  }
  // functional aspect
  public void run () {
    try {
      ServerSocket ss = new ServerSocket(8080);
      System.out.println("Comanche HTTP Server ready on port 8080.");
      while (true) {
        final Socket socket = ss.accept();
        s.schedule(new  Runnable () {
          public void run () {
            try {
              rh.handleRequest(new Request(socket));
            } catch (IOException _) { }
          }
        });
      }
    } catch (IOException e) { e.printStackTrace(); }
  }
}
