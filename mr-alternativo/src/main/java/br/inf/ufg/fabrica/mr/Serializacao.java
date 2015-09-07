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
     * Serializa o grafo de objetos em um vetor de bytes.
     *
     * @return Vetor de bytes contendo uma sequência de
     * bytes correspondente ao grafo de objetos.
     *
     * @see #toBytes(OutputStream)
     * @see #fromBytes(byte[])
     * @see #toJson()
     * @see #toXml()
     */
    byte[] toBytes();

    /**
     * Deposita no destino o grafo de
     * objetos como uma sequência de bytes.
     *
     * <p>Este método permite que a serialização do grafo
     * seja enviada para um arquivo, serviço web ou outro
     * destino.</p>
     *
     * @throws IllegalArgumentException Caso ocorra falha
     * na tentativa de depositar as informações no destino.
     *
     * @see #fromBytes(InputStream)
     * @see #toJson()
     * @see #toXml()
     * @see #toBytes()
     */
    void toBytes(OutputStream destino);

    /**
     * Carrega o grafo de objetos a partir do vetor
     * de bytes.
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
     *
     * @see #fromBytes(InputStream)
     * @see #fromJson(String)
     * @see #fromXml(String)
     */
    void fromBytes(byte[] bytes);

    /**
     * Carrega um grafo de objetos baseados no Modelo de
     * Referência do openEHR a partir do <i>stream</i>.
     *
     * @param entrada Origem da qual bytes correspondentes
     *                a um grafo de objetos serão recuperados.
     *
     * @throws IllegalArgumentException Caso a tentativa
     * de criar o grafo de objetos tenha falhado.
     *
     * @see #fromBytes(byte[])
     * @see #fromJson(String)
     * @see #fromXml(String)
     */
    void fromBytes(InputStream entrada);

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
     *
     * @see #toXml(OutputStream)
     * @see #toBytes()
     * @see #toJson()
     */
    String toXml();

    /**
     * Serializa o grafo de objetos em um documento XML
     * enviado para o <i>stream</i>.
     *
     * @param stream Destino do documento XML correspondente
     *               ao grafo de objetos.
     *
     * @see #toXml()
     * @see #toJson()
     * @see #toBytes()
     */
    void toXml(OutputStream stream);

    /**
     * Carrega o grafo de objetos, em conformidade com o
     * Modelo de Referência, correspondente ao documento
     * XML fornecido.
     *
     * @param xml Documento XML contendo grafo de objetos
     *            baseados no Modelo de Referência.
     *
     * @see #fromXml(InputStream)
     * @see #fromJson(String)
     * @see #fromBytes(byte[])
     */
    void fromXml(String xml);

    /**
     * Carrega o grafo de objetos a partir do <i>stream</i>
     * fornecido.
     *
     * @param stream Origem de documento XML a partir do qual
     *               o grafo de objetos correspondente será
     *               carregado.
     *
     * @see #fromXml(String)
     * @see #fromJson(String)
     * @see #fromBytes(byte[])
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
     *
     * @see #toJson(OutputStream)
     * @see #toXml()
     * @see #toJson()
     */
    String toJson();

    /**
     * Deposita no <i>stream</i> o conteúdo correspondente
     * do grafo de objetos no formato JSON.
     *
     * @param stream Saída na qual a serialização JSON do
     *               grafo de objetos será depositada.
     *
     * @see #toJson()
     * @see #toXml()
     * @see #toBytes()
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
     *
     * @see #fromJson(String)
     * @see #fromBytes(byte[])
     * @see #fromXml(String)
     */
    void fromJson(InputStream entrada);
}