
package org.apollo.game.scheduling;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class which manages {@link ScheduledTask}s.
 * @author Graham
 */
public final class Scheduler
{

	/**
	 * the singleton instance
	 */
	private static final Scheduler INSTANCE = new Scheduler();

	/**
	 * A deque of currently active tasks.
	 */
	private final Deque<ScheduledTask> tasks = new LinkedList<ScheduledTask>();


	/**
	 * Schedules a new task.
	 * @param task The task to schedule.
	 */
	public void schedule( ScheduledTask task )
	{
		tasks.addFirst( task );
	}


	/**
	 * Called every pulse: executes tasks that are still pending, adds new
	 * tasks and stops old tasks.
	 */
	public void pulse()
	{
		Iterator<ScheduledTask> it = tasks.iterator();
		while( it.hasNext() ) {
			ScheduledTask task = it.next();
			if( ! task.isRunning() ) {
				it.remove();
				continue;
			}
			task.pulse();
		}
	}


	/**
	 * Default private constructor to prevent instantiation by other classes.
	 */
	private Scheduler()
	{

	}


	/**
	 * Gets the singleton instance
	 * @return the instance The singleton instance.
	 */
	public static Scheduler getInstance()
	{
		return INSTANCE;
	}

}
