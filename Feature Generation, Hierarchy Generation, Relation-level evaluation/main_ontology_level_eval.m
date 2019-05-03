% main running script for ontology-level evaluation
% time: estimated time about 30 minutes per each 50 roots for DBpedia; much faster for ACM CCS.

% To note that, the Accurary shown in the console is not the real accurary: this is just a part of the LIBSVM output.
% Acknowledgement to 
%       the LIBSVM tool, Copyright (c) 2000-2018 Chih-Chung Chang and Chih-Jen Lin
%       the cell2csv function by Sylvain Fiedler, Copyright (c) 2004-2010, Sylvain Fiedler.

%% define root here
clearvars;
%seed_file_name = 'seed_tags_to_predict_dbpedia_CS_IS_5';
%seed_file_name = 'seed_tags_to_predict_dbpedia_education_3';
%seed_file_name = 'seed_tags_to_predict_dbpedia_economics_3';
%seed_file_name = 'seed_tags_to_predict_acm_2';
%seed_file_name = 'seed_tags_to_predict_acm_3_no_leaf';
%seed_file_name = 'seed_tags_to_predict_acm_4_over_3_no_leaf'; % the new part in acm 4th layer, compared to the first 3 layers.
%seed_file_name = 'seed_tags_to_predict_acm_4_no_leaf';

seed_file_name = 'seed_tags_test';
%% co-occurrence vs. probabilistic topic modelling features
%% FS_co + adaboost
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

%ontology_generation_for_enrichment_from_prediction_alex8ft_ada

%% FS_all + adaboost
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

%ontology_generation_for_enrichment_from_prediction_adaboost

%% FS_all + SVM RBF
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

%ontology_generation_for_enrichment_from_prediction

%% FS_co + SVM RBF
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

%ontology_generation_for_enrichment_from_prediction_alex8ft

%% FS_all+FS_co + adaboost
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

%ontology_generation_for_enrichment_from_prediction_14ft_8ft_ada

%% FS_all+FS_co + SVM RBF
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

%ontology_generation_for_enrichment_from_prediction_14ft_alex8ft

%% Feature Selection models
%% FS_topicSim + adaboost
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

% generate options of feature selection set 
% here feature_selection_set is a row |feature| dimensional vector
% containing 0 and 1, where 0 means not used and 1 means used as feature.
set_simiar_1 = zeros(1,14); set_simiar_1(1:4) = 1;
% [important] here to specify the feature selection set
feature_selection_set = (set_simiar_1 == 1);

outputNameSuffix = ['topicSim' '_ada'];
ontology_generation_for_enrichment_from_prediction_ft_selec_ada

%% FS_topicDist + adaboost
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

% generate options of feature selection set 
% here feature_selection_set is a row |feature| dimensional vector
% containing 0 and 1, where 0 means not used and 1 means used as feature.
set_topic_dist_2 = zeros(1,14); set_topic_dist_2(5:8) = 1;
% [important] here to specify the feature selection set
feature_selection_set = (set_topic_dist_2 == 1);

outputNameSuffix = ['topicDist' '_ada'];
ontology_generation_for_enrichment_from_prediction_ft_selec_ada

%% FS_probAsso + adaboost
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

% generate options of feature selection set 
% here feature_selection_set is a row |feature| dimensional vector
% containing 0 and 1, where 0 means not used and 1 means used as feature.
set_prob_asso_3 = zeros(1,14); set_prob_asso_3(9:14) = 1;
% [important] here to specify the feature selection set
feature_selection_set = (set_prob_asso_3 == 1);

outputNameSuffix = ['probAsso' '_ada'];
ontology_generation_for_enrichment_from_prediction_ft_selec_ada

%% FS_topicSim + SVM RBF
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

% generate options of feature selection set 
% here feature_selection_set is a row |feature| dimensional vector
% containing 0 and 1, where 0 means not used and 1 means used as feature.
set_simiar_1 = zeros(1,14); set_simiar_1(1:4) = 1;
% [important] here to specify the feature selection set
feature_selection_set = (set_simiar_1 == 1);

cvalue = 2^10.5;
gammavalue = 2^9;

outputNameSuffix = ['topicSim' '_svm'];
ontology_generation_for_enrichment_from_prediction_ft_select

%% FS_topicDist + SVM RBF
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

% generate options of feature selection set 
% here feature_selection_set is a row |feature| dimensional vector
% containing 0 and 1, where 0 means not used and 1 means used as feature.
set_topic_dist_2 = zeros(1,14); set_topic_dist_2(5:8) = 1;
% [important] here to specify the feature selection set
feature_selection_set = (set_topic_dist_2 == 1);

cvalue = 2^10;
gammavalue = 2^11;

outputNameSuffix = ['topicDist' '_svm'];
ontology_generation_for_enrichment_from_prediction_ft_select

%% FS_probAsso + SVM RBF
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

% generate options of feature selection set 
% here feature_selection_set is a row |feature| dimensional vector
% containing 0 and 1, where 0 means not used and 1 means used as feature.
set_prob_asso_3 = zeros(1,14); set_prob_asso_3(9:14) = 1;
% [important] here to specify the feature selection set
feature_selection_set = (set_prob_asso_3 == 1);

cvalue = 2^12;
gammavalue = 2^8.5;

outputNameSuffix = ['probAsso' '_svm'];
ontology_generation_for_enrichment_from_prediction_ft_select
