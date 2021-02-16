package Sim;


public class GaussNode extends Node{

	protected double _mean;
	protected int sizeBetweenStaple;
	protected int std_diviation;
	protected double x;

	public GaussNode(int network, int node, double _mean, int std_diviation) {
		super(network, node);
		this.std_diviation = std_diviation;
		this._mean = _mean;
		this.x = -(std_diviation * 2);
	}


	public void StartSending(int network, int node, int number, int sizeBetweenStaple, int startSeq) {
		_toNetwork = network;
		_toHost = node;
		_stopSendingAfter = number;
		this.sizeBetweenStaple = sizeBetweenStaple;
		_seq = startSeq;
		send(this,new TimerEvent(),0);
		System.out.println("Sending start signal normal distribution");
	}

	public double calcNumberOfPackagesNB(double x){
		return _stopSendingAfter * (Math.exp(-Math.pow((x - _mean)/std_diviation,2)/2) / (std_diviation * Math.sqrt(2 * Math.PI)));
	}


	// Override: Modified to send packages as a normal distribution with a random gaussian number.
	public void recv(SimEnt src, Event ev)
	{
		if (ev instanceof TimerEvent)
		{
			if(_stopSendingAfter > _sentmsg) {
				int packages = (int)calcNumberOfPackagesNB(x);


				for (int i = 0; i < packages; i++) {

					System.out.println("Time of sending package is: " + SimEngine.getTime());

					_sentmsg++;
					send(_peer, new Message(_id, new NetworkAddr(_toNetwork, _toHost), _seq), 0);
					System.out.println("Node " + _id.networkId() + "." + _id.nodeId() + " sent message with seq: " + _seq + " at time " + thisTime);
					_seq++;
				}
				send(this, new TimerEvent(), 1);
			}
		}

		if (ev instanceof Message)
		{

			System.out.println("Node "+_id.networkId()+ "." + _id.nodeId() +" receives message with seq: "+((Message) ev).seq() + " at time "+SimEngine.getTime());
		}
	}
}
