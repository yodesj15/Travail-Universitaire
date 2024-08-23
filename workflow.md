# Guide de Workflow Git

Ce document décrit les pratiques recommandées pour travailler avec Git dans notre projet.

## Modèle de Workflow

Nous utilisons le modèle Gitflow pour organiser notre développement. Voici un aperçu rapide :

- `master`: Branche principale, toujours prête pour la production.
- `dev`: Branche de développement où les fonctionnalités sont intégrées.
- Branches `dev` personnels à chaque membre: Chaque nouvelle fonctionnalité est développée dans la branche personnels des programmeurs.
- Branches de fonctionnalités (`nom-de-la-fonctionnalite`): Chaque nouvelle fonctionnalité est développée dans sa propre branche de fonctionnalité.

## Processus de Contribution

1. Créez une branche personnel à partir de `dev`.
2. Travaillez sur votre fonctionnalité dans cette branche.
3. Une fois terminé, ouvrez une Pull Request (PR) vers `dev`.
4. Votre PR sera révisée par un pair avant d'être fusionnée.
5. Une fois approuvée, votre PR sera fusionnée dans `dev`.


## Conventions de Nom de Branche

- Branche personnel: `dev` + Nom du programmeur 
- Branche de fonctionnalité: `nom-de-la-fonctionnalite`

## Revue de Code

Toutes les modifications doivent être examinées avant d'être fusionnées. Assurez-vous de demander une révision dès que votre travail est terminé.

