package com.krld.schedulers;

import com.krld.models.Championship;

public interface Scheduler {
    Championship schedule(Championship championship);

    int getIterations();

    String getName();
}
