% calculate the p(i|tag1,tag2) and ranking it for all tags to get the most
% associated tags from two other tags.
function [mostAssoTagList,prob_values] = getMostAssociatedTagsFromTwoTagsFromCandidates(tag1,tag2,candidate_tag_list,taglist,ptz,rep,pz,threshold)
    m = size(taglist,1);
    m_c = size(candidate_tag_list,1);
    asso_probs=zeros(m_c,1);
    [~,index1] = getvector(tag1,taglist,rep);
    [~,index2] = getvector(tag2,taglist,rep);
	
	m = size(taglist,1);
    % using hashmap to get index.
    hm = java.util.HashMap;
    for i=1:m
        hm.put(taglist{i},i);
    end
	
    for i=1:m_c
        indexi = hm.get(candidate_tag_list{i});
		%[~,indexi] = getvector(candidate_tag_list{i},taglist,rep); % find the index of this candidate in the taglist.
        if (indexi ~= index1 && indexi~=index2)
            asso_probs(i,1) = getpwwR(index1,index2,indexi,ptz,rep,pz)*getpww(index2,indexi,ptz,rep)*getpw(indexi,ptz,pz)/(getpww(index1,index2,ptz,rep)*getpw(index2,ptz,pz));
            % p(i|tag1,tag2) = p(tag1|tag2,i)*p(tag2|i)*p(i)/p(tag1|tag2)*p(tag2)
        else
            asso_probs(i,1) = 0;
        end
    end
    [prob_values,I] = sort(asso_probs,'descend');
    num = sum(prob_values > threshold);
    
    mostAssoTagList = candidate_tag_list(I(1:num));
end
