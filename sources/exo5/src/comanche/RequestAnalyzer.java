package comanche;
import java.io.*;
import org.objectweb.fractal.api.control.BindingController;
import org.objectweb.fractal.api.control.LifeCycleController;

public class RequestAnalyzer implements RequestHandler, BindingController, LifeCycleController{
  private RequestHandler rh;
  private Logger l;
  private boolean started;
  public String getFcState () {
    return started ? STARTED : STOPPED;
  }
  public synchronized void startFc () {
    started = true;
    notifyAll();
  }
  public synchronized void stopFc () {
    started = false;
  }
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
      if(rq.contains("reconf")){
        l.log("RECONFIGURATION");
        HttpHandler rh = new HttpHandler();
        stopFc();
        unbindFc("rh");
        bindFc("rh",rh);
        startFc();
        l.log("HTTP_HANDLER ADDED");
      }
      else{
        r.url = rq.substring(5, rq.indexOf(' ', 4));
        rh.handleRequest(r);
      }
    }
    r.out.close();
    r.s.close();
  }

}
