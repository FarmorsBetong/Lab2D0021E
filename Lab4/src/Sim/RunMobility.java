package Sim;

// An example of how to build a topology and starting the simulation engine

public class RunMobility {
    public static void main (String [] args)
    {

        //Creates x§two links

        Link link1 = new Link();
        Link link2 = new Link();
        Link link3 = new Link();
        Link link4 = new Link();



        // Create two end hosts that will be
        // communicating via the router
        Node host1 = new Node(1,1);
        Node host2 = new Node(1,2);
        Node host3 = new Node(1,3);
        Node host4 = new Node(2,2);
        //Connect links to hosts
        host1.setPeer(link1);
        host2.setPeer(link2);
        host3.setPeer(link3);
        host4.setPeer(link4);

        // Creates as router and connect
        // links to it. Information about
        // the host connected to the other
        // side of the link is also provided
        // Note. A switch is created in same way using the Switch class
        Router R1 = new Router(3,1);
        Router R2 = new Router(3,2);
        R1.connectInterface(0, host1._peer, host1);
        R1.connectInterface(1,host2._peer,host2);
        R1.connectInterface(3,host3._peer,host3);
        R2.connectInterface(1,host4._peer,host4);


        // Generate some traffic

        host1.StartSending(2, 2, 5,10, 0);


        // node 3 wants to switch to router 2 since it has better connection after a delay of 40MS
        host3.send(R2,new RequestNetworkChange(R1),40);

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
