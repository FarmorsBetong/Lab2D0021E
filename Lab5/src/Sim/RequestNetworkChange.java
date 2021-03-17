package Sim;

public class RequestNetworkChange implements Event {
    //the event holds the information about the Home Agent
    private Router homeAgent;
    private int time;

    public RequestNetworkChange(Router homeAgent, int time){
        this.homeAgent = homeAgent;
        this.time = time;
    }

    public Router getHomeAgent(){
        return homeAgent;
    }
    public int getTime(){
        return time;
    }
    public void entering(SimEnt locale){

    }
}
