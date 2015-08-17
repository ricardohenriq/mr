#Storage Engine for openEHR-based Object Graphs
Um possível título para ilustrar o que está sendo feito.
Naturalmente, deveria estar em português. 

Um grafo de objetos baseado no modelo de referência do openEHR,
nesta _storage engine_ é persistido como uma lista de 
vetores de bytes, onde cada vetor de bytes se refere à 
serialização de um objeto do MR (grafo). Seja L esta lista, 
então, L(1) refere-se a um objeto deste grafo, L(2) a um outro
objeto e assim sucessivamente. Se o grafo inclui n objetos, 
então teremos os objetos L(0), L(1), ..., L(n - 1). 

Além da lista é necessário identificar a raiz do grafo. Um grafo de objetos em conformidade com o modelo de referência possui uma raiz, que é um dos objetos armazenados. À semelhança da forma como referências para outros objetos foram tratadas, veja acima, a raiz é um dos objetos L(0), L(1), ..., L(n-1). Ou seja, além da lista, é necessário armazenar qual objeto é a raiz, ou seja, um dos valores de 0 a n - 1, inclusive. 

## O que existe em cada vetor de bytes?

Os seguintes elementos são necessários:

1. Tipo do objeto armazenado. Esta informação permite identificar exatamente a forma de interpretar os bytes que seguem este, no vetor em questão. Por exemplo, se o primeiro byte tem como valor 0, e zero representa o tipo DV_IDENTIFIER, então sabe-se que o que seguem são quatro strings. Se 1 é o valor do primeiro byte, e um representa o tipo DV_BOOLEAN, então sabe-se que o que segue são os bytes correspondentes a um valor to tipo boolean.
2. Serialização dos valores de tipos primitivos. Ou seja, funções como getBytes(boolean) que retorna(m) o(s) byte(s) necessário(s) para armazenar um valor do tipo boolean e assim para todos os demais tipos primitivos. Naturalmente, sabe-se, antecipadamente, quantos bytes são necessários para armazenar cada um dos tipos, não apenas o boolean, mas também int, float, long e demais necessários. 
3. Alguns objetos fazem referência a outros objetos e, neste caso, tal referência será a serialização do inteiro que indica onde está o objeto referenciado na lista. Por exemplo, o objeto L(4) pode referenciar o objeto L(5), o que significa que o objeto serializado em L(5) é referenciado pelo objeto L(4) e, neste caso, para guardar em L(4) esta referência, guarda-se o inteiro de valor 5, como todos os demais valores de tipos primitivos.
4. Armazenar strings. As strings, ao contrário dos tipos primitivos, possuem tamanho variável. Impor um tamanho predefinido não atende a especificação. A proposta é armazenar, para cada string, como primeiro byte, a quantidade de bytes da string. Em consequência, para otimizar o acesso aos valores primitivos, o melhor é, por convenção, depositá-los no início do vetor de cada objeto. Naturalmente, quando o objeto possuir valores de tipos primitivos e strings.
