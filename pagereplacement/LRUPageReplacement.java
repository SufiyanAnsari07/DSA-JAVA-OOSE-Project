package pagereplacement;
import java.io.*;
import java.util.*;

class LRUPageReplacement implements PageReplacementAlgorithm {
    public int getPageFaultCount ( List<Integer> referenceString , int frameCount ) {
        Set<Integer> frames = new HashSet<>() ;
        Map<Integer, Integer> recentUse = new HashMap<>() ;
        int pageFaults = 0 ;

        for ( int i = 0 ; i < referenceString.size() ; i++ ) {
            int page = referenceString.get ( i ) ;

            if ( !frames.contains ( page ) ) {
                if ( frames.size() == frameCount ) {
                    int lruPage = -1 ;
                    int minIndex = Integer.MAX_VALUE ;

                    for ( int key : recentUse.keySet() ) {
                        int lastUsed = recentUse.get ( key ) ;

                        if ( lastUsed < minIndex ) {
                            minIndex = lastUsed ;
                            lruPage = key ;
                        }
                    }
                    frames.remove ( lruPage ) ;
                    recentUse.remove ( lruPage ) ;
                }
                frames.add ( page ) ;
                pageFaults++ ;
            }
            recentUse.put ( page , i ) ;
        }
        return pageFaults ;
    }
}
