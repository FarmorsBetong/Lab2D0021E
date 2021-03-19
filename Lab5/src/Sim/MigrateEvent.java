package Sim;

public class MigrateEvent implements Event {
    Node mobileNode;
    Router homeAgent;
    int handoffTime;
    public MigrateEvent(Node MN, Router homeAgent, int handoffTime){
        this.mobileNode = MN;
        this.homeAgent = homeAgent;
        this.handoffTime = handoffTime;
    }

    public Router getHomeAgent(){
        return homeAgent;
    }
    public Node getMobileNode(){
        return mobileNode;
    }

    public int getHandoffTime(){
        return handoffTime;
    }
    @Override
    public void entering(SimEnt locale) {

    }
}
