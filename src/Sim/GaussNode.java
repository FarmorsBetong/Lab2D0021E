package Sim;
import java.util.ArrayList;

/**
 * Gauss generator, stores the values of the nb (probability), the mean, and the starting x value.
 *
 * @author Robin Olofsson, Johan Larsson, roblof-8, johlax-8
 *
 */

public class GaussNode extends Node{


	protected double _mean;
	protected int std_diviation;
	protected double x;
	protected int index = 0;
	//A list with values of a normal distribution
	protected ArrayList<Double> NB = new ArrayList<Double>();

	public GaussNode(int network, int node, double _mean, int std_diviation) {
		super(network, node);
		this.std_diviation = std_diviation;
		this._mean = _mean;
		this.x = -(std_diviation * 2);
		NB.add(0.0014);
		NB.add(0.0214);
		NB.add(0.1359);
		NB.add(0.3413);
		NB.add(0.3413);
		NB.add(0.1359);
		NB.add(0.0214);
		NB.add(0.0014);
	}


	/**
	 *
	 * @param network Network id
	 * @param node	node id
	 * @param number number of message to be sent
	 * @param time	time between messages
	 * @param startSeq starting seq
	 */
	public void StartSending(int network, int node, int number, int time, int startSeq) {
		_toNetwork = network;
		_toHost = node;
		_timeBetweenSending = time;
		_stopSendingAfter = number;
		_seq = startSeq;
		send(this,new TimerEvent(),0);
		System.out.println("Sending start signal normal distribution");
	}

	/**
	 *
	 * @param x value in the normal distribution
	 * @return the value of packes, total multiply the probability in the normal distribution
	 */
	public double calcNumberOfPackagesNB(double x){
		double procent = Math.exp(- Math.pow((x - _mean)/std_diviation,2)/2) / (std_diviation * Math.sqrt(2 * Math.PI));
		System.out.println(procent);
		return (_stopSendingAfter * procent);
	}


	/**
	 * Calculates the number of packages to be sent, sends them at the same time and then creates a new event that executes after _timeBetweenSending
	 *
	 * @param src src node
	 * @param ev type of event
	 */
	// Override: Modified to send packages as a normal distribution with a random gaussian number.
	public void recv(SimEnt src, Event ev)
	{
		if (ev instanceof TimerEvent)
		{
			if(_stopSendingAfter > _sentmsg) {
				//int packages = (int)Math.ceil(calcNumberOfPackagesNB(x));

					System.out.println(NB.get(index) * _stopSendingAfter);
					System.out.println((int)(Math.ceil(NB.get(index) * _stopSendingAfter)));

					int packages = (int)(Math.ceil(NB.get(index) * _stopSendingAfter));
					System.out.println("---------------------------------------");
					System.out.println("Sending " + packages + " packages");

					for (int i = 0; i < packages; i++) {

						System.out.println("Time of sending package is: " + SimEngine.getTime());
						_sentmsg++;
						send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost), _seq), 0);
						System.out.println("Node " + _id.networkId() + "." + _id.nodeId() + " sent message with seq: " + _seq + " at time " + SimEngine.getTime());
						_seq++;
						if(_sentmsg >= _stopSendingAfter) break;
					}
					System.out.println("sent msg is : " + _sentmsg);
					x = x + 1.0;
					index++;
					send(this, new TimerEvent(), _timeBetweenSending);

					
			}
		}

		if (ev instanceof Message)
		{

			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
		}
	}
}
