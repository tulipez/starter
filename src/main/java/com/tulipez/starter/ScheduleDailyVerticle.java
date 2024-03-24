package com.tulipez.starter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class ScheduleDailyVerticle extends AbstractVerticle {

	// En attendant un bon cron-like
	
	private Runnable task;

	public ScheduleDailyVerticle(Runnable task) {
		this.task = task;
	}
	
	public @Override void start(Promise<Void> startPromise) throws Exception {
		scheduleDailyTask();
	}

	private void scheduleDailyTask() {
        long delay = calculateDelay();
        vertx.setTimer(delay, id -> {
        	task.run();
            scheduleDailyTask();
        });
    }
	
	private long calculateDelay() {
        LocalTime now = LocalTime.now();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate nextMidnight = now.isAfter(midnight) ? tomorrow : today;
        LocalDateTime nextMidnightDateTime = LocalDateTime.of(nextMidnight, midnight);
        return Duration.between(LocalDateTime.now(), nextMidnightDateTime).toMillis();
    }

}
