package br.com.zer0framework.utils.json;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Encoder {
    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");

    StringBuilder buf;

    // Keep track of circular data-structures: before encoding a
    // JSON-Object/Hash/Map/List/Array make sure it's not contained in
    // `circ`. If it is contained, throw an exception, b/c we can't encode
    // circular structs. If it's not contained, put it in so that we can
    // recognize it next time around...
    //
    // A `Set` would be a better fit here but:
    //   * HashSet's get confused at circular Maps
    //   * TreeSet's won't work w/out a custom Comparator
    //   * I got sick of fiddling around with this crap.
    List circ;

    Encoder() {
        this.buf = new StringBuilder();
        this.circ = new java.util.LinkedList<Object>();
    }

    /**
     * override this in subclasses to allow custom encoding
     */
    boolean canEncode(Object o) {
        return true;
    }

    void encodeCustom(Object o) {
        eggsplod("unexpected object: " + o.getClass());
    }

    void encode(Object o) {
        if (null == o) {
            buf.append("null");
            return;
        }
        if (o instanceof Map) {
            encode((Map) o);
        } else if (o instanceof List) {
            encode((List) o);
        } else if (o instanceof Number) {
            encode(buf, (Number) o);
        } else if (o instanceof CharSequence) {
            encode(buf, (CharSequence) o);
        } else if (o instanceof Date) {
            encode(buf, (CharSequence) df.format(o));
        } else if (o instanceof Character) {
            encode(buf, ((Character) o).charValue());
        } else if (o instanceof Boolean) {
            encode(buf, ((Boolean) o).booleanValue());
        } else if (o.getClass().isArray()) {
            encodeArray(o);
        } else {
            if (canEncode(o)) {
                Map<String, Object> introspectedMap = null;
                try {
                    introspectedMap = introspect(o);
                } catch (Exception e) {
                    encodeCustom(o);
                }
                encode((Map) introspectedMap);
            } else {
                eggsplod(o.getClass());
            }
        }
    }
    public static Map<String, Object> introspect(Object obj) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null)
                if (!"class".equalsIgnoreCase(pd.getName())) {
                    result.put(pd.getName(), reader.invoke(obj));
                }
        }
        return result;
    }

    void eggsplod(Object o) {
        throw new RuntimeException(o.toString());
    }

    void encode(Map m) {
        checkCircular(m);
        buf.append('{');
        for (Object k : m.keySet()) {
            Object v = m.get(k);
            encode(buf, k.toString());
            buf.append(':');
            encode(v);
            buf.append(",");
        }
        buf.setCharAt(buf.length() - 1, '}');
    }

    void encode(List l) {
        checkCircular(l);
        buf.append('[');
        for (Object k : l) {
            encode(k);
            buf.append(",");
        }
        buf.setCharAt(buf.length() - 1, ']');
    }

    void encodeArray(Object arr) {
        checkCircular(arr);
        assert arr.getClass().isArray();

        buf.append('[');
        Object o = null;
        for (int i = 0; ; ++i) {
            try {
                o = java.lang.reflect.Array.get(arr, i);
                encode(o);
                buf.append(",");
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                break;
            }
        }
        buf.setCharAt(buf.length() - 1, ']');
    }

    void checkCircular(Object m) {
        if (circ.contains(m)) {
            eggsplod("circular");
        } else {
            circ.add(m);
        }
    }

    static void encode(StringBuilder buf, CharSequence s) {
        char c = 0;
        buf.append('"');
        for (int i = 0; i != s.length(); ++i) {
            c = s.charAt(i);
            if (Character.isISOControl(c)) {
                continue; // really!? just skip?
            }
            switch (c) {
                case '"':
                case '\\':
                case '\b':
                case '\f':
                case '\n':
                case '\r':
                case '\t':
                    buf.append('\\');
                    buf.append(c);
                    continue;
                default:
                    buf.append(c);
            }
        }
        buf.append('"');
    }

    static void encode(StringBuilder buf, Number n) {
        buf.append(n.toString());
    }

    static void encode(StringBuilder buf, boolean b) {
        if (b) {
            buf.append("true");
        } else {
            buf.append("false");
        }
    }

    static void encode(StringBuilder buf, int i) {
        buf.append(i);
    }

    static void encode(StringBuilder buf, long i) {
        buf.append(i);
    }

    static void encode(StringBuilder buf, float i) {
        buf.append(i);
    }

    static void encode(StringBuilder buf, double i) {
        buf.append(i);
    }

    static void encode(StringBuilder buf, byte i) {
        buf.append(i);
    }

    static void encode(StringBuilder buf, short i) {
        buf.append(i);
    }

    static void encode(StringBuilder buf, char c) {
        buf.append((int) c);
    }
}