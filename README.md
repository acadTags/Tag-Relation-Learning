# tag-relation-learning

This program contains the source code and materials for the paper titled "Knowledge Base Enrichment by Relation Learning from Social Tagging Data". Files and data over the size limit of GitHub can be downloaded from [OneDrive]().

To be completed by early May.

# Requirements
* [MALLET](http://mallet.cs.umass.edu/index.php) for Data Representation with Topic Modelling
* Java package: [LevenshteinAutomaton](https://github.com/klawson88/LevenshteinAutomaton) for Semantic Grounding
* [LIBSVM](https://www.csie.ntu.edu.tw/~cjlin/libsvm/) Matlab version for Classification and Testing with SVM RBF models. 
* Matlab [Classification Learner App](https://www.mathworks.com/help/stats/classificationlearner-app.html) for Classification and Testing with other classifiers

# Contents
* ```Data representation``` folder: the MALLET command-line code to process social tags
* ```Feature Generation, Hierarcy Generation, Relation-level evaluation``` folder: the Matlab code for the corresponding modules in the system
* ```Semantic Grounding, Instance Labelling, Ontology-level evaluation``` folder: the JAVA code for the corresponding modules in the system and the evaulation results
* ```Knowledge Base Enrichment based evaluation``` folder: the cover letter, evaluation sheet and evaluation results for the manual assessment of enriched relations
* ```Hierarchy Visualisation``` folder: the Matlab code to visualise some learned hierarchy in several selected sub-domains

# Quick start and tutorial

#### View the Learned Hierarchies
View or run the ```visualise_hierarchy.m``` to see the learned hierarchies for the selected sub-domains (data mining, e-commerce, information retrieval, machine learning, research methods, social software) in the ```Hierarchy Visualisation``` folder. Two of the learned hierarchies are displayed below.

![alt text](https://github.com/acadTags/tag-relation-learning/blob/master/Hierarchy%20Visualisation/data_mining_dbpedia_svm.PNG)
![alt text](https://github.com/acadTags/tag-relation-learning/blob/master/Hierarchy%20Visualisation/machine_learning_acm_svm.PNG)

#### Hierarchy Generation
Generate hierarchies with the seed root tags with the proposed feature set by running ```main_ontology_generation.m``` in the ```Feature Generation, Hierarcy Generation, Relation-level evaluation``` folder. You can choose which set of seed root tags to use by changing the value of ```seed_file_name``` in ```main_ontology_generation.m```.

To generate hierarchies also with other baselines, run ```main_ontology_level_eval.m```.

#### Ontology-level evaluation
After the hierarchies are learned (each as a generated ```.csv``` file), you can put the learned hierarchies together in a folder and then evaluate the hierarchies with the automated ontology-level evaluation (described in the paper, Section 6.4.2). This is done by running the ```TaxonomicCSCforPredictionSubKBfromFolder.java``` in the ```Semantic Grounding, Instance Labelling, Ontology-level evaluation``` folder.

#### Classification and Testing, Relation-level evaluation
Load the ```bibsonomy_hier_instances_final_0.001_features_14ft_ori.mat``` file from [OneDrive]() and use the Matlab Classification Learner App to train and test with AdaBoost, Logistic Regression, CART and other classifiers. Select the variable ```featureTable_training``` to the session and choose 10-fold cross-validation. Select ```Boosted Trees```, ```Logistic Regression``` and ```Medium Tree``` for AdaBoost, LR and CART respectively. Export the trained models and then run ```classifier_prediction_and_evaluation.m``` to see the results.

The training and testing with grid search of SVM RBF models are implemented in ```libsvm_training.m```.

#### Knowledge Base Enrichment based evaluation
See the cover letter, evaluation sheet and results from the folder. The multi-rater Fleis kappa and the  free-marginal kappa can be calculated with the [Online Kappa Calculator](http://justusrandolph.net/kappa/).

#### Data Representation with MALLET

#### Semantic Grounding, Instance Labelling


# Acknowledgement
* The official Bibsonomy dataset is acquired from https://www.kde.cs.uni-kassel.de/bibsonomy/dumps/ after request.
* The whole Microsoft Concept Graph is acquired from https://concept.research.microsoft.com/Home/Introduction.
* The DBpedia dataset, 2015-10 version, is from http://downloads.dbpedia.org/2015-10/.
* The ACM Computing Classification System is from https://www.acm.org/publications/class-2012.
