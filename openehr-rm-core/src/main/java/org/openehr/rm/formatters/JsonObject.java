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

    public void fromJSON(String json) {

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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent'}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'items':[#items]}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'item':'#item'}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent'}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'rows':[#rows]}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'items':[#items]}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'value':'#value', 'nullFlavour':'#nullFlavour', 'terminologyService':'#terminologyService'}";
				template = template.replaceAll("#uid", buildJson(obtemInteiro(idNodoGrafo,0)));
				template = template.replaceAll("#archetypeNodeId", obtemString(idNodoGrafo,1));
				template = template.replaceAll("#name", buildJson(obtemInteiro(idNodoGrafo,2)));
				template = template.replaceAll("#feederAudit", buildJson(obtemInteiro(idNodoGrafo,3)));
				
				int idListaLinks = obtemInteiro(idNodoGrafo,4);
				int tamanhoListaLinks = obtemTamanhoLista(idListaLinks);
				String listaLinks = "";
				for(int k = 0; k < tamanhoListaLinks; k++){
                    int idObjetoLista = obtemInteiro(idListaLinks,k);
                    listaLinks = (k == tamanhoListaLinks - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
                }
				
				template = template.replaceAll("#links", listaLinks);
				template = template.replaceAll("#parent", buildJson(obtemInteiro(idNodoGrafo,5)));
				template = template.replaceAll("#value", buildJson(obtemInteiro(idNodoGrafo,6)));
				template = template.replaceAll("#nullFlavour", buildJson(obtemInteiro(idNodoGrafo,7)));
				template = template.replaceAll("#terminologyService", buildJson(obtemInteiro(idNodoGrafo,8)));
				template = template.replaceAll("'", "\"");
				break;
			case CLUSTER:
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'items':[#items]}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'folders':[#folders], 'items':[#items]}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'details':'#details', 'timeValidity':'#timeValidity', 'source':'#source', 'target':'#target'}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'folders':[#folders], 'compositions':[#compositions]}";
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'content':[#content], 'language':'#language', 'context':'#context', 'composer':'#composer', 'category':'#category', 'territory':'#territory', 'terminologyService':'#terminologyService'}";
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
                    listaFolders = (k == tamanhoListaContent - 1) ? buildJson(idObjetoLista) + "," : buildJson(idObjetoLista);
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
				template = "{'uid':'#uid', 'archetypeNodeId':'#archetypeNodeId', 'name':'#name', 'archetypeDetails':'#archetypeDetails', 'feederAudit':'#feederAudit', 'links':[#links], 'parent':'#parent', 'details':'#details'}";
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

