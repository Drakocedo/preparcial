package edu.eci.arsw.primefinder;

import java.math.BigInteger;

public class PrimeFinderThread extends Thread{

    private PrimesResultSet primesResultSet;
    private BigInteger a;
    private BigInteger b;

    public PrimeFinderThread(BigInteger a, BigInteger b, PrimesResultSet primesResultSet){
        this.primesResultSet = primesResultSet;
        this.a = a;
        this.b = b;
    }


    @Override
    public void run(){
    	
        PrimeFinder.findPrimes(a, b, primesResultSet);
    }


}