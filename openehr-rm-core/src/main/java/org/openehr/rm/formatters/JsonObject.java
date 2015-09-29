package org.openehr.rm.formatters;

import br.inf.ufg.fabrica.mr.ModeloDeReferencia;

import java.io.InputStream;
import java.io.OutputStream;

public class JsonObject implements ModeloDeReferencia {

    public String toJSON() {
        int idRaiz = obtemRaiz();
        String jsonFinal = "";
        return jsonFinal += "{" + buildJson(idRaiz) + "}";
    }

    public int fromJSON(String json) {
        int idRaizGrafo = buildGraph(json);
        return idRaizGrafo;
    }

    public int buildGraph(JsonObject jsonObject){
        int tipo = jsonObject["globalTypeIdn"];

        if(tipo == TERMINOLOGY_ID){
            return adicionaTerminologyId(jsonObject["name"],jsonObject["value"]);
        }else if(tipo == CODE_PHRASE){
            int idTerminologyID = buildGraph(jsonObject["terminologyId"]);
            return adicionaCodePhrase(idTerminologyID,jsonObject["codeString"]);
        }else if(tipo == ITEM){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			return adicionaItem(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent);
		}else if(tipo == ITEM_LIST){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			//Lista
			int idItems = buildGraph(jsonObject["items"]);
			return adicionaItemList(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItems);
		}else if(tipo == ITEM_SINGLE){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			int idItem = buildGraph(jsonObject["item"]);
			return adicionaItemSingle(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItem);
		}else if(tipo == ITEM_STRUCTURE){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			return adicionaItemStructure(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent);
		}else if(tipo == ITEM_TABLE){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			//Lista
			int idRows = buildGraph(jsonObject["rows"]);
			return adicionaItemList(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idRows);
		}else if(tipo == ITEM_TREE){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			//Lista
			int idItems = buildGraph(jsonObject["items"]);
			return adicionaItemList(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItems);
		}else if(tipo == ELEMENT){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			int idValue = buildGraph(jsonObject["value"]);
			int idNullFlavour = buildGraph(jsonObject["nullFlavour"]);
			int idTerminologyService = buildGraph(jsonObject["terminologyService"]);
			return adicionaItemList(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idValue, idNullFlavour, idTerminologyService);
		}else if(tipo == CLUSTER){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			//Lista
			int idItems = buildGraph(jsonObject["items"]);
			return adicionaItemList(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idItems);
		}else if(tipo == FOLDER){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			//Lista
			int idFolders = buildGraph(jsonObject["folders"]);
			//Lista
			int idItems = buildGraph(jsonObject["items"]);
			return adicionaItemList(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idFolders, idItems);
		}else if(tipo == PARTY_RELATIONSHIP){
			int idUid = buildGraph(jsonObject["uid"]);
			String archetypeNodeId = jsonObject["archetypeNodeId"];
			int idName = buildGraph(jsonObject["name"]);
			int idArchetypeDetails = buildGraph(jsonObject["archetypeDetails"]);
			int idFeederAudit = buildGraph(jsonObject["feederAudit"]);
			//Lista
			int idLinks = buildGraph(jsonObject["links"]);
			int idParent = buildGraph(jsonObject["parent"]);
			int idDetails = buildGraph(jsonObject["details"]);
			int idTimeValidity = buildGraph(jsonObject["timeValidity"]);
			int idSource = buildGraph(jsonObject["source"]);
			int idTarget = buildGraph(jsonObject["target"]);
			return adicionaItemList(idUid, archetypeNodeId, idName, idArchetypeDetails, idFeederAudit, idLinks, idParent, idDetails, idTimeValidity, idSource, idTarget);
		}else if(tipo == XFOLDER){
		
		}else if(tipo == COMPOSITION){
		
		}else if(tipo == ADDRESS){
		
		}else if(tipo == PARTY){
		
		}else if(tipo == ROLE){
		
		}else if(tipo == ACTOR){
		
		}else if(tipo == PERSON){
		
		}else if(tipo == AGENT){
		
		}else if(tipo == ORGANISATION){
		
		}else if(tipo == EHR_STATUS){
		
		}else if(tipo == ACTIVITY){
		
		}else if(tipo == EVENT){
		
		}else if(tipo == INTERVAL_EVENT){
		
		}else if(tipo == POINT_EVENT){
		
		}else if(tipo == MESSAGE_CONTENT){
		
		}else if(tipo == EHR_ACCESS){
		
		}else if(tipo == PARTY_IDENTITY){
		
		}else if(tipo == CONTENT_ITEM){
		
		}else if(tipo == ADMIN_ENTRY){
		
		}else if(tipo == CARE_ENTRY){
		
		}else if(tipo == OBSERVATION){
		
		}else if(tipo == INSTRUCTION){
		
		}else if(tipo == ACTION){
		
		}else if(tipo == EVALUATION){
		
		}else if(tipo == SECTION){
		
		}
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
                template = "{ 'originalLanguage' : #originalLanguage, 'translations' : #translations, 'description' : #description, 'revisionHistory' : #revisionHistory, 'isControlled' : #isControlled }";
                template = template.replaceAll("#originalLanguage",buildJson(obtemByte(idNodoGrafo,0)));
                //template = template.replaceAll("#translations",buildJson(obtemByte(idNodoGrafo,1))); // Translations é um map(String,TranslationDetails) checar como proceder.
                template = template.replaceAll("#description",buildJson(obtemByte(idNodoGrafo,2)));
                template = template.replaceAll("#revisionHistory",buildJson(obtemByte(idNodoGrafo,3)));
                template = template.replaceAll("#isControlled",String.valueOf(obtemValorLogico(idNodoGrafo,4)));
                template = template.replaceAll("'", "\"");
                break;
            case REVISION_HISTORY_ITEM:
                template = "{ 'audits' : [#audits], 'versionId' : #versionId }";
                int idListaAudits = obtemInteiro(idNodoGrafo,0);
                int tamanhoListaAudits = obtemTamanhoLista(idListaAudits);
                String listaAudits = "";
                for(int k = 0; k < tamanhoListaAudits; k++){
                    int idObjetoLista = obtemInteiro(idListaAudits,k);
                    listaAudits = (k == tamanhoListaAudits - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#audits",listaAudits);
                template = template.replaceAll("#originalLanguage",buildJson(obtemByte(idNodoGrafo,1)));
                template = template.replaceAll("'", "\"");
                break;
            case REVISION_HISTORY:
                template = "{ 'items' : [#items]}";
                int idListaItems = obtemInteiro(idNodoGrafo,0);
                int tamanhoListaItems = obtemTamanhoLista(idListaItems);
                String listaItems = "";
                for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#audits",listaItems);
                template = template.replaceAll("'", "\"");
                break;
            case AUDITY_DETAILS:
                template = "{ 'systemId' : '#systemId', 'committer' : #committer, 'timeCommitted' : #timeCommitted, 'changeType' : #changeType, 'description' : #description }";
                template = template.replaceAll("#systemId",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#committer",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCommitted",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#changeType",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#description",String.valueOf(obtemValorLogico(idNodoGrafo,4)));
                template = template.replaceAll("'", "\"");
                break;
            case ATTESTATION:
                template = "{ 'systemId' : '#systemId', 'committer' : #committer, 'timeCommitted' : #timeCommitted, 'changeType' : #changeType," +
                        "'description' : #description, 'attestedView' : attestedView#, 'proof' : '#proof', 'items' : items#, 'reason' : reason#, 'isPending' : isPending# }";
                template = template.replaceAll("#systemId",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#committer",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#timeCommitted",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#changeType",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#description",buildJson(obtemInteiro(idNodoGrafo, 4)));

                template = template.replaceAll("#attestedView",buildJson(obtemInteiro(idNodoGrafo, 5)));
                template = template.replaceAll("#proof",obtemString(idNodoGrafo,6));
                //template = template.replaceAll("#items",buildJson(obtemInteiro(idNodoGrafo, 7))); Items é um SET(DvEHRURI) checar como proceder.
                template = template.replaceAll("#reason",buildJson(obtemInteiro(idNodoGrafo, 8)));
                template = template.replaceAll("#isPending",String.valueOf(obtemValorLogico(idNodoGrafo,9)));
                template = template.replaceAll("'", "\"");
                break;
            case LINK:
                template = "{ 'meaning' : #meaning, 'type' : #type, 'target' : #target}";
                template = template.replaceAll("#meaning",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#type",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#target",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_ID:
                template = "{ 'value' : '#value'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("'", "\"");
                break;
            case TEMPLATE_ID:
                template = "{ 'value' : '#value'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("'", "\"");
                break;
            case TERMINOLOGY_ID:
                template = "{ 'value' : '#value','name' : '#name','version' : '#version'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#name",obtemString(idNodoGrafo,1));
                template = template.replaceAll("#version",obtemString(idNodoGrafo,2));
                template = template.replaceAll("'", "\"");
                break;
            case GENERIC_ID:
                template = "{'value' : '#value','scheme' : '#scheme'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#scheme",obtemString(idNodoGrafo,1));
                template = template.replaceAll("'", "\"");
                break;
            case ARCHETYPE_ID:
                template = "{ 'value' : '#value', 'rmOriginator' : '#rmOriginator', 'rmName' : '#rmName', 'rmEntity' : '#rmEntity'," +
                        "'domainConcept' : '#domainConcept', 'conceptName' : '#conceptName', 'specialisation' : [#specialisation], 'versionID' : '#versionID', }";
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
                    listaSpecisialisation = (k == tamanhoListaSpecialisation - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }

                template = template.replaceAll("#specialisation",listaSpecisialisation);
                template = template.replaceAll("#versionID",obtemString(idNodoGrafo,7));
                template = template.replaceAll("'", "\"");
                break;
            case UID_BASED_ID:
                // Classe Abstrata
                break;
            case HIER_OBJECT_ID:
                template = "{ 'value' : '#value','root':#root,'extesion' : '#extension'}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#root",buildJson(obtemInteiro(idNodoGrafo,1)));
                template = template.replaceAll("#extesion",obtemString(idNodoGrafo,2));
                template = template.replaceAll("'", "\"");
                break;
            case OBJECT_VERSION_ID:
                template = "{ 'value' : '#value','objectID':objectID#,'versionTreeID':versionTreeID#,'creatingSystemID':creatingSystemID#}";
                template = template.replaceAll("#value",obtemString(idNodoGrafo,0));
                template = template.replaceAll("#objectID",buildJson(obtemInteiro(idNodoGrafo, 1)));
                template = template.replaceAll("#versionTreeID",buildJson(obtemInteiro(idNodoGrafo, 2)));
                template = template.replaceAll("#creatingSystemID",buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("'", "\"");
                break;
            case ORIGINALVERSION:
                template = "{'commitAudit': '#commitAudit', 'uid':'#uid', 'precedingVersionID':'#precedingVersionID', 'contribution':'#contribution', 'data':'#data', 'lifecycleState':'#lifecycleState', signature:'#signature', 'otherInputVersionUids':'#otherInputVersionUids', 'attestations':'#attestations', 'isMerged':'#isMerged'}";
                template = template.replaceAll("#commitAudit",buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#uid",buildJson(obtemInteiro(idNodoGrafo,1)));
                template = template.replaceAll("#precedingVersionID",buildJson(obtemInteiro(idNodoGrafo,2)));
                template = template.replaceAll("#contribution",buildJson(obtemInteiro(idNodoGrafo,3)));
                template = template.replaceAll("#data",buildJson(obtemInteiro(idNodoGrafo,4)));
                template = template.replaceAll("#lifecycleState",buildJson(obtemInteiro(idNodoGrafo,5)));
                template = template.replaceAll("#signature",buildJson(obtemInteiro(idNodoGrafo,6)));
                template = template.replaceAll("#otherInputVersionUids",buildJson(obtemInteiro(idNodoGrafo,7)));
                template = template.replaceAll("#otherInputVersionUids",buildJson(obtemInteiro(idNodoGrafo,7)));
                template = template.replaceAll("#attestations",buildJson(obtemInteiro(idNodoGrafo,8)));
                //template = template.replaceAll("#isMerged",obtemBoolean(idNodoGrafo,9)); //TODO CRIAR METODO obtemBoolean(idNodoGrafo, posicao);
				//Não havia o break.
				break;
			case ITEM:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6));
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_LIST:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_LIST));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				
				int idListaItems = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_SINGLE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'item':#item}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_SINGLE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#item", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_STRUCTURE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_STRUCTURE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_TABLE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'rows':[#rows]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_TABLE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				
				int idListaClusters = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaClusters = obtemTamanhoLista(idListaClusters);
				String listaClusters = "";
				for(int k = 0; k < tamanhoListaClusters; k++){
                    int idObjetoLista = obtemInteiro(idListaClusters,k);
                    listaClusters = (k == tamanhoListaClusters - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("rows", listaClusters);
				template = template.replaceAll("'", "\"");
				break;
			case ITEM_TREE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ITEM_TREE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				
				int idListaItems = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				template = template.replaceAll("'", "\"");
				break;
			case ELEMENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'value':#value, 'nullFlavour':#nullFlavour, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ELEMENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#value", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#nullFlavour", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("'", "\"");
				break;
			case CLUSTER:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(CLUSTER));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));

