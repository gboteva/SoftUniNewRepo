package goldDigger.models.discoverer;

public class Archaeologist extends BaseDiscoverer{

    private final static double ENERGY = 60.00;

    public Archaeologist(String name) {
        super(name, ENERGY);
    }
}
