A detailed program running steps for Ontology-level evaluation:

* Run ```GetSubGraphsFromKB.java```

* For Computer Science and Information Science (CS/IS) domains, run ```getDistinctRelations.java``` and ```getDistinctSKOSconceptsCSIS.java```. For the Education or Economics domain, run ```getDistinctAndDisjointRelations.java``` and ```getDistinctSKOSconceptsSubDomains.java```.

* Run ```MapTags2Dbpedia.java```

* Run ```SelectRootsForEvaluation.java```

* Then go to matlab and create ```.mat``` file of ```roots-to-predict-xxx.txt``` through “import” in matlab, as a cell array. (the created seed root tags are in ```seed_tags_to_predict_xxx.mat``` in the ```Feature Generation, Hierarchy Generation, Relation-level evaluation``` folder.

* Run ```main_ontology_level_eval.m``` in the ```Feature Generation, Hierarchy Generation, Relation-level evaluation``` folder.

* Copy the generated hierarchies of each experimental setting to a folder.

* Finally run ```TaxonomicCSCforPredictionSubKBfromFolder.java``` to see evaluation results (the program needs two folders, a folder of learned hierarchies and a blank folder to store the reference or ground truth hierarchies).
