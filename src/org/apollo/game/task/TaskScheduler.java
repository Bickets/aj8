
package org.apollo.game.task;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class which manages {@link Task}s.
 * @author Graham
 */
public final class TaskScheduler
{

	/**
	 * the singleton instance
	 */
	private static final TaskScheduler INSTANCE = new TaskScheduler();

	/**
	 * A {@link Deque} of currently active tasks.
	 */
	private final Deque<Task> tasks = new LinkedList<Task>();


	/**
	 * Schedules a new task.
	 * @param task The task to schedule.
	 */
	public void schedule( Task task )
	{
		tasks.addFirst( task );
	}


	/**
	 * Called every pulse: executes tasks that are still pending, adds new
	 * tasks and stops old tasks.
	 */
	public void pulse()
	{
		Iterator<Task> it = tasks.iterator();
		while( it.hasNext() ) {
			Task task = it.next();
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
	private TaskScheduler()
	{

	}


	/**
	 * Gets the singleton instance
	 * @return the instance The singleton instance.
	 */
	public static TaskScheduler getInstance()
	{
		return INSTANCE;
	}

}
