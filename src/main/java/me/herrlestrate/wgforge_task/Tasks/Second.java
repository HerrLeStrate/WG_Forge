package me.herrlestrate.wgforge_task.Tasks;

import me.herrlestrate.wgforge_task.Application;
import me.herrlestrate.wgforge_task.SQL.SQLWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Second {

    private List<Integer> tails = new ArrayList<>();
    private List<Integer> whiskers = new ArrayList<>();

    public Second(){
        SQLWorker sqlWorker = Application.getSqlWorker();

        ResultSet rs = sqlWorker.getCats();

        if(rs == null){
            System.out.println("Second task failed!");
            return;
        }

        tails.clear();
        whiskers.clear();
        sqlWorker.clearTable("cats_stat");

        while(true){
            try {
                if (!rs.next()) break;

                tails.add(rs.getInt(rs.findColumn("tail_length")));
                whiskers.add(rs.getInt(rs.findColumn("whiskers_length")));

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        String sql = "INSERT INTO cats_stat VALUES ('"
                +getMean(tails)+"', '"
                +getMiddle(tails)+"', "
                +toSQLQuery(getMode(tails))+", '"
                +getMean(whiskers)+"', '"
                +getMiddle(whiskers)+"', "
                +toSQLQuery(getMode(whiskers))+");";
        sqlWorker.putCatsStat(sql);

        System.out.println("Second task done!");

    }

    public double getRounded(double value){
        return Math.round(value*100)/100.;
    }

    public double getMean(List<Integer> elements){
        long mean = 0;
        for(int i=0;i<elements.size();i++)mean+=elements.get(i);
        return ((elements.size() == 0) ? 0 : ((mean/1.) / (elements.size()/1.)));
    }

    public double getMiddle(List<Integer> elements){
        if(elements.size() == 0)return 0;
        Collections.sort(elements);
        return ((elements.size() % 2 == 1)
                ? elements.get(elements.size()/2)
                : ((elements.get(elements.size()/2) + elements.get(elements.size()/2 - 1))/2.0));
    }

    public List<Integer> getMode(List<Integer> elements){
        List<Integer> result = new ArrayList<>();
        result.clear();

        if(elements.size() == 0){
            result.add(0);
            return result;
        }

        Collections.sort(elements);

        int mx = 1,max = 0;
        for(int i=1;i<elements.size();i++)
            if(!elements.get(i).equals(elements.get(i-1))) {
                max = Math.max(max,mx);
                mx = 1;
            }
            else mx++;
        max = Math.max(max,mx);
        mx = 1;
        for(int i=1;i<elements.size();i++)
            if(!elements.get(i).equals(elements.get(i-1))) {
                if(mx == max){
                    result.add(elements.get(i-1));
                }
                mx = 1;
            }
            else mx++;
        if(mx == max)result.add(elements.get(elements.size()-1));
        return result;
    }

    public String toSQLQuery(List<Integer> list){
        String sql = "'{";
        for(int i=0;i<list.size();i++)sql+=list.get(i)+((i!=list.size()-1) ? ", " : "");
        sql+="}'";
        return sql;
    }
}
