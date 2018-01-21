package comanche;
import java.io.*;
import java.util.*;
import org.objectweb.fractal.api.control.BindingController;

public class RequestDispatcher implements RequestHandler, BindingController {
  private Map<String,Object> handlers = new TreeMap<String,Object>();
  // configuration aspect
  public String[] listFc () {
    return (String[])handlers.keySet().toArray(new String[handlers.size()]);
  }
  public Object lookupFc (String itfName) {
    if (itfName.startsWith("h")) { return handlers.get(itfName); }
    else return null;
  }
  public void bindFc (String itfName, Object itfValue) {
    if (itfName.startsWith("h")) { handlers.put(itfName, itfValue); }
  }
  public void unbindFc (String itfName) {
    if (itfName.startsWith("h")) { handlers.remove(itfName); }
  }
  // functional aspect
  public void handleRequest (Request r) throws IOException {
    Iterator i = handlers.values().iterator();
    while (i.hasNext()) {
      try {
        ((RequestHandler)i.next()).handleRequest(r); return;
      } catch (IOException _) { }
    }
  }
}
