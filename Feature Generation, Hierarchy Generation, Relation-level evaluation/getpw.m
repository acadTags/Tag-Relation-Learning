function pw = getpw(index,ptz,pz)
% get p(w1)
% input: index of the tag, the ptz normalised matrix, the pz normalised row vector
% output: pw as a value
    pw = pz * ptz(:,index);
end