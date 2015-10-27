package org.openehr.terminology.json;

import org.openehr.rm.datatypes.text.*;
import org.openehr.rm.datatypes.uri.DvURI;
import org.openehr.rm.support.identification.TerminologyID;
import org.openehr.rm.support.terminology.OpenEHRCodeSetIdentifiers;
import org.openehr.rm.support.terminology.TerminologyService;
import org.openehr.terminology.SimpleTerminologyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Mock{
    private CodePhrase codePhraseMock; // idNodo 1000
    private DvText dvTextMock; // idNodo 1002
    private Match matchMock; // idNodo 1003
    private TermMapping termMappingMock; // idNodo 1004
    private DvCodedText dvCodedTextMock; // idNodo 1005
    private SimpleTerminologyService simpleTerminologyServiceMock; // idNodo 1006
    private DvURI dvURIMock; // idNodo 1007
    // idNodo 1008 LISTA TERM MAPPING

    public Mock(){

        try {
            simpleTerminologyServiceMock = (SimpleTerminologyService) SimpleTerminologyService.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Set<CodePhrase> en = simpleTerminologyServiceMock.terminology(TerminologyService.OPENEHR).codesForGroupName("term mapping purpose", "en");
        codePhraseMock = en.iterator().next();

        matchMock = Match.BROADER;
        dvCodedTextMock = new DvCodedText("valueMock",codePhraseMock);


        termMappingMock = new TermMapping(codePhraseMock,matchMock,dvCodedTextMock,simpleTerminologyServiceMock);

        dvURIMock = new DvURI("inf.ufg.br");

        List<TermMapping> termMappingList = new ArrayList();
        termMappingList.add(termMappingMock);
        termMappingList.add(termMappingMock);
        termMappingList.add(termMappingMock);
        termMappingList.add(termMappingMock);
        termMappingList.add(termMappingMock);


        CodePhrase next = simpleTerminologyServiceMock.codeSetForId(OpenEHRCodeSetIdentifiers.LANGUAGES).allCodes().iterator().next();
        CodePhrase charSet = simpleTerminologyServiceMock.codeSetForId(OpenEHRCodeSetIdentifiers.CHARACTER_SETS).allCodes().iterator().next();
        dvTextMock = new DvText("valueMock",termMappingList,"formattingMock",dvURIMock,next,charSet,simpleTerminologyServiceMock);

    }


    public CodePhrase getCodePhraseMock() {
        return codePhraseMock;
    }

    public DvText getDvTextMock() {
        return dvTextMock;
    }

    public Match getMatchMock() {
        return matchMock;
    }

    public TermMapping getTermMappingMock() {
        return termMappingMock;
    }

    public DvCodedText getDvCodedTextMock() {
        return dvCodedTextMock;
    }

    public SimpleTerminologyService getSimpleTerminologyServiceMock() {
        return simpleTerminologyServiceMock;
    }

    public DvURI getDvURIMock() {
        return dvURIMock;
    }
}