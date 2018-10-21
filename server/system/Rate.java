package server.system;

/**
 * A resource rate in memory
 *
 * @author JoeDezzy1
 */
public enum Rate
{
	KB(1), MB(100), GB(1000);
	/**
	 * The rate conversion value
	 */
	private final int rate;

	/**
	 * Create a new rate
	 *
	 * @param rate
	 * 		- the conversion value
	 */
	Rate(int rate)
	{
		this.rate = rate;
	}

	/**
	 * Converts the long value into the rate as a double
	 *
	 * @param value
	 * 		- the value to convert
	 * @return the converted rate to decimal
	 */
	public synchronized double convert(long value)
	{
		return ((((double) value) / rate) / Math.pow(rate, 2));
	}
}
