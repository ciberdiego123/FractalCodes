package comanche;
import java.io.*;

public class HttpHandler implements RequestHandler {
    public void handleRequest (Request r) throws IOException {
        r.out.print("HTTP/1.0 200 OK\n\n");
        r.out.print("<html><meta http-equiv=\"refresh\" content=\"0 url=http://www.google.fr\"></html>");
    }
}
