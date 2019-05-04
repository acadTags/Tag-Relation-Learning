function [ confusion_matrix, precision,recall,f_measure,accuracy,specificity,negative_predicted_value,false_positive_rate ] = GetPrecRecAccSpecNPV(label_cell_array, predicted_cell_array)
% get precision/positive_predicted_value, recall/sensitivity, f-measure,
% accuracy, specificity, negative_predicted_value, false_positive_rate.
% input: the labels and the predicted results, all as cell_array marked by
% 'b' (positive) and 'g' (negative)
% output: the confusion_matrix, [TP,FN;FP,TN]; the four values in the range [0,1], precision, recall, f_measure
% and accuracy.
% according to https://en.wikipedia.org/wiki/Precision_and_recall#Definition_.28classification_context.29
    boolean_comparison = strcmp(label_cell_array,predicted_cell_array);
    m=size(label_cell_array,1);
    TP=0;
    TN=0;
    FP=0;
    FN=0;
    for i=1:m
        P = strcmp(label_cell_array(i),'b');
        T = boolean_comparison(i);
        if (T && P)
            TP=TP+1;
        elseif (T && ~P)
            TN=TN+1;
        elseif (~T && P)
            % the false negative is actually a positive.
            FN=FN+1;
        elseif (~T && ~P)
            % the false positive is actually a negative.
            FP=FP+1;
        end
    end
    confusion_matrix = zeros(2,2);
    confusion_matrix(1,1) = TP;
    confusion_matrix(1,2) = FN;
    confusion_matrix(2,1) = FP;
    confusion_matrix(2,2) = TN;
    precision = TP/(TP+FP);
    recall = TP/(TP+FN);
    f_measure = 2*precision*recall/(precision+recall);
    accuracy = (TP+TN)/(TP+TN+FP+FN);
    specificity = TN/(TN+FP);
    negative_predicted_value = TN/(TN+FN);
    false_positive_rate = FP/(FP+TN);
end

