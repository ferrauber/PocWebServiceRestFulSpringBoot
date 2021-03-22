# Getting Started

# Rodando o Projeto

* Utilizado IntelliJ IDEA;
* Requer configuração para SDK 11;
* Requer diretório, Temp, criado na unidade C, para armazenamento da base de dados H2;
* Arquivo para leitura precisa estar em resources/static, com nome movielist.csv;
* Rodar clean, build, bootRun;

* [Acessar swagger pela url] (http://localhost:8080/swagger-ui.html);
* [Acessar banco de dados H2] (http://localhost:8080/h2) 
* Dados de login do H2: 
* ------ Driver Class: org.h2.Driver
* ------ JDBC URL: jdbc:h2:file:/C:/Temp/movies.db
* ------ User: sa
* ------ Pwd: 

* Após rodar e acessar o swagger: 
* ------ end-point principais: /importar e /consultar-premios 

# Testes de integração

* Rodar a class DemoApplicationTest, clicando com botão direito sobre e run 'DemoApplicationTest'
 ou pelo Grade, verification, test;

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.4/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.4/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [JDBC API](https://docs.spring.io/spring-boot/docs/2.4.4/reference/htmlsingle/#boot-features-sql)
* [Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Using Spring Data JDBC](https://github.com/spring-projects/spring-data-examples/tree/master/jdbc/basics)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [Lib openSource para leitura de arquivo .csv] (http://opencsv.sourceforge.net/#quick_start)
* [WebService RESTfull segue nível 2 de maturidade de Richardson] (https://martinfowler.com/articles/richardsonMaturityModel.html#level2)

