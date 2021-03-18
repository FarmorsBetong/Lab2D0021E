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
        Link routerLink = new Link();



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
        Router R1 = new Router(5,1);
        Router R2 = new Router(5,2);

        //Wire up routers

        // 3 nodes + R2 connected to R1
        R1.connectInterface(0, link1, host1);
        R1.connectInterface(1,link2,host2);
        R1.connectInterface(2,link3,host3);
        R1.connectInterface(3,routerLink,R2);

        // 1 Node + 1 R1 connected to R2
        R2.connectInterface(0,routerLink,R1);
        R2.connectInterface(2,link4,host4);

        System.out.println("Start by printing each router tables");
        System.out.println("R1 table:");
        R1.printInterfaceTable();
        System.out.println("\nR2 table:");
        R2.printInterfaceTable();
        System.out.println("");

        // Generate some traffic

        host1.StartSending(host3.getAddr(), 5,10, 0);


        // node 3 wants to switch to router 2 since it has better connection after a delay of 40MS
        host3.send(R2,new RequestNetworkChange(R1,30),30);

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
