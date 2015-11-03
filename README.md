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
  
  1.1. Obtenção do nó raiz do grafo
  
    Primeiramente,  é recuperado o inteiro que representa o nodo raiz do grafo. A recuperação deste inteiro é feita     pelo método obtemRaiz(). Com esse inteiro em mãos o método buildJson é chamado passando esse inteiro.
    
  1.2. Obtenção do tipo do nó
  
    Já dentro do método buildJson e com o inteiro idNodo que representa o nodo no grafo em mãos é preciso identificar de qual tipo é esse inteiro. Essa identificação é feita com o método obtemTipo para qual repassamos um inteiro idNodo e nos é retornado um outro inteiro chamado tipoNodo que representa o tipo de nodo com o qual estamos lidando. Como é sabido, o modelo de referência do openEHR tem por volta de 150 classes e com isso teremos um total de 150 inteiros únicos que identificam cada tipo de nodo possível.
    
  1.3. Montagens dos templates
  
  A terceira etapa é a construção dos templates para os 150 tipos diferentes de classes, porém, antes de tudo é necessário adicionar um atributo com o nome de globalTypeIdn. Esse atributo é um inteiro referente ao tipo do nodo com o qual estamos lidando. Exemplo: { “globalTypeIdn” : 23 } onde o inteiro 23 referência o tipo do nodo obtido atráves do método obtemTipo.
  
  Em relação aos atributos do nodo propriamente dito, os mesmos podem ser:

  * Um dos tipos nativos do Java que são int, float, double, long, boolean e String;
  * Um outro nodo.

  Em todo os casos colocamos o nome do atributo json exatamente igual ao nome do atributo do nodo. Por exemplo, um nodo tem uma String chamada version, logo o template terá um atributo chamado { “version” : #version } onde o #version será substituído pelo valor da String version.

  Exemplo de template com base da classe DvText:
	```
{
    'globalTypeIdn': #globalTypeIdn,
    'value': #value,
    'mappings': [#mappings ],
    'formatting': #formatting,
    'hyperlink': #hyperlink,
    'language': #language,
    'encoding': #encoding
}
```
Nesse exemplo acima como podemos ver, a classe DvText possuí seis atributos e no caso do atributo mappings 	foi adicionado colchetes [ ] pois o mesmo se trata de uma lista que ao ser convertido para json se tornará um 	Json Array.

    1.4. Recuperação dos atributos


2.0 fromJson

	O método fromJson visa reconstruir o grafo uma vez serializado em Json de volta ao seu estado normal. O fluxo principal desse método começa com a criação de um JSONObject (http://www.json.org/java/index.html) que é repassado ao método buildGraph que de fato constroí o grafo retornando ao final de sua execução o inteiro chave que identifica o nodo raiz do grafo.
	
	O método fromJson faz uso dos diversos métodos “adiciona” também definidos na interface do projeto. A ideia é que para cada um dos diferentes tipos de objetos serializados em json, o método buildGraph seja chamado em recursividade fazendo a checagem inicial quanto ao tipo do objeto persistido no json (e que agora será reconstruído) e instanciando-o para a montagem do grafo de objetos de acordo com o atributo globalTypeIdn definido no json. A construção do grafo de objetos utiliza a abordagem bottom-up, em que são instanciados primeiramente aqueles elementos folha do grafo e então, gradativamente, são construídos os nodos que dependem de alguma forma destes mais externos.

















