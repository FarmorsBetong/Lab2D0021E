package Sim;

public class RequestNetworkChange implements Event {
    //the event holds the information about the Home Agent
    private Router homeAgent;

    public RequestNetworkChange(Router homeAgent){
        this.homeAgent = homeAgent;
    }

    public Router getHomeAgent(){
        return homeAgent;
    }
    public void entering(SimEnt locale){

    }
}
