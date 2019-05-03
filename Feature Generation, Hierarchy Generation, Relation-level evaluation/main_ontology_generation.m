% ontology-generation with the proposed feature set

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

%% FS_all + SVM RBF
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

ontology_generation_for_enrichment_from_prediction

%% FS_all + adaboost
clearvars -except seed_file_name;
load([seed_file_name '.mat'])
seed_tags_with_root = eval(seed_file_name);

ontology_generation_for_enrichment_from_prediction_adaboost