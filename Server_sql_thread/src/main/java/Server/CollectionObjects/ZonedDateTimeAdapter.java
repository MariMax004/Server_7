package Server.CollectionObjects;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.io.Serializable;
import java.time.ZonedDateTime;

public class ZonedDateTimeAdapter extends XmlAdapter<String, ZonedDateTime> implements Serializable {

    @Override
    public ZonedDateTime unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }
        return ZonedDateTime.parse(v);
    }

    @Override
    public String marshal(ZonedDateTime v) throws Exception {
        if (v == null) {
            return null;
        }
        return v.toString();
    }
}