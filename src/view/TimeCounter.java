package view;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class TimeCounter {
    private static JLabel label2;
    private static void time3() {
        final int[] midTime = {50};
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                midTime[0]--;
//                long hh = midTime[0] / 60 / 60 % 60;
//                long mm = midTime[0] / 60 % 60;
                long ss = midTime[0] % 60;
                label2.setText("还剩"+ss+"秒");
                System.out.println("还剩" + ss + "秒");
                if(ss==0){
                    
                }
            }
        }, 0, 1000);
    }
    public static void main(String[] args){
        System.out.println("a");
        time3();
    }
    public void changeTime(JLabel label){
        this.label2=label;
    }


}
