package photocopy;

public class CartImpl implements EncreNoir, EncreCouleur{

    @Override
    public void encreCouleur() {
        System.out.println("Encre Couleur");
    }

    @Override
    public void encreNoir() {
        System.out.println("Encre noir");
    }
}
