package photocopy;
import org.objectweb.fractal.api.control.BindingController;

public class UserImpl implements Runnable, BindingController {
    private CommandeImp commandeImp;
    private CommandeNum commandeNum;

    // configuration aspect
    public String[] listFc () { return new String[] { "commandeImp", "commandeNum" }; }
    public Object lookupFc (String itfName) {
        if (itfName.equals("commandeImp")) { return commandeImp; }
        else if (itfName.equals("commandeNum")) { return commandeNum; }
        else return null;
    }
    public void bindFc (String itfName, Object itfValue) {
        if (itfName.equals("commandeImp")) { commandeImp = (CommandeImp) itfValue; }
        else if (itfName.equals("commandeNum")) { commandeNum = (CommandeNum) itfValue; }
    }
    public void unbindFc (String itfName) {
        if (itfName.equals("commandeImp")) { commandeImp = null; }
        else if (itfName.equals("commandeNum")) { commandeNum = null; }
    }
    // functional aspect
    public void run () {
        try {
            System.out.println("RUN User");
            commandeNum.commandeNum();
            commandeImp.commandeImp();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
