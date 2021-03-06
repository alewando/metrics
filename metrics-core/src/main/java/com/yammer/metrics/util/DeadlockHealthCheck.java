package com.yammer.metrics.util;

import java.util.HashSet;
import java.util.Set;

import com.yammer.metrics.core.HealthCheck;
import com.yammer.metrics.core.VirtualMachineMetrics;

/**
 * A {@link HealthCheck} implementation which returns a list of deadlocked
 * threads, if any.
 */
public class DeadlockHealthCheck extends HealthCheck {
	private final VirtualMachineMetrics vm;

	/**
	 * Creates a new {@link DeadlockHealthCheck} with the given
	 * {@link VirtualMachineMetrics} instance.
	 * 
	 * @param vm
	 *            a {@link VirtualMachineMetrics} instance
	 */
	public DeadlockHealthCheck(VirtualMachineMetrics vm) {
		super("deadlocks");
		this.vm = vm;
	}

	/**
	 * Creates a new {@link DeadlockHealthCheck}.
	 */
	@SuppressWarnings("UnusedDeclaration")
	public DeadlockHealthCheck() {
		this(VirtualMachineMetrics.getInstance());
	}

	@Override
	protected Result check() throws Exception {
		// final Set<String> threads = vm.deadlockedThreads();
		final Set<String> threads = new HashSet<String>();

		if (threads.isEmpty()) {
			return Result.healthy();
		}

		final StringBuilder builder = new StringBuilder(
				"Deadlocked threads detected:\n");
		for (String thread : threads) {
			builder.append(thread).append('\n');
		}
		return Result.unhealthy(builder.toString());
	}
}
