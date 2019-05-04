% prediction
function yfit = predictFromTheModel(model, feature_matrix)
    %feature_matrix = minMaxNorm(feature_matrix);
    T = array2table(feature_matrix);
    T.Properties.VariableNames = model.RequiredVariables;    
    yfit = model.predictFcn(T);
end