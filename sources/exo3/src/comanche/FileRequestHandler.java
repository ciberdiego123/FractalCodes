package comanche;
import java.io.*;

public class FileRequestHandler implements RequestHandler {
  public void handleRequest (Request r) throws IOException {
    File f = new File(r.url);
    if (f.exists() && !f.isDirectory()) {
      InputStream is = new FileInputStream(f);
      byte[] data = new byte[is.available()];
      is.read(data);
      is.close();
      r.out.print("HTTP/1.0 200 OK\n\n");
      r.out.write(data);
    } else {
      throw new IOException("File not found");
    }
  }
}
