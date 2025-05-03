package cpuscheduler;

import java.io.*;
import java.util.*;

public interface Scheduler {
    void schedule ( ArrayList<Process> processList , BufferedWriter bw , PriorityQueue<AlgoResult> pq ) throws IOException ;
}
