%% for AdaBoost, LR (Logistic Regression), CART (Classification and Regression Trees) and other models
% set the right part as the exported variable name from the Classification Learner App
classificationModel = trainedModel1;

yfit = predictFromTheModel(classificationModel,feature_matrix_validation);
[ confusion_matrix, precision,recall,f_measure,accuracy,specificity,negative_predicted_value,false_positive_rate ] = GetPrecRecAccSpecNPV(Y_validation,yfit)

%% for LR only
% get roc for LR model
theta = classificationModel.GeneralizedLinearModel.Coefficients.Estimate;
m=size(feature_matrix_validation,1);
feature_matrix_validation=[ones(m,1) feature_matrix_validation];
scores = ones(m,1)./(1+exp(-feature_matrix_validation*theta));
%scores(isnan(scores))=0; 
[X,Y,T,AUC]=perfcurve(Y_validation,scores,'b');
AUC
plot(X,Y)
xlabel('False positive rate')
ylabel('True positive rate')
title('ROC for Classification by Logistic Regression')