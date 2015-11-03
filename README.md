# mr
Modelo de Referência do openEHRModelo de Referência do openEHR: 
Analisando Possibilidades de Persistência

[<img src="https://api.travis-ci.org/kyriosdata/mr.svg?branch=master">](https://travis-ci.org/kyriosdata/mr)

O presente projeto é derivado do conteúdo
disponível em https://github.com/openehr/java-libs.git.
TODOS OS CRÉDITOS DEVEM SER DEVIDAMENTE ATRIBUÍDOS
AOS DEVIDOS AUTORES. 

O presente "fork" inclui apenas parte do disponível na
URL acima, denominada de Modelo de Referência do openEHR. 
URL acima, denominada de Modelo de Referência do openEHR,
doravante apenas MR. Tal "fork" é estendido com uma
proposta de implementação alternativa do MR. O código
do "fork" é utilizado como referência para facilitar a
verificação e validação da implementação alternativa.

O objetivo é facilitar a experimentação de estratégias 
de persistência de informações registradas segundo o
MR. 

##DOCUMENTAÇÂO

1. toJson

  O método toJson visa serializar no formato Json um grafo contendo nodos dispostos de forma hierárquica. O fluxo principal do método começa com a obtenção de um inteiro que representa o nodo raiz do grafo o qual é repassado ao método buildJson que de fato constrói o Json e que será invocado de forma recursiva retornando ao final uma String que representa fielmente o grafo em formato Json válido.
