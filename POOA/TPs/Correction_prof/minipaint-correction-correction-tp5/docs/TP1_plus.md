# Minipaint
## TP1 – Découverte du projet

### 🎮 Tu me montres ta collection ?
---
_Alors, vous avez trouvé ?_

En fait, une bonne pratique consiste à s’inspirer du patron _Iterator_. Nous n’allons pas implémenter un itérateur (quoi que cela soit possible), mais simplement utiliser l’itérateur existant dans l’API java (sous la classe du même nom : `java.lang.Iterator`).


>**🖥 TODO** 
>
> - Documentez-vous sur le patron *Iterator*. Vous pouvez aussi lire l’article [https://blog.zenika.com/2010/10/31/au-coeur-du-jdk-l-interface-iterable/](https://blog.zenika.com/2010/10/31/au-coeur-du-jdk-l-interface-iterable/)
> - Faites en sorte que la classe `DrawingPane` implémente l’interface `java.lang.Iterable`
> - Supprimez la méthode `getShapes()` et remplacez ses appels (dans l’application, et dans les tests) pour qu’ils utilisent plutôt la nouvelle méthode.
  

<br>

💡 _**À retenir**_ : lorsque vous encapsulez une collection au sein d’une classe, pensez à l’interface Iterable ; votre code sera mieux encapsulé et plus facile à utiliser ! 

<br>

[🔙 Retour](./TP1.md)