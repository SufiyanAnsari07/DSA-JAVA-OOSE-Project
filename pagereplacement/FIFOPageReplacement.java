package pagereplacement;
import java.io.*;
import java.util.*;

class FIFOPageReplacement implements PageReplacementAlgorithm {
    public int getPageFaultCount ( List<Integer> referenceString , int frameCount ) {
        Set<Integer> frames = new HashSet<>() ;
        Queue<Integer> queue = new LinkedList<>() ;
        int pageFaults = 0 ;

        for ( int page : referenceString ) {
            if ( !frames.contains ( page ) ) {
                if ( frames.size() == frameCount ) {
                    int removed = queue.poll() ;
                    frames.remove ( removed ) ;
                }
                frames.add ( page ) ;
                queue.add ( page ) ;
                pageFaults++ ;
            }
        }
        return pageFaults ;
    }
}
