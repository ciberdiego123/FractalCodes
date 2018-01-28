package photocopy;
import org.objectweb.fractal.api.control.BindingController;

public class ImpImpl implements CommandeImp , BindingController {
    private EncreNoir encreNoir;
    private EncreCouleur encreCouleur;
    private MagasinPapier magasinPapier;
    private DocNum docNum;
    // configuration aspect
    public String[] listFc () { return new String[] { "encreNoir", "encreCouleur", "magasinPapier", "docNum"}; }
    public Object lookupFc (String itfName) {
        if (itfName.equals("encreNoir")) { return encreNoir; }
        else if (itfName.equals("encreCouleur")) { return encreCouleur; }
        else if (itfName.equals("magasinPapier")) { return magasinPapier; }
        else if (itfName.equals("docNum")) { return docNum; }
        else return null;
    }
    public void bindFc (String itfName, Object itfValue) {
        if (itfName.equals("encreNoir")) { encreNoir = (EncreNoir) itfValue; }
        else if (itfName.equals("encreCouleur")) { encreCouleur = (EncreCouleur) itfValue; }
        else if (itfName.equals("magasinPapier")) { magasinPapier = (MagasinPapier) itfValue; }
        else if (itfName.equals("docNum")) { docNum = (DocNum) itfValue; }
    }
    public void unbindFc (String itfName) {
        if (itfName.equals("encreNoir")) { encreNoir = null; }
        else if (itfName.equals("encreCouleur")) { encreCouleur = null; }
        else if (itfName.equals("magasinPapier")) { magasinPapier = null; }
        else if (itfName.equals("docNum")) { docNum = null; }
    }
    // functional aspect
    @Override
    public void commandeImp() {
        System.out.println("RUN Commande Imprimer");
        encreNoir.encreNoir();
        encreCouleur.encreCouleur();
        magasinPapier.magasinPapier();
        docNum.docNum();
    }
}
