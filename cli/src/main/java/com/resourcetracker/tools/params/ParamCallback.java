package com.resourcetracker.tools.params;

import com.resourcetracker.config.Config;
import java.util.ArrayList;

public interface ParamCallback {
    default public void call(ArrayList<String> args) throws Exception{};
}

