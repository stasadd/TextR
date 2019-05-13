package GV;

import javafx.concurrent.Task;

import java.math.BigDecimal;
import java.math.BigInteger;

public class FibonacciTask extends Task<String> {

    String outputdata;
    BigInteger number;
    int percentage;

    public void setNumber(BigInteger number) {
        this.number = number;
        percentage = 0;
    }

    public FibonacciTask() {
        this.number = BigInteger.valueOf(1);
        percentage = 0;
    }

    public FibonacciTask(String inputdata) {
        number = new BigInteger(inputdata);
    }

    public String createRezult(){
        outputdata = "";
        for(int i=1;number.compareTo(BigInteger.valueOf(i))>-1;i++){
            Long l = Math.round(BigDecimal.valueOf(i).divide(BigDecimal.valueOf(number.intValue())).multiply(BigDecimal.valueOf(100)).doubleValue());
            percentage = l.intValue();

            this.updateProgress(percentage, 100);

            BigInteger help = findNumericArr(BigInteger.valueOf(i));
            outputdata+=help.toString();
            outputdata+=" ";

            if(Thread.currentThread().isInterrupted()) return "";
        }
        return outputdata;
    }

    public int getPercentage() {
        return percentage;
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

    @Override
    protected String call() throws Exception {
        return createRezult();
    }
}
