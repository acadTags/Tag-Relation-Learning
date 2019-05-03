function cossim = getcosinesim(vtag1,vtag2)
% get the cosine similarity (= 1 - cosine divergence) between two tags, tag1 and tag2.
% input: the vector of tag1 and tag2, resp. vtag1 and vtag2.
% output: a similarity value
    % check whether the magnitude of the vector is too small.
    %if sqrt(sum(vtag1.^2,2)) < 0.0001 || sqrt(sum(vtag2.^2,2)) < 0.0001
    %    cossim = NaN;
    %else  
        cossim = 1-pdist([vtag1;vtag2], 'cosine');
        %cossim = acos(dot(vtag1,vtag2)/(norm(vtag1,2)*norm(vtag2,2)));
    %end    
end