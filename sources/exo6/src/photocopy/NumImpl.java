package photocopy;
import org.objectweb.fractal.api.control.BindingController;

public class NumImpl implements DocNum, CommandeNum, BindingController  {
    private DocOriginal docOriginal;
    private Alimentation alimentation;
    // configuration aspect
    public String[] listFc () { return new String[] { "docOriginal", "alimentation" }; }
    public Object lookupFc (String itfName) {
        if (itfName.equals("docOriginal")) { return docOriginal; }
        else if (itfName.equals("alimentation")) { return alimentation; }
        else return null;
    }
    public void bindFc (String itfName, Object itfValue) {
        if (itfName.equals("docOriginal")) { docOriginal = (DocOriginal) itfValue; }
        else if (itfName.equals("alimentation")) { alimentation = (Alimentation) itfValue; }
    }
    public void unbindFc (String itfName) {
        if (itfName.equals("docOriginal")) { docOriginal = null; }
        else if (itfName.equals("alimentation")) { alimentation = null; }
    }
    // functional aspect
    @Override
    public void commandeNum() {
        System.out.println("Commande Numerisation");
    }

    @Override
    public void docNum() {
        System.out.println("RUN Document numerisé");
        docOriginal.docOriginal();
        alimentation.alim();
    }
}
