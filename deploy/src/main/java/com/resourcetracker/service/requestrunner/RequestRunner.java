package com.resourcetracker.service.requestrunner;

import com.resourcetracker.ProcService;
import com.resourcetracker.service.context.entity.ContextEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class RequestRunner implements Runnable{
	ProcService procService;

	private ContextEntity.Request request;

	public RequestRunner(ContextEntity.Request request){
		this.request = request;

		this.procService = new ProcService();
	}

	@Override
	public void run() {
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(() -> {
			procService.setCommands(request.data);
		}, 0, this.request.frequency, MILLISECONDS);
	}
}
