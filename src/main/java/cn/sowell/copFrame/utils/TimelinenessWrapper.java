package cn.sowell.copFrame.utils;

import java.util.function.Supplier;

public class TimelinenessWrapper<T> {
	private T source;
	private final long timeline;
	private Long lastTime = null;
	public TimelinenessWrapper(long timeline) {
		super();
		this.timeline = timeline;
	}
	
	public synchronized T getObject(Supplier<T> loadFunction) {
		Long now = System.currentTimeMillis();
		if(lastTime == null || now - lastTime > timeline) {
			source = loadFunction.get();
			lastTime = now;
		}
		return source;
	}
	
	
	
}
