# Lab06 Static Code analysis (with SonarQube)

# Local analysis

1. Inicie o servidor executando:
```bash
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9906:9000 sonarqube:latest
```
>[ver mais](https://docs.sonarqube.org/latest/try-out-sonarqube/)
2. Acesse o servidor em [http://localhost:9906](http://localhost:9906)

3. Criar um projeto _"manually"_ , alterar para "Lab06_1" ou "local analysis"

4. **Take note of the generated user token**, [token](/Lab06_1/notas.txt)

5. Execute o comando dado para analisar o projeto.
```bash
$ mvn clean verify sonar:sonar \
  -Dsonar.projectKey=local-analysis \
  -Dsonar.host.url=http://localhost:9906 \
  -Dsonar.login=sqp_7c9886b5629f6e7b34d1ba18121cd433a8e48acb

```

6. Acesse o projeto no servidor e veja os resultados.

![](Lab06_1/LocalAnalysis.png)
## Questoẽs

1. Has your project passed the defined quality gate? Elaborate your answer.

_Resposta:_


1. Explore the analysis results and complete with a few sample issues, as applicable. 

_Resposta:_

 **Issue** | **Problem Description** | **How to solve** |
 --- | --- | --- |
Bug| - | - |
Vulnerability | -  | - 
Code smell(major)| |


# Reference
[https://docs.sonarqube.org/latest/user-guide/user-account/generating-and-using-tokens/](https://docs.sonarqube.org/latest/user-guide/user-account/generating-and-using-tokens/)