package GV;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
    String outputdata;
    BigInteger number;

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    public Fibonacci() {
        this.number = BigInteger.valueOf(1);
    }

    public Fibonacci(String inputdata) {
        number = new BigInteger(inputdata);
    }

    public String createRezult(){
        outputdata = "";
        for(int i=1;number.compareTo(BigInteger.valueOf(i))>-1;i++){
            BigInteger help = findNumeric(BigInteger.valueOf(i));
            outputdata+=help.toString();
            outputdata+=" ";
        }
        return outputdata;
    }

    BigInteger findNumeric(BigInteger n) {
        if (n.compareTo(BigInteger.valueOf(1))==0) return BigInteger.valueOf(1);
        if (n.compareTo(BigInteger.valueOf(2))==0) return BigInteger.valueOf(1);
        return findNumeric(n.subtract(BigInteger.valueOf(1))).add(findNumeric(n.subtract(BigInteger.valueOf(2))));
    }

    BigInteger findNumericArr(BigInteger n) {
        BigInteger n1 = BigInteger.valueOf(1);
        BigInteger n2 = BigInteger.valueOf(1);
        BigInteger n3 = null;
        if(n.compareTo(BigInteger.valueOf(1))==0)
            return n1;
        else if(n.compareTo(BigInteger.valueOf(2))==0)
            return n2;
        else{
            for(int i=3;n.compareTo(BigInteger.valueOf(i))>-1;i++){
                n3=n1.add(n2);
                n1=n2;
                n2=n3;
            }
            return n3;
        }
    }
}