package org.openehr.rm.formatters;

import br.inf.ufg.fabrica.mr.ModeloDeReferencia;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;

public class JsonObject implements ModeloDeReferencia {

    public String toJSON() {
        int idRaiz = obtemRaiz();
        String jsonFinal = "";
        return jsonFinal += "{" + buildJson(idRaiz) + "}";
    }

    public int fromJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int idRaizGrafo = buildGraph(jsonObject);
            return idRaizGrafo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int buildGraph(JSONObject jsonObject){
        int tipo = 0;
        try {
            tipo = jsonObject.getInt("globalTypeIdn");
            if(tipo == TERMINOLOGY_ID){
                return adicionaTerminologyId(jsonObject.getString("name"),jsonObject.getString("value"));
            }else if(tipo == CODE_PHRASE){
                int idTerminologyID = buildGraph(jsonObject.getJSONObject("terminologyId"));
                return adicionaCodePhrase(idTerminologyID,jsonObject.getString("codeString"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tipo;
    }

    private String buildJson(int idNodoGrafo) {
        String out = "";
        String template = "";
        switch (obtemTipo(idNodoGrafo)) {
            case DV_TEMPORAL:
                template = "{ 'dateTime' : #dateTime, 'value' : #value }";
                template = template.replaceAll("#dateTime",buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#value",obtemString(idNodoGrafo, 1));
                template = template.replaceAll("'", "\"");
                break;
            case DV_PARSABLE:
                template = "{ 'charset' : #charset, 'language' : #language, 'value' : #value, 'formalism' : #formalism, 'terminologyService' : #terminologyService }";
                template = template.replaceAll("#charset",buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#language",buildJson(obtemByte(idNodoGrafo, 1)));
                template = template.replaceAll("#value",obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#formalism",obtemString(idNodoGrafo, 3));
                template = template.replaceAll("#terminologyService", buildJson(obtemByte(idNodoGrafo, 4)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_PERIODIC_TIME_SPECIFICATION:
                template = "{ 'value' : #value }";
                template = template.replaceAll("#value",buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("'", "\"");
                break;
            case DV_GENERAL_TIME_SPECIFICATION:
                template = "{ 'value' : #value }";
                template = template.replaceAll("#value",buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("'", "\"");
                break;
            case LOCATABLE:
                template = "{ 'uid' : #uid, 'archetypeNodeId' : #archetypeNodeId, 'originalArchetypeNodeId' : #originalArchetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, " +
                        "'feederAudit' : #feederAudit, 'links' : #links, 'parent' : #parent }";
                template = template.replaceAll("#uid",buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#originalArchetypeNodeId", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#name", buildJson(obtemByte(idNodoGrafo, 3)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemByte(idNodoGrafo, 4)));
                template = template.replaceAll("#feederAudit",buildJson(obtemByte(idNodoGrafo, 5)));
                int idListaLinks = obtemInteiro(idNodoGrafo, 6);
                int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                String listaLinks = "";
                for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemByte(idNodoGrafo, 7)));
                template = template.replaceAll("'", "\"");
                break;
            case DATA_STRUCTURE:
                // verificar se deve ser feito ou não pois possui os mesmos atributos de Locatable (também extende ela)
                break;
            case GROUP:
                template = "{ 'name' : #name }";
                template = template.replaceAll("#name",obtemString(idNodoGrafo, 0));
                int idListaConcepts = obtemInteiro(idNodoGrafo, 1);
                int tamanhoListaConcepts = obtemTamanhoLista(idListaConcepts);
                String listaConcepts = "";
                for(int k = 0; k < tamanhoListaConcepts; k++){
                    int idObjetoLista = obtemInteiro(idListaConcepts,k);
                    listaConcepts = (k == tamanhoListaConcepts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#concepts",listaConcepts);
                template = template.replaceAll("'", "\"");
                break;
            case ENTRY:
                template = "{ 'uid' : #uid, 'archetypeNodeId' : #archetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, 'feederAudit' : #feederAudit, 'links' : #links, 'parent' : #parent," +
                        "'language' : #language, 'encoding' : #encoding, 'subject' : #subject, 'provider' : #provider, 'workflowId' : #workflowId, 'otherParticipations' : #otherParticipations, 'terminologyService' : #terminologyService }";
                template = template.replaceAll("#uid",buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#name", buildJson(obtemByte(idNodoGrafo, 2)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemByte(idNodoGrafo, 3)));
                template = template.replaceAll("#feederAudit",buildJson(obtemByte(idNodoGrafo, 4)));
                idListaLinks = obtemInteiro(idNodoGrafo, 5);
                tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                listaLinks = "";
                for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemByte(idNodoGrafo, 7)));
                template = template.replaceAll("#language", buildJson(obtemByte(idNodoGrafo, 8)));
                template = template.replaceAll("#encoding", buildJson(obtemByte(idNodoGrafo, 9)));
                template = template.replaceAll("#subject", buildJson(obtemByte(idNodoGrafo, 10)));
                template = template.replaceAll("#provider", buildJson(obtemByte(idNodoGrafo, 11)));
                template = template.replaceAll("#workflowId", buildJson(obtemByte(idNodoGrafo, 12)));
                int idListaParticipations = obtemInteiro(idNodoGrafo, 13);
                int tamanhoListaParticipations = obtemTamanhoLista(idListaParticipations);
                String listaParticipations = "";
                for(int k = 0; k < tamanhoListaParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaParticipations,k);
                    listaParticipations = (k == tamanhoListaParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherParticipations", listaParticipations);
                template = template.replaceAll("#terminologyService", buildJson(obtemByte(idNodoGrafo, 14)));
                template = template.replaceAll("'", "\"");
                break;
            case CONTACT:
                template = "{ 'uid' : #uid, 'archetypeNodeId' : #archetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, " +
                        "'feederAudit' : #feederAudit, 'links' : #links, 'parent' : #parent, 'timeValidity' : #timeValidity, 'addresses' : #addresses }";
                template = template.replaceAll("#uid",buildJson(obtemByte(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#name", buildJson(obtemByte(idNodoGrafo, 2)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemByte(idNodoGrafo, 3)));
                template = template.replaceAll("#feederAudit", buildJson(obtemByte(idNodoGrafo, 4)));
                idListaLinks = obtemInteiro(idNodoGrafo, 5);
                tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                listaLinks = "";
                for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemByte(idNodoGrafo, 6)));
                //template = template.replaceAll("#timeValidity", buildJson(obtemByte(idNodoGrafo, 7))); <-- timeValidity é um DvInterval<DvDate> - verificar como proceder
                int idListaAddresses = obtemInteiro(idNodoGrafo, 8);
                int tamanhoListaAddresses = obtemTamanhoLista(idListaAddresses);
                String listaAddresses = "";
                for(int k = 0; k < tamanhoListaAddresses; k++){
                    int idObjetoLista = obtemInteiro(idListaAddresses,k);
                    listaAddresses = (k == tamanhoListaAddresses - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#addresses", listaAddresses);
                template = template.replaceAll("'", "\"");
                break;
            case AUTHORED_RESOURCE:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'originalLanguage' : #originalLanguage, 'translations' : [#translations], 'description' : #description, 'revisionHistory' : #revisionHistory, 'isControlled' : #isControlled }";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(AUTHORED_RESOURCE));
                template = template.replaceAll("#originalLanguage",buildJson(obtemByte(idNodoGrafo,0)));
                int idMapaTranslations = obtemInteiro(idNodoGrafo,1);
                int tamanhoMapaTranslations = obtemTamanhoLista(idMapaTranslations);
                String mapaTranslations = "";
                for(int k = 0; k < tamanhoMapaTranslations; k++){
                    int idObjetoMapa = obtemInteiro(idMapaTranslations,k);
                    mapaTranslations = mapaTranslations + ((k == tamanhoMapaTranslations - 1) ? "{'" + obtemString(idObjetoMapa,0) + "' : {"  + buildJson(obtemInteiro(idObjetoMapa,1)) + "}}," : "{'" + obtemString(idObjetoMapa,0) + "' : {"  + buildJson(obtemInteiro(idObjetoMapa, 1)) + "}}");
                }
                template = template.replaceAll("#author",mapaTranslations);

                template = template.replaceAll("#description",buildJson(obtemByte(idNodoGrafo,2)));
                template = template.replaceAll("#revisionHistory",buildJson(obtemByte(idNodoGrafo,3)));
                template = template.replaceAll("#isControlled",String.valueOf(obtemValorLogico(idNodoGrafo,4)));
                template = template.replaceAll("'", "\"");
                break;
            case REVISION_HISTORY_ITEM:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'audits' : [#audits], 'versionId' : #versionId }";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(REVISION_HISTORY_ITEM));
                int idListaAudits = obtemInteiro(idNodoGrafo,0);
                int tamanhoListaAudits = obtemTamanhoLista(idListaAudits);
                String listaAudits = "";
                for(int k = 0; k < tamanhoListaAudits; k++){
                    int idObjetoLista = obtemInteiro(idListaAudits,k);
                    listaAudits = (k == tamanhoListaAudits - 1) ? '{' + buildJson(idObjetoLista) + "}," : '{' + buildJson(idObjetoLista) + '}';
                }
                template = template.replaceAll("#audits",listaAudits);
                template = template.replaceAll("#originalLanguage",buildJson(obtemByte(idNodoGrafo,1)));
                template = template.replaceAll("'", "\"");
                break;
            case REVISION_HISTORY:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'items' : [#items]}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(REVISION_HISTORY));
                int idListaItems = obtemInteiro(idNodoGrafo,0);
                int tamanhoListaItems = obtemTamanhoLista(idListaItems);
                String listaItems = "";
                for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? '{' + buildJson(idObjetoLista) + "}," : '{' + buildJson(idObjetoLista) + '}';
                }
                template = template.replaceAll("#audits",listaItems);
                template = template.replaceAll("'", "\"");
                break;
            case AUDITY_DETAILS:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'systemId' : '#systemId', 'committer' : #committer, 'timeCommitted' : #timeCommitted, 'changeType' : #changeType, 'description' : #description }";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(AUDITY_DETAILS));
                template = template.replaceAll("#systemId",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#committer",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCommitted",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#changeType",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#description",String.valueOf(obtemValorLogico(idNodoGrafo,4)));
                template = template.replaceAll("'", "\"");
                break;
            case ATTESTATION:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'systemId' : '#systemId', 'committer' : #committer, 'timeCommitted' : #timeCommitted, 'changeType' : #changeType," +
                        "'description' : #description, 'attestedView' : #attestedView, 'proof' : '#proof', 'items' : [#items], 'reason' : #reason, 'isPending' : #isPending }";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(ATTESTATION));
                template = template.replaceAll("#systemId",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#committer",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCommitted",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#changeType",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#description",buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#attestedView",buildJson(obtemInteiro(idNodoGrafo, 5)));
                template = template.replaceAll("#proof",obtemString(idNodoGrafo,6));

                int idListaItens = obtemInteiro(idNodoGrafo,7);
                int tamanhoListaItens = obtemTamanhoLista(idListaItens);
                String listaItens = "";
                for(int k = 0; k < tamanhoListaItens; k++){
                    int idObjetoLista = obtemInteiro(idListaItens,k);
                    listaItens = (k == tamanhoListaItens - 1) ? '{' + buildJson(idObjetoLista) + "}," : '{' + buildJson(idObjetoLista) + '}';
                }
                template = template.replaceAll("#items",listaItens);

                template = template.replaceAll("#reason",buildJson(obtemInteiro(idNodoGrafo, 8)));
                template = template.replaceAll("#isPending",String.valueOf(obtemValorLogico(idNodoGrafo,9)));
                template = template.replaceAll("'", "\"");
                break;
            case LINK:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'meaning' : #meaning, 'type' : #type, 'target' : #target}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(LINK));
                template = template.replaceAll("#meaning",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#type",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#target",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_ID:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(OBJECT_ID));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("'", "\"");
                break;
            case TEMPLATE_ID:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(TEMPLATE_ID));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("'", "\"");
                break;
            case TERMINOLOGY_ID:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value','name' : '#name','version' : '#version'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(TERMINOLOGY_ID));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#name",obtemString(idNodoGrafo,1));
                template = template.replaceAll("#version",obtemString(idNodoGrafo,2));
                template = template.replaceAll("'", "\"");
                break;
            case GENERIC_ID:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value','scheme' : '#scheme'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(GENERIC_ID));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#scheme",obtemString(idNodoGrafo,1));
                template = template.replaceAll("'", "\"");
                break;
            case ARCHETYPE_ID:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value', 'rmOriginator' : '#rmOriginator', 'rmName' : '#rmName', 'rmEntity' : '#rmEntity'," +
                        "'domainConcept' : '#domainConcept', 'conceptName' : '#conceptName', 'specialisation' : [#specialisation], 'versionID' : '#versionID', }";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(ARCHETYPE_ID));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#rmOriginator",obtemString(idNodoGrafo,1));
                template = template.replaceAll("#rmName",obtemString(idNodoGrafo,2));
                template = template.replaceAll("#rmEntity",obtemString(idNodoGrafo,3));
                template = template.replaceAll("#domainConcept",obtemString(idNodoGrafo,4));
                template = template.replaceAll("#conceptName",obtemString(idNodoGrafo,5));

                int idListaSpecialisation = obtemInteiro(idNodoGrafo,6);
                int tamanhoListaSpecialisation = obtemTamanhoLista(idListaSpecialisation);
                String listaSpecisialisation = "";
                for(int k = 0; k < tamanhoListaSpecialisation; k++){
                    int idObjetoLista = obtemInteiro(idListaSpecialisation,k);
                    listaSpecisialisation = (k == tamanhoListaSpecialisation - 1) ? '{' + buildJson(idObjetoLista) + "}," : '{' + buildJson(idObjetoLista) + '}';
                }

                template = template.replaceAll("#specialisation",listaSpecisialisation);
                template = template.replaceAll("#versionID",obtemString(idNodoGrafo,7));
                template = template.replaceAll("'", "\"");
                break;
            case UID_BASED_ID:
                // Classe Abstrata
                break;
            case HIER_OBJECT_ID:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'classType' : '#type', 'value' : '#value','root':#root,'extesion' : '#extension'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(HIER_OBJECT_ID));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#root",buildJson(obtemInteiro(idNodoGrafo,1)));
                template = template.replaceAll("#extesion",obtemString(idNodoGrafo,2));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_VERSION_ID:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'value' : '#value','objectID': #objectID, 'versionTreeID':#versionTreeID, 'creatingSystemID': #creatingSystemID}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(OBJECT_VERSION_ID));
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#objectID",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#versionTreeID",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#creatingSystemID",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("'", "\"");
                break;
            case ISM_TRANSITION:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'currentState' : #currentState, 'transition': #transition, 'careflowStep':#careflowStep}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(ISM_TRANSITION));
                template = template.replaceAll("#currentState",buildJson(obtemInteiro(idNodoGrafo,0)));
                template = template.replaceAll("#transition",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#careflowStep",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_REF:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'id' : #id, 'namespace': '#namespace', 'type': '#type'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(OBJECT_REF));
                template = template.replaceAll("#id",buildJson(obtemInteiro(idNodoGrafo,0)));
                template = template.replaceAll("#namespace",obtemString(idNodoGrafo,1));
                template = template.replaceAll("#type",obtemString(idNodoGrafo,2));
                template = template.replaceAll("'", "\"");
                break;
            case ACCESS_GROUP_REF:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'id' : #id}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(ACCESS_GROUP_REF));
                // super classe
                template = template.replaceAll("#id",buildJson(obtemInteiro(idNodoGrafo,0)));
                // Classe não tem atributos próprios
                template = template.replaceAll("'", "\"");
                break;
            case PARTY_REF:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'id' : #id, 'type': '#type'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(PARTY_REF));
                // super classe
                template = template.replaceAll("#id",buildJson(obtemInteiro(idNodoGrafo,0)));
                template = template.replaceAll("#type",obtemString(idNodoGrafo,2));
                // Classe não tem atributos próprios
                template = template.replaceAll("'", "\"");
                break;
            case LOCATABLE_REF:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'id' : #id, 'namespace': '#namespace', 'type': '#type', 'path': '#path'}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(LOCATABLE_REF));
                // super classe
                template = template.replaceAll("#id",buildJson(obtemInteiro(idNodoGrafo,0)));
                template = template.replaceAll("#namespace",obtemString(idNodoGrafo,1));
                template = template.replaceAll("#type",obtemString(idNodoGrafo,2));

                // Classe LOCATABLE_REF
                template = template.replaceAll("#path",obtemString(idNodoGrafo,3));
                template = template.replaceAll("'", "\"");
                break;
            case TRANSLATIONDETAILS:
                template = "{ 'globalTypeIdn' : #globalTypeIdn, 'language' : #language, 'author': [#author], 'accreditation': '#accreditation', 'otherDetails': [#otherDetails]}";
                template = template.replaceAll("#globalTypeIdn",String.valueOf(TRANSLATIONDETAILS));

                template = template.replaceAll("#language",buildJson(obtemInteiro(idNodoGrafo,0)));
                template = template.replaceAll("#accreditation",obtemString(idNodoGrafo,2));

                int idMapaAuthor = obtemInteiro(idNodoGrafo,1);
                int tamanhoMapa = obtemTamanhoLista(idMapaAuthor);
                String mapa = "";
                for(int k = 0; k < tamanhoMapa; k++){
                    int idObjetoMapa = obtemInteiro(idMapaAuthor,k);
                    mapa = mapa + ((k == tamanhoMapa - 1) ? "{'" + obtemString(idObjetoMapa,0) + "' : '"  + obtemString(idObjetoMapa,1)+ "'}," : "{'" + obtemString(idObjetoMapa,0) + "' : '"  + obtemString(idObjetoMapa,1)+ "}'");
                }
                template = template.replaceAll("#author",mapa);

                idMapaAuthor = obtemInteiro(idNodoGrafo,3);
                tamanhoMapa = obtemTamanhoLista(idMapaAuthor);
                mapa = "";
                for(int k = 0; k < tamanhoMapa; k++){
                    int idObjetoMapa = obtemInteiro(idMapaAuthor,k);
                    mapa = mapa + ((k == tamanhoMapa - 1) ? "{'" + obtemString(idObjetoMapa,0) + "' : '"  + obtemString(idObjetoMapa,1)+ "'}," : "{'" + obtemString(idObjetoMapa,0) + "' : '"  + obtemString(idObjetoMapa,1)+ "}'");
                }
                template = template.replaceAll("#otherDetails",mapa);
                template = template.replaceAll("'", "\"");
                break;

        }
        out += template;
        return out;
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

    public String toJson() {
        return null;
    }

    public void toJson(OutputStream stream) {

    }

    public void fromJson(String json) {

    }

    public void fromJson(InputStream entrada) {

    }

    public String toXML() {
        return null;
    }

    public void fromXML(String xml) {

    }


    public int obtemQtdeBytes(int id, int campo) {
        return 0;
    }

    public byte[] obtemBytes(int id, int campo, int ini, int fim) {
        return new byte[0];
    }

    public byte[] obtemBytes(int id, int campo) {
        return new byte[0];
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

    public int obtemTipo(int id, int campo) {
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

    public int obtemChave(int id, int campo) {
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

    public int obtemTamanhoVetorBytes(int id, int campo) {
        return 0;
    }

    public InputStream obtemStreamVetorBytes(int id, int campo) {
        return null;
    }

    public int adicionaLista(int quantidade) {
        return 0;
    }

    public int adicionaItem(int lista, int item) {
        return 0;
    }

    public int obtemTamanhoLista(int lista) {
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

    public int adicionaVersionTreeId(String valor) {
        return 0;
    }

    public int adicionaArchetypeId(String valor) {
        return 0;
    }

    public int adicionaGenericId(String valor, String scheme) {
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

    public int adicionaHierObjectId(String raiz, String extensao) {
        return 0;
    }

    public int adicionaObjectVersionId(String valor) {
        return 0;
    }

    public int adicionaTemplateId(String valor) {
        return 0;
    }

    public int adicionaObjectVersionId(String objectId, String versionTreeId, String creatingSystemId) {
        return 0;
    }
}

