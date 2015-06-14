package br.ufg.inf.fabrica.persistencia.sqlite;

import br.ufg.inf.fabrica.persistencia.DataValueRepository;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.openehr.rm.datatypes.uri.DvEHRURI;
import org.openehr.rm.datatypes.uri.DvURI;

import java.util.UUID;

public class DataValueRepositorySqliteTest extends TestCase {

    private DataValueRepository repo;

    @Override
    public void setUp() {
        repo = new DataValueRepositorySqlite();
    }

    public void testPersisteRecupera() {
        DvURI uri = new DvURI("https://github.com");

        String key = UUID.randomUUID().toString();

        repo.save(key, uri);

        Assert.assertEquals(uri, repo.get(key));
    }

    public void testPersisteRecuperaEHRURI() {
        DvURI uri = new DvEHRURI("https://github.com");

        String key = UUID.randomUUID().toString();

        repo.save(key, uri);

        DvEHRURI recuperado = (DvEHRURI) repo.get(key);
        Assert.assertEquals(uri, recuperado);
    }

}
