package server.core.system;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * A Class used to get system resource values
 *
 * @author JoeDezzy1
 */
public class Resources
{
	private static final String GETTERS = "get";
	private static final String CPU_LOAD = "getSystemCpuLoad";
	private static final String TOTAL_MEMORY = "getTotalPhysicalMemorySize";
	private static final String TOTAL_SWAP_SPACE = "getTotalSwapSpaceSize";
	private static final String PHYSICAL_MEMORY = "getFreePhysicalMemorySize";
	private static final String FREE_SWAP_SPACE = "getFreeSwapSpaceSize";
	private static final String OS_NAME = "os.name";
	private static final String WINDOWS = "win";
	private static final char BACKSLASH = '\\';
	private static final char FORWARD_SLASH = '/';

	public static void main(String[] args)
	{
		printUsage();
	}

	/**
	 * The operating systems MBean
	 */
	private static OperatingSystemMXBean instance;

	/**
	 * Gets the character to split directory names
	 *
	 * @return the "/" or "\" or "\\" characters for the different OS types
	 */
	public static char getDirectorySplitter()
	{
		return System.getProperty(OS_NAME).toLowerCase().contains(WINDOWS) ? BACKSLASH : FORWARD_SLASH;
	}

	/**
	 * Gets the systems CPU load
	 *
	 * @return the system CPU load: -1 if it couldnt access the method, or -1 if there is no CPU load.
	 */
	public static final synchronized double getSystemCPULoad()
	{
		return Resources.<Double>getValue(CPU_LOAD).doubleValue();
	}

	/**
	 * Gets the total physical RAM converted to the representative rate
	 *
	 * @param rate
	 * 		- the rate to
	 * @return the physical RAM in terms of the given rate
	 */
	public static final synchronized double getTotalPhysicalRAM(final Rate rate)
	{
		return rate.convert(Resources.<Long>getValue(TOTAL_MEMORY).longValue());
	}

	/**
	 * Gets the total virtual (swap space) RAM converted to the representative rate
	 *
	 * @param rate
	 * 		- the rate to
	 * @return the virtual RAM in terms of the given rate
	 */
	public static final synchronized double getTotalVirtualRAM(final Rate rate)
	{
		return rate.convert(Resources.<Long>getValue(TOTAL_SWAP_SPACE).longValue());
	}

	/**
	 * Gets the free physical RAM converted to the representative rate
	 *
	 * @param rate
	 * 		- the rate to
	 * @return the free physical RAM in terms of the given rate
	 */
	public static final synchronized double getFreePhysicalRAM(final Rate rate)
	{
		return rate.convert(Resources.<Long>getValue(PHYSICAL_MEMORY).longValue());
	}

	/**
	 * Gets the total physical RAM converted to the representative rate
	 *
	 * @param rate
	 * 		- the rate to
	 * @return the free virtual RAM in terms of the given rate
	 */
	public static final synchronized double getFreeVirtualRAM(final Rate rate)
	{
		return rate.convert(Resources.<Long>getValue(FREE_SWAP_SPACE).longValue());
	}

	/**
	 * Gets the resource utility instance
	 *
	 * @return the current instance of the resource utility
	 */
	private static synchronized OperatingSystemMXBean getInstance()
	{
		if (Resources.instance == null)
			return (Resources.instance = ManagementFactory.getOperatingSystemMXBean());

		return Resources.instance;
	}

	/**
	 * Gets a resource value from a method in the instance class
	 *
	 * @return the value of a method invoked from the instance class
	 */
	private static synchronized final <E> E getValue(String methodName)
	{
		try
		{
			Method method = Resources.getInstance().getClass().getMethod(methodName);
			method.setAccessible(true);
			return (E) method.invoke(Resources.getInstance());
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Prints the gettable method usage names
	 */
	@SuppressWarnings("unused")
	private static void printUsage()
	{
		for (final Method method : getInstance().getClass().getDeclaredMethods())
		{
			method.setAccessible(true);
			if (method.getName().startsWith(GETTERS) && Modifier.isPublic(method.getModifiers()))
			{
				Object value;
				try
				{
					value = method.invoke(getInstance());
				}
				catch (Exception e)
				{
					value = e;
				}
				System.out.println(method.getName() + " = " + value);
			}
		}
		System.out.println("System CPU Load: " + Resources.getSystemCPULoad());
		System.out.println("Free physical RAM: " + Resources.getFreePhysicalRAM(Rate.GB) + " " + Rate.GB.toString());
		System.out.println("Free virtual RAM: " + Resources.getFreeVirtualRAM(Rate.GB) + " " + Rate.GB.toString());
		System.out.println("Total physical RAM: " + Resources.getTotalPhysicalRAM(Rate.GB) + " " + Rate.GB.toString());
		System.out.println("Total virtual RAM: " + Resources.getTotalVirtualRAM(Rate.GB) + " " + Rate.GB.toString());
		System.out.println("System CPU Load: " + Resources.getSystemCPULoad());
		System.out.println("Free physical RAM: " + Resources.getFreePhysicalRAM(Rate.MB) + " " + Rate.MB.toString());
		System.out.println("Free virtual RAM: " + Resources.getFreeVirtualRAM(Rate.MB) + " " + Rate.MB.toString());
		System.out.println("Total physical RAM: " + Resources.getTotalPhysicalRAM(Rate.MB) + " " + Rate.MB.toString());
		System.out.println("Total virtual RAM: " + Resources.getTotalVirtualRAM(Rate.MB) + " " + Rate.MB.toString());
	}
}
