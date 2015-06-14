package br.ufg.inf.fabrica.persistencia.ram;

import org.openehr.rm.datatypes.uri.DvEHRURI;
import org.openehr.rm.datatypes.uri.DvURI;

/**
 * Registro que armazena DvURI ou DvEHRURI.
 * 
 * Ao obter uma instância do registro é 
 * necessário conhecer o tipo armazenado e
 * empregar o método apropriado, ou seja,
 * {@code #to} ou {@code #toEHRURI}.
 */
public class RegistroDvURI {

    private String value;

    public void from(DvURI objeto) {
        this.value = objeto.getValue();
    }

    public DvURI to() {
        return new DvURI(value);
    }
    
    public DvURI toEHRURI() {
        return new DvEHRURI(value);
    }
}
