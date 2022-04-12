package com.resourcetracker.loop;

import com.resourcetracker.ticker.Ticker;
import com.resourcetracker.entities.Result;
import com.resourcetracker.requests.RequestManager;
import org.javatuples.Pair;

public class Loop {
	public static void run() {
		while (true) {
			Pair<ArrayList<String>>RequestManager.handleRequestsFromContext();
			Ticker.wait();
		}
	}
}
