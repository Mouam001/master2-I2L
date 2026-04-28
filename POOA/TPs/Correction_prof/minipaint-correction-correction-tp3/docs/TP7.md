# Minipaint 
## TP7 – Améliorations

### 🧬 Dupliquer une forme
---
On aimerait pouvoir dupliquer facilement la ou les formes sélectionnées. 

>**🖥 TODO**
>
> - Identifiez le patron de création qui semble correspondre à ce besoin. 
> - Implémentez le code nécessaire.

<br>

### 📝 Ajouter du texte sur une forme
---
On voudrait pouvoir ajouter du texte sur une forme sélectionnée. On ne veut pas en faire un attribut de la classe `ShapeAdapter`, car en général, seulement quelques formes seront concernées : inutile de « polluer » la classe avec cela. 

Nous allons pour cela utiliser le patron *Décorateur* (lien vers la [vidéo qui en parle](https://www.youtube.com/watch?v=GCraGHx6gso&t=140s)). Ce patron permet d’ajouter de manière dynamique des informations sur certains objets. 


>**🖥 TODO**
>
> - Étudiez le fonctionnement du patron, identifiez les classes concernées dans l’application, et faites les changements nécessaires. 
> - On pourra ajouter un bouton dans la Toolbar permettant d’ajouter du texte sur la (ou les) forme(s) sélectionnée(s).

<br> 

### 🚧 Gestion des exceptions
---
>**🖥 TODO**
>
> - Si on cherche à ajouter du texte sur un groupe de formes, une exception doit être levée au niveau du décorateur. Cette exception sera capturée par la commande qui est à l’origine de l’action, et un message d’erreur doit être affiché. Si une commande provoque une exception, elle ne doit pas être ajoutée à la pile de commandes (l'historique pour le undo/redo).
> - Étendre ce mécanisme d’exception à toutes les commandes. Si une commande rencontre une erreur (par exemple si aucune forme n’est sélectionnée), alors une exception doit être levée, et la commande ne sera pas ajoutée à l’historique. 
> - Les messages d’erreur des exceptions seront affichés dans une nouvelle barre de statut.

<br>

[🔙 Retour](../README.md)