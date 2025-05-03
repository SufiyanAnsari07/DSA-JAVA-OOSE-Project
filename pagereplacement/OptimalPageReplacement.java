package pagereplacement;
import java.io.*;
import java.util.*;

class OptimalPageReplacement implements PageReplacementAlgorithm {
    public int getPageFaultCount ( List<Integer> referenceString , int frameCount ) {
        Set<Integer> frames = new HashSet<>() ;
        int pageFaults = 0 ;

        for ( int i = 0 ; i < referenceString.size() ; i++ ) {
            int page = referenceString.get ( i ) ;

            if ( !frames.contains ( page ) ) {
                if ( frames.size() == frameCount ) {
                    int farthestIndex = -1 ;
                    int pageToRemove = -1 ;

                    for ( int p : frames ) {
                        int j ;
                        for ( j = i + 1 ; j < referenceString.size() ; j++ ) {
                            if ( referenceString.get ( j ) == p ) {
                                break ;
                            }
                        }

                        if ( j > farthestIndex ) {
                            farthestIndex = j ;
                            pageToRemove = p ;
                        }
                    }
                    frames.remove( pageToRemove ) ;
                }
                frames.add( page ) ;
                pageFaults++ ;
            }
        }
        return pageFaults ;
    }
}
