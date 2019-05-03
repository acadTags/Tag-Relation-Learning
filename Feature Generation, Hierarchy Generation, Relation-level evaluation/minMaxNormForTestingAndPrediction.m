function X_mmNorm = minMaxNormForTestingAndPrediction( X,minF,maxF )
% normalise the matrix based on pre-defined minimum and maximum values for
% each feature.
    m = size(X,1);
    %minF = min(X);
    %maxF = max(X);
    minM = repmat(minF,[m,1]); % minimum matrix.
    maxM = repmat(maxF,[m,1]); % maximum matrix.
    X_mmNorm = X - minM;
    X_mmNorm = X_mmNorm ./ (maxM - minM);
    
    % revised in May 31, 2017
    % dealing with NaN in X_mmNorm when all entries in a column of X are 0.
    X_mmNorm(isnan(X_mmNorm)) = 0;
end

