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

  * Um dos tipos nativos do Java que são int, float, double, long, boolean, byte e String;
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

	1.4 Recuperação dos atributos
	
	Nessa última etapa do toJson os valores reais dos atributos será recuperados e colocamos em seus devidos lugares no template.
	
	Para a recuperação dos atributos foi decidido que os mesmos seriam identificados atráves de inteiros de 0 a N-1, onde N é a quantidade de atributos que o nodo possuí. Caso o nodo represente uma classe que extenda outra classe, é necessário contabilizar também os atributos da superclasse. Portanto, para na chamada de todos os métodos que visam obter o valor de algum atributo é necessário repassar o inteiro que identifica o atributo no nodo, bem como o inteiro idNodo.
	
	Os valores dos atributos primitivos citados na sessão 1.3 serão recuperados por métodos específicos tais como obtemInteiro, obtemFloat, obtemDouble, obtemString dentre outros. Existe uma ressalva para o método obtemString, onde o mesmo poderá retornar um null ou “” pois o tipo primitivo String aceita valores nulos ou vazios. Para esse caso devemos recorrer ao método formataString para o qual repassamos uma String e o mesmo nos retorna uma String null caso a String repassada seja nula ou ‘’ caso a String tenha o valor “” e caso o valor da String não seja nem nulo nem igual a “” o seu valor real é retornado.

	Para o tratamento de casos de array de bytes no método toJson, é chamado o método “arrayBytesToBase64”, que é responsável por efetuar a conversão dos bytes em uma String no formato Base64, que por sua vez é persistida no JSON. Já no contexto do fromJson, o método responsável é o “base64ToArrayBytes”, que recebe uma String no formato Base64 e então a converte para um array de bytes.
	
	Para atributos não primitivos será utilizado o método obtemChave para o qual é repassado o inteiro chave que representa o nodo atual(pai) e o número do atributo  e nos é retornado um inteiro chave que identifica o nodo filho no grafo. Com essa chave do nodo filho em mãos chamamos recursivamente o método buildJson passando o chave identificadora voltando assim a sessão 1.2.
	
	Enfim, com todos os atributos em mãos será utilizado o método replaceAll para colocar o valor do atributo no template.

	Exemplo:
	```
	          String template = "{'globalTypeIdn' : #globalTypeIdn, 'value' : #value, 'mappings' : [#mappings], 'formatting' : #formatting, 'hyperlink' : #hyperlink, 'language' : #language, 'encoding' : #encoding }";

            template = template.replaceAll("#globalTypeIdn",String.valueOf(DV_TEXT));
            template = template.replaceAll("#value", formataString(obtemString(idNodo,0)));

            int idTermMappingList = obtemChave(idNodo,1);
            int termMappingListSize = obtemTamanhoLista(idTermMappingList);
            String termMappingList = "";
            for(int k = 0; k < termMappingListSize; k++){
                int idListObject = obtemChave(idTermMappingList, k);
                termMappingList = termMappingList + ( (k == termMappingListSize - 1) ? buildJson(idListObject) : buildJson(idListObject) + ",");
            }
            template = template.replaceAll("#mappings", termMappingList);

            template = template.replaceAll("#formatting", formataString(obtemString(idNodo,2)));
            template = template.replaceAll("#hyperlink",buildJson(obtemChave(idNodo,3)));
            template = template.replaceAll("#language",buildJson(obtemChave(idNodo,4)));
            template = template.replaceAll("#encoding",buildJson(obtemChave(idNodo,5)));
            template = template.replaceAll("'", "\"");
            return template;
	```

2.0 fromJson

O método fromJson visa reconstruir o grafo uma vez serializado em Json de volta ao seu estado normal. O fluxo principal desse método começa com a criação de um JSONObject (http://www.json.org/java/index.html) que é repassado ao método buildGraph que de fato constroí o grafo retornando ao final de sua execução o inteiro chave que identifica o nodo raiz do grafo.
	
O método fromJson faz uso dos diversos métodos “adiciona” também definidos na interface do projeto. A ideia é que para cada um dos diferentes tipos de objetos serializados em json, o método buildGraph seja chamado em recursividade fazendo a checagem inicial quanto ao tipo do objeto persistido no json (e que agora será reconstruído) e instanciando-o para a montagem do grafo de objetos de acordo com o atributo globalTypeIdn definido no json. A construção do grafo de objetos utiliza a abordagem bottom-up, em que são instanciados primeiramente aqueles elementos folha do grafo e então, gradativamente, são construídos os nodos que dependem de alguma forma destes mais externos.
