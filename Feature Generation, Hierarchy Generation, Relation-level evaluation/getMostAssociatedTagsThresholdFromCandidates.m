% get the most associated tags of one tag.
function [mostAssoTagList,asso_probs] = getMostAssociatedTagsThresholdFromCandidates(tag,candidate_tag_list,taglist,ptz,rep,threshold)
    m = size(taglist,1);
    m_c = size(candidate_tag_list,1);
    asso_probs=zeros(m_c,1);
    [~,index] = getvector(tag,taglist,rep);
	
	m = size(taglist,1);
    % using hashmap to get index.
    hm = java.util.HashMap;
    for i=1:m
        hm.put(taglist{i},i);
    end
	
    for i=1:m_c
		indexi = hm.get(candidate_tag_list{i});
        %[~,indexi] = getvector(candidate_tag_list{i},taglist,rep); % find the index of this candidate in the taglist.
        if indexi ~= index
            %vtag1 = rep(i,:);
            %sim(i,1) = getcossim(vtag,vtag1); %cosine sim does not make a difference.
            %sim(i,1) = JSDiv(vtag,vtag1);
            asso_probs(i,1) = getpww(indexi,index,ptz,rep); % here we used a symmetric divergence based on kullback-leibler divergence.
            %cossim(i,2) = i;
        else
            asso_probs(i,1) = 0;
            %cossim(i,2) = 1;
        end
    end
    [prob_values,I] = sort(asso_probs,'descend');
    num = sum(prob_values > threshold);
    mostAssoTagList = candidate_tag_list(I(1:num));
end
