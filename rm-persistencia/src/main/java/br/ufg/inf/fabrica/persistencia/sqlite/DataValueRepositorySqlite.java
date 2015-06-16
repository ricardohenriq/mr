package br.ufg.inf.fabrica.persistencia.sqlite;

import br.ufg.inf.fabrica.persistencia.DataValueRepository;
import org.openehr.rm.datatypes.basic.DataValue;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataValueRepositorySqlite implements DataValueRepository {
    public void save(String key, DataValue object) {
        try {
            Class clazz = object.getClass();
            int instanceId = saveInstance(key, clazz);
            BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
            saveAttributes(beanInfo, object, instanceId);
        } catch (Exception e) {
            System.out.print(e.getStackTrace());
        }
    }

    private void saveAttributes(BeanInfo beanInfo, DataValue object, int instanceId) throws Exception {
        for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
            String propertyName = propertyDesc.getName();
            if (isValidAttr(propertyName)) {
                String propertyType = propertyDesc.getPropertyType().getName();
                Object value = propertyDesc.getReadMethod().invoke(object);
                String sqlType = "insert into attribute_type(name, instance_id,type) values(\'" + propertyName + "\'," + instanceId + ",\'" + propertyType + "\')";
                int typeId = DBHelper.executeInsert(sqlType, "attribute_type");
                String sqlValue = "insert into attribute_value(value, type_id) values(\'" + value + "\'," + typeId + ")";
                DBHelper.executeInsert(sqlValue);
            }
        }
    }

    private int saveInstance(String key, Class clazz) {
        String sql = "insert into instances(key, type) values(\'" + key + "\',\'" + clazz.getName() + "\')";
        return DBHelper.executeInsert(sql, "instances");
    }

    public DataValue get(String key) {
        String sqlInstance = "select type,id from instances where key=\'" + key + "\' and father_id is null";
        ResultSet resultSet = DBHelper.executeSelect(sqlInstance);
        try {
            resultSet.next();
            String instanceName = resultSet.getString(1);
            int id = resultSet.getInt(2);
            Map<String, Map<String, Object>> properties = getProperties(id);
            Class clazz = Class.forName(instanceName);
            List<String> attrsNames = getAttributesNames(clazz);

            for(String attrName : attrsNames){
                Map<String, Object> attr = properties.get(attrName);

                String.class.getConstructor(new Class[]{String.class});
                // How do I find the right constructor?
            }


            System.out.println("");

        } catch (Exception e) {
            System.out.println("");
        }
        return null;
    }

    private List<String> getAttributesNames(Class clazz) {
        List<String> attrs = new ArrayList<String>();
        for (Field field : clazz.getDeclaredFields()) {
            attrs.add(field.getName());
        }
        return attrs;
    }

    private Map<String, Map<String, Object>> getProperties(int id) throws Exception {

        String selectAttributeTypes = "select type, id, name from attribute_type where instance_id =\'" + id + "\'";
        ResultSet resultSetTypes = DBHelper.executeSelect(selectAttributeTypes);
        Map<String, Map<String, Object>> properties = new HashMap<String, Map<String, Object>>();

        while (resultSetTypes.next()) {
            final String type = resultSetTypes.getString(1);
            String idType = resultSetTypes.getString(2);
            String columnName = resultSetTypes.getString(3);
            String selectValue = "select value from attribute_value where type_id= " + idType + "";
            ResultSet resultSetValues = DBHelper.executeSelect(selectValue);
            while (resultSetValues.next()) {
                final String value = resultSetValues.getString(1);
                properties.put(columnName, new HashMap<String, Object>() {{
                    put(type, value);
                }});
            }
        }
        return properties;
    }

    public Map<String, DataValue> search(Map<String, String> criterios) {
        return null;
    }

    private boolean isValidAttr(String name) {
        return !name.equals("class") && !name.equals("referenceModelName");
    }
}
