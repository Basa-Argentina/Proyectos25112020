package com.pruebi;
import java.math.BigInteger; 
class Puzzle { 
final static BigInteger M = new BigInteger("2017"); 
private static BigInteger compute(long n) { 
String s = ""; 
String a = new BigInteger(n+"").mod(M)+""; 
for (long i = 0; i < Integer.parseInt(a); i++) { 
s = s + a ; 
s = new BigInteger(s).mod(M)+""; 
} 
return new BigInteger(s.toString()).mod(M); 
} 
public static void main(String args[]) { 
for (long n : new long[] { 1L, 2L, 5L, 10L, 20L, 58184241583791680L }) { 
System.out.println("" + n + ": " + compute(n)); 
} 
} 
} 