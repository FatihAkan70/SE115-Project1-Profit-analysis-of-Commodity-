// Main.java — Students version
import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    static int[][][] profits = new int[MONTHS][DAYS][COMMS];
    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        // implementing the data to "profits" multidimensional array
        for(int m = 0;m < months.length;m++) {
        try{
            Scanner sc = new Scanner(Paths.get("Data_Files/" + months[m]+".txt"));

            while(sc.hasNextLine()){
                String[] words = sc.nextLine().split(",");
                int day = Integer.parseInt(words[0]); // day - com - profit sırası oldugu ıcın ayırıp okuyorum
                String commodity = words[1];
                int c=-1;
                for(int i=0;i<5;i++){
                    if(commodities[i].equals(commodity)){
                        c=i;
                        break;
                    }
                }
                int profit = Integer.parseInt(words[2]);
                if(c!=-1){
                    profits[m][day-1][c] = profit;
                }

            }
            sc.close();
        }catch(Exception e){}
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if(month<0 || month >11)return "INVALID_MONTH";
        int[] profOfComm = new int[5];
        for(int d = 0;d<28;d++){
            for(int c = 0;c<5;c++){
                profOfComm[c] += profits[month][d][c];
            }
        }
        int bestC =0;
        for(int c =1;c<5;c++){
            if(profOfComm[c] >profOfComm[bestC]){
                bestC =c;
            }
        }
        return (commodities[bestC] + " " + profOfComm[bestC]);
    }

    public static int totalProfitOnDay(int month, int day) {
        return 1234;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}