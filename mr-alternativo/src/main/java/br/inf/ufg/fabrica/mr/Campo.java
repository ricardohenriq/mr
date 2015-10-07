/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import java.io.InputStream;

/**
 * Operações sobre campo de um objeto.
 *
 * <p>Um objeto é tratado como uma coleção de
 * campos de valores de tipos primitivos. Quando
 * um objeto possui referência para outro objeto,
 * então este campo é tratado como um referência,
 * nesse caso, um inteiro, que unicamente identifica
 * o objeto em questão (referenciado).
 * </p>
 */
public interface Campo {
    /**
     * Retorna inteiro que identifica o tipo do campo
     * do objeto.
     * @param id O identificador do objeto.
     * @param campo O identificador do campo (<i>zero-based</i>).
     * @return Valor inteiro correspondente ao tipo
     * primitivo do campo.
     */
    int obtemTipo(int id, int campo);

    /**
     * Recupera o byte do campo do objeto.
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cujo valor é um byte.
     * @return Valor do byte (campo do objeto).
     *
     * @throws IllegalArgumentException Se pelo menos uma das
     * condições abaixo for verificada:
     * (a) o campo não é do tipo byte; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemTexto(int, int)
     * @see #obtemVetorBytes(int, int)
     */
    byte obtemByte(int id, int campo);

    /**
     * Recupera a String do campo do objeto.
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cujo valor é uma String.
     * @return String do campo do objeto.
     *
     * @throws IllegalArgumentException Se pelo menos uma das
     * condições abaixo for verificada:
     * (a) o campo não é do tipo String; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemTipo(int, int)
     * @see #obtemTexto(int, int)
     * @see #obtemVetorBytes(int, int)
     */
    String obtemString(int id, int campo);

    /**
     * Recupera o valor lógico do objeto.
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cujo valor lógico é desejado.
     * @return Valor lógico do campo do objeto.
     *
     * @throws IllegalArgumentException Se pelo menos uma das
     * condições abaixo for verificada:
     * (a) o campo não é do tipo lógico; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemTipo(int, int)
     * @see #obtemTexto(int, int)
     * @see #obtemVetorBytes(int, int)
     */
    boolean obtemLogico(int id, int campo);

    /**
     * Recupera uma chave ("endereço" para um objeto).
     *
     * <p>O tipo de uma chave é {@code int}, ou seja,
     * o método @see #obtemInteiro poderia ser empregado
     * para essa finalidade. O presente método, contudo,
     * é obrigatório para este tipo de informação e
     * permite verificação de "tipo" do campo.</p>
     *
     * @param id O identificador único do objeto que contém
     *           uma chave.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cuja chave nele depositada é desejada.
     * @return Valor inteiro (campo do objeto).
     *
     * @throws IllegalArgumentException Se pelo menos uma das
     * condições abaixo for verificada:
     * (a) o campo não é do tipo inteiro; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemTipo(int, int)
     * @see #obtemTexto(int, int)
     * @see #obtemInteiro(int, int)
     * @see #obtemVetorBytes(int, int)
     */
    int obtemChave(int id, int campo);

    /**
     * Recupera inteiro.
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cujo valor inteiro é desejado.
     * @return Valor inteiro (campo do objeto).
     *
     * @throws IllegalArgumentException Se pelo menos uma das
     * condições abaixo for verificada:
     * (a) o campo não é do tipo inteiro; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemTipo(int, int)
     * @see #obtemTexto(int, int)
     * @see #obtemVetorBytes(int, int)
     */
    int obtemInteiro(int id, int campo);

    /**
     * Recupera o valor de precisão simples (ponto
     * flutuante).
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cujo valor {@code float} é desejado.
     * @return Valor {@code float} do campo do objeto.
     *
     * @throws IllegalArgumentException Se pelo menos uma das
     * condições abaixo for verificada:
     * (a) o campo não é do tipo float; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemTipo(int, int)
     * @see #obtemTexto(int, int)
     * @see #obtemVetorBytes(int, int)
     */
    float obtemFloat(int id, int campo);

    /**
     * Recupera valor de precisão dupla (ponto flutuante).
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cujo valor é um {@code double}.
     * @return Valor {@code double} do campo do objeto.
     *
     * @throws IllegalArgumentException Se pelo menos uma das
     * condições abaixo for verificada:
     * (a) o campo não é do tipo {@code double}; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemTipo(int, int)
     * @see #obtemTexto(int, int)
     * @see #obtemVetorBytes(int, int)
     */
    double obtemDouble(int id, int campo);

    /**
     * Recupera texto do objeto.
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cuja sequência de caracteres
     *              correspondente é desejada.
     * @return Sequência de caracteres correspondente ao
     * campo do objeto.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o campo não é texto; (b) o campo não existe;
     * (c) o objeto não existe.
     */
    String obtemTexto(int id, int campo);

    /**
     * Recupera vetor de bytes (valor do campo do objeto).
     *
     * @param id O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, cujo
     *              valor, um vetor de bytes, é desejado.
     * @return Valor do campo do objeto.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o campo não é um vetor de bytes; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemTexto(int, int)
     * @see #obtemTamanhoVetorBytes(int, int)
     */
    byte[] obtemVetorBytes(int id, int campo);

    /**
     * Recupera o tamanho em bytes do vetor de bytes
     * armazenado no campo em questão.
     * @param id O identificador único do objeto.
     * @param campo O identificador do campo (<i>zero-based</i>).
     * @return A quantidade de bytes do vetor de bytes
     * contido no campo identificado.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o campo não é um vetor de bytes; (b) o campo não existe;
     * (c) o objeto não existe.
     *
     * @see #obtemVetorBytes(int, int)
     */
    int obtemTamanhoVetorBytes(int id, int campo);

    /**
     * Retorna @see InputStream para o conteúdo do vetor
     * de bytes indicado pelo campo.
     *
     * <p>A quantidade máxima de bytes que podem ser
     * lidos do retorno desse método é indicada pelo
     * método @see #obtemTamanhoVetorBytes.
     * </p>
     *
     * @param id O identificador único do objeto.
     * @param campo O identificador do campo (<i>zero-based</i>).
     * @return @see InputStream para o campo identificado.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) o campo não é um vetor de bytes; (b) o campo não existe;
     * (c) o objeto não existe.
     */
    InputStream obtemStreamVetorBytes(int id, int campo);
}
