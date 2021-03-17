package Sim;

// This class implements a node (host) it has an address, a peer that it communicates with
// and it count messages send and received.


public class Node extends SimEnt {
	protected NetworkAddr _id;
	protected SimEnt _peer;
	protected int _sentmsg=0;
	protected int _seq = 0;
	protected boolean migrating;

	
	public Node (int network, int node)
	{
		super();
		_id = new NetworkAddr(network, node);
		this.migrating = false;
	}	

	public boolean getMigrate(){
		return this.migrating;
	}
	public void setMigrating(boolean value){
		this.migrating = value;
	}
	
	// Sets the peer to communicate with. This node is single homed
	
	public void setPeer (SimEnt peer)
	{
		_peer = peer;
		
		if(_peer instanceof Link )
		{
			 ((Link) _peer).setConnector(this);
		}
	}
	
	
	public NetworkAddr getAddr()
	{
		return _id;
	}

	public void switchInterface(int changeInterfaceAfter, int changeInterfaceTo){
		this.changeInterfaceAfter = changeInterfaceAfter;
		this.newInterface = changeInterfaceTo;
	}
//**********************************************************************************	
	// Just implemented to generate some traffic for demo.
	// In one of the labs you will create some traffic generators
	
	protected int _stopSendingAfter = 0; //messages
	protected int _timeBetweenSending = 10; //time between messages
	protected int _toNetwork = 0;
	protected int _toHost = 0;
	protected NetworkAddr destination;

	// changeInterfaceAfter holds the number of packets needs to be sent before a interface switch
	// changeInterfaceTo holds the interface we are going to switch to.
	protected int changeInterfaceAfter;
	protected int newInterface;
	
	public void StartSending(NetworkAddr dest, int number, int timeInterval, int startSeq)
	{
		_stopSendingAfter = number;
		_timeBetweenSending = timeInterval;
		destination = dest;
		_seq = startSeq;
		System.out.println("creates first timer event");
		send(this, new TimerEvent(),0);	
	}
	public void StartSending(int network, int host, int number, int timeInterval, int startSeq)
	{
		_stopSendingAfter = number;
		_timeBetweenSending = timeInterval;
		_toNetwork = network;
		_toHost = host;
		_seq = startSeq;
		System.out.println("creates first timer event");
		send(this, new TimerEvent(),0);
	}
	
//**********************************************************************************	
	
	// This method is called upon that an event destined for this node triggers.
	
	public void recv(SimEnt src, Event ev)
	{
		if (ev instanceof TimerEvent)
		{			
			if (_stopSendingAfter > _sentmsg)
			{
				_sentmsg++;
				System.out.println("Node creates msg event");
				send(_peer, new Message(_id, destination,_seq),0);
				System.out.println("Node creates timer event");
				send(this, new TimerEvent(),_timeBetweenSending);
				System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" sent message with seq: "+_seq + " at time "+SimEngine.getTime());
				_seq++;

				if(_sentmsg == changeInterfaceAfter)
				{

					System.out.println("Switching Interface to " + newInterface);

					//Creates a switchinterface event that triggers immediately.
					System.out.println("Creates a SwitchInterface event");
					send(_peer,new SwitchInterface(this._id,this.newInterface),0);
				}
			}
		}
		if (ev instanceof Message)
		{
			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
			
		}
		if (ev instanceof MigrateEvent){
			MigrateEvent event = (MigrateEvent)ev;
			System.out.println("Node got a migrate event");
			System.out.println("Setting migrate flag to true");
			this.setMigrating(true);
			//send (dest,ev,timer)
			send(event.homeAgent,new MigrateComplete(event.mobileNode._id),0);
		}
	}
}
