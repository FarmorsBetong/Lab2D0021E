package Sim;

// This class implements a simple router

public class Router extends SimEnt{

	private RouteTableEntry [] _routingTable;
	private int _interfaces;
	private int _now=0;
	//To identify the router
	private int RID;

	// When created, number of interfaces are defined and router ID
	Router(int interfaces, int RID)
	{
		this.RID = RID;
		_routingTable = new RouteTableEntry[interfaces];
		_interfaces=interfaces;
	}
	Router(int interfaces)
	{
		_routingTable = new RouteTableEntry[interfaces];
		_interfaces=interfaces;
	}
	
	// This method connects links to the router and also informs the 
	// router of the host connects to the other end of the link
	
	public void connectInterface(int interfaceNumber, SimEnt link, SimEnt node)
	{
		if (interfaceNumber<_interfaces)
		{
			_routingTable[interfaceNumber] = new RouteTableEntry(link, node);
		}
		else
			System.out.println("Trying to connect to port not in router");
		
		((Link) link).setConnector(this);
	}

	// This method searches for an entry in the routing table that matches
	// the network number in the destination field of a messages. The link
	// represents that network number is returned
	
	private SimEnt getInterface(int networkAddress)
	{
		System.out.println("we got the interface nr : " + networkAddress);
		System.out.println("We are in router :" + this.RID + " and trying to look for something connected to network 2");
		SimEnt routerInterface=null;
		for(int i=0; i<_interfaces; i++)
			if (_routingTable[i] != null)
			{
				//Takes the device inside the table, and takes its link and returns it (Node or Router)
				SimEnt device = _routingTable[i].getDevice();
				if(device instanceof Node){
					if (((Node) _routingTable[i].node()).getAddr().networkId() == networkAddress)
					{
						routerInterface = _routingTable[i].link();
					}
				}
				else if(device instanceof Router){
					//If the device in the table is a router, and it matches to the router we are suppose to send to
					if (((Router) device).RID == networkAddress){
						//return the routers link towards the dest router.
						System.out.println("vi skickar tillbaks link");
						return _routingTable[i].link();
					}
				}

			}
		//No device was found
		return routerInterface;
	}

	
	public void switchInterface(NetworkAddr idCurrentInterface, int newInterface) {
		System.out.println("SwitchInterface func in router executes");

		// First check if the new interface spot isen't already taken
		if(_routingTable[newInterface] != null){
			System.out.println("This interface is already occupied");
			return;
		}
		// Go through the list of interface and identify which node the event is switching with.
		for ( int i  = 0; i < _interfaces; i++){
			if(_routingTable[i] != null){
				try{
					if(((Node)_routingTable[i].node()).getAddr() == idCurrentInterface){
						RouteTableEntry r = _routingTable[i];
						_routingTable[i] = null;
						_routingTable[newInterface] = r;
					}

				}catch(Exception e){
					System.out.println("Null pointer ouch");
					continue;
				}
			}
		}
	}

	//prints out the table of interfaces when an changeInterface event has occurred
	public void printInterfaceTable(){
		for(int i = 0; i < _interfaces; i++){
			//type cast
			try{
				Node node = (Node)_routingTable[i].node();
				System.out.println("Interface " + i + " have the " + node.getAddr().networkId() + "." + node.getAddr().nodeId());

			}catch(Exception e){
				System.out.println("Interafce " + i + " is empty ");
			}
		}
	}
	// prtocol, if this method returns -1 == no available interface
	public int getNextAvailableInterface() {
		for (int i = 0; i < _interfaces; i++ ){
			if(_routingTable[i] == null){
				return i;
			}
		}
		System.out.println("No available interface on router : " + this.RID);
		return -1;
	}
	
	
	// When messages are received at the router this method is called
	
	public void recv(SimEnt source, Event event)
	{
		//check if the event is of type "SwitchInterface
		System.out.println("--------------------------------");
		System.out.println("Router recvs the event :" + event);

		if(event instanceof SwitchInterface)
		{
			System.out.println("Router recv a SwitchInterface Event");
			//call the switch interface functions with events id and interface location.
			switchInterface(((SwitchInterface) event).getIdCurrentInterface(), ((SwitchInterface) event).getNewInterfaceNr());
			printInterfaceTable();

		}

		if (event instanceof Message)
		{
			System.out.println("Router handles packet with seq: " + ((Message) event).seq()+" from node: "+((Message) event).source().networkId()+"." + ((Message) event).source().nodeId() );
			SimEnt sendNext = getInterface(((Message) event).destination().networkId());
			System.out.println("Router sends to node: " + ((Message) event).destination().networkId()+"." + ((Message) event).destination().nodeId());		
			send (sendNext, event, _now);
	
		}
		if(event instanceof RequestNetworkChange){
			// Execution of Foreign Agent when it recieves a networkchange req.
			//Mobile node
			Node MN = (Node) source;
			System.out.println("\n\n\n\n\n");
			System.out.println("This is MN network addr: " + MN.getAddr().networkId() + "  and this is ID :" + MN.getAddr().nodeId());
		}

	}
}
