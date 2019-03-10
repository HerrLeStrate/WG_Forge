package me.herrlestrate.wgforge_task;

import me.herrlestrate.wgforge_task.SQL.SQLWorker;
import me.herrlestrate.wgforge_task.Tasks.First;

import java.util.Scanner;

public class Application {
    private static SQLWorker sqlWorker;
    private static int TASKS_AVAILABLE = 1;

    public static void main(String[] args){
        sqlWorker = new SQLWorker();

        init();
    }

    private static void init(){
        Scanner in = new Scanner(System.in);
        boolean correctInput = false;
        int numberTask = 0;

        while(!correctInput) {
            System.out.println("Please, choose your task: ");
            numberTask= in.nextInt();
            correctInput = (numberTask>0 && numberTask <= TASKS_AVAILABLE);
        }

        switch (numberTask){
            case 1:
                new First();
                break;
            case 2:
                break;
            default:
                break;
        }
    }


    public static SQLWorker getSqlWorker() {
        return sqlWorker;
    }

}
