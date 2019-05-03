function entropy = getEntropy(vtag)
% get the entropy from a tag distribution on topics
    temp = vtag.*log2(vtag);
    entropy = -sum(temp,2);
end

