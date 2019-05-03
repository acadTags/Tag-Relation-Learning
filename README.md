# tag-relation-learning

This program contains the source code and materials for the paper titled "Knowledge Base Enrichment by Relation Learning from Social Tagging Data". Files and data over the size limit of GitHub can be downloaded from [OneDrive]().

To be completed by early May.

# Requirements
* [MALLET](http://mallet.cs.umass.edu/index.php) for topic modelling
* Java package: [LevenshteinAutomaton](https://github.com/klawson88/LevenshteinAutomaton) for semantic grounding
* Matlab: Classification Learner app for classification

# Contents
* ```Data representation``` folder contains the MALLET command-line code to process social tags
* ```Feature Generation, Hierarcy Generation, Relation-level evaluation``` folder contains the Matlab code for the corresponding modules in the system
* ```Semantic Grounding, Instance Labelling, Ontology-level evaluation``` folder contains the JAVA code for the corresponding modules in the system and the evaulation results
* ```Enrichment-level evaluation``` folder contains the cover letter, evaluation sheet and evaluation results for the manual assessment of enriched relations
* ```Hierarchy Visualisation``` folder contains the Matlab code to visualise some learned hierarchy in several selected sub-domains

# Quick guide

View or run the ```visualise_hierarchy.m``` to see the learned hierarchies for the selected sub-domains (data mining, e-commerce, information retrieval, machine learning, research methods, social software) in the ```Hierarchy Visualisation``` folder.

Generate hierarchies with the seed root tags with the proposed feature set by running ```main_ontology_generation.m``` in the ```Feature Generation, Hierarcy Generation, Relation-level evaluation``` folder. You can choose which set of seed root tags to use by changing the value of ```seed_file_name``` in ```main_ontology_generation.m```.

After the hierarchies are learned (each as a generated ```.csv``` file), you can put the learned hierarchies together in a folder and then evaluate the hierarchies with the automated ontology-level evaluation (described in the paper, Section 6.4.2). This is done by running the ```TaxonomicCSCforPredictionSubKBfromFolder.java''' in the ```Semantic Grounding, Instance Labelling, Ontology-level evaluation``` folder. The program needs to create a blank folder to contain the reference (ground truth) hierarchies and then compares the folder with learned hierarchies with the folder of reference hierarchies to generate an averaged Taxonomic Precision, Taxonomic Recall and then a final Taxonomic F-measure.

# Detailed guide
to be updated soon

# Acknowledgement
* The official Bibsonomy dataset is acquired from https://www.kde.cs.uni-kassel.de/bibsonomy/dumps/ after request.
* The whole Microsoft Concept Graph is acquired from https://concept.research.microsoft.com/Home/Introduction.
* The DBpedia dataset, 2015-10 version, is from http://downloads.dbpedia.org/2015-10/.
* The ACM Computing Classification System is from https://www.acm.org/publications/class-2012.
