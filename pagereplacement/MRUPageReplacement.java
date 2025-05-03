package pagereplacement;
import java.io.*;
import java.util.*;

class MRUPageReplacement implements PageReplacementAlgorithm {
    public int getPageFaultCount ( List<Integer> referenceString , int frameCount ) {
        Set<Integer> frames = new HashSet<>() ;
        Map<Integer, Integer> recentUse = new HashMap<>() ;
        int pageFaults = 0 ;

        for ( int i = 0 ; i < referenceString.size() ; i++ ) {
            int page = referenceString.get ( i ) ;

            if ( !frames.contains ( page ) ) {
                if ( frames.size() == frameCount ) {
                    int mruPage = -1 ;
                    int maxIndex = -1 ;

                    for ( int key : recentUse.keySet() ) {
                        int lastUsed = recentUse.get ( key ) ;

                        if ( lastUsed > maxIndex ) {
                            maxIndex = lastUsed ;
                            mruPage = key ;
                        }
                    }
                    frames.remove ( mruPage ) ;
                    recentUse.remove ( mruPage ) ;
                }
                frames.add ( page ) ;
                pageFaults++ ;
            }
            recentUse.put ( page , i ) ;
        }
        return pageFaults ;
    }
}
