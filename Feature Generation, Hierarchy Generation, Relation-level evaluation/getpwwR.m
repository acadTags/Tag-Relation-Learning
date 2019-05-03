function pwwR = getpwwR(index1,index2,indexroot,ptz,pzt,pz)
% get p(w1|w2,R) when R contains only one tag concept
% input: indexes of the two words and the root word; ptz normalised matrix;
% pzt normalised matrix; the pz normalised row vector
% output: pwwR as a value
    pw2R = getpww(index2,indexroot,ptz,pzt);
    %pRw2 = getpww(indexroot,index2,ptz,pzt);
    pR = getpw(indexroot,ptz,pz);
    %pw2 = getpw(index2,ptz,pz);
    pw2Rjoint = pw2R*pR;
    %pRw2joint = pRw2*pw2; %as tested pRw2joint is equal to pw2Rjoint.
    pzw2R = ptz(:,index2)'.*ptz(:,indexroot)'.*pz/pw2Rjoint; % p(z|w2,R)
    pwwR = pzw2R*ptz(:,index1);
    %also this works 
    %pwwR = sum(ptz(:,index1).*ptz(:,index2).*ptz(:,indexroot).*(pz')/pw2Rjoint);
end