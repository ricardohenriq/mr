/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

/**
 * Operações de gerência de objetos do pacote Identification.
 */
public interface Identification {
    /**
     * Adiciona um Identificador de Objeto da
     * ISO/IEC 8824 ({@code ISO_OID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um ISO_OID.
     * @return O identificador único na estrutura deste
     *          identificador de objeto da ISO.
     *
     * @see #adicionaInternetId(String)
     * @see #adicionaUuid(String)
     */
    int adicionaIsoOid(String valor);

    /**
     * Adiciona um Identificador Único Universal DCE
     * ({@code UUID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um UUID.
     * @return O identificador único na estrutura do UUID.
     *
     * @see #adicionaInternetId(String)
     * @see #adicionaIsoOid(String) (String)
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
     *
     * @see #adicionaIsoOid(String) (String)
     * @see #adicionaUuid(String)
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
     * Adiciona um identificador de template, ou seja,
     * um objeto {@code TEMPLATE_ID}.
     * @param valor Identificador de <i>template</i>.
     * @return O identificador único do objeto criado.
     */
    int adicionaTemplateId(String valor);

    /**
     * Adiciona um identificador de terminologia ({@code TERMINOLOGY_ID}).
     * @param valor Identificador de terminologia.
     * @return O identificador único do objeto criado.
     */
    int adicionaTerminologyId(String valor);

    /**
     * Adiciona um identificador de versão ({@code VERSION_TREE_ID}).
     *
     * <p>O formato desse identificador é:<b>trunk-version</b> seguido,
     * opcionalmente, de <b>.branch-number.branch-version</b>.
     * @param valor Sequência de caracteres de um identificador de versão,
     *              conforme o formato esperado.
     * @return Identificador único do objeto criado.
     */
    int adicionaVersionTreeId(String valor);

    /**
     * Adiciona um identificador de arquétipo ({@code ARCHETYPE_ID}).
     *
     * @param valor O identificador de arquétipo.
     * @return O identificador único do objeto adicionado.
     */
    int adicionaArchetypeId(String valor);

    /**
     * Adiciona um identificador genérico ({@code GENERIC_ID}), desconhecido
     * do openEHR.
     *
     * @param valor O identificador "genérico".
     * @param scheme O esquema, possivelmente local, empregado pelo
     *               identificador desconhecido do openEHR.
     * @return O identificador único do objeto adicionado.
     */
    int adicionaGenericId(String valor, String scheme);
}
