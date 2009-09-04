/**
 * jcombinatorics:
 * Java Combinatorics Library
 *
 * Copyright (c) 2009 by Alistair A. Israel.
 *
 * This software is made available under the terms of the MIT License.
 * See LICENSE.txt.
 *
 * Created Sep 4, 2009
 */
package jcombinatorics.benchmark;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import jcombinatorics.permutations.Permutations;

/**
 * @author Alistair A. Israel
 */
public class PermutatedOrderBenchmark {

    private final int reps;

    private final Map<String, Task> tasks = new LinkedHashMap<String, Task>();

    /**
     * @param reps
     *        the number of repetitions
     */
    public PermutatedOrderBenchmark(final int reps) {
        super();
        this.reps = reps;
    }

    /**
     * @return the reps
     */
    public final int getReps() {
        return reps;
    }

    /**
     * @return {@link Collection}&lt;{@link Task}&gt;
     */
    public final Collection<Task> getTasks() {
        return tasks.values();
    }

    /**
     * @param taskName
     *        the task name
     * @param runnable
     *        the task runnable
     */
    public final void addTask(final String taskName, final Runnable runnable) {
        final Task task = new Task(taskName, runnable);
        tasks.put(taskName, task);
    }

    /**
     *
     */
    public final void benchmarkAll() {
        final Task[] elements = tasksToArray();
        for (int i = 0; i < reps; ++i) {
            System.out.println("Run " + i + " of " + reps);
            runThrough(elements);
        }
    }

    /**
     * @return Task[]
     */
    private Task[] tasksToArray() {
        final Task[] elements = new Task[getTasks().size()];
        int i = 0;
        for (final Task task : getTasks()) {
            elements[i++] = task;
        }
        return elements;
    }

    /**
     * @param elements
     *        Task[]
     */
    private void runThrough(final Task[] elements) {
        for (final Task[] permutation : Permutations.over(elements)) {
            for (final Task task : permutation) {
                System.out.println(task.getTaskName());
                task.runOnce();
            }
        }
    }

    /**
     * @author Alistair A. Israel
     */
    public static class Task {

        private final String taskName;

        private final Runnable runnable;

        private long totalNanos;

        /**
         * @param taskName
         *        the task name
         * @param runnable
         *        the task runnable
         */
        public Task(final String taskName, final Runnable runnable) {
            super();
            this.taskName = taskName;
            this.runnable = runnable;
        }

        /**
         * @return the taskName
         */
        public final String getTaskName() {
            return taskName;
        }

        /**
         * @return the runnable
         */
        public final Runnable getRunnable() {
            return runnable;
        }

        /**
         * @return the totalNanos
         */
        public final long getTotalNanos() {
            return totalNanos;
        }

        /**
         *
         */
        private void runOnce() {
            final long start = System.nanoTime();
            runnable.run();
            final long stop = System.nanoTime();
            totalNanos += (stop - start);
        }

    }
}
