% calculate the p(i|tag1,tag2) and ranking it for all tags to get the most
% associated tags from two other tags.
function [mostAssoTagList,prob_values] = getMostAssociatedTagsFromTwoTags(tag1,tag2,taglist,ptz,rep,pz,threshold)
    m = size(taglist,1);
    asso_probs=zeros(m,1);
    [~,index1] = getvector(tag1,taglist,rep);
    [~,index2] = getvector(tag2,taglist,rep);
    for i=1:m
        if (i ~= index1 && i~=index2)
            asso_probs(i,1) = getpwwR(index1,index2,i,ptz,rep,pz)*getpww(index2,i,ptz,rep)*getpw(i,ptz,pz)/(getpww(index1,index2,ptz,rep)*getpw(index2,ptz,pz));
            % p(i|tag1,tag2) = p(tag1|tag2,i)*p(tag2|i)*p(i)/p(tag1|tag2)*p(tag2)           
        else
            asso_probs(i,1) = 0;
        end
    end
    [prob_values,I] = sort(asso_probs,'descend');
    num = sum(prob_values > threshold);
    mostAssoTagList = taglist(I(1:num));
end
