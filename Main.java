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
            sc.nextLine(); // because of the "month,day,commodities" line at first.
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
        if(month<0 || month>11 || day<1 || day>28){
            return -99999;
        }
        int total =0;
        for(int c = 0;c < commodities.length;c++){
            total += profits[month][day -1][c];
        }
        // made it day - 1 because it says the input is from 1 to 28 but array indeks is 0-27
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int c = -1;
        for(int i = 0;i< commodities.length;i++){
           if(commodities[i].equals(commodity)) c = i;
        }
        if(c == -1 || from < 1 || to <1 || to > 28 || from > to) {
            return -99999;
        }
        int total=0;
        for(int m =0;m<MONTHS;m++){
            for(int f =from;f <= to;f++){
                total += profits[m][f-1][c];
            }
        }
        return total;
    }

    public static int bestDayOfMonth(int month) {
        if(month<0 || month>11)return -1;
        int bestDay =1;
        int bestProfit=Integer.MIN_VALUE; // so that any profit in day 1 is still bigger than this initializing value.
        for(int d =1;d<=28;d++){
            int dailyP=0;
            for(int c =0;c<commodities.length;c++){
                dailyP += profits[month][d-1][c];
            }
            if(dailyP > bestProfit){
                bestDay =d;
                bestProfit = dailyP;
            }
        }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {
        int c=-1;
        for(int i = 0;i<commodities.length;i++){
            if(commodities[i].equals(comm)){
                c=i;
                break;
            }
        }
        if(c==-1)return "INVALID_COMMODITY";
        int bestM=0;
        int bestProfit=Integer.MIN_VALUE;
        for(int m=0;m <months.length;m++){
            int total=0;
            for(int d=0;d < 28;d++){
                total += profits[m][d][c];
            }
            if(total>bestProfit){
                bestM=m;
                bestProfit=total;
            }
        }
        return months[bestM];
    }

    public static int consecutiveLossDays(String comm) {
        int c=-1;
        for(int i = 0;i<commodities.length;i++){
            if(commodities[i].equals(comm)){
                c=i;
                break;
            }
        }
        if(c==-1)return -1;
        int longestStreak=0;
        int streak=0;
        for(int m = 0;m<12;m++){
            for(int d=0;d<28;d++){
                if(profits[m][d][c] < 0){
                    streak++;
                    if(streak > longestStreak){
                        longestStreak=streak;
                    }
                }
                else{
                    streak=0;
                }
            }
        }
        return longestStreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
        int c=-1;
        for(int i = 0;i<commodities.length;i++){
            if(commodities[i].equals(comm)){
                c=i;
                break;
            }
        }
        if(c==-1)return -1;

        int aboveThreshold=0;
        for(int m = 0;m<12;m++){
            for(int d=0;d<28;d++){
                if(profits[m][d][c] >threshold){
                    aboveThreshold++;
                }
            }
        }
        return aboveThreshold;
    }

    public static int biggestDailySwing(int month) {
        if(month<0 || month>11)return -99999;
        int biggestSwing=0;
        int swing;
        for(int d =2;d<29;d++){ // d input is one more than 1 -27 because totalProfıt day's day ınput ıs 1 plus of the array index.
            swing = Math.abs(totalProfitOnDay(month,d) - totalProfitOnDay(month,d-1));
            if(swing>biggestSwing){
                biggestSwing=swing;
            }
        }
        return biggestSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
        int c_1 = -1;
        int c_2 = -1;
        for(int i = 0;i< commodities.length;i++){
            if(commodities[i].equals(c1)) c_1 = i;
            if(commodities[i].equals(c2)) c_2 = i;

        }
        if(c_1 == -1 || c_2 == -1) {
            return "INVALID_COMMODITY";
        }
        int total1=0;
        int total2=0;
        for(int m =0;m<MONTHS;m++){
            for(int d=0;d<28;d++){
                total1 += profits[m][d][c_1];
                total2 += profits[m][d][c_2];
            }
        }
        if(total1 > total2){
            return c1 + " is better by " + (total1 - total2);
        }else if(total1 < total2){
            return c2 + " is better by " + (total2 - total1);
        }else{
            return "Equal";
        }
    }
    
    public static String bestWeekOfMonth(int month) {
        if(month<0 || month >11) return "INVALID_MONTH";
        int bestWeek=-1;
        int bestProfit= Integer.MIN_VALUE;
        for(int w =1;w<=(DAYS/7);w++){
            int week=0;
            for(int i =0;i<7;i++){
                week += totalProfitOnDay(month,(w-1)*7 + i + 1); // +1 because method takes 1 more than the index.
            }
            if(week>bestProfit){
                bestWeek=w;
                bestProfit=week;
            }
        }

        return "Week " + bestWeek;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}