package pagereplacement;
import java.io.*;
import java.util.*;

public class Main {
    public static void main ( String[] args ) throws IOException {
        Scanner sc = new Scanner( System.in ) ;
        List<Integer> referenceString = new ArrayList<>() ;
        BufferedWriter writer = new BufferedWriter ( new FileWriter ("Files/input.txt") ) ;

        System.out.print( "Enter number of frames : " ) ;
        int frameCount = sc.nextInt() ;
        writer.write ( "Number of frames : " + frameCount + "\n" ) ;
        sc.nextLine() ;

        System.out.print( "Enter page reference string (space-separated) : " ) ;
        String[] input = sc.nextLine().split ( " " ) ;
        writer.write ( "Reference String : " ) ;
        for ( String s : input ) {
            referenceString.add ( Integer.parseInt ( s ) ) ;
            writer.write ( s + " " ) ;
        }
        writer.close() ;

        PriorityQueue<AlgoResult> pq = new PriorityQueue<>() ;
        BufferedWriter bw = new BufferedWriter( new FileWriter ( "Files/output.txt" ) ) ;

        System.out.println ( "\nSelect the page replacement algorithm to use or press -1 to quit : " ) ;
        System.out.println ( "1. FIFO" ) ;
        System.out.println ( "2. LRU" ) ;
        System.out.println ( "3. Optimal" ) ;
        System.out.println ( "4. MRU" ) ;
        while ( true ) {
            System.out.print ( "Your choice : " ) ;
            int choice = sc.nextInt() ;

            if ( choice == -1 ) break ;

            PageReplacementAlgorithm algorithm = null ;
            String name = "" ;

            switch ( choice ) {
                case 1 :
                    algorithm = new FIFOPageReplacement() ;
                    name = "FIFO Page Replacement :" ;
                    break ;

                case 2 :
                    algorithm = new LRUPageReplacement() ;
                    name = "LRU Page Replacement :" ;
                    break ;

                case 3 :
                    algorithm = new OptimalPageReplacement() ;
                    name = "Optimal Page Replacement :" ;
                    break ;

                case 4 :
                    algorithm = new MRUPageReplacement() ;
                    name = "MRU Page Replacement :" ;
                    break ;

                default :
                    System.out.println ( "Invalid choice." ) ;
            }

            int faults = algorithm.getPageFaultCount ( referenceString , frameCount ) ;
            int hits = referenceString.size() - faults ;
            double hitRatio = (double) hits / referenceString.size() * 100 ;
            double faultRatio = (double) faults / referenceString.size() * 100 ;
            bw.write ( name + "\n" ) ;
            bw.write ( "Page Hits : " + hits + "\n" ) ;
            bw.write ( "Page Faults : " + faults + "\n" ) ;
            bw.write ( "Hit Ratio : " + hitRatio + "\n" ) ;
            bw.write ( "Fault Ratio : " + faultRatio + "\n\n" ) ;
            pq.add ( new AlgoResult ( faults , name ) ) ;
        }
        bw.write ("\n----- Algorithm Ranking by Minimum Page Faults -----\n" ) ;
        int rank = 1 ;
        while ( !pq.isEmpty() ) {
            AlgoResult result = pq.poll() ;
            bw.write ( rank + ". " + result.name + " Page Faults : " + result.faults + "\n" ) ;
            rank++ ;
        }
        bw.close() ;
        System.out.println ( "Page Replacement completed. Check 'Files/output.txt' for the results." ) ;
    }
}
class AlgoResult implements Comparable<AlgoResult> {
    int faults ;
    String name ;

    AlgoResult ( int faults , String name ) {
        this.faults = faults ;
        this.name = name ;
    }

    @Override
    public int compareTo ( AlgoResult other ) {
        if ( this.faults < other.faults ) return -1 ;   
        if ( this.faults > other.faults ) return 1 ;    
        return 0 ;                                    
    }
}

// 7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1 