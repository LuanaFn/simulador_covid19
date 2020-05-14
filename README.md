# simulador_covid19
Simulador gráfico de contaminação de COVID19 em uma pequena população.

Feito a partir do projeto de autômato celular de exemplo fornecido pela professora Maria das Graças Bruno Marietto da UFABC. O projeto fornecido com poucas alterações pode ser encontrado no pacote conway.original com poucas alterações.

## 1. Definir o que o Automato Celular poderá simular (1 página)
Antes de se definir as regras do autômato celular, é preciso definir o que se pretende modelar/representar neste autômato. Tendo como base esta definição, as regras poderão ser criadas.
Neste trabalho vamos simular de forma muito simplificada o contágio por coronavírus em uma população jovem que não esteja em quarentena.
De acordo com [1] o índice R0 de contágio do coronavírus é de aproximadamente 5,7, ou seja, cada indivíduo infectado infecta em média 5,7 pessoas.
Ainda de acordo com este artigo vamos que o vírus leva em média 4,2 dias para começar a dar sintomas e depois disso aproximadamente 16,1 dias para chegar a morte ou alta no hospital.
Seguindo estes dados, nosso autômato celular vai calcular quantas novas pessoas são infectadas por dia, quantas morrem e quantas se recuperam, considerando que as pessoas recuperadas sejam imunes ao contágio.
Consideraremos que a probabilidade de infectar cada pessoa próxima seja de 3,5% por dia, totalizando 71,25% de infecção ao longo da infecção.
De acordo com um artigo da Setor Saúde [3] a taxa de morte entre jovens de 20 a 29 anos por coronavírus é de 0,3% - estaremos utilizando esta taxa para todas as pessoas representadas.

## 2. Novas Regras Propostas para o Jogo da Vida (2 página)
Nesta seção devem ser apresentadas as novas regras para o Jogo da Vida.
Para aproveitar a atividade anterior nós vamos utilizar um grid de quadrados conectados em suas extremidades para representar cada jovem. Cada quadrado possuirá portanto 8 "vizinhos" e caso o jovem representado por este quadrado esteja infectado ele vai possuir uma chance de infectar cada um de seus vizinhos diariamente, ainda que não apresente sintomas ainda.

### 2.1 Jovens não contagiados e sem vizinhos contagiados
Como o vírus não surge espontaneamente os jovens que estiverem nesta condição possuem zero chances de serem infectados.
Estes indivíduos serão representados pela cor verde.
### 2.2 Jovens não contagiados que possuem vizinhos doentes
Todo dia, para cada vizinho doente, o jovem saudável terá + 3,5% de ser contagiado. Esta probabilidade será renovada todos os dias.
Como ainda estão saudáveis, estes indivíduos também serão representados pela cor verde.
### 2.3 Jovens contagiados
Após ser contagiado, o jovem deve iniciar um contador que o manterá contagiado durante 20 dias.
Estes indivíduos serão representados pela cor vermelha.
### 2.4 Jovens doentes a 20 dias
Após acabarem os 20 dias de doença o jovem fica saudável e imune mas com uma chance de 0,3% de morte.
Como ainda estão doentes, estes indivíduos serão representados pela cor vermelha.
### 2.5 Jovens imunes
Estes jovens possuem chance zero de reinfecção pois estão imunes a doença.
Estes indivíduos serão representados pela cor amarela.
### 2.6 Jovens mortos
Consideramos aqui que os jovens mortos não são capazes de infectar seus vizinhos e também não "voltam a vida" de forma alguma.
Estes indivíduos serão representados pela cor preta.

## 3. Aplicação das Regras (1 página)
Gerar quatro iterações, mostrando o estado das células em cada iteração.

## 4. Padrões Gráficos Obtidos (4 páginas)
Nesta seção devem ser apresentados os padrões gráficos obtidos na execução do Jogo da Vida, com novas regras propostas neste relatório.
Cada padrão deve ser explicado, apresentando a lógica que direcionou a formação do mesmo.  

## Referências Bibliográficas
Nesta seção devem ser colocadas as referências bibliográficas utilizadas para o desenvolvimento e escrita deste trabalho.

- [1] Sanche, Steven, et al. "High Contagiousness and Rapid Spread of Severe Acute Respiratory Syndrome Coronavirus 2." Emerging infectious diseases 26.7 (2020)
- [2] Covid-19: 1 doente contagia 6, e casos podem dobrar em 3 dias, diz estudo... - Wanderley Preite Sobrinho, Do UOL, em São Paulo, 10/04/2020 04h00 e Atualizada em 10/04/2020 12h02 - https://noticias.uol.com.br/saude/ultimas-noticias/redacao/2020/04/10/covid-19-1-doente-contagia-6-e-casos-podem-duplicar-em-3-dias-diz-estudo.htm?cmpid=copiaecola
- [3] Os riscos do novo coronavírus: perfil dos óbitos por faixa etária - Com informações do site Vox e Ministério da Saúde. Edição do Setor Saúde. - 26/03/2020 -   https://setorsaude.com.br/os-riscos-do-novo-coronavirus-perfil-dos-obitos-por-faixa-etaria/
