# Minipaint 
## TP5 – Groupement de formes


Même s’il est possible de déplacer plusieurs formes à la fois (grâce à la sélection multiple), il pourrait être intéressant de « grouper » plusieurs formes, pour que le groupe soit considéré comme une forme à part entière. Un groupe serait donc une forme composée de plusieurs formes, qu’il s’agisse de formes « de base » ou bien aussi de groupes.

>**🖥 TODO**
>
> - Il existe un patron de structure permettant de représenter cela. Identifiez lequel semble le mieux correspondre au problème énoncé. 
> - Ensuite, cherchez à faire correspondre les différents éléments du patron à des classes ou interfaces de notre application. 
> - Identifiez quelles classes ou interfaces il vous reste à créer, et implémentez-la.
> - Ajouter 2 boutons dans l’interface : un bouton permettant de grouper les formes sélectionnées ; et un bouton pour dégrouper la forme sélectionnée, s’il s’agit d’un groupe. 
> - Testez. 

<br>

### 🎀 Quelques améliorations
---
Vous avez sans doute remarqué qu'il reste quelques fonctionnements disgracieux : par exemple, si on souhaite créer une forme, et que le point de départ se trouve sur une forme déjà existante, celle-ci est déplacée en même temps que l'autre est créée. 

>**🖥 TODO**
>
> - Faites en sorte que le déplacement soit désactivé lorsqu'une forme est en train d'être créée.

<br>

Une autre fonction intéressante serait de pouvoir mettre une forme au premier plan (devant les autres).

>**🖥 TODO**
>
> - Implémentez cette fonctionnalité

<br>

#### 🏆 Bonus

>**🖥 TODO**
>
> - On voudrait aussi pouvoir sélectionner plusieurs formes en délimitant une "zone" de sélection avec la souris (par un clic-drag-release)



<br>


[🔙 Retour](../README.md)