package Sim; 

// An example of how to build a topology and starting the simulation engine

public class Run {
	public static void main (String [] args)
	{
		
		double a[] = {1,0};
		double b[] = {3,5};
		double c[] = {1,0};
		double d[] = {2,6};
		
 		//Creates two links
 		//LossyLink link1 = new LossyLink(a, b, 0);
		//LossyLink link2 = new LossyLink(c, d, 0);
		
		Link link1 = new Link();
		Link link2 = new Link();
		
		//PoissonNode host1 = new PoissonNode(1,1,5);
		//GaussNode host1 = new GaussNode(1,1,0,1);
		
		
		//CBRNode host1 = new CBRNode(1,1,7);
		//CBRNode host2 = new CBRNode(2,1,5);
		
		// Create two end hosts that will be
		// communicating via the router
		Node host1 = new Node(1,1);
		Node host2 = new Node(2,1);

		//Connect links to hosts
		host1.setPeer(link1);
		host2.setPeer(link2);

		// Creates as router and connect
		// links to it. Information about 
		// the host connected to the other
		// side of the link is also provided
		// Note. A switch is created in same way using the Switch class
		Router routeNode = new Router(3);
		routeNode.connectInterface(0, link1, host1);
		routeNode.connectInterface(1, link2, host2);
		
		// Generate some traffic
		// host1 will send 3 messages with time interval 5 to network 2, node 1. Sequence starts with number 1
		host1.StartSending(2, 2, 5,10, 1);
		// host2 will send 2 messages with time interval 10 to network 1, node 1. Sequence starts with number 10
		host2.StartSending(1, 1, 1, 10, 10);
		
		routeNode.switchInterface(0, 2);
		
		//host1.StartSending(2, 2, 3, 3, 25);
		
		// Start the simulation engine and of we go!
		Thread t=new Thread(SimEngine.instance());
	
		t.start();
		try
		{
			t.join();
		}
		catch (Exception e)
		{
			System.out.println("The motor seems to have a problem, time for service?");
		}		



	}
}
