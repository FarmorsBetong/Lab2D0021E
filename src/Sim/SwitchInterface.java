package Sim;

public class SwitchInterface implements Event {

    private NetworkAddr currentInterface;
    private int newInterfaceNr;

    public SwitchInterface(NetworkAddr currentInterface, int newInterface){
        this.currentInterface = currentInterface;
        this.newInterfaceNr = newInterface;
    }

    public NetworkAddr getOldInterfaceNr() {
        return currentInterface;
    }

    public int getNewInterfaceNr() {
        return newInterfaceNr;
    }

    @Override
    public void entering(SimEnt locale) {

    }
}
