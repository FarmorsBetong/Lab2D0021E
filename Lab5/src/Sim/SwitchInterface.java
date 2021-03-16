package Sim;

public class SwitchInterface implements Event {

    //Reason why we use NetworkAddr, is to identify what host current location is, so we can separate and create
    // events without needing to specify which network (node) that we want to change interface on. We just
    // create events after a set of packages and send the the NetworkAddr of the node who wants to swap interface.

    private NetworkAddr idCurrentInterface;
    private int newInterfaceNr;

    public SwitchInterface(NetworkAddr currentInterface, int newInterface){
        this.idCurrentInterface = currentInterface;
        this.newInterfaceNr = newInterface;
    }

    public NetworkAddr getIdCurrentInterface() {
        return idCurrentInterface;
    }

    public int getNewInterfaceNr() {
        return newInterfaceNr;
    }

    @Override
    public void entering(SimEnt locale) {

    }
}
