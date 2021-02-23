package Sim;

public class SwitchInterface implements Event {

    private NetworkAddr currentInterface;
    private int newInterfaceNr;

    public SwitchInterface(NetworkAddr currentInterface, int newInterface){
        this.currentInterface = currentInterface;
        this.newInterfaceNr = newInterface;
    }

    public int getOldInterfaceNr() {
        return oldInterfaceNr;
    }

    public int getNewInterfaceNr() {
        return newInterfaceNr;
    }

    @Override
    public void entering(SimEnt locale) {

    }
}
