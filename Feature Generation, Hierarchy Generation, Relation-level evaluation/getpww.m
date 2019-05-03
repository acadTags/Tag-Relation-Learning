function pww = getpww(index1,index2,ptz,pzt)
% get p(w1|w2)
% input: indexes of the two words and ptz normalised matrix, pzt normalised matrix
% output: pww as a value
    %index1;
    %index2;
    pww = pzt(index2,:) * ptz(:,index1);
end