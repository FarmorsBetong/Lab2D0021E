package Sim;

public class CBRNode extends Node {
	
	
	private int amountSendings;

	public CBRNode(int network, int node, int amountSendings) {
		super(network, node);
		// TODO Auto-generated constructor stub
		this.amountSendings = amountSendings;
	}
	
	
	
	
	
	
	public void recv(SimEnt src, Event ev)
	{
		double percentSendings = ((double) this._stopSendingAfter)/((double) this.amountSendings);
		//System.out.println("THIS IS THE PERCENT OF SENDINGS: " + (Math.round(Math.ceil(percentSendings))));
		
		if (ev instanceof TimerEvent)
		{			
			if (_stopSendingAfter > _sentmsg) {
				
				
				//Divide the packets into equal shipments and send them every intervall
				for(int i = 0; i < Math.round(Math.ceil(percentSendings)); i++){
					
					_sentmsg++;
					send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost),_seq),0);
					
					System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
					_seq++;
					
					if(_stopSendingAfter >= _sentmsg) break; //if we have sent the total amount of packets we need to stop sending more
					
				
				 }
				send(this, new TimerEvent(),_timeBetweenSending);
				
			}
		}
		if (ev instanceof Message)
		{
			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
			
		}
	}

}
