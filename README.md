![](https://raw.githubusercontent.com/WDAqua/Qanary/master/doc/logo-qanary_s.png)

# A Reference Implementation for Creating Question Answering Systems following the Qanary Methodology
(To see how to run the code, scroll down till the last)
## Qanary in a Nutshell

Qanary is a Methodology for Creating Question Answering Systems it is part of the [WDAqua project](http://wdaqua.informatik.uni-bonn.de) where question answering systems are researched and developed. Here, we are providing our key contributions on-top of the [RDF vocabulary qa](/WDAqua/QAOntology) the reference implementation of the Qanary methodology. In detail the following sub-projects are available all aiming at establishing an ecosystem for question answering systems.

 * [**Qanary Pipeline**](#qanarypipeline) implementation: a central component where components for question answering systems are connected automatically and can be called by Web UIs
 * Qanary component implementations: components providing wrappers to existing functionality or implement new question answering approaches 
   * a [**Qanary component template**](#qanarycomponenttemplate) implementation: use this to build you own component ([howto]()) as it provides [several features]()
   * [**Qanary AGDISTIS**](#qanaryagdists): a wrapper for disambiguating named entity in text using the [AGDISTIS](http://aksw.org/Projects/AGDISTIS.html) (NED) tool
   * [**Qanary Alchemy**](#qanaryalchemy): a wrapper for the [Alchemy Entity Extraction API](http://www.alchemyapi.com/products/alchemylanguage/entity-extraction) (commercial, but offers a free API key) computing named entities and disambiguates them (NER+NED)
   * [**Qanary DBpedia Spotlight NER**](#qanarydbpediaspotlight): a wrapper for the service interface of [DBpedia Spotlight](https://github.com/dbpedia-spotlight/dbpedia-spotlight) spotting named entities within text (NER)
   * [**Qanary DBpedia Spotlight NED**](#qanarydbpediaspotlight): a wrapper for the service interface of [DBpedia Spotlight](https://github.com/dbpedia-spotlight/dbpedia-spotlight) disambiguating named entities within text (NED) to [DBpedia](http://dbpedia.org) resources
   * [**Qanary FOX**](#qanaryfox): a wrapper for the [FOX](http://aksw.org/Projects/FOX.html) tool for recognizing named entities within text (NER)
   * [**Qanary Lucene Linker**](#qanarylucenelinker): a tool for recognizing and disambiguating named entities within text derived from an implementation within the [SINA](http://aksw.org/Projects/SINA.html) question answering system (NER+NED)
   * [**Qanary Stanford NER**](#qanarystanfordner): a wrapper for the [Stanford Named Entity Recognizer](http://nlp.stanford.edu/ner/) recognizing named entities within text 
 * Qanary benchmarking
   * [**QALD evaluator**](#qaldevaluator): a client for the Qanary Pipeline evaluating the capabilities w.r.t. named entity recognition and disambiguation of a given Qanary Pipeline configuration with the [QALD benchmark](http://qald.sebastianwalter.org/) (Question Answering over Linked Data) data 
   * [**QALD annotated with named entities**](#qaldnerddataset): questions of [QALD](http://qald.sebastianwalter.org/) annotated with named entities containing 

<a name="qanarypipeline"></a>
## Qanary Pipeline

[source](https://github.com/WDAqua/Qanary/tree/master/qanary_pipeline-template)

More details follow soon.


<a name="qanarycomponents"></a>
## Qanary Components


<a name="qanarycomponenttemplate"></a>
### Qanary component template
[source]()

More details follow soon.


<a name="qanaryagdists"></a>
### Qanary AGDISTIS
[source](https://github.com/WDAqua/Qanary/tree/master/qanary_component-AGDISTIS-NED)

More details follow soon.


<a name="qanaryalchemy"></a>
### Qanary Alchemy
[source](https://github.com/WDAqua/Qanary/tree/master/qanary_component-Alchemy-NERD)

More details follow soon.


<a name="qanarydbpediaspotlight"></a>
### Qanary DBpedia Spotlight

 * Qanary DBpedia Spotlight NER: [source](https://github.com/WDAqua/Qanary/tree/master/qanary_component-DBpedia-Spotlight-NER)
 * Qanary DBpedia Spotlight NED: [source](https://github.com/WDAqua/Qanary/tree/master/qanary_component-DBpedia-Spotlight-NED)

More details follow soon.


<a name="qanaryfox"></a>
### Qanary FOX
[source](https://github.com/WDAqua/Qanary/tree/master/qanary_component-FOX-NER)

More details follow soon.


<a name="qanarylucenelinker"></a>
### Qanary Lucene Linker
[source](https://github.com/WDAqua/Qanary/tree/master/qanary_component-Lucene-Linker-NERD)

More details follow soon.


<a name="qanarystanfordner"></a>
### Qanary Stanford NER
[source](https://github.com/WDAqua/Qanary/tree/master/qanary_component-stanford-NER)

More details follow soon.


## Additional Resources


<a name="qaldevaluator"></a>
### QALD evaluator
[source](https://github.com/WDAqua/Qanary/tree/master/qald-evaluator)

More details follow soon.


<a name="qaldnerddataset"></a>
### QALD annotated with named entities
[source](https://github.com/WDAqua/Qanary/tree/master/ISWC-results)

More details follow soon.


### ISWC Resources
For a mapping between the resources presented at ISWC and this repository please refer to the wiki under section "Resources presented at ISWC".



## HowTos

### Quick Start
coming soon


### Using Qanary Pipeline Functionality 
coming soon


### Implement Your First Qanary Component
coming soon


### Implement Your First Qanary Pipeline
coming soon



## Publications / References

If you want to inform yourself about the Qanary methodology in general, please use this publication:  *Andreas Both, Dennis Diefenbach, Kuldeep Signh, Saedeeh Shekarpour, Didier Cherix and Christoph Lange: Qanary - A Methodology for Vocabulary-driven Open Question Answering Systems* appearing in [13th Extended Semantic Web Conference](http://2016.eswc-conferences.org), 2016. 


## Stuff used to make this:

 * [Spring Boot](http://projects.spring.io/spring-boot/) project
 * 
 
## How to run the code

Please follow below mentioned steps to run the complete project including all the existing components in Qanary Pipeline
* Clone the GitHub repository.
* Open your IDE( Eclipse etc.) to build as maven package. If you use Eclipse, run following commands: Go to Run as-> maven clean, then Maven build-> type "clean package install" in Goals section of the Edit Configuration and Launch Window.
* Download Stardog Triplestore (http://stardog.com/) and run it in background. All the triples (or Knowledge) generated by the components will be stored in Stardog. This will act as a Knowledge Base.
* A jar file is generated for Pipeline template after the maven build. Go to Pipeline component-> cd qanary_pipeline-template/ and then in target folder inside it. Run java -jar target/qa.pipeline-0.1.0.jar to start the pipeline.
 * After maven build different jar files will be generated in the corresponding folders of the components. For example to start the Alchemy API components, please go to folder: cd qanary_component-Alchemy-NERD and run: java -jar target/qa.Alchemy-NERD-0.1.0.jar. It will start the Alchemy API components. Similar process can be followed for all the components which you would like to run and include in the Pipeline.
 * After running corresponding jar files, you can see Springboot application running on http://localhost:8080/#/overview that will tell the status of currently running components. 
 * Now your pipeline is ready to use. Go to http://localhost:8080/startquestionansweringwithtextquestion . Here you can find a User Interface to interact for adding question via web interface, and then select the components you need to include in the pipeline via checking a checkbox for each component. Press the start button and you are ready to go!
 
