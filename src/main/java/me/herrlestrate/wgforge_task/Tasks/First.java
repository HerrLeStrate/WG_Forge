package me.herrlestrate.wgforge_task.Tasks;

import me.herrlestrate.wgforge_task.Application;
import me.herrlestrate.wgforge_task.SQL.SQLWorker;

import java.sql.ResultSet;
import java.sql.SQLException;

public class First {

    public First(){
        SQLWorker sqlWorker = Application.getSqlWorker();

        sqlWorker.clearTable("cat_colors_info");

        ResultSet res = sqlWorker.getCats();

        if(res == null){
            System.out.println("First task Failed!");
            return;
        }

        while(true){
            try {
                if (!res.next()) break;
                String color = res.getString(res.findColumn("color"));
                sqlWorker.putCatColor(color);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        System.out.println("Task done!");
    }
}
