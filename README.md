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

# toJson()

A seguir segue modelo de implementação que no geral irá servir para a construção da implementação to método toJson() de qualquer classe do modelo de referência.

Exemplo 1
```
private String buildJson(int idNodoGrafo){
   String out = "";
   switch (obtemTipo(idNodoGrafo)){
	...
       case 5:
           String template = "{'globalTypeIdn' : '#globalTypeIdn, 'atributo0' : '#atributo0','atributo1' : '#atributo1','atributo2' : '#atributo2'}";
           template = template.replaceAll("#globalTypeIdn",5); // 5 é o id da classe
           template = template.replaceAll("#atributo0",obtemTexto(idNodoGrafo,0));
           template = template.replaceAll("#atributo1",String.valueOf(obtemValorLogico(idNodoGrafo,1)));
           template = template.replaceAll("#atributo2",buildJson(obtemChave(idNodoGrafo,2)));
           template = template.replaceAll("'", "\"");
           break;
	...
   }
   out += template;
   return out;
}
```
