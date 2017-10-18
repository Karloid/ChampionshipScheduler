package com.krld;

public interface ScheduleAlgorithm {
    Championship schedule(Championship championship);

    int getIterations();

    String getName();
}
