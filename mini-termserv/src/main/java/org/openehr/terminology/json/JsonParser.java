package org.openehr.terminology.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.String;





import br.inf.ufg.fabrica.mr.ModeloDeReferencia;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonParser implements ModeloDeReferencia{

    Mock mock;

    public JsonParser(){
        mock = new Mock();
    }


    public void fromJson(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            int idRaizGrafo = buildGraph(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public int buildGraph(JSONObject jsonObject){
        int type = 0;
        try {
            type = jsonObject.getInt("globalTypeIdn");
            if(type == CODE_PHRASE){
                int idTerminologyID = buildGraph(jsonObject.getJSONObject("terminologyId"));
                return adicionaCodePhrase(idTerminologyID,jsonObject.getString("codeString"));
            }else if(type == TERMINOLOGY_ID){
                return adicionaTerminologyId(jsonObject.getString("name"),jsonObject.get("version").toString());
            }else if(type == DV_TEXT){
                String value = jsonObject.getString("value");

                int idListaTermMapping = adicionaLista(jsonObject.getJSONArray("mappings").length());

                if(jsonObject.getJSONArray("mappings").length() > 0){

                    idListaTermMapping = adicionaLista(jsonObject.getJSONArray("mappings").length());
                    int tamanhoLista = jsonObject.getJSONArray("mappings").length();
                    for(int k = 0; k < tamanhoLista; k++){
                        JSONObject now = (JSONObject) jsonObject.getJSONArray("mappings").get(k);
                        int idTermMapping = buildGraph(now);
                        adicionaALista(idListaTermMapping,idTermMapping);
                    }
                }
                String formatting = jsonObject.getString("formatting");
                int idDvURI = buildGraph(jsonObject.getJSONObject("hyperlink"));
                int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
                int idEncoding = buildGraph(jsonObject.getJSONObject("encoding"));
                return adicionaDvText(value, idListaTermMapping, formatting, idDvURI, idLanguage, idEncoding);
            }else if(type == TERM_MAPPING){
                int target = buildGraph(jsonObject.getJSONObject("target"));
                int match = buildGraph(jsonObject.getJSONObject("match"));
                int purpose = buildGraph(jsonObject.getJSONObject("purpose"));
                return adicionaTermMapping(target,match,purpose);
            }else if(type == MATCH){
                String value = jsonObject.getString("value");
                return adicionaMatch(value);
            }else if(type == DV_CODED_TEXT){
                String value = jsonObject.getString("value");
                int idListaTermMapping = adicionaLista(jsonObject.getJSONArray("mappings").length());


                if(jsonObject.getJSONArray("mappings").length() > 0){
                    idListaTermMapping = adicionaLista(jsonObject.getJSONArray("mappings").length());
                    int tamanhoLista = jsonObject.getJSONArray("mappings").length();
                    for(int k = 0; k < tamanhoLista; k++){
                        JSONObject now = (JSONObject) jsonObject.getJSONArray("mappings").get(k);
                        int idTermMapping = buildGraph(now);
                        adicionaALista(idListaTermMapping,idTermMapping);
                    }

                }

                String formatting = jsonObject.getString("formatting");
                int idDvURI = buildGraph(jsonObject.getJSONObject("hyperlink"));
                int idLanguage = buildGraph(jsonObject.getJSONObject("language"));
                int idEncoding = buildGraph(jsonObject.getJSONObject("encoding"));
                int idDefiningCode = buildGraph(jsonObject.getJSONObject("definingCode"));
                return adicionaDvCodedText(value,idListaTermMapping,formatting,idDvURI,idLanguage,idEncoding,idDefiningCode);
            }else if(type == DV_URI){
                return adicionaDvUri(jsonObject.getString("value"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return type;
    }

    public int adicionaTerminologyId(String nome, String versao) {
        return TERMINOLOGY_ID;
    }
    public int adicionaCodePhrase(int terminologyId, String codeString) {
        return CODE_PHRASE;
    }



    public String toJson() {
        int idRaiz = obtemRaiz();
        return buildJson(idRaiz);
    }

    private String buildJson(int idNodo){
        int nodeType = obtemTipo(idNodo);
        if(nodeType == CODE_PHRASE){
            String template = "{'globalTypeIdn' : #globalTypeIdn, 'codeString' : #codeString, 'terminologyId' : #terminologyId }";
            template = template.replaceAll("#globalTypeIdn",String.valueOf(CODE_PHRASE));
            template = template.replaceAll("#terminologyId",buildJson(obtemChave(idNodo,0)));
            template = template.replaceAll("#codeString",formataString(obtemString(idNodo, 1)));
            template = template.replaceAll("'", "\"");
            return template;
        }else if(nodeType == TERMINOLOGY_ID){
            String template = "{'globalTypeIdn' : #globalTypeIdn, 'name' : #name, 'version' : #version }";
            template = template.replaceAll("#globalTypeIdn",String.valueOf(TERMINOLOGY_ID));
            template = template.replaceAll("#name", formataString(obtemString(idNodo,0)));
            template = template.replaceAll("#version", formataString(obtemString(idNodo,1)));
            template = template.replaceAll("'", "\"");
            return template;
        }else if(nodeType == DV_TEXT){
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
        }else if(nodeType == TERM_MAPPING){
            String template = "{'globalTypeIdn' : #globalTypeIdn, 'target' : #target, 'match' : #match, 'purpose' : #purpose}";
            template = template.replaceAll("#globalTypeIdn",String.valueOf(TERM_MAPPING));
            template = template.replaceAll("#target",buildJson(obtemChave(idNodo,0)));
            template = template.replaceAll("#match",buildJson(obtemChave(idNodo,1)));
            template = template.replaceAll("#purpose",buildJson(obtemChave(idNodo,2)));
            template = template.replaceAll("'", "\"");
            return template;
        }else if(nodeType == MATCH){
            String template = "{'globalTypeIdn' : #globalTypeIdn, 'value' : #value}";
            template = template.replaceAll("#globalTypeIdn",String.valueOf(MATCH));
            template = template.replaceAll("#value", formataString(obtemString(idNodo, 0)));
            template = template.replaceAll("'", "\"");
            return template;
        }else if(nodeType == DV_CODED_TEXT){
            String template = "{'globalTypeIdn' : #globalTypeIdn, 'value' : #value, 'mappings' : [#mappings], 'formatting' : #formatting, 'hyperlink' : #hyperlink, 'language' : #language, 'encoding' : #encoding, 'definingCode' : #definingCode}";
            template = template.replaceAll("#globalTypeIdn",String.valueOf(DV_CODED_TEXT));
            template = template.replaceAll("#value",formataString(obtemString(idNodo, 0)));

            int idTermMappingList = obtemChave(idNodo,1);
            int termMappingListSize = obtemTamanhoLista(idTermMappingList);
            String termMappingList = "";
            for(int k = 0; k < termMappingListSize; k++){
                int idListObject = obtemChave(idTermMappingList, k);
                termMappingList = termMappingList + ( (k == termMappingListSize - 1) ? buildJson(idListObject) : buildJson(idListObject) +  ",");
            }
            template = template.replaceAll("#mappings", termMappingList);

            template = template.replaceAll("#formatting", formataString(obtemString(idNodo,2)));
            template = template.replaceAll("#hyperlink",buildJson(obtemChave(idNodo,3)));
            template = template.replaceAll("#language",buildJson(obtemChave(idNodo, 4)));
            template = template.replaceAll("#encoding",buildJson(obtemChave(idNodo, 5)));
            template = template.replaceAll("#definingCode",buildJson(obtemChave(idNodo,6)));
            template = template.replaceAll("'", "\"");
            return template;
        }else if(nodeType == DV_URI){
            String template = "{'globalTypeIdn' : #globalTypeIdn, 'value' : #value}";
            template = template.replaceAll("#globalTypeIdn",String.valueOf(DV_URI));
            template = template.replaceAll("#value",formataString(obtemString(idNodo,0)));
            template = template.replaceAll("'", "\"");
            return template;
        }

        return "";
    }

    public int obtemChave(int id, int campo) {

        if(id == 1000){
            if(campo == 1) return 1004;
            if(campo == 3) return 1001;
            if(campo == 4) return 1002;
            if(campo == 5) return 1003;
        }
        if(id == 1002){
            if(campo == 0) return 1011;
        }
        if(id == 1003){
            if(campo == 0) return 1012;
        }
        if(id == 1004){
            return 1005;
        }
        if(id == 1005){
            if(campo == 0) return 1009;
            if(campo == 1) return 1013;
            if(campo == 2) return 1008;
        }
        if(id == 1008){
            if(campo == 6) return 1009;
            if(campo != 1) return obtemChave(1000,campo);
        }
        if(id == 1009){
            if(campo == 0) return 1010;
        }
        return 0;
    }


    public String obtemString(int id, int campo) {
        if(id == 1000){
            if(campo == 0) return mock.getDvTextMock().getValue();
            if(campo == 2) return mock.getDvTextMock().getFormatting();
        }
        if(id == 1001){
            if(campo == 0) return mock.getDvURIMock().getValue().toString();
        }
        if(id == 1002){
            if(campo == 0) return mock.getDvTextMock().getLanguage().getCodeString();
        }
        if(id == 1003){
            if(campo == 0) return mock.getDvTextMock().getEncoding().getCodeString();
        }
        if(id == 1008){
            if(campo == 0) return mock.getDvCodedTextMock().getValue();
            return obtemString(1000,campo);
        }
        if(id == 1009){
            if(campo == 1) return mock.getCodePhraseMock().getCodeString();
        }
        if(id == 1010){
            if(campo == 0) return mock.getCodePhraseMock().getTerminologyId().name();
            if(campo == 1)  return mock.getCodePhraseMock().getTerminologyId().versionID();
        }
        if(id == 1011){
            if(campo == 0) return mock.getDvTextMock().getLanguage().getTerminologyId().name();
            if(campo == 1)  return mock.getDvTextMock().getLanguage().getTerminologyId().versionID();
        }
        if(id == 1012){
            if(campo == 0) return mock.getDvTextMock().getEncoding().getTerminologyId().name();
            if(campo == 1)  return mock.getDvTextMock().getEncoding().getTerminologyId().versionID();
        }
        if(id == 1013){
            if(campo == 0) return mock.getMatchMock().getValue();
        }
        return "";
    }

    public int obtemTipo(int id) {
        if(id == 1000) return DV_TEXT;
        if(id == 1001) return DV_URI;
        if(id == 1002) return CODE_PHRASE;
        if(id == 1003) return CODE_PHRASE;
        if(id == 1004) return TERM_MAPPING;
        if(id == 1005) return TERM_MAPPING;
        if(id == 1006) return TERM_MAPPING;
        if(id == 1007) return TERM_MAPPING;
        if(id == 1008) return DV_CODED_TEXT;
        if(id == 1009) return CODE_PHRASE;
        if(id == 1010) return TERMINOLOGY_ID;
        if(id == 1011) return TERMINOLOGY_ID;
        if(id == 1012) return TERMINOLOGY_ID;
        if(id == 1013) return MATCH;
        return 0;
    }

    public int obtemTamanhoLista(int lista) {
        if(lista == 1004){
            return 5;
        }
        return 0;
    }

    public int obtemRaiz() {
        return 1000;
    }

    public String formataString(String string){
        if(string == null){
            return "null";
        }else if(string.equals("")){
            return "''";
        }else{
            return "'" + string + "'";
        }
    }





































    public boolean obtemLogico(int id, int campo) {
        return false;
    }

    public int adicionaIsoOid(String valor) {
        return 0;
    }

    public int adicionaLocatableRef(String namespace, String type, String path, int object_id, int uid_based_id) {
        return 0;
    }

    public int adicionaObjectRef(String namespace, String type, int object_id) {
        return 0;
    }

    public int adicionaPartyRef(String namespace, String type, int object_id) {
        return 0;
    }

    public int adicionaAccessGroupRef(String namespace, String type, int object_id) {
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

    public int adicionaObjectVersionId(String valor) {
        return 0;
    }

    public int adicionaTemplateId(String valor) {
        return 0;
    }

    public int adicionaTerminologyId(String valor) {
        return 0;
    }

    public int adicionaVersionTreeId(String valor) {
        return 0;
    }

    public int adicionaArchetypeId(String valor) {
        return 0;
    }

    public int adicionaGenericId(String valor, String scheme) {
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

    public int adicionaMatch(String valor) {
        return 0;
    }

    public void adicionaALista(int idLista, int chave) {

    }

    public int adicionaDvCodedText(String valor, int listaTermMappins, String formatting, int hyperlink, int language, int encoding, int definingCode) {
        return 0;
    }

    public int adicionaDvText(String value, int idListaTermMapping, String formatting, int idDvURI, int idLanguage, int idEncoding) {
        return 0;
    }

    public int adicionaTermMapping(int target, int match, int purpose) {
        return 0;
    }

    public int adicionaDvURI(String valor) {
        return 0;
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

    public String obtemTexto(int id, int campo) {
        return null;
    }

    public byte[] obtemVetorBytes(int id, int campo) {
        return new byte[0];
    }

    public int obtemTamanhoVetorBytes(int id, int campo) {
        return 0;
    }

    public InputStream obtemStreamVetorBytes(int id, int campo) {
        return null;
    }

    public int obtemQtdeBytes(int id, int campo) {
        return 0;
    }

    public byte[] obtemBytes(int id, int campo, int ini, int fim) {
        return new byte[0];
    }

    public int obtemTipo(int id, int campo) {
        return 0;
    }

    public byte obtemByte(int id, int campo) {
        return 0;
    }


    public byte[] obtemBytes(int id, int campo) {
        return new byte[0];
    }

    public void defineRaiz(int raiz) {

    }

    public int totalObjetos() {
        return 0;
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

    public int adicionaDvBoolean(boolean valor) {
        return 0;
    }

    public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
        return 0;
    }

    public int adicionaDvUri(String uri) {
        return 0;
    }

    public int adicionaDvEhrUri(String uri) {
        return 0;
    }


    public byte[] toBytes() {
        return new byte[0];
    }

    public void toBytes(OutputStream destino) {

    }

    public void fromBytes(byte[] bytes) {

    }

    public void fromBytes(InputStream entrada) {

    }

    public String toXml() {
        return null;
    }

    public void toXml(OutputStream stream) {

    }

    public void fromXml(String xml) {

    }

    public void fromXml(InputStream stream) {

    }

    public void toJson(OutputStream stream) {

    }

    public void fromJson(InputStream entrada) {

    }
}