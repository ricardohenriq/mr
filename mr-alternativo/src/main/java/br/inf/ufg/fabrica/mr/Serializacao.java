/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Operações de serialização de um grafo de objetos
 * baseados no Modelo de Referência do openEHR.
 *
 * <p>As operações definidas contemplam três formatos:
 * (a) vetor de bytes; (b) XML e (c) JSON. Para cada um
 * desses formatos são definidas quatro operações, duas
 * para serialização e as outras duas para desserialização
 * de um grafo de objetos.
 * </p>
 *
 * <p>As variantes empregado @see InputStream tem como
 * objetivo permitir a criação de um grafo de objetos
 * de tamanho arbitrário. Enquanto aquelas empregando
 * @see OutputStream permitem persistir um grafo de
 * objetos de tamanho arbitrário.</p>
 *
 * <p>Observe que as variantes empregando um vetor de
 * bytes ou sequência de caracteres ({@code String})
 * exigem que o conteúdo do grafo esteja em memória
 * principal, o que pode ser inviável em alguns
 * cenários.</p>
 */
public interface Serializacao {

    /**
     * Produz um vetor de bytes correspondente ao
     * grafo mantido pela instância.
     *
     * <p>O conteúdo do grafo pode ser suficientemente
     * extenso e, em consequência, inviável de ser depositado
     * em um único vetor. Nesses casos, use o método
     * @see #toBytes(OutputStream).
     * </p>
     *
     * @return Serialização em um vetor de bytes do
     * grafo de objetos.
     *
     * @see #toBytes(OutputStream)
     */
    byte[] toBytes();

    /**
     * O grafo de objetos mantidos pela presente instância
     * é enviado para o {@code OutputStream}.
     *
     * <p>Este método permite que a serialização do grafo
     * seja enviada para um arquivo, serviço web ou outro
     * destino.</p>
     *
     * @see #fromBytes(byte[])
     * @see #toJson()
     * @see #toXml()
     * @see #toBytes()
     */
    void toBytes(OutputStream stream);

    /**
     * Carrega o grafo de objetos a partir do vetor
     * de bytes correspondente.
     *
     * <p>Uma cópia do vetor é realizada de tal forma que
     * posterior alteração no vetor fornecido não altera
     * o grafo de objetos mantido pela instância.</p>
     *
     * <p>Em alguns casos o grafo pode ser extenso e,
     * em consequência, desejável o emprego de método
     * alternativo como @see #fromBytes(InputStream).
     * </p>
     *
     * @param bytes O vetor de bytes contendo um grafo de
     *              objetos.
     *
     * @throws IllegalArgumentException Caso o vetor fornecido
     * não contenha um grafo "válido".
     */
    void fromBytes(byte[] bytes);

    /**
     * Carrega um grafo de objetos baseados no Modelo de
     * Referência do openEHR a partir do <i>stream</i>.
     *
     * @param bytes Stream de bytes que é uma serialização
     *              de grafo de objetos baseados no Modelo
     *              de Referência do openEHR.
     *
     * @throws IllegalArgumentException Caso a tentativa
     * de criar o grafo de objetos tenha falhado.
     */
    void fromBytes(InputStream bytes);

    /**
     * Serializa as informações do presente objeto, baseado
     * no MR, em um documento XML.
     *
     * <p>O documento XML produzido pelo presente método,
     * sequência de caracteres, deve estar em conformidade
     * com os esquemas adotados pelo openEHR.</p>
     *
     * <p>O conteúdo do grafo pode ser suficientemente
     * extenso e, em consequência, pode ser empregado o
     * método alternativo @see #toXml(OutputStream).
     * </p>
     *
     * @return Documento XML correspondente ao grafo
     * de objetos.
     */
    String toXml();

    /**
     * Serializa o grafo de objetos em um documento XML
     * enviado para o <i>stream</i>.
     *
     * @param stream Destino do documento XML correspondente
     *               ao grafo de objetos.
     */
    void toXml(OutputStream stream);

    /**
     * Carrega o grafo de objetos, em conformidade com o
     * Modelo de Referência, correspondente ao documento
     * XML fornecido.
     *
     * @param xml Documento XML contendo grafo de objetos
     *            baseados no Modelo de Referência.
     */
    void fromXml(String xml);

    /**
     * Carrega o grafo de objetos a partir do <i>stream</i>
     * fornecido.
     *
     * @param stream Origem de documento XML a partir do qual
     *               o grafo de objetos correspondente será
     *               carregado.
     */
    void fromXml(InputStream stream);

    /**
     * Serializa a instância em uma sequência de caracteres
     * no formato JSON.
     *
     * @see #fromJson(String)
     * @see #toBytes()
     * @see #toXml()
     *
     * @return Sequência de caracteres, no formato JSON,
     * correspondente à serialização do presente objeto.
     */
    String toJson();

    /**
     * Deposita no <i>stream</i> o conteúdo correspondente
     * do grafo de objetos no formato JSON.
     *
     * @param stream Saída na qual a serialização JSON do
     *               grafo de objetos será depositada.
     */
    void toJson(OutputStream stream);

    /**
     * Cria o grafo de objetos, representado pelo presente
     * objeto, em conformidade com o Modelo de Referência e
     * serializado em JSON.
     *
     * <p>Este método faz o processo inverso ao do método
     * {@see #toJSON}.</p>
     *
     * @see #toJson()
     * @see #fromXml(String)
     * @see #fromBytes(byte[])
     *
     * @param json Sequência de caracteres, no formato JSON,
     *             correspondentes a um grafo de objetos
     *             serializado do Modelo de Referência do
     *             openEHR.
     */
    void fromJson(String json);

    /**
     * Carrega grafo de objetos obtido da entrada.
     *
     * @param entrada Origem da qual um grafo de objetos
     *                serializado no formato JSON será
     *                recuperado.
     */
    void fromJson(InputStream entrada);
}
