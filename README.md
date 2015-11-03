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

Esse primeiro exemplo, case 5, contempla as principais meios de implementar a solução do toJson(), segue as definições e regras para implementação.

* A construção da String template deverá ser feita de acordo com o nome dos atributos do modelo de referência, ou seja, os nomes dos campos no Json seguem estritamente os nomes dos atributos contidos nas classes do modelo de referência, portanto nada de usar nome de classes.
* O método utilizado para substituir as strings “placeholders” será o .replaceAll(). Este método também deverá ser invocado ao final para substituir todas as ocorrências de ‘ ou “ de acordo com o padrão Json;
* Para qualquer o atributo que não for primitivo(String, int, float, double ou boolean) o método obtemChave(idNodoGrafo,numeroAtributo) deverá ser invocado para retornando a chave do atributo não primitivo que o localiza no grafo. Essa chave deverá ser utiliza para reinvocar o método buildJson(chaveNodo) e assim caracterizando a recursão;
* O número do atributo citado no item 3 deverá ser um inteiro incrementado um a um começando do 0. Este número representa e identifica o atributo da classe no modelo de referência. Por exemplo, se uma classe tiver 5 atributos sejam eles primitivos ou não, esses atributos serão identificados de 0 a 4 e dispostos de acordo com a implementação do modelo de referência; 
* Os atributos primitivos serão recuperados com os métodos de acordo com a interface ModeloDeReferencia.

Exemplo 2
```
private String buildJson(int idNodoGrafo){
   String out = "";
   switch (obtemTipo(idNodoGrafo)){
	...
case 3:
   template = "{'atributoLista': [#atributoLista]}";
   int idLista = obtemChave(idNodoGrafo,0);
   int k = 0;
   String lista = "";
   for(int k = 0; k < obtemTamanhoLista(idList);k++){
       int idObjetoLista = obtemChave(idLista,k);
       lista = lista + buildJson(idObjetoLista) + ",";
   }
   template = template.replaceAll("#atributoLista",lista);
   template = template.replaceAll("'", "\"");
   break;
	...
   }
   out += template;
   return out;
}
```

Nesse segundo exemplo vemos um caso onde a classe do modelo de referência possui um atributo do tipo lista.
* Para atributos do tipo lista serão utilizados o método obtemTamanhoLista() para recuperar o tamanho da lista e com essa informação iterar sobre todos os elementos da lista.

