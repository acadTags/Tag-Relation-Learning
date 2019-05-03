% get the most associated tags of one tag.
function [mostAssoTagList,asso_probs] = getMostAssociatedTagsThreshold(tag,taglist,ptz,rep,threshold)
    m = size(taglist,1);
    asso_probs=zeros(m,1);
    [~,index] = getvector(tag,taglist,rep);
    for i=1:m
        if i ~= index
            %vtag1 = rep(i,:);
            %sim(i,1) = getcossim(vtag,vtag1); %cosine sim does not make a difference.
            %sim(i,1) = JSDiv(vtag,vtag1);
            asso_probs(i,1) = getpww(i,index,ptz,rep); % here we used a symmetric divergence based on kullback-leibler divergence.
            %cossim(i,2) = i;
        else
            asso_probs(i,1) = 0;
            %cossim(i,2) = 1;
        end
    end
    [prob_values,I] = sort(asso_probs,'descend');
    num = sum(prob_values > threshold);
    mostAssoTagList = taglist(I(1:num));
end
