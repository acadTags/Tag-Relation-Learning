rng('default');
clearvars;

% specify the suffix of the training/testing data file name.
fileNameSuffix = 'bibsonomy_s1_ori_final';
% specify the class weight C+/C-.
%weight = 2058*2/2128; % C+ weight; C- = 1; this value is C+ or C+/C-.
weight = 1;
cv_fold = 10; % fold for cross-validation.
fine_search = true;
prob_estimate = false; % true will make the training much slower.

if fine_search == false
    % generating the outputFileName.
    outputFileName = ['results',fileNameSuffix,'_weight',num2str(weight),'_coarse.csv'];
else
    outputFileName = ['results',fileNameSuffix,'_weight',num2str(weight),'_fine.csv'];
end

[trainY, trainX] = libsvmread(['training',fileNameSuffix,'.txt']);
[testY, testX] = libsvmread(['testing',fileNameSuffix,'.txt']);

if fine_search == false
    % coarse search: make this fixed
    C = 9:1:10; %// you must choose your own set of values for the parameters that you want to test. You can either do it this way by explicitly typing out a list
    gamma = 6:1:7; %// or you can do it this way using the : operator
else
    % fine search: change based on the results of coarse search
    C = 10:0.5:11; %// you must choose your own set of values for the parameters that you want to test. You can either do it this way by explicitly typing out a list
    gamma = 7:0.5:9; %// or you can do it this way using the : operator
end

f1scores = zeros(numel(C), numel(gamma)); %// Pre-allocation
%rocscores = zeros(numel(C), numel(gamma)); %// Pre-allocation
recallscores = zeros(numel(C), numel(gamma)); %// Pre-allocation
precisionscores = zeros(numel(C),numel(gamma));

for c = 1:numel(C)
    for s = 1:numel(gamma)
        cvalue = 2^C(c);
        gammavalue = 2^gamma(s);
        param = ['-c ', num2str(cvalue), ' -g ', num2str(gammavalue), ' -w1 ', num2str(weight), ' -w-1 1 -b ', num2str(prob_estimate), ' -h 0']
        evaluation_metrics = do_binary_cross_validation(trainY, trainX, param, cv_fold);
        recallscores(c,s) = evaluation_metrics(1);
        f1scores(c,s) = evaluation_metrics(3);
        precisionscores(c,s) = evaluation_metrics(2);
    end
end

f1=figure;
contour(gamma,C,recallscores,'ShowText','on');
xlabel('gamma');
ylabel('C')
title('Parameter tuning for SVM grim search 14ft Recall');
savefig([outputFileName(:,1:(size(outputFileName,2)-4)),'_recall.fig'])

f2=figure;
contour(gamma,C,f1scores,'ShowText','on');
xlabel('gamma');
ylabel('C')
title('Parameter tuning for SVM grim search 14ft f-score');
savefig([outputFileName(:,1:(size(outputFileName,2)-4)),'_f1.fig'])

f3=figure;
contour(gamma,C,precisionscores,'ShowText','on');
xlabel('gamma');
ylabel('C')
title('Parameter tuning for SVM grim search 14ft precision');
savefig([outputFileName(:,1:(size(outputFileName,2)-4)),'_prec.fig'])

k=1;

% optimise the recall
recallscores_temp=recallscores;

C_gamma_recall_test=zeros(k,4);
for i=1:k
    [cbest, sbest] = find(recallscores_temp == max(recallscores_temp(:)));
    if size(cbest,1)>1 %prevent a vector output for cbest and sbest.
        cbest=cbest(1);
        sbest=sbest(1);
    end
    C_gamma_recall_test(i,1)=2^C(cbest);
    C_gamma_recall_test(i,2)=2^gamma(sbest);
    C_gamma_recall_test(i,3)=recallscores_temp(cbest,sbest);    
    recallscores_temp(cbest,sbest)=0;
    param = ['-c ', num2str(C_gamma_recall_test(i,1)), ' -g ', num2str(C_gamma_recall_test(i,2)), ' -w1 ', num2str(weight), ' -w-1 1 -b ', num2str(prob_estimate), ' -h 0']
    [~,C_gamma_recall_test(i,4:8),~]= do_binary_predict(testY,testX,svmtrain(trainY, trainX, param));
