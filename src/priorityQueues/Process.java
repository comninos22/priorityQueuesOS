/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package priorityQueues;

/**
 *
 * @author CHRISTOS
 */
public class Process {

    private static int serial = 0;
    public int id;
    public int arrivalTime;
    public int burstTime;
    public int finishTime;
    public int remainingBurstTime;
    public int priority;
    public int waitingTime;
    public int turnaroundTime;
    public int startTime = -1;
    public int responseTime;

    public Process(int arrivalTime, int burstTime, int priority) {
        this.id = serial++;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
        this.priority = priority;
    }

    public static void restart() {
        serial = 0;
    }

    public void calcTurnaroundTime() {
        this.turnaroundTime = this.finishTime - this.arrivalTime;
    }

    public void calcWaitingTime() {
        this.waitingTime = this.finishTime - this.arrivalTime - this.burstTime;
    }

    public void calcResponeTime() {
        this.responseTime = this.startTime - this.arrivalTime;
        System.out.println(this.responseTime);
    }

    public Process() {
    }

    @Override
    public String toString() {
        return "\nProcess " + id + ": arrival time=" + arrivalTime + ", burst time=" + burstTime + ", finish time=" + finishTime + ", remaining burst time=" + remainingBurstTime + ", priority=" + priority + ", waiting time=" + waitingTime + ", response time=" + responseTime +  ", turn around time=" + turnaroundTime + ", start time=" + startTime;
    }

    public String miniToString() {
        return "\nProcess " + id + ": arrivalTime=" + arrivalTime + ", burstTime=" + burstTime + ", priority=" + priority;
    }
}
