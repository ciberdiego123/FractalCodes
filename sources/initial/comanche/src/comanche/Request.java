package comanche;
import java.io.*;
import java.net.*;

public class Request {
  public Socket s;
  public Reader in;
  public PrintStream out;
  public String url;
  public Request (Socket s) { this.s = s; }
}