end

dlmwrite([outputFileName(:,1:(size(outputFileName,2)-4)),' opt_rec.csv'],C_gamma_recall_test); % output the result that optimise recall.

% optimise the f1
f1scores_temp=f1scores;
% get the highest k f1-score
C_gamma_f1_test=zeros(k,4);
for i=1:k
    [cbest, sbest] = find(f1scores_temp == max(f1scores_temp(:)));
    if size(cbest,1)>1
        cbest=cbest(1);
        sbest=sbest(1);
    end
    C_gamma_f1_test(i,1)=2^C(cbest);
    C_gamma_f1_test(i,2)=2^gamma(sbest);
    C_gamma_f1_test(i,3)=recallscores(cbest,sbest);
    C_gamma_f1_test(i,4)=precisionscores(cbest,sbest);
    C_gamma_f1_test(i,5)=f1scores_temp(cbest,sbest);
    f1scores_temp(cbest,sbest)=0;
    param = ['-c ', num2str(C_gamma_f1_test(i,1)), ' -g ', num2str(C_gamma_f1_test(i,2)), ' -w1 ', num2str(weight), ' -w-1 1 -b ', num2str(prob_estimate), ' -h 0']
    [~,C_gamma_f1_test(i,6:10),~]= do_binary_predict(testY,testX,svmtrain(trainY, trainX, param));
    %C_gamma_f1_test(i,6:8) =funf1score_rocauc_recall_with_class_weight(feature_matrix,Y_training,feature_matrix_validation,Y_validation,2^C(cbest),2^gamma(sbest),cost);
    
%     % output the best f1 test prediction result.
%     if (i == 1)
%         pred = svmpredict(testY,testX,svmtrain(trainY, trainX, param));
%         test_pred_result = [tagpair_validation num2cell(pred)];
%         cell2csv([fileNameSuffix, '_', num2str(cbest), '_', num2str(sbest), '_best_test_pred.csv'],test_pred_result);
%     end    
end

dlmwrite([outputFileName(:,1:(size(outputFileName,2)-4)), 'opt_f1.csv'],C_gamma_f1_test); % output the result that optimise f1 score.

% optimise the precision
precisionscores_temp=precisionscores;
% get the highest k f1-score
C_gamma_precision_test=zeros(k,4);
for i=1:k
    [cbest, sbest] = find(precisionscores_temp == max(precisionscores_temp(:)));
    if size(cbest,1)>1
        cbest=cbest(1);
        sbest=sbest(1);
    end
    C_gamma_precision_test(i,1)=2^C(cbest);
    C_gamma_precision_test(i,2)=2^gamma(sbest);
    C_gamma_precision_test(i,3)=precisionscores_temp(cbest,sbest);
    precisionscores_temp(cbest,sbest)=0;
    param = ['-c ', num2str(C_gamma_precision_test(i,1)), ' -g ', num2str(C_gamma_precision_test(i,2)), ' -w1 ', num2str(weight), ' -w-1 1 -b ', num2str(prob_estimate), ' -h 0']
    [~,C_gamma_precision_test(i,4:8),~]= do_binary_predict(testY,testX,svmtrain(trainY, trainX, param));
    %C_gamma_f1_test(i,6:8) =funf1score_rocauc_recall_with_class_weight(feature_matrix,Y_training,feature_matrix_validation,Y_validation,2^C(cbest),2^gamma(sbest),cost);
end

dlmwrite([outputFileName(:,1:(size(outputFileName,2)-4)), 'opt_prec.csv'],C_gamma_precision_test); % output the result that optimise f1 score.
