# Minipaint 
## TP8 – Le retour de l'Observer 

### 💥 Encore de l’observer 
---
On l’aime vraiment bien, ce patron… En fait, il est tellement utile qu’on le retrouve partout, implémenté dans chaque recoin des API. Prenons encore un exemple. 

Reprenons la fonctionnalité permettant d’ajouter du texte à une forme. Pour cela, dans le décorateur, on a ajouté un objet de type `Text` qui contient la chaine de caractères. On l’a positionné comme on a pu, et ensuite on a veillé à bien le déplacer en même temps que la forme lors des appels à la méthode offset.

C’est bien, mais on aurait pu utiliser une fonctionnalité de l’API `javafx` qui est basée sur le patron *observer* : c’est la notion de *Binding*. En gros, cela permet de lier un attribut d’un objet à une propriété d’un autre objet. C’est-à-dire que la valeur du premier attribut sera automatiquement mise à jour lorsque l’autre attribut sera modifié. 

Voyons comment utiliser cela. Tout d’abord, les propriétés qu’on veut « écouter » sont celles qui sont relatives au déplacement de la forme de base. 

>**🖥 TODO**
>
> - On va les rendre accessibles en ajoutant deux méthodes à l’interface `IShape` :
>
		public ObservableValue translateXProperty();
		public ObservableValue translateYProperty();
	
> - On va commencer par implémenter ces méthodes uniquement dans la classe `ShapeAdapter`. L’implémentation consiste simplement à retourner le résultat de l’appel à `translateXProperty()` et `translateYProperty()` sur l’objet interne de type Shape.

<br> 

Maintenant, cela veut dire qu’on peut « écouter » les propriétés `translateX` et `translateY`. 

>**🖥 TODO**
>
> - Il ne reste plus qu’à connecter les propriétés correspondantes de l’objet `Text` du décorateur sur ces propriétés (c’est le « *binding* »). Dans le décorateur, lors de l’instanciation de l’objet `Text` : appelez sur l’objet `Text` les méthodes `translateXProperty().bind()` et `translateYProperty().bind()`, en passant en paramètre les appels aux deux méthodes écrites précédemment. 
> - N’oubliez pas de supprimer l’ancien code qui permettait de déplacer l’objet de type `Text` dans la méthode `offset`. 

Admirez le résultat.🥲

#### 🍹 Amélioration
En fait, lors du « *binding* », plutôt que de se référer aux `translateX/Y`, il serait plus intéressant d’obtenir directement les coordonnées du centre de la forme, autrement dit la valeur du `translateX` (ou `translateY`) + les coordonnées du centre de la forme au moment de sa création. Ainsi, on n’aurait même plus à initialiser les coordonnés du centre de l’objet `Text` : le *binding* seul suffira.

>**🖥 TODO**
>
> - Modifiez les méthodes `translateXProperty()` et `translateYProperty()` pour y ajouter les coordonnées X et Y du centre de la forme. 
> - Renommez ces méthodes pour que leur nom reflète mieux l'information qu'elles retournent.

<br>

### 🪡 Relier des formes
---
Cette fonction très utile va beaucoup simplifier le développement d’une nouvelle fonctionnalité : relier des formes entre elles par un trait. 

>**🖥 TODO**
>
> - Commencer par écrire une nouvelle classe `Edge`, qui hérite de `IShape`, et qui possède trois attributs : `from` et `to` de type `IShape`, et `shape` de type `Line`.
> - La classe aura un constructeur à 2 paramètres de type `IShape`, qui seront les 2 formes à relier. Lors de l’instanciation, le paramètre `shape` sera créé (une nouvelle instance de `Line`). Les coordonnées du point de départ et du point d’arrivée de cette ligne seront liées (avec un « *binding* ») aux propriétés des formes `from` et `to` (grâce aux méthodes écrites précédemment).

NB : Un objet de type `Edge` peut être sélectionné, mais ne peut pas être déplacé. 

>**🖥 TODO**
>
> - Créer une *Commande* permettant de relier par un trait les 2 formes sélectionnées (on vérifiera qu’il y a bien 2 formes sélectionnées). Il faudra donc créer une nouvelle instance de `Edge`, et l’ajouter à la liste des `shapes` du `DrawingPane`. 

#### 🏆 Bonus
En implémentant les méthodes `translateXProperty()` et `translateYProperty()` de la classe `Edge`, de telle sorte qu’elles retournent le *Binding* correspondant aux coordonnées du milieu du segment, alors on peut ajouter du texte sur une ligne, ou même relier des lignes entre elles !

<br>

[🔙 Retour](../README.md)