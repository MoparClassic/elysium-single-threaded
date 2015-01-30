package org.moparscape.elysium.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by daniel on 19/01/2015.
 */
public class PersistenceManager {

    private static final XStream xstream = new XStream(new StaxDriver());

    static {
        setupAliases();
    }

    public static Object load(String filename) {
        try {
            InputStream is = new FileInputStream(new File(System.getProperty("elysium.conf.directory"), filename));
            if (filename.endsWith(".gz")) {
                is = new GZIPInputStream(is);
            }
            Object rv = xstream.fromXML(is);
            return rv;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static void setupAliases() {
        try {
            Properties aliases = new Properties();
            FileInputStream fis = new FileInputStream(new File(System.getProperty("elysium.conf.directory"), "aliases.xml"));
            aliases.loadFromXML(fis);
            for (Enumeration e = aliases.propertyNames(); e.hasMoreElements(); ) {
                String alias = (String) e.nextElement();
                Class c = Class.forName((String) aliases.get(alias));
                xstream.alias(alias, c);
            }
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
    }

    public static void write(String filename, Object o) {
        try {
            OutputStream os = new FileOutputStream(new File(System.getProperty("elysium.conf.directory"), filename));
            if (filename.endsWith(".gz")) {
                os = new GZIPOutputStream(os);
            }
            xstream.toXML(o, os);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
