package br.ufg.inf.fabrica.persistencia.sqlite;

import br.ufg.inf.fabrica.persistencia.DataValueRepository;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.openehr.rm.datatypes.basic.DvIdentifier;

import java.util.UUID;

/**
 * Testes com DvBoolean.
 */
public class DvIdentifierRepositoryTest extends TestCase {

    private DataValueRepository repo;

    @Override
    public void setUp() {
        // Obtém repositório

        repo = new DataValueRepositorySqlite();
    }

    public void testPersisteRecupera() {
        DvIdentifier di = new DvIdentifier("issuer", "assigner", "id", "type");

        String key = UUID.randomUUID().toString();

        repo.save(key, di);

        Assert.assertEquals(di, repo.get(key));
    }
}
