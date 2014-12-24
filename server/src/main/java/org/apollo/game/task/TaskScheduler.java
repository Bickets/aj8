package org.apollo.game.task;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

/**
 * A class which manages {@link Task}s.
 *
 * @author Graham
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class TaskScheduler {

	/**
	 * A {@link ArrayDeque} of new tasks currently waiting to be added.
	 */
	private final Queue<Task> pendingTasks = new ArrayDeque<>();

	/**
	 * An {@link ArrayList} of currently active tasks.
	 */
	private final List<Task> tasks = new ArrayList<>();

	/**
	 * Schedules a new pending {@link Task}.
	 * 
	 * @param task The task to schedule.
	 * @return {@code true} if and only if the task was added successfully,
	 *         otherwise {@code false}.
	 */
	public boolean schedule(Task task) {
		return pendingTasks.add(task);
	}

	/**
	 * Pulses all active tasks, adds pending tasks, executes active tasks and
	 * removes inactive tasks.
	 */
	public void pulse() {
		for (;;) {
			Task task = pendingTasks.poll();
			if (task == null) {
				break;
			}
			tasks.add(task);
		}

		Iterator<Task> iterator = tasks.iterator();
		while (iterator.hasNext()) {
			Task task = iterator.next();
			task.pulse();

			if (!task.isRunning()) {
				iterator.remove();
			}
		}
	}

}