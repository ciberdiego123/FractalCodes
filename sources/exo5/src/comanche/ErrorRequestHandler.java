package comanche;
import java.io.*;

public class ErrorRequestHandler implements RequestHandler {
  public void handleRequest (Request r) throws IOException {
    //r.out.print("HTTP/1.0 404 Not Found\n\n");
    r.out.print("<html>Document not found.</html>");
  }
}
