function y = fuzzySetSimilarityGeneralisedJaccard(A,B)
    if sum(max(A,B)) == 0
        y = 0;
    else
        y = sum(min(A,B))/sum(max(A,B));
    end    
end