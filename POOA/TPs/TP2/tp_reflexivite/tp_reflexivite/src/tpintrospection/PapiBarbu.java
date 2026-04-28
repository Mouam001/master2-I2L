package tpintrospection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Vector;

public class PapiBarbu {

    static Vector vaChercherLesCadeaux() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        /**
         * PAS BESOIN DE REGARDER ICI,
         * VUE QUE C'EST LE PAPI BARBU QUI S'OCCUPE D'ALLER CHERCHER LES CADEAUX...
         ***/

        Class cls = Class.forName("tpintrospection.Magasin");

        Method main = cls.getMethod("getCadeau", null);
        Object cadeau = main.invoke(null, null);

        return (Vector)cadeau;
    }

}
