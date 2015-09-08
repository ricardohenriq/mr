package br.inf.ufg.fabrica.mr;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Fábrica de objetos baseados no Modelo de Referência
 * do openEHR.
 *
 * <p>Um objeto que implementa esta interface permite
 * criar um grafo de objetos em conformidade com o
 * Modelo de Referência do openEHR.</p>
 *
 * <p>Uma implementação desta interface deve,
 * NECESSARIAMENTE, estar em conformidade com as
 * especificações (padrões) do Modelo de Referência
 * do openEHR.</p>
 *
 * <p>Um objeto baseado no Modelo de Referência do openEHR
 * é um objeto em conformidade com as especificações
 * desse Modelo de Referência. Tais especificações são
 * acompanhadas de uma implementação de referência em
 * Java, disponível em
 * <a href="https://github.com/openEHR/java-libs">aqui</a>.
 * </p>
 *
 * <p>A implementação desta interface é uma implementação
 * alternativa. Não é um <i>fork</i>,
 * nem trabalho derivado da implementação (citada acima).
 * Trata-se de uma nova implementação, que emprega
 * estratégia distinta.
 * </p>
 *
 * <p>Um dos principais objetivos dessa implementação
 * é atender o uso do Modelo de Referência por dispositivos
 * móveis que, em geral, apresentam restrições de capacidade de
 * processamento e memória.</p>
 *
 * <p>Neste sentido, duas decisões de projeto foram
 * estabelecidas: (a) reduzir o tamanho do arquivo jar
 * correspondente à implementação e (b) minimizar o espaço
 * exigido para guardar um grafo de objetos baseado no
 * openEHR.</p>
 *
 * <p>Em decorrência das decisões acima duas orientações
 * são experimentadas: (a) não é criada uma classe para
 * cada conceito (conforme implmentação de referência) e
 * (b) toBytes são armazenados em um vetor de bytes que
 * serializa um grafo típico baseado na implementação
 * de referência.</p>
 *
 * <h3>Visão geral da interface</h3>
 *
 * <p>As operações {@link #obtemTexto(int, int)},
 * {@link #obtemVetorBytes(int, int)} e
 * {@link #obtemLogico(int, int)}, dentre outras similares
 * para os demais tipos primitivos, permitem recuperar um
 * valor primitivo, ou seja, um membro de algum objeto. Em
 * consequência, todos eles fazem uso de dois parâmetros. O
 * primeiro identifica o objeto e o segundo identifica o campo
 * que contém a informação desejada.</p>
 *
 * <p>As operações acima permitem recuperar valores presentes
 * no grafo, enquanto as operações como
 * {@link #adicionaDvBoolean(boolean)} e
 * {@link #adicionaDvEhrUri(String)} permitem inserir tais
 * valores. A inserção, contudo, ao contrário da recuperaçào,
 * não ocorre por campo, mas por toda a coleção de valores
 * que formam um objeto.</p>
 */
public interface ModeloDeReferencia extends Serializacao, Campo {

    /**
     * Identificador do tipo DV_BOOLEAN.
     */
    final int DV_BOOLEAN = 0;

    /**
     * Identificador do tipo DV_IDENTIFIER.
     */
    final int DV_IDENTIFIER = 1;
    final int DV_DATE = 2;
    final int DV_DATE_TIME = 3;
    final int DV_TEMPORAL = 4;
    final int DV_ABSOLUTE_QUANTITY = 5;
    final int DV_QUANTIFIED = 6;
    final int DV_ORDINAL = 7;
    final int DV_AMOUNT = 8;
    final int DV_DURATION = 9;
    final int DV_QUANTITY = 10;
    final int DV_PROPORTION = 11;
    final int DV_COUNT = 12;
    final int DV_TIME = 13;
    final int DV_CODED_TEXT = 14;
    final int DV_EHR_URI = 15;
    final int DV_ORDERED = 16;
    final int DV_TEXT = 17;
    final int DV_URI = 18;
    final int DV_PARAGRAPH = 19;
    final int DV_ENCAPSULATED = 20;
    final int DV_PARSABLE = 21;
    final int DV_MULTIMEDIA = 22;
    final int DV_PERIODIC_TIME_SPECIFICATION = 23;
    final int DV_GENERAL_TIME_SPECIFICATION = 24;
    final int DV_INTERVAL = 25;

    // TODO acrescente uma constante para todos os demais tipos

    /**
     * Retorna o tamanho, em bytes, de um campo de um objeto.
     * 
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0.
     *
     * @return Quantidade de bytes do campo do objeto.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o objeto não existe;
     * (b) o campo não existe.
     */
    int obtemQtdeBytes(int id, int campo);
    
    /**
     * Recupera parte do campo do objeto,
     * conforme a capacidade de memória suportada.
     *  
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0.
     * @param ini A posição do byte inicial.
     * @param fim A posição do byte final.
     *
     * @return Parte do campo do objeto.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o objeto não existe;
     * (b) o campo não existe;
     * (c) ini negativo; (d) ini maior do que fim;
     * (e) fim maior do que o tamanho total do campo.
     */
    byte[] obtemBytes(int id, int campo, int ini, int fim);

    /**
     * Recupera o campo do objeto.
     *  
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0.
     *
     * @return Sequência de bytes correspondente ao campo.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o objeto não existe;
     * (b) o campo não existe;
     */
    byte[] obtemBytes(int id, int campo);

    /**
     * Define a raiz do presente objeto.
     * 
     * <p>Uma instância desta interface é um grafo com uma
     * raiz única. Após todos os objetos serem adicionados
     * ao grafo, partindo dos objetos "primitivos" até o objeto 
     * de mais "alto nível" (raiz), este método deve ser chamado
     * a fim de guardar a identificação da raiz. Isso possibilita
     * que seja estabelecido um ponto de acesso único ao grafo 
     * para uma posterior remontagem.</p>
     * 
     * @see #obtemRaiz()
     * 
     * @param O identificador único da raiz.
     * 
     * @throws IllegalArgumentException O objeto raiz não existe.
     */
    void defineRaiz(int raiz);
    
    /** 
     * Obtém o identificador da raiz do presente objeto.
     * 
     * <p>Este método retorna o identificador que determina
     * o ponto inicial para remontagem do grafo de objetos,
     * conforme a especificação do Modelo de Referência.</p>
     * 
     * @see #defineRaiz(int)
     * 
     * @return O identificador único da raiz.
     */
    int obtemRaiz();
    
    /**
     * Obtém o total de objetos, instâncias de elementos
     * do Modelo de Referência, ocupados pelo presente
     * objeto.
     *
     * <p>Uma instância desta interface é um grafo de
     * objetos. O presente método permite identificar
     * quantos objetos fazem parte deste grafo.</p>
     *
     * <p>Objeto aqui deve ser interpretado como
     * instância de "classe" do Modelo de Referência
     * do openEHR. Ou seja, não necessariamente este valor
     * é quantidade de instâncias de classes em Java
     * empregadas para representar o presente grafo de
     * objetos.</p>
     *
     * <p>Se o valor retornado é 3, então existem,
     * no presente grafo, três objetos, cujos
     * identificadores são 0, 1 e 2.</p>
     *
     * @return Total de objetos mantidos pela instância. O
     * primeiro é zero.
     */
    int totalObjetos();

    /**
     * Retorna inteiro que identifica o tipo do objeto
     * identificado.
     * @param id O identificador do objeto.
     * @return Valor inteiro correspondente ao tipo do
     * objeto.
     */
    int obtemTipo(int id);

    /**
     * Cria uma lista de objetos.
     * Note que na montagem do grafo de objetos todos os
     * objetos "filhos" devem ser adicionados antes de se
     * adicionar o "pai" ao grafo. Logo, o tamanho da lista
     * é fixo porque todos seus objetos são previamente
     * conhecidos/adicionados.
     * 
     * @param quantidade Quantidade de objetos da lista.
     * @return Identificador único da lista.
     */
    int adicionaLista(int quantidade);

    /**
     * Adiciona um item à lista.
     * @param lista Lista de objetos a ser adicionada
     *              de um item.
     * @param item Identificador do objeto a ser
     *             inserido na lista.
     * @return Identificador único do item na lista.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) a lista não existe; (b) o item não existe.
     */
    int adicionaItem(int lista, int item);

    /** 
     * Retorna o tamanho da lista de objetos.
     * 
     * @param lista Identificador da lista.
     * @throws IllegalArgumentException a lista não existe.
     */
    int obtemTamanhoLista(int lista);
    
    /**
     * Procura pelo objeto na lista.
     *
     * @param lista Identificador da lista onde o
     *              objeto será procurado.
     * @param objeto Identificador do objeto
     *               a ser procurado. Esse é um
     *               objeto temporário, construído
     *               com a classe ObjectTemp.
     * @return Ordem na lista onde o objeto se
     * encontra, ou o valor -1, caso o objeto não
     * esteja presente na lista.
     * 
     * @throws IllegalArgumentException a lista não existe.
     * 
     */
    int buscaEmLista(int lista, int objeto);

    /**
     * Elimina o objeto.
     *
     * <p>Este método é particularmente útil
     * durante uma busca, onde um objeto foi
     * construído especificamente para esta
     * finalidade.</p>
     *
     * @param objeto Identificador do objeto
     *               a ser eliminado.
     */
    void elimineObjeto(int objeto);

    /**
     * Cria um dicionário (<i>hash table</i>).
     *
     * <p>Um dicionário é tratado como uma combinação
     * de duas listas. Assim, para um par (Chave, Valor)
     * qualquer, se Chave se encontra na posição i
     * da lista de chaves, então Valor se encontra
     * na posição i da lista de valores.</p>
     *
     * @param chaves Identificador único da lista
     *               de chaves.
     * @param valores Identificador único da lista de
     *                valores.
     * @return Identificador único do dicionário.
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) a lista de chaves não existe;
     * (b) a lista de valores não existe;
     * (c) a lista de chaves é incompatível (contém elementos repetidos).
     */
    int adicionaHash(int chaves, int valores);
    
    /**
     * Adiciona um valor lógico ({@code DV_BOOLEAN}).
     *
     * @param valor Valor lógico (DV_BOOLEAN) a ser adicionado.
     * @return Identificador do valor lógico adicionado.
     *
     * @see #obtemLogico(int, int)
     */
    int adicionaDvBoolean(boolean valor);

    /**
     * Adiciona um identificador ({@code DV_IDENTIFIER}).
     *
     * @param issuer Entidade que emite identificação.
     * @param assigner Entidade que assina identificação.
     * @param id Identificador propriamente dito.
     * @param type Tipo da identificação.
     *
     * @return O identificador único deste identificador
     * na estrutura.
     */
    int adicionaDvIdentifier(
            String issuer,
            String assigner,
            String id,
            String type);

    /**
     * Adiciona um {@link java.net.URI} ({@code DV_URI}).
     *
     * @param uri Sequência de caracteres correspondentes
     *            à {@link java.net.URI}.
     *
     * @return O identificador único desta URI na estrutura.
     */
    int adicionaDvUri(String uri);

    /**
     * Adiciona um {@link java.net.URI} cujo esquema é
     * "ehr" ({@code DvEHRURI}).
     *
     * @param uri Sequência de caracteres correspondentes
     *            à {@link java.net.URI}.
     * @return O identificador único desta DvEHRURI na estrutura.
     */
    int adicionaDvEhrUri(String uri);

    /**
     * Adiciona um identificador de terminologia
     * ({@code TERMINOLOGY_ID}).
     *
     * @param nome Nome da terminologia.
     * @param versao Versão da terminologia.
     * @return O identificador único do identificador de
     * terminologia na estrutura.
     */
    int adicionaTerminologyId(String nome, String versao);

    /**
     * Adiciona um identificador de terminologia
     * ({@code TERMINOLOGY_ID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um identificador de
     *              terminologia ({TERMINOLOGY_ID}).
     * @return O identificador único deste identificador de
     * terminologia na estrutura.
     */
    int adicionaTerminologyId(String valor);

    /**
     * Adiciona um código ({@code CODE_PHRASE}).
     *
     * @param terminologyId Um identificador de terminologia.
     * @param codeString A sequência correspondente ao código.
     * @return O identificador único do código na estrutura.
     */
    int adicionaCodePhrase(String terminologyId, String codeString);

    /**
     * Adiciona dado encapsulado em uma sequência de caracteres
     * ({@code DV_PARSABLE}).
     *
     * @param valor Dado encapsulado propriamente dito.
     * @param formalismo Formalismo empregado pelo encapsulamento.
     * @return O identificador único do dado encapsulado na
     * estrutura.
     */
    int adicionaDvParsable(String valor, String formalismo);

    /**
     * Adiciona dado encapsulado em uma sequência de caracteres
     * ({@code DV_PARSABLE}).
     *
     * @param codePhraseCharSet A codificação empregada pelo
     *                          dado encapsulado.
     * @param codePhraseLanguage A linguagem empregada pelo
     *                           dado encapsulado.
     * @param valor O dado encapsulado propriamente dito.
     * @param formalismo O formalismo empregado pelo dado
     *                   encapsulado.
     * @return O identificador único do dado encapsulado na
     * estrutura.
     */
    int adicionaDvParsable(
            String codePhraseCharSet,
            String codePhraseLanguage,
            String valor,
            String formalismo);

    /**
     * Adiciona dado codificado
     * ({@code DV_MULTIMEDIA}).
     *
     * @param codePhraseCharSet A codificação empregada.
     * @param codePhraseLinguagem A linguagem empregada.
     * @param textoAlternativo Texto alternativo para os dados.
     * @param codePhraseTipoMidia A codificação do tipo de mídia.
     * @param codePhraseAlgoritmoCompressao O algoritmo de
     *                                      compressão empregado.
     * @param integridade A sequência de bytes que serve para
     *                    verificar a integridade dos dados.
     * @param codePhraseAlgoritmoIntegridade O algoritmo de
     *                                       verificação de
     *                                       integridade dos
     *                                       dados.
     * @param hDvMultimediaThumbnail O identificador único de
     *                               dados codificados que serve
     *                               como representação
     *                               comprimida do presente
     *                               dado codificado.
     * @param dvUri Sequência de caracteres que é a URI do
     *              dado codificado.
     * @param dado O dado codificado propriamente dito.
     * @return O identificador únido do dado codificado.
     */
    int adicionaDvMultimedia(
            String codePhraseCharSet,
            String codePhraseLinguagem,
            String textoAlternativo,
            String codePhraseTipoMidia,
            String codePhraseAlgoritmoCompressao,
            byte[] integridade,
            String codePhraseAlgoritmoIntegridade,
            int hDvMultimediaThumbnail,
            String dvUri,
            byte[] dado);
    
    /**
     * Adiciona um Identificador de Objeto da
     * ISO/IEC 8824) ({@code ISO_OID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um ISO_OID.
     * @return O identificador único na estrutura deste
     *          identificador de objeto da ISO.
     */
    int adicionaIsoOid(String valor);
    
    /**
     * Adiciona um Identificador Único Universal DCE 
     * ({@code UUID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um UUID.
     * @return O identificador único na estrutura do UUID.
     */
    int adicionaUuid(String valor);
    
    /**
     * Adiciona um identificador de domínio
     * da internet invertido ({@code INTERNET_ID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um identificador de domínio.
     * @return O identificador único na estrutura 
     *          do identificador de internet.
     */
    int adicionaInternetId(String valor);

    /**
     * Adiciona um identificador de hierarquia
     * ({@code HIER_OBJECT_ID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um identificador de
     *              hierarquia ({HIER_OBJECT_ID}).
     * @return O identificador único na estrutura deste
     *         identificador de hierarquia.
     */
    int adicionaHierObjectId(String valor);
    
    /**
     * Adiciona um identificador de hierarquia
     * ({@code HIER_OBJECT_ID}).
     *
     * @param raiz identificador único permanente de 
     *          entidade (@code UID).
     * @param extensão identificador local do objeto.
     * @return O identificador único na estrutura do 
     * identificador de hierarquia.
     */
    int adicionaHierObjectId(String raiz, String extensao);

    /**
     * Adiciona um identificador único global para uma
     * versão de um objeto ({@code OBJECT_VERSION_ID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um identificador de uma
     *              versão de um objeto ({OBJECT_VERSION_ID}).
     * @return O identificador único na estrutura deste
     *          identificador de versão de objeto.
     */
    int adicionaObjectVersionId(String valor);
    
    /**
     * Adiciona um identificador único global para uma
     * versão de um objeto ({@code OBJECT_VERSION_ID}).
     *
     * @param objectId identificador único para
     *                  objeto lógico (@code UID).
     * @param versionTreeId identificador da versão
     *                      com relação aos outros de sua 
     *                      árvore (@code VERSION_TREE_ID).
     * @param creatingSystemId identificador do sistema
     *                      criador da versão (@code UID).
     * @return O identificador único na estrutura do 
     *          identificador de versão de objeto.
     */
    int adicionaObjectVersionId(String objectId,
            String versionTreeId,
            String creatingSystemId);

}
