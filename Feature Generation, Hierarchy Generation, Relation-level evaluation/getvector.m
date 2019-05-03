function [vtag,index] = getvector(tag, taglist, pzt)
% tag is a string
% taglist is a cellarray
% pzt is a matrix
    [m,~]=size(taglist);
    index = 0;
    for i=1:m
        if strcmp(taglist(i), tag)
            index = i;
            %return
        end
    end
    if index ~= 0
        vtag = pzt(index,:);
    else
        vtag = zeros(1,size(pzt,2));
    end    
end