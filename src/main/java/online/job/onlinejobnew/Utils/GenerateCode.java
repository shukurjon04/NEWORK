package online.job.onlinejobnew.Utils;

import java.util.Random;

public class GenerateCode {

    private static int low;
    public static String Code(int bound){
        return String.valueOf(new Random().nextInt(bond(bound) - lowBound()) + lowBound());
    }

    private static int bond(int bound){
        if (bound <= 0){
            throw new IllegalArgumentException("bound must be greater than 0");
        }
        int lowBound = (int) Math.pow(10,bound-1);
        int highBound = (int) Math.pow(10,bound)-1;
        setlow(lowBound);
        return highBound;
    }
    private static int lowBound(){
        return low;
    }
    private static void setlow(int bound){
        low =bound;
    }
}
