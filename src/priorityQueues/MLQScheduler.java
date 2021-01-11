/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package priorityQueues;

import java.util.LinkedList;

public class MLQScheduler {

    private LinkedList[] mlq;
    private LinkedList<Process> processes = new LinkedList();
    private LinkedList<Process> processQueue = new LinkedList();
    private LinkedList<Process> arrivedProcesses = new LinkedList();
    private LinkedList<Process> waitingProcesses = new LinkedList();
    private LinkedList<Process> finishedProcesses = new LinkedList();
    private int quanta;
    private int time = 0;
    private Process execProc;

    public MLQScheduler(LinkedList<Process> processes, int quanta) {
        this.quanta = quanta;
        this.processes = processes;
        processes.sort((o1, o2) -> {
            return o1.arrivalTime - o2.arrivalTime;
        });
        this.processQueue = (LinkedList<Process>) processes.clone();
        this.mlq = new LinkedList[7];
        for (int i = 0; i < mlq.length; i++) {
            mlq[i] = new <Process> LinkedList();
        }

    }

    public void insertArrivedProcesses() {
        while (true) {
            if (!processes.isEmpty() && processes.peek().arrivalTime <= time) {
                mlq[processes.peek().priority - 1].addLast(processes.pop());
            } else {
                return;
            }
        }
    }

    public void nextCycle() {
        while (!waitingProcesses.isEmpty()) {
            mlq[waitingProcesses.peek().priority - 1].offerLast(waitingProcesses.removeFirst());
        }
    }

    public LinkedList<Process> executeProcesses() {
        //o xronos pernei tin timi tou xronou afiksis tis prwtis diergasias
        time = processes.peek().arrivalTime;
        //topo9etounte sta epipeda protereotitas oi diergasies tis listas processes
        insertArrivedProcesses();
        //oso diarkei i diadikasia
        while (true) {
            //gia ka9e stoixeio tou pinaka tis ouras prwtereotitwn
            for (int i = 0; i < mlq.length - 1; i++) {
                //oso i sigkekrimeni oura den einai adia
                while (!mlq[i].isEmpty()) {
                    //i diergasia pou 9a ekteles9ei vgenei apo tin oura tis me dequeue
                    execProc = (Process) mlq[i].removeFirst();
                    //an ektelite gia prwti fora
                    if (execProc.startTime == -1) {
                        execProc.startTime = time;
                    }
                    //an o xronos kataigismou pou apomenei einai megaliteros tou kvantou i diergasia ektelite oso to kvanto
                    if (execProc.remainingBurstTime > quanta) {
                        time += quanta;
                        execProc.remainingBurstTime -= quanta;
                        //eisagwgi stin oura anamonis
                        waitingProcesses.offer(execProc);
                        //an o xronos kategismou einai mikroteros tou kvantou, tote i diergasia oloklironete kai topo9etite stin lista me tis olokliromenes diergasies
                    } else if (execProc.remainingBurstTime <= quanta) {
                        time += execProc.remainingBurstTime;
                        execProc.finishTime = time;
                        //ypologizontai ksexwrista WT, TAT
                        execProc.calcWaitingTime();
                        execProc.calcTurnaroundTime();
                        execProc.calcResponeTime();
                        execProc.remainingBurstTime = 0;
                        finishedProcesses.offer(execProc);
                    }
                }
                //an to mege9os tis listas me tis olokliromenes diergasies einai to idio me to mege9os tis arxikis listas, return
                if (processQueue.size() == finishedProcesses.size()) {
                    System.out.println();
                    return finishedProcesses;
                }
            }
            //elegxoume an meta tin ektelesi olwn twn twrinwn diergasiwn, exoun er9ei alles diergasies gia na eisax9oun stis oures protereotitas
            insertArrivedProcesses();
            //otan teleionei o kyklos oles tis oures protereotitas,  epanatopo9etoume tis diergasies apo tin oura anamonis, stis antistoixes oures protereotitas
            nextCycle();
        }
    }

    public double averageTurnaroundTime() {
        LinkedList<Process> temp = (LinkedList<Process>) processQueue.clone();
        int sum = 0;
        int size = temp.size();
        while (!temp.isEmpty()) {
            sum += temp.poll().turnaroundTime;
        }
        return (double) sum / (double) size;
    }

    public double averageWaitingTime() {
        LinkedList<Process> temp = (LinkedList<Process>) processQueue.clone();
        int sum = 0;
        int size = temp.size();
        while (!temp.isEmpty()) {
            sum += temp.poll().waitingTime;
        }
        return (double) sum / (double) size;
    }

    public double averageResponseTime() {
        LinkedList<Process> temp = (LinkedList<Process>) processQueue.clone();
        int sum = 0;
        int size = temp.size();
        while (!temp.isEmpty()) {
            sum += temp.poll().responseTime;
        }
        return (double) sum / (double) size;
    }
}
