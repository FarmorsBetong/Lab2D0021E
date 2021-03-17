package Sim;

public class MigrateComplete implements Event {
    NetworkAddr node;

    public MigrateComplete(NetworkAddr node){
        this.node = node;
    }

    @Override
    public void entering(SimEnt locale) {

    }
}
