package Sim;

public class MigrateComplete implements Event {
    Node node;
    Router homeAgent;

    public MigrateComplete(Node node, Router homeAgent){
        this.node = node;
        this.homeAgent = homeAgent;
    }

    public Router getHomeAgent(){
        return homeAgent;
    }

    public Node getNode(){
        return node;
    }

    @Override
    public void entering(SimEnt locale) {

    }
}
