package pagereplacement;

import java.util.List;

public interface PageReplacementAlgorithm {
    int getPageFaultCount ( List<Integer> referenceString , int frameCount ) ;
}

