package com.example.similarity;

import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

/**
 * Created by pmandrek on 4/10/13.
 */
public enum Algorithm {
    LEVENSHTEIN(1, "Levenshtein"),
    MONGEELKAN(2, "Monge Elkan"),
    BLOCKDISTANCE(3, "Block Distance"),
    CHAPMANLENGTHDEVIATION(4,"Chapman Length Deviation"),
    CHAPMANMATCHINGSOUNDEX(5,"Chapman Matching Soundex"),
    CHAPMANORDEREDNAMECOMPOUNDSIMILARITY(6,"Chapman Ordered Name Compound Similarity"),
    COSINESIMILARITY(7,"Cosine Similarity"),
    DICESIMILARITY(8,"Dice Similarity"),
    EUCLIDEANDISTANCE(9,"Euclidean Distance"),
    JACCARDSIMILARITY(10,"Jaccard Similarity"),
    MATCHINGCOEFFICIENT(11,"Matching Coefficient"),
    JARO(12,"Jaro"),
    JAROWINKLER(13,"Jaro Winkler"),
    NEEDLEMANWUNCH(14,"Needleman Wunch"),
    OVERLAPCOEFFICIENT(15,"Overlap Coefficient"),
    QGRAMSDISTANCE(16,"QGrams Distance"),
    SMITHWATERMAN(17,"Smith Waterman"),
    SMITHWATERMANGOTOH(18,"Smith Waterman Gotoh"),
    SMITHWATERMANGOTOHWINDOWEDAFFINE(19,"Smith Waterman Gotoh Windowed Affine"),
    SOUNDEX(20,"Soundex"),
    TAGLINK(21,"Tag Link"),
    TAGLINKTOKEN(22,"Tag Link Token");

    int mCode;
    String mDisplayName;

    private Algorithm(int code, String displayName){
        this.mCode = code;
        this.mDisplayName = displayName;
    }

    public int getCode(){
        return mCode;
    }

    public String getDisplayName(){
        return mDisplayName;
    }


    public static Algorithm getAlgorithm(String displayName){

        for(Algorithm a: Algorithm.values()){
            if(a.getDisplayName().equals(displayName))
                return a;
        }

        return Algorithm.LEVENSHTEIN;
    }

    public static AbstractStringMetric getMetric(Algorithm algo){
        switch(algo){
            case LEVENSHTEIN:
                return new Levenshtein();
            case MONGEELKAN:
                return new MongeElkan();
            case BLOCKDISTANCE:
                return new BlockDistance();
            case CHAPMANLENGTHDEVIATION:
                return new ChapmanLengthDeviation();
            case CHAPMANMATCHINGSOUNDEX:
                return new ChapmanMatchingSoundex();
            case CHAPMANORDEREDNAMECOMPOUNDSIMILARITY:
                return new ChapmanOrderedNameCompoundSimilarity();
            case COSINESIMILARITY:
                return new CosineSimilarity();
            case DICESIMILARITY:
                return new DiceSimilarity();
            case EUCLIDEANDISTANCE:
                return new EuclideanDistance();
            case JACCARDSIMILARITY:
                return new JaccardSimilarity();
            case MATCHINGCOEFFICIENT:
                return new MatchingCoefficient();
            case JARO:
                return new Jaro();
            case JAROWINKLER:
                return new JaroWinkler();
            case NEEDLEMANWUNCH:
                return new NeedlemanWunch();
            case OVERLAPCOEFFICIENT:
                return new OverlapCoefficient();
            case QGRAMSDISTANCE:
                return new QGramsDistance();
            case SMITHWATERMAN:
                return new SmithWaterman();
            case SMITHWATERMANGOTOH:
                return new SmithWatermanGotoh();
            case SMITHWATERMANGOTOHWINDOWEDAFFINE:
                return new SmithWatermanGotohWindowedAffine();
            case SOUNDEX:
                return new Soundex();
            case TAGLINK:
                return new TagLink();
            case TAGLINKTOKEN:
                return new TagLinkToken();
            default:
                return new Levenshtein();

        }

    }

}
