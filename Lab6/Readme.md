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
> Com base nos resultados da análise do _SonarQube_ que se obteve, parece que o projeto dado foi analisado com sucesso e que passou em termos de _defined quality gate_.
> O projeto tinha 0 bugs ou vulnerabilidades, o que é bom para confiabilidade e segurança. No entanto possui 1 security Hotspot, o que sugere que há uma área no código que pode potencialmente levar a uma vulnerabilidade de segurança e que deve ser revisada.A nível de _Maintainability_ existe áreas do código que poderiam ser melhoradas (24 _code smells_). 
> Em relação aos _Unit Tests_m o projeto tem um alcance de 80,3%, o que garaante a qualidade do uso deste código.



2. Explore the analysis results and complete with a few sample issues, as applicable. 

_Resposta:_

 **Issue** | **Problem Description** | **How to solve** |
 --- | --- | --- |
Bug| - | - |
Vulnerability | -  | - 
Code smell(major)| 1. Invoke method(s) only conditionally <br> 2. Refactor the code in order to not assign to this loop counter from within the loop body. <br> 3.This block of commented-out lines of code should be removed. |  2. `for (int i = 0; i < 10; i++) {...}`<br> 3. Remove the comment 


¹
```java
EuromillionsDraw draw = EuromillionsDraw.generateRandomDraw();
log.info("Draw results:\n{}", draw.getDrawResults().format() )
```

²
```java
Dip randomDip = new Dip();
for (int i = 0; i < NUMBERS_REQUIRED; ) {
  int candidate = generator.nextInt(NUMBERS_RANGE_MAX) + 1;
  if (!randomDip.getNumbersColl().contains(candidate)) {
    randomDip.getNumbersColl().add(candidate);
i++;
```

³
```java
// return !intersection.isEmpty();
```

> Correção de alguns _code Smell_.
![](Lab06_1/refactory.png)
# Reference
[https://docs.sonarqube.org/latest/user-guide/user-account/generating-and-using-tokens/](https://docs.sonarqube.org/latest/user-guide/user-account/generating-and-using-tokens/)