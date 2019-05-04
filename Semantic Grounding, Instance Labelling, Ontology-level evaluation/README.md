A detailed program running steps for Ontology-level evaluation:

* Run GetSubGraphsFromKB.java

* For Computer Science and Information Science (CS/IS) domains, run getDistinctRelations.java and getDistinctSKOSconceptsCSIS.java. For the Education or Economics domain, run getDistinctAndDisjointRelations.java and getDistinctSKOSconceptsSubDomains.java.

* Run MapTags2Dbpedia.java

* Run SelectRootsForEvaluation.java

* Then go to matlab and create .mat file of roots-to-predict-xxx.txt through “import” in matlab, as a cell array.

* Run main_ontology_level_eval.m (or other programs)

* Copy the generated hierarchies of each setting to a folder.

* Finally run TaxonomicCSCforPredictionSubKBfromFolder.java to see evaluation results.
