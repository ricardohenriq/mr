package org.openehr.rm.formatters;

import br.inf.ufg.fabrica.mr.ModeloDeReferencia;

public class JsonObject implements ModeloDeReferencia {

    /* ÍNICIO MÉTODOS INTERFACE */
    public byte[] toBytes() {
        return new byte[0];
    }

    public void fromBytes(byte[] bytes) {

    }

    public String toXML() {
        return null;
    }

    public void fromXML(String xml) {

    }

    public String toJSON() {
        int idRaiz;
        String jsonFinal = "";
        // -- talvez precise { } envolvendo o retorno do json, validar
        idRaiz = obtemRaiz();
        jsonFinal += "{";
        jsonFinal += buildJson(idRaiz);
        jsonFinal += "}";
        return jsonFinal;
    }

    public void fromJSON(String json) {

    }

    public void defineRaiz(int raiz) {

    }

    public int obtemRaiz() {
        return 0;
    }

    public int totalObjetos() {
        return 0;
    }

    public int obtemTipo(int id) {
        return 0;
    }

    public byte obtemByte(int id, int campo) {
        return 0;
    }

    public String obtemString(int id, int campo) {
        return null;
    }

    public boolean obtemLogico(int id, int campo) {
        return false;
    }

    public int obtemInteiro(int id, int campo) {
        return 0;
    }

    public float obtemFloat(int id, int campo) {
        return 0;
    }

    public double obtemDouble(int id, int campo) {
        return 0;
    }

    public int adicionaDvBoolean(boolean valor) {
        return 0;
    }

    public boolean obtemDvBoolean(int id) {
        return false;
    }

    public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
        return 0;
    }

    public boolean obtemValorLogico(int id, int campo) {
        return false;
    }

    public String obtemTexto(int id, int campo) {
        return null;
    }

    public byte[] obtemVetorBytes(int id, int campo) {
        return new byte[0];
    }

    public int adicionaLista(int quantidade) {
        return 0;
    }

    public int adicionaItem(int lista, int item) {
        return 0;
    }

    public int buscaEmLista(int lista, int objeto) {
        return 0;
    }

    public void elimineObjeto(int objeto) {

    }

    public int adicionaHash(int chaves, int valores) {
        return 0;
    }

    public int adicionaDvUri(String uri) {
        return 0;
    }

    public int adicionaDvEhrUri(String uri) {
        return 0;
    }

    public int adicionaTerminologyId(String nome, String versao) {
        return 0;
    }

    public int adicionaTerminologyId(String valor) {
        return 0;
    }

    public int adicionaCodePhrase(String terminologyId, String codeString) {
        return 0;
    }

    public int adicionaDvParsable(String valor, String formalismo) {
        return 0;
    }

    public int adicionaDvParsable(String codePhraseCharSet, String codePhraseLanguage, String valor, String formalismo) {
        return 0;
    }

    public int adicionaDvMultimedia(String codePhraseCharSet, String codePhraseLinguagem, String textoAlternativo, String codePhraseTipoMidia, String codePhraseAlgoritmoCompressao, byte[] integridade, String codePhraseAlgoritmoIntegridade, int hDvMultimediaThumbnail, String dvUri, byte[] dado) {
        return 0;
    }

    public int adicionaIsoOid(String valor) {
        return 0;
    }

    public int adicionaUuid(String valor) {
        return 0;
    }

    public int adicionaInternetId(String valor) {
        return 0;
    }

    public int adicionaHierObjectId(String valor) {
        return 0;
    }

    public int adicionaHierObjectId(String raiz, String extensao) {
        return 0;
    }

    public int adicionaObjectVersionId(String valor) {
        return 0;
    }

    public int adicionaObjectVersionId(String objectId, String versionTreeId, String creatingSystemId) {
        return 0;
    }
    /* FIM MÉTODOS INTERFACE */

    /* ÍNICIO MÉTODOS AUXILIARES */
    private String buildJson(int idNodoGrafo) {
        String out = "";
        String template = "";
        String list = "";
        switch (obtemTipo(idNodoGrafo)) {
            case 1: //DvBoolean
                template = "'DvBoolean': {'value': #value}";
                template = template.replaceAll("#value", String.valueOf(obtemValorLogico(idNodoGrafo, 0)));
                template = template.replaceAll("'", "\"");
                break;
            case 2: //DvIdentifier
                template = "'DvIdentifier': {'issuer': '#issuer', 'assigner': '#assigner', 'id': '#id', 'type': '#type'}";
                template = template.replaceAll("#issuer", obtemString(idNodoGrafo, 0));
                template = template.replaceAll("#assigner", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#id", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#type", obtemString(idNodoGrafo, 3));
                template = template.replaceAll("'", "\"");
                break;
            case 3: //DvParagraph
                template = "'DvParagraph': {'items': [#listDvText]}";
                int idLista = obtemInteiro(idNodoGrafo,0); // método vai ser trocado pelo obtemChave segundo orientações do Fábio
                int k = 0;
                // Utilizamos a exception como condição de parada. O Fábio disse que apesar de funcionar não é o jeito correto e que vamos precisar mudar isso
                // Segundo ele, vai ser disponibilizado um método na interface que retorna o tamanho da lista
                // Assim que tivermos o método, precisamos atualizar o laço (condição de parada)
                while(true){
                    try{
                        int idObjetoLista = obtemInteiro(idLista,k); // obtem o id ou a chave do nodo do grafo
                        list = list + buildJson(idObjetoLista);
                    }catch(IllegalArgumentException e){
                        break;
                    }
                    k++;
                }
                template = template.replaceAll("#listDvText",list);
                template = template.replaceAll("'", "\"");
                list = ""; //clear list
                break;
            case 4: //TermMapping
                template = "'TermMapping': {'target': #callCodePhrase, 'match': #callMatch, 'purpose': #callDvCodedText}";
                template = template.replaceAll("#callCodePhrase", buildJson(obtemInteiro(idNodoGrafo,0)));
                template = template.replaceAll("#callMatch", buildJson(obtemInteiro(idNodoGrafo,1)));
                template = template.replaceAll("#callDvCodedText", buildJson(obtemInteiro(idNodoGrafo,2)));
                template = template.replaceAll("'", "\"");
                break;
            case 5: //DvParsable -- recebe parâmetros que vão para o super -- Validar
                template = "'DvParsable': {'charset': #callCodePhrase1, 'language': #callCodePhrase2, 'value': '#value', 'formalism': '#formalism'}";
                template = template.replaceAll("#callCodePhrase1", buildJson(obtemInteiro(idNodoGrafo,0)));
                template = template.replaceAll("#callCodePhrase2", buildJson(obtemInteiro(idNodoGrafo,1)));
                template = template.replaceAll("#value", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#formalism", obtemString(idNodoGrafo,3));
                template = template.replaceAll("'", "\"");
                break;
            case 6: //CodePhrase
                template = "'CodePhrase': {'terminologyId': #callTerminologyId, 'codeString': '#codeString'}";
                template = template.replaceAll("#callTerminologyId", buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#codeString", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("'", "\"");
                break;
        }
        out += template;
        return out;
    }
    /* FIM MÉTODOS AUXILIARES */
}

