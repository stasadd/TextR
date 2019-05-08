package GV;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Fibonacci {
    String outputdata;
    List<BigInteger> list;
    BigInteger number;

    public Fibonacci(String inputdata) {
        number = new BigInteger(inputdata);
        list = new ArrayList<BigInteger>();
        outputdata = "";
    }
}
