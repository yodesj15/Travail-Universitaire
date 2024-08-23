# Style du projet

## Variables et constantes

- Les noms des variables, méthodes et classes sont écrits en anglais avec des noms significatifs.
- Les variables et méthodes sont écrites en `lowerCamelCase`.
- Les constantes sont écrites en `UPPER_SNAKE_CASE`.
- Les noms de classes sont écrits en `UpperCamelCase`.

## Blocs et indentation

- L'ouverture d'un bloc est déterminée par l'ouverture d'une accolade `{`, par exemple pour un if, une méthode, etc., et se trouve à la fin de la première ligne du bloc, précédé d'un espace.
- La fermeture du bloc est indiqué par la fermeture d'une accolade `}` qui est faite sur une nouvelle ligne.
- Dans les cas comme des `if/else-if/else` et des `try/catch`, les termes `else-if/else` et `catch` peuvent être écrit sur la même ligne que l'accolade de fermeture avec un espace qui le précède.
- Un bloc enfant est écrit avec une indentation de 4 espaces depuis son bloc parent.
<h5>Exemple:</h5>

 ```
if (<comparaison>) {
	Faire quelque chose...
} else {
	Faire autre chose...
}
```
## Commentaires

Les commentaires sont écrits en anglais et sont pertinents, c'est-à-dire qu'ils aident à décrire 
ce que le code est incapable d'expliquer par lui-même.

Les classes sont précédées d'un commentaire Javadoc qui décrit la classe.

Les méthodes doivent avoir un commentaire Javadoc qui décrit la classe, les paramètres `@param`, 
le retour `@return` et les erreurs lancés `@throw`.

## Commits

Les commits dans Git sont atomiques et possèdent un message descriptif écrit en français.
