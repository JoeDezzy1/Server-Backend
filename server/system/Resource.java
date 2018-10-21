package server.system;

/**
 * A system resource value gettable through the OSMBean
 *
 * @author JoeDezzy1
 */
public enum Resource
{
	PHYSICAL_RAM_FREE, VIRTUAL_RAM_FREE, PHYSICAL_RAM_TOTAL, VIRTUAL_RAM_TOTAL, CPU_LOAD;

	/**
	 * Gets the value of the current system resource
	 *
	 * @param rate
	 * 		- the rate
	 * @return the system resource value
	 */
	public double get(Rate rate)
	{
		switch (this)
		{
		case PHYSICAL_RAM_FREE:
			return Resources.getFreePhysicalRAM(rate);
		case VIRTUAL_RAM_FREE:
			return Resources.getFreeVirtualRAM(rate);
		case PHYSICAL_RAM_TOTAL:
			return Resources.getTotalPhysicalRAM(rate);
		case VIRTUAL_RAM_TOTAL:
			return Resources.getTotalVirtualRAM(rate);
		case CPU_LOAD:
			return Resources.getSystemCPULoad();
		default:
			return 0;
		}
	}
}
