# Minipaint 
## TP3 – Le patron Adapter

### 🛼 IShape 
--- 
Pour différentes raisons, l’architecte souhaite que l’application utilise l’interface `IShape` (fournie ci-dessous) pour manipuler les formes. Il aurait pu nous prévenir avant… La classe `Shape` n’implémente pas cette interface ! 😩

🤔 Le problème, c’est qu’on ne peut pas modifier la classe `Shape` et les classes qui en héritent, comme `Ellipse`, et `Rectangle`, car elles font partie de l’API JavaFx. On pourrait bien sûr étendre toutes les sous-classes qu’on utilise (`Ellipse`, `Rectangle`, etc.) et faire en sorte que ces sous-classes héritent de l’interface. 

>**🖥 TODO**
>
> - Expliquez pourquoi ce n’est pas une bonne solution. 💩

<br>
L'interface `IShape` en question :

	import javafx.scene.layout.Pane;
	
	public interface IShape {
		boolean isSelected();
    	void setSelected(boolean selected);
    	boolean isOn(double x, double y);
    	void offset(double x, double y);
    	void addShapeToPane(Pane pane);
    	void removeShapeFromPane(Pane pane);
	}

<br> 

Une meilleure solution consiste à utiliser le patron *Adapter*. 

Documentez-vous sur ce patron. Vous pouvez notamment regarder la [vidéo de Christopher Okhravi](https://youtu.be/2PKQtcJjYvc) 📺

Si vous avez bien suivi :

-	l’interface cible est donc l’interface `IShape` ; 
-	la classe à adapter est la classe `Shape` ; 
-	et l’adapteur reste donc à créer. 

>**🖥 TODO**
>
> - Commencez par importer (ou créer) l’interface `IShape`. 
> - Créez une classe `ShapeAdapter`, qui sera notre adapteur, et qui implémente donc l’interface `IShape`. Elle contiendra pour l’instant un attribut de type `Shape` (la forme réellement dessinée, qui est la classe à adapter). Cet attribut sera fourni au moment de l’instanciation, en paramètre du constructeur. Pour l’instant, nous allons laisser vides le corps des méthodes définies par l’interface `IShape`.

<br> 

Ensuite, dans toute l’application, il faut utiliser l’interface `IShape` plutôt que la classe `Shape`. 

>**🖥 TODO**
>
> - Dans la classe `DrawingPane` :
> 
>  -	 la classe doit plutôt implémenter l’interface `Iterable<IShape>` ; 
>  - l’attribut `shapes` doit être de type `List<IShape>` ; 
>  - et la méthode `addShape` doit prendre un objet de type `IShape` en paramètre. La ligne 
>
			this.getChildren().add(shape);

>  		sera remplacée par un appel à la méthode `addShapeToPane()` de l’interface `IShape`. Le corps de cette méthode (dans la classe `ShapeAdapter`) contiendra la ligne supprimée, moyennant quelques ajustements pour que ce soit la forme réelle (adaptée) qui soit ajoutée à la liste des « `children` » du `DrawingPane`. 
> 
> - Du coup, dans la classe `ShapeButtonHandler`, la méthode `createShape` doit elle aussi retourner un objet de type `IShape`. Modifiez les classes qui implémentent cette méthode pour qu’elles retournent une nouvelle instance de `ShapeAdapter`, contenant une référence à la forme réelle (Rectangle, Ellipse, ou autre). 
> - Procédez de même dans les autres classes. Problème : l’interface `IShape` ne dispose pas des méthodes `getBoundsInParent()`, `getTranslateX()`, `getTranslateY()`, `setTranslateX()`, `setTranslateY()`, auxquelles on fait appel dans la classe `MouseMoveHandler`. C’est normal, ces méthodes étaient spécifiques à la classe `Shape` de JavaFx. Il faut donc les remplacer, en utilisant les méthodes disponibles dans l’interface `IShape`. Profitez-en pour implémenter le corps de ces méthodes dans la classe `ShapeAdapter` :
>  - La méthode `offset(double x, double y)`, qui doit décaler la `Shape` de `x` en abscisses et `y` en ordonnées.
>  - La méthode `isOn(double x, double y)`, qui doit retourner `true` si le point dont les coordonnées passées en paramètre se trouve sur la forme réelle (ce qui pourra être vérifié en appelant la méthode `getBoundsInParent().contains(x,y)`  sur la forme réelle). 
> 
> On va ajouter un retour visuel pour voir quelle forme est déplacée. Pour cela, il suffit de définir une nouvelle classe CSS et de l’ajouter ou de la retirer à la forme réelle dans la méthode `setSelected` de la classe `ShapeAdapter`. Ensuite, il faut appeler la méthode `setSelected` depuis le bon endroit dans le `MouseMoveHandler`. Attention, il faut aussi penser à « dé-sélectionner » les formes au bon moment…
 
NB : la classe CSS peut être définie ainsi (mais vous êtes libre de choisir un autre style…) :

	.selected{ -fx-effect:dropshadow(gaussian,cornflowerblue,8,0.7,0,0);  }

<br> 

### 🖇 Sélection et déplacement multiple
--- 
La classe `MouseMoveHandler` permet de déplacer une forme sur laquelle on a cliqué. Cette classe implémente le patron *Observer* pour « écouter » les événements `MousePressed`, `MouseDragged` et `MouseReleased` déclenchés au niveau du `DrawingPane`. 

Nous allons nous inspirer de cette classe pour pouvoir sélectionner une ou plusieurs formes (et par la suite leur appliquer différentes actions).
 
>**🖥 TODO**
>
> - Commencez par créer un diagramme de séquence permettant de comprendre le fonctionnement de la méthode `handle(MouseEvent)` de la classe `MouseMoveHandler`.
> - Créez la classe `SelectionHandler`, sur le modèle de la classe `MouseMoveHandler`. Dans cette nouvelle classe, on ne déplacera pas les formes. On ne fera que les sélectionner ou les désélectionner. 
> - Pensez à créer une instance de la classe `SelectionHandler` dans le `DrawingPane`.
 
<br> 

Pour l’instant, une seule forme peut être sélectionnée. Nous allons maintenant modifier la classe `SelectionHandler` pour pouvoir sélectionner plusieurs formes en même temps, sur le modèle de ce qui se passe dans beaucoup d’applications. 

>**🖥 TODO**
>
> - Modifiez le type de l’attribut `selectedShape` pour qu’il puisse stocker une liste de `IShape` plutôt qu’une seule forme.
> - Implémentez le remplissage et le vidage de la liste. Le principe de fonctionnement est le suivant : lorsqu’un clic sur une forme est détecté : si la touche MAJ est enfoncée, on ajoute cette forme à la liste de sélection (ou on la retire de la sélection si elle y était déjà). Sinon, on vide la liste et on y met seulement la forme sélectionnée. Si on clique ailleurs que sur une forme, la sélection est vidée. 
 
<br> 


Maintenant, on peut sélectionner une ou plusieurs formes. Du coup, il serait intéressant de modifier la classe `MouseMoveHandler`, pour qu’elle puisse déplacer d’un seul coup toutes les formes sélectionnées. 

>**🖥 TODO**
>
> - Commencez par créer la méthode `getSelection()`, dans la classe `DrawingPane`, qui retourne la liste des formes sélectionnées par le `SelectionHandler`. Réfléchissez au type de retour de cette méthode.
> - Dans la classe `MouseMoveHandler`, remplacez l’usage de l’attribut `selectedShape`. La classe `MouseMoveHandler` n’a plus besoin de « détecter » la forme qui a été cliquée (c’est le `SelectionHandler` qui s’en charge). À la place, utilisez la méthode `getSelection()` créée précédemment pour récupérer toutes les formes sélectionnées et les déplacer.
  
<br> 



À présent, on peut déplacer plusieurs formes en même temps. Bravo ! 🕺🏻


<br>

[🔙 Retour](../README.md)

