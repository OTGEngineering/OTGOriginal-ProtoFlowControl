package com.Xernium.ProtoFlow;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;


public class MovementErrorFilter extends AbstractFilter {

	@Override
	public Result filter(LogEvent event) {
		if (event.getLevel().equals(Level.WARN)) {
			return isLoggable(event.getMessage().getFormattedMessage());
		}
		return Result.NEUTRAL;

	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
		if (level.equals(Level.WARN)) {
			return isLoggable(msg.getFormattedMessage());
		}
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
		if (level.equals(Level.WARN)) {
			return isLoggable(msg);
		}
		return Result.NEUTRAL;
	}

	@Override
	public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
		if (level.equals(Level.WARN)) {
			return isLoggable(msg.toString());
		}
		return Result.NEUTRAL;
	}

	private Result isLoggable(String msg) {
		try {
			if (msg != null) {
				if (msg.contains(" moved too quickly!")) {
					
						return Result.DENY;
						
					
						

				}
			}
		} catch (Exception e) {
			return Result.NEUTRAL;
		}

		return Result.NEUTRAL;
	}

}