package br.ufg.inf.fabrica.persistencia.sqlite;

import br.ufg.inf.fabrica.persistencia.DataValueRepository;
import org.openehr.rm.datatypes.basic.DataValue;

import java.util.Map;

public class DataValueRepositorySqlite implements DataValueRepository {
    public void save(String key, DataValue objeto) {

    }

    public DataValue get(String key) {
        return null;
    }

    public Map<String, DataValue> search(Map<String, String> criterios) {
        return null;
    }
}
