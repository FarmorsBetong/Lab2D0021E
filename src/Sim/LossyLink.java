package Sim;
import java.util.Random;
import java.math.*;


public class LossyLink extends Link {
	
	//jitter and delay are normally distributed, the 0th idex is the mean and the 1th index is the standard deviance
	//private int[] _jitter, _delay;
	private double _Pdrop;
	
	private Random r;
	private double[] _effectiveDelay; 
	
	public LossyLink(double[] jitter, double[] delay, double Pdrop) {
		
		super();
		
		this._Pdrop = Pdrop;
		
		this.r = new Random();
		
		double mean = jitter[0] + delay[0];
		double stdDev = Math.sqrt(jitter[1]*jitter[1] + delay[1]*delay[1]);
		this._effectiveDelay = new double[2];
		this._effectiveDelay[0] = mean;
		this._effectiveDelay[1] = stdDev;
			
	}
	
	public void recv(SimEnt src, Event ev) {
		
		if(r.nextDouble() < this._Pdrop) {
			System.out.println("Packet dropped");
			return;
		}
		
		if (ev instanceof Message)
		{
			double delay;
			do {
				delay = (r.nextGaussian()*_effectiveDelay[1]) + _effectiveDelay[0];
			}
			while(delay < 0);
			
			System.out.println("Link recv msg, passes it through");
			if (src == _connectorA)
			{
				send(_connectorB, ev, _now + delay);
			}
			else
			{
				send(_connectorA, ev, _now + delay);
			}
		}
	}
}