				int idListaItems = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				template = template.replaceAll("'", "\"");
				break;
			case FOLDER:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'folders':[#folders], 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(FOLDER));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));

				int idListaFolders = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaFolders = obtemTamanhoLista(idListaFolders);
				String listaFolders = "";
				for(int k = 0; k < tamanhoListaFolders; k++){
                    int idObjetoLista = obtemInteiro(idListaFolders,k);
                    listaFolders = (k == tamanhoListaFolders - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#folders", listaFolders);
				
				int idListaItems = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				template = template.replaceAll("'", "\"");
				break;
			case PARTY_RELATIONSHIP:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'details':#details, 'timeValidity':#timeValidity, 'source':#source, 'target':#target}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(PARTY_RELATIONSHIP));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#timeValidity", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#source", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#target", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("'", "\"");
				break;
			case XFOLDER:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'folders':[#folders], 'compositions':[#compositions]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(XFOLDER));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));

				int idListaFolders = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaFolders = obtemTamanhoLista(idListaFolders);
				String listaFolders = "";
				for(int k = 0; k < tamanhoListaFolders; k++){
                    int idObjetoLista = obtemInteiro(idListaFolders,k);
                    listaFolders = (k == tamanhoListaFolders - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#folders", listaFolders);
				
				int idListaCompositions = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaCompositions = obtemTamanhoLista(idListaCompositions);
				String listaCompositions = "";
				for(int k = 0; k < tamanhoListaCompositions; k++){
                    int idObjetoLista = obtemInteiro(idListaCompositions,k);
                    listaCompositions = (k == tamanhoListaCompositions - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#compositions", listaCompositions);
				template = template.replaceAll("'", "\"");
				break;
			case COMPOSITION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'content':[#content], 'language':#language, 'context':#context, 'composer':#composer, 'category':#category, 'territory':#territory, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(COMPOSITION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));

				int idListaContent = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContent = obtemTamanhoLista(idListaContent);
				String listaContent = "";
				for(int k = 0; k < tamanhoListaContent; k++){
                    int idObjetoLista = obtemInteiro(idListaContent,k);
                    listaContent = (k == tamanhoListaContent - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#content", listaContent);
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#context", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#composer", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#category", buildJson(obtemInteiro(idNodoGrafo,11)));
				template = template.replaceAll("#territory", buildJson(obtemInteiro(idNodoGrafo,12)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("'", "\"");
				break;
			case ADDRESS:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'details':#details}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ADDRESS));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("'", "\"");
				break;
			case PARTY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(PARTY));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("'", "\"");
				break;
			case ROLE:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'capabilities':[#capabilities], 'timeValidity':#timeValidity, 'performer':#performer}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ROLE));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaCapabilities = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaCapabilities = obtemTamanhoLista(idListaCapabilities);
				String listaCapabilities = "";
				for(int k = 0; k < tamanhoListaCapabilities; k++){
                    int idObjetoLista = obtemInteiro(idListaCapabilities,k);
                    listaCapabilities = (k == tamanhoListaCapabilities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#capabilities", listaCapabilities);
				
				template = template.replaceAll("#timeValidity", buildJson(obtemInteiro(idNodoGrafo,12)));
				template = template.replaceAll("#performer", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("'", "\"");
				break;
			case ACTOR:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'roles':[#roles], 'languages':[#languages]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ACTOR));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaRoles = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaRoles = obtemTamanhoLista(idListaRoles);
				String listaRoles = "";
				for(int k = 0; k < tamanhoListaRoles; k++){
                    int idObjetoLista = obtemInteiro(idListaRoles,k);
                    listaRoles = (k == tamanhoListaRoles - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#roles", listaRoles);
				
				int idListaLanguages = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaLanguages = obtemTamanhoLista(idListaLanguages);
				String listaLanguages = "";
				for(int k = 0; k < tamanhoListaLanguages; k++){
                    int idObjetoLista = obtemInteiro(idListaLanguages,k);
                    listaLanguages = (k == tamanhoListaLanguages - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#languages", listaLanguages);
				
				template = template.replaceAll("'", "\"");
				break;
			case PERSON:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'roles':[#roles], 'languages':[#languages]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(PERSON));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaRoles = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaRoles = obtemTamanhoLista(idListaRoles);
				String listaRoles = "";
				for(int k = 0; k < tamanhoListaRoles; k++){
                    int idObjetoLista = obtemInteiro(idListaRoles,k);
                    listaRoles = (k == tamanhoListaRoles - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#roles", listaRoles);
				
				int idListaLanguages = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaLanguages = obtemTamanhoLista(idListaLanguages);
				String listaLanguages = "";
				for(int k = 0; k < tamanhoListaLanguages; k++){
                    int idObjetoLista = obtemInteiro(idListaLanguages,k);
                    listaLanguages = (k == tamanhoListaLanguages - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#languages", listaLanguages);
				
				template = template.replaceAll("'", "\"");
				break;
			case AGENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'roles':[#roles], 'languages':[#languages]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(AGENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaRoles = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaRoles = obtemTamanhoLista(idListaRoles);
				String listaRoles = "";
				for(int k = 0; k < tamanhoListaRoles; k++){
                    int idObjetoLista = obtemInteiro(idListaRoles,k);
                    listaRoles = (k == tamanhoListaRoles - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#roles", listaRoles);
				
				int idListaLanguages = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaLanguages = obtemTamanhoLista(idListaLanguages);
				String listaLanguages = "";
				for(int k = 0; k < tamanhoListaLanguages; k++){
                    int idObjetoLista = obtemInteiro(idListaLanguages,k);
                    listaLanguages = (k == tamanhoListaLanguages - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#languages", listaLanguages);
				
				template = template.replaceAll("'", "\"");
				break;
			case ORGANISATION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'identities':[#identities], 'contacts':[#contacts], 'relationships':[#relationships], 'reverseRelationships':[#reverseRelationships], 'details':#details, 'roles':[#roles], 'languages':[#languages]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ORGANISATION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				
				int idListaIdentities = obtemInteiro(idNodoGrafo,6);
				int tamanhoListaIdentities = obtemTamanhoLista(idListaIdentities);
				String listaIdentities = "";
				for(int k = 0; k < tamanhoListaIdentities; k++){
                    int idObjetoLista = obtemInteiro(idListaIdentities,k);
                    listaIdentities = (k == tamanhoListaIdentities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#identities", listaIdentities);
				
				int idListaContacts = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaContacts = obtemTamanhoLista(idListaContacts);
				String listaContacts = "";
				for(int k = 0; k < tamanhoListaContacts; k++){
                    int idObjetoLista = obtemInteiro(idListaContacts,k);
                    listaContacts = (k == tamanhoListaContacts - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#contacts", listaContacts);
				
				int idListaRelationships = obtemInteiro(idNodoGrafo,8);
				int tamanhoListaRelationships = obtemTamanhoLista(idListaRelationships);
				String listaRelationships = "";
				for(int k = 0; k < tamanhoListaRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaRelationships,k);
                    listaRelationships = (k == tamanhoListaRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#relationships", listaRelationships);
				
				int idListaReverseRelationships = obtemInteiro(idNodoGrafo,9);
				int tamanhoListaReverseRelationships = obtemTamanhoLista(idListaReverseRelationships);
				String listaReverseRelationships = "";
				for(int k = 0; k < tamanhoListaReverseRelationships; k++){
                    int idObjetoLista = obtemInteiro(idListaReverseRelationships,k);
                    listaReverseRelationships = (k == tamanhoListaReverseRelationships - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#reverseRelationships", listaReverseRelationships);
				
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,10)));
				
				int idListaRoles = obtemInteiro(idNodoGrafo,11);
				int tamanhoListaRoles = obtemTamanhoLista(idListaRoles);
				String listaRoles = "";
				for(int k = 0; k < tamanhoListaRoles; k++){
                    int idObjetoLista = obtemInteiro(idListaRoles,k);
                    listaRoles = (k == tamanhoListaRoles - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#roles", listaRoles);
				
				int idListaLanguages = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaLanguages = obtemTamanhoLista(idListaLanguages);
				String listaLanguages = "";
				for(int k = 0; k < tamanhoListaLanguages; k++){
                    int idObjetoLista = obtemInteiro(idListaLanguages,k);
                    listaLanguages = (k == tamanhoListaLanguages - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#languages", listaLanguages);
				
				template = template.replaceAll("'", "\"");
				break;
			case EHR_STATUS:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'subject':#subject, 'isQueryable':#isQueryable, 'isModifiable':#isModifiable, 'otherDetails':#otherDetails}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(EHR_STATUS));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#isQueryable",obtemBoolean(idNodoGrafo,8));
				template = template.replaceAll("#isModifiable",obtemBoolean(idNodoGrafo,9));
				template = template.replaceAll("#otherDetails", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("'", "\"");
				break;
			case ACTIVITY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'description':#description, 'timing':#timing, 'actionArchetypeId':'#actionArchetypeId'}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ACTIVITY));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#description", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#timing", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#actionArchetypeId", obtemString(idNodoGrafo,9));
				template = template.replaceAll("'", "\"");
				break;
			case EVENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'time':#time, 'data':#data, 'state':#state}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(EVENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#state", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("'", "\"");
				break;
			case INTERVAL_EVENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'time':#time, 'data':#data, 'state':#state, 'width':#width, 'mathFunction':#mathFunction, 'sampleCount':#sampleCount, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(INTERVAL_EVENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#state", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#width", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#mathFunction", buildJson(obtemInteiro(idNodoGrafo,11)));
				template = template.replaceAll("#sampleCount", obtemInteiro(idNodoGrafo,12));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("'", "\"");
				break;
			case POINT_EVENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'time':#time, 'data':#data, 'state':#state}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(POINT_EVENT));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#state", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("'", "\"");
				break;
			case MESSAGE_CONTENT:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid' : #uid, 'archetypeNodeId' : '#archetypeNodeId', 'originalArchetypeNodeId' : #originalArchetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, " +
                        "'feederAudit' : #feederAudit, 'links' : [#links], 'parent' : #parent }";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(MESSAGE_CONTENT));
                template = template.replaceAll("#uid",buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#originalArchetypeNodeId", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#feederAudit",buildJson(obtemInteiro(idNodoGrafo, 5)));
                int idListaLinks = obtemInteiro(idNodoGrafo, 6);
                int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                String listaLinks = "";
                for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo, 7)));
                template = template.replaceAll("'", "\"");
                break;
			case EHR_ACCESS:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'settings':#settings}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(EHR_ACCESS));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#settings", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("'", "\"");
                break;
			case PARTY_IDENTITY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'details':#details}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(PARTY_IDENTITY));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#details", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("'", "\"");
                break;
			case CONTENT_ITEM:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid' : #uid, 'archetypeNodeId' : '#archetypeNodeId', 'originalArchetypeNodeId' : #originalArchetypeNodeId, 'name' : #name, 'archetypeDetails' : #archetypeDetails, " +
                        "'feederAudit' : #feederAudit, 'links' : [#links], 'parent' : #parent }";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(CONTENT_ITEM));
                template = template.replaceAll("#uid",buildJson(obtemInteiro(idNodoGrafo, 0)));
                template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo, 1));
                template = template.replaceAll("#originalArchetypeNodeId", obtemString(idNodoGrafo, 2));
                template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo, 3)));
                template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo, 4)));
                template = template.replaceAll("#feederAudit",buildJson(obtemInteiro(idNodoGrafo, 5)));
                int idListaLinks = obtemInteiro(idNodoGrafo, 6);
                int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
                String listaLinks = "";
                for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#links", listaLinks);
                template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo, 7)));
                template = template.replaceAll("'", "\"");
                break;
			case ADMIN_ENTRY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'data':#data, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ADMIN_ENTRY));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("'", "\"");
                break;
			case CARE_ENTRY:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid' : #uid, 'archetypeNodeId' : '#archetypeNodeId', 'name' : #name, 'archetypeDetails' : #archetypeDetails, 'feederAudit' : #feederAudit, 'links' : [#links], 'parent' : #parent," +
                        "'language' : #language, 'encoding' : #encoding, 'subject' : #subject, 'provider' : #provider, 'workflowId' : #workflowId, 'otherParticipations' : #otherParticipations, 'protocol':#protocol, 'guidelineId':#guidelineId,'terminologyService' : #terminologyService }";
                template = template.replaceAll("#globalTypeIdn", String.valueOf(CARE_ENTRY));
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
                template = template.replaceAll("#parent", buildJson(obtemByte(idNodoGrafo, 6)));
                template = template.replaceAll("#language", buildJson(obtemByte(idNodoGrafo, 7)));
                template = template.replaceAll("#encoding", buildJson(obtemByte(idNodoGrafo, 8)));
                template = template.replaceAll("#subject", buildJson(obtemByte(idNodoGrafo, 9)));
                template = template.replaceAll("#provider", buildJson(obtemByte(idNodoGrafo, 10)));
                template = template.replaceAll("#workflowId", buildJson(obtemByte(idNodoGrafo, 11)));
                int idListaParticipations = obtemInteiro(idNodoGrafo, 12);
                int tamanhoListaParticipations = obtemTamanhoLista(idListaParticipations);
                String listaParticipations = "";
                for(int k = 0; k < tamanhoListaParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaParticipations,k);
                    listaParticipations = (k == tamanhoListaParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
                template = template.replaceAll("#otherParticipations", listaParticipations);
                template = template.replaceAll("#protocol", buildJson(obtemByte(idNodoGrafo, 13)));
                template = template.replaceAll("#guidelineId", buildJson(obtemByte(idNodoGrafo, 14)));
                template = template.replaceAll("#terminologyService", buildJson(obtemByte(idNodoGrafo, 15)));
                template = template.replaceAll("'", "\"");
                break;
			case OBSERVATION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'protocol':#protocol, 'guidelineId':#guidelineId, 'data':#data, 'state':#state, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(OBSERVATION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#protocol", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#guidelineId", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,15)));
				template = template.replaceAll("#state", buildJson(obtemInteiro(idNodoGrafo,16)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,17)));
				template = template.replaceAll("'", "\"");
                break;
			case INSTRUCTION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'protocol':#protocol, 'guidelineId':#guidelineId, 'narrative':#narrative, 'activities':[#activities], 'expiryTime':#expiryTime, 'wfDefinition':#wfDefinition, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(INSTRUCTION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#protocol", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#guidelineId", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("#narrative", buildJson(obtemInteiro(idNodoGrafo,15)));
				
				int idListaActivities = obtemInteiro(idNodoGrafo,16);
				int tamanhoListaActivities = obtemTamanhoLista(idListaActivities);
				String listaActivities = "";
				for(int k = 0; k < tamanhoListaActivities; k++){
                    int idObjetoLista = obtemInteiro(idListaActivities,k);
                    listaActivities = (k == tamanhoListaActivities - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#activities", listaActivities);
				template = template.replaceAll("#expiryTime", buildJson(obtemInteiro(idNodoGrafo,17)));
				template = template.replaceAll("#wfDefinition", buildJson(obtemInteiro(idNodoGrafo,18)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,19)));
				template = template.replaceAll("'", "\"");
                break;
			case ACTION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'protocol':#protocol, 'guidelineId':#guidelineId, 'time':#time, 'description':#description, 'ismTransition':#ismTransition, 'instructionDetails':#instructionDetails, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(ACTION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#protocol", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#guidelineId", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("#time", buildJson(obtemInteiro(idNodoGrafo,15)));
				template = template.replaceAll("#description", buildJson(obtemInteiro(idNodoGrafo,16)));
				template = template.replaceAll("#ismTransition", buildJson(obtemInteiro(idNodoGrafo,17)));
				template = template.replaceAll("#instructionDetails", buildJson(obtemInteiro(idNodoGrafo,18)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,19)));
				template = template.replaceAll("'", "\"");
                break;
			case EVALUATION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'language':#language, 'encoding':#encoding, 'subject':#subject, 'provider':#provider, 'workflowId':#workflowId, 'otherParticipations':[#otherParticipations], 'protocol':#protocol, 'guidelineId':#guidelineId, 'data':#data, 'terminologyService':#terminologyService}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(EVALUATION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#language", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#encoding", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("#subject", buildJson(obtemInteiro(idNodoGrafo,9)));
				template = template.replaceAll("#provider", buildJson(obtemInteiro(idNodoGrafo,10)));
				template = template.replaceAll("#workflowId", buildJson(obtemInteiro(idNodoGrafo,11)));
				
				int idListaOtherParticipations = obtemInteiro(idNodoGrafo,12);
				int tamanhoListaOtherParticipations = obtemTamanhoLista(idListaOtherParticipations);
				String listaOtherParticipations = "";
				for(int k = 0; k < tamanhoListaOtherParticipations; k++){
                    int idObjetoLista = obtemInteiro(idListaOtherParticipations,k);
                    listaOtherParticipations = (k == tamanhoListaOtherParticipations - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#otherParticipations", listaOtherParticipations);
				template = template.replaceAll("#protocol", buildJson(obtemInteiro(idNodoGrafo,13)));
				template = template.replaceAll("#guidelineId", buildJson(obtemInteiro(idNodoGrafo,14)));
				template = template.replaceAll("#data", buildJson(obtemInteiro(idNodoGrafo,15)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,16)));
				template = template.replaceAll("'", "\"");
                break;
			case SECTION:
				template = "{'globalTypeIdn':'#globalTypeIdn', 'uid':#uid, 'archetypeNodeId':'#archetypeNodeId', 'name':#name, 'archetypeDetails':#archetypeDetails, 'feederAudit':#feederAudit, 'links':[#links], 'parent':#parent, 'items':[#items]}";
				template = template.replaceAll("#globalTypeIdn", String.valueOf(SECTION));
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#archetypeDetails", buildJson(obtemInteiro(idNodoGrafo,3)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,4)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,5);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,6)));
				
				int idListaItems = obtemInteiro(idNodoGrafo,7);
				int tamanhoListaItems = obtemTamanhoLista(idListaItems);
				String listaItems = "";
				for(int k = 0; k < tamanhoListaItems; k++){
                    int idObjetoLista = obtemInteiro(idListaItems,k);
                    listaItems = (k == tamanhoListaItems - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#items", listaItems);
				
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

