package org.apollo.util;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.concurrent.TimeUnit;

/**
 * An object that measures elapsed time in nanoseconds. It is useful to measure
 * elapsed time using this class instead of direct calls to
 * {@link System#nanoTime} for a few reasons:
 *
 * <ul>
 * <li>An alternate time source can be substituted, for testing or performance
 * reasons.
 * <li>As documented by {@code nanoTime}, the value returned has no absolute
 * meaning, and can only be interpreted as relative to another timestamp
 * returned by {@code nanoTime} at a different time. {@code Stopwatch} is a more
 * effective abstraction because it exposes only these relative values, not the
 * absolute ones.
 * </ul>
 *
 * <p>
 * <b>Note:</b> This class is not thread-safe.
 *
 * @author Ryley Kimmel <ryley.kimmel@live.com>
 */
public final class Stopwatch {

    /**
     * A time source representing a fixed but arbitrary point in time.
     *
     * <p>
     * This is a functional interface whose functional method is {@link #time()}
     *
     * @author Ryley Kimmel <ryley.kimmel@live.com>
     */
    @FunctionalInterface
    private interface Ticker {

	/**
	 * Returns the amount of time that has elapsed since the tickers fixed
	 * point of reference.
	 */
	long time();

	/**
	 * A default, system ticker which reads the systems current time, in
	 * nanoseconds.
	 */
	public static final Ticker NANOSECOND_TICKER = () -> System.nanoTime();
    }

    /**
     * The time source of this stopwatch.
     */
    private final Ticker ticker;

    /**
     * A flag which denotes whether or not the stopwatch is running.
     */
    private boolean running;

    /**
     * Represents the elapsed time since this stopwatch was created, in
     * nanoseconds.
     */
    private long elapsed;

    /**
     * Represents the start time, when this stopwatch was started, not
     * initiated, in nanoseconds.
     */
    private long startTime;

    /**
     * Returns a standard, un-started stopwatch.
     */
    public static Stopwatch createUnstarted() {
	return new Stopwatch();
    }

    /**
     * Returns a standard, un-started stopwatch with the specified time source.
     */
    public static Stopwatch createUnstarted(Ticker ticker) {
	return new Stopwatch(ticker);
    }

    /**
     * Returns a started, standard stopwatch.
     */
    public static Stopwatch createStarted() {
	return new Stopwatch().start();
    }

    /**
     * Returns a started, standard stopwatch with the specifid time source.
     */
    public static Stopwatch createStarted(Ticker ticker) {
	return new Stopwatch(ticker).start();
    }

    /**
     * Constructs a new {@link Stopwatch} with the
     * {@link Ticker#NANOSECOND_TICKER} time source.
     */
    private Stopwatch() {
	this(Ticker.NANOSECOND_TICKER);
    }

    /**
     * Constructs a new {@link Stopwatch} with the specified time source.
     */
    private Stopwatch(Ticker ticker) {
	this.ticker = ticker;
    }

    /**
     * Returns a flag representing whether or not the stopwatch is running.
     */
    public boolean isRunning() {
	return running;
    }

    /**
     * Starts the stopwatch and returns the instance of this stopwatch, for
     * chaining.
     */
    public Stopwatch start() {
	running = true;
	startTime = ticker.time();
	return this;
    }

    /**
     * Stops the stopwatch and returns the instance of this stopwatch, for
     * chaining.
     */
    public Stopwatch stop() {
	long current = ticker.time();
	running = false;
	elapsed += current - startTime;
	return this;
    }

    /**
     * Resets, stops and returns the instance of this stopwatch, for chaining.
     */
    public Stopwatch reset() {
	elapsed = 0;
	running = false;
	return this;
    }

    /**
     * Returns the elapsed time, in nanoseconds.
     */
    private long elapsed() {
	return running ? ticker.time() - startTime + elapsed : elapsed;
    }

    /**
     * Returns the elapsed time in the specified time unit.
     */
    public long elapsed(TimeUnit desiredUnit) {
	return desiredUnit.convert(elapsed(), NANOSECONDS);
    }

}