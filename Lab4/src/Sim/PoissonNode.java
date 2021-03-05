package Sim;
import java.math.*;
import java.text.*;

public class PoissonNode extends Node{
	
	
	/** The lambda value for the Poisson distribution */
	private double lambda;
	private DecimalFormat df;
	
	/** An iterator variable to create the Poisson probabilities*/
	private int poissonIter = 0;
	
	
	public PoissonNode(int network, int node, double lambda) {
		super(network, node);
		// TODO Auto-generated constructor stub
		this.lambda = lambda;
		this.df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.UP);
		
		
	}
	
	
	/** A function to calculate the factorial of the positive integer x*/
	private int fact(int x) {
		if(x == 0 || x == 1) return 1;
		
		return fact(x-1) * x;
	}
	
	/** The function which returns the probability to score the value of x on a Poisson distribution of value lambda(set in the constructor) */
	 private double poissonDist(int x){
	        
	        return ((Math.pow(lambda,x))*(Math.pow(Math.exp(1),(lambda * (-1)))))/fact(x);
	 }
	 
	 public void recv(SimEnt src, Event ev)
		{
			if (ev instanceof TimerEvent)
			{			
				if (_stopSendingAfter > _sentmsg)
				{
					//send an amount of packets equal to the total amount of packets times the probability to get the value of the shipment number
					for(int i = 0; i < Math.ceil(poissonDist(poissonIter)*_stopSendingAfter); i++) {
						_sentmsg++;
						send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq),0);
						
						System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
						_seq++;
						
						if(_sentmsg >= _stopSendingAfter) break; //if we have sent all the packets we need to stop sending more
					}
					//System.out.println("SENT " + Math.ceil(poissonDist(poissonIter)*_stopSendingAfter) + " PACKETS");
					poissonIter++;
					
					
					send(this, new TimerEvent(),_timeBetweenSending);
				}
			}
			if (ev instanceof Message)
			{
				System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
				
			}
		}
	 
	 
	 

}
