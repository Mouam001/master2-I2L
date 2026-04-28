package tpintrospection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        try {
            Vector<?> cadeaux = PapiBarbu.vaChercherLesCadeaux();
            System.out.println("Ouh le gros cadeau que voilà ! Voyons voir ce qu'il y a dedans...\n ");

            for (Object cadeau : cadeaux)
                introspect(cadeau);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void introspect(Object o) {
try{    /**
         * C'est ici qu'il faut compléter !
         **/

        System.out.println("La classe de " + o +
                " est " + o.getClass().getName());

        Class<?> c = o.getClass();

        System.out.println("les Methodes\n");
        Method[] methodes = c.getDeclaredMethods();
        for (Method m : methodes) {
            System.out.println(m.getName());
        }


       /* System.out.println("les Fields getDeclaredFields\n" );
        Field [] fields = c.getDeclaredFields();
        for (Field f : fields) {
            System.out.println(f.getName());
        }

        System.out.println("les Fields getFields\n" );
        Field [] fields1 = c.getFields();
        for (Field f : fields1) {
            System.out.println(f.getName());
        }*/

        System.out.println("getDeclaredFields() → Tous les champs déclarés DANS la classe :");
        Field[] declaredFields = c.getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println("    " + f.getName() +
                    " | Type: " + f.getType().getSimpleName() +
                    " | Modificateur: " + java.lang.reflect.Modifier.toString(f.getModifiers()));
        }

        System.out.println();
        System.out.println("getFields() → Seulement les champs PUBLICS, y compris ceux HÉRITÉS :");
        Field[] fields = c.getFields();
        for (Field f : fields) {
            System.out.println("    " + f.getName() +
                    " | Type: " + f.getType().getSimpleName());
        }
        System.out.println();

        if (declaredFields.length > 0) {
            Field champPublic = declaredFields[0];
            champPublic.setAccessible(true);
            champPublic.set(o, "nouvelle valeur");
            System.out.println("Modification du champs " + champPublic.getName());
            System.out.println(" Nouvelle valeur" + champPublic.get(0));
        } else {
            System.out.println("Aucun champs public trouvé");
        }

    } catch(Exception e){
        e.printStackTrace();
    }
}

}

