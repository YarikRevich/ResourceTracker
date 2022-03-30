package com.resourcetracker.loop;

import com.resourcetracker.ticker.Ticker;
import com.resourcetracker.context.Context;
import com.resourcetracker.tools.utils.AddressSort;
import com.resourcetracker.entities.Result;
import com.resourcetracker.requests.Request;

public class Loop {
    public static void run() {
        ArrayList<Result> res = new ArrayList<Result>();
        while (true) {
            res.clear();

            synchronized (Loop) {
                Context.getAddresses().forEach((k, v) -> {
                    new Thread(() -> {
                        for (String url : v.schemas()) {
                            boolean ok = Request.isOk(url);
                            if (ok){
                                
                            }
                        }
                        // boolean isReachable = this.isReachable(addressWithTag.address());
                        // if (isReachable) {
                        // infoResult.add(new ListenerResult(addressWithTag.index(),
                        // String.format("%s(%s): ok",
                        // addressWithTag.tag(), addressWithTag.address().toString())));
                        // } else {
                        // infoResult.add(new ListenerResult(addressWithTag.index(),
                        // String.format("%s(%s): bad",
                        // addressWithTag.tag(), addressWithTag.address().toString())));
                        // }

                    }).start();
                });
            }

            AddressSort.sort(res);

            Ticker.wait();
        }
    }
}
