package Sim;

public class MigrateEvent implements Event {
    Node mobileNode;
    Router homeAgent;
    public MigrateEvent(Node MN, Router homeAgent){
        this.mobileNode = MN;
        this.homeAgent = homeAgent;
    }

    public Router getHomeAgent(){
        return homeAgent;
    }
    public Node getMobileNode(){
        return mobileNode;
    }

    @Override
    public void entering(SimEnt locale) {

    }
}
