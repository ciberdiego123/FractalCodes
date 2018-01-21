package comanche;
import java.io.*;
import org.objectweb.fractal.api.control.BindingController;

public class RequestAnalyzer implements RequestHandler, BindingController {
  private RequestHandler rh;
  private Logger l;
  // configuration aspect
  public String[] listFc () { return new String[] { "l", "rh" }; }
  public Object lookupFc (String itfName) {
    if (itfName.equals("l")) { return l; }
    else if (itfName.equals("rh")) { return rh; }
    else return null;
  }
  public void bindFc (String itfName, Object itfValue) {
    if (itfName.equals("l")) { l = (Logger)itfValue; }
    else if (itfName.equals("rh")) { rh = (RequestHandler)itfValue; }
  }
  public void unbindFc (String itfName) {
    if (itfName.equals("l")) { l = null; }
    else if (itfName.equals("rh")) { rh = null; }
  }
  // functional aspect
  public void handleRequest (Request r) throws IOException {
    r.in = new InputStreamReader(r.s.getInputStream());
    r.out = new PrintStream(r.s.getOutputStream());
    String rq = new LineNumberReader(r.in).readLine();
    l.log(rq);
    if (rq.startsWith("GET ")) {
      r.url = rq.substring(5, rq.indexOf(' ', 4));
      rh.handleRequest(r);
    }
    r.out.close();
    r.s.close();
  }
}
