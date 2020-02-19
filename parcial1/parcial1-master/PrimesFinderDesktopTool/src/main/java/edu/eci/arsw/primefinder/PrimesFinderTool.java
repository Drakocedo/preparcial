package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class PrimesFinderTool {
	
	
	
    static int procesos=4;
	public static AtomicInteger hilosActivos = new AtomicInteger(procesos);
	static Boolean pausa =false;

	public static void main(String[] args) {
		            
            int maxPrim=50;
           
            
            PrimesResultSet prs=new PrimesResultSet("john");

            PrimeFinderThread  hilos[] = new  PrimeFinderThread[procesos];
            
            int inicio =0;
            
            int parte = maxPrim/procesos;
           
            int fin=0;
            
            for (int i=0;i<procesos;i++) {
                fin= inicio+parte;

            	//Si los hilos no se pueden dividir equitativamente 
                if (procesos==i+1 && fin<maxPrim) {
                	fin=maxPrim;
                }
                //System.out.println("inicio:"+inicio);

                //System.out.println("fin:"+fin);
            	hilos[i]=new PrimeFinderThread(new BigInteger(String.valueOf(inicio++)),new BigInteger(String.valueOf(fin)), prs);
            	hilos[i].start();  
            	inicio=fin;
            	
               
            }
           
            
            while (hilosActivos.get()>0) {
            	 try {
                     //check every 10ms if the idle status (10 seconds without mouse
                     //activity) was reached. 
                     Thread.sleep(10);
                     if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement()>10000){
                         System.out.println("Idle CPU ");
                         pausa=true;
                     }
                     else{
                         System.out.println("User working again!");
                         pausa=false;

                     }
                 } catch (InterruptedException ex) {
                     Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
                 }
            }
            
            //PrimeFinder.findPrimes(new BigInteger("1"), new BigInteger("50"), prs);
          
            System.out.println("Prime numbers found:");
            
            System.out.println(prs.getPrimes());
            
            
                        
            
            
            
            
	}
	
	 public static boolean isPaused(){
         return pausa;
     }
	
}


