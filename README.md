<div  align=center>
<br>
<br>
 <h3>Calcul des remboursements de demandes d'assurance maladie</h3>
 <a  href="#installation">Installation</a>
 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a  href="#compilation">Compilation</a>
 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a  href="#utilisation">Utilisation</a>
 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a  href="#format-des-fichiers">Format des fichiers</a>
 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a  href="#structure">Structure</a>
 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a  href="#statistiques">Statistiques</a>
 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a  href="#details">Détails</a>
 &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
 <a  href="#contributeurs">Contributeurs</a>
</div>

## Installation
```console
# cloner le dépôt GitLab
$ git clone https://gitlab.info.uqam.ca/dupuy.louis/equipe15-projet-inf2050-hiv2024.git

# changer le répertoire de travail pour equipe15-projet-inf2050-hiv2024
$ cd equipe15-projet-inf2050-hiv2024
```

## Compilation
Ce projet est maintenu avec l'outil de build Maven.

Afin de compiler ce projet, utilisez la commande: `mvn compile`.

Ensuite, pour générer le jar, utilisez la commande: `mvn package`.

## Utilisation
```console
NOM
    Refund - Calcule les remboursements pour les demandes d'assurance maladie.

SYNOPSIS
    java -jar Refund.jar [inputfile.json] [outputfile.json]

OPTIONS
	-S Affiche les statistiques accumulées des traitements précédents. 
	
	-SR Réinitialise les statistiques accumulées à zéro et affiche un message de confirmation. 

DESCRIPTION
    Refund est un outil logiciel conçu pour traiter les demandes d'assurance maladie et calculer les remboursements. Il nécessite Java pour fonctionner et prend deux arguments : un fichier d'entrée et un fichier de sortie, tous deux au format JSON. Le fichier d'entrée contient les données à analyser, et le fichier de sortie est l'endroit où le programme imprime ses résultats.
    

    Le programme utilise la bibliothèque Gson, qui est impérative pour son fonctionnement. Gson permet la sérialisation et la désérialisation des objets Java en JSON et vice versa, facilitant ainsi le traitement des données d'entrée et de sortie.


    Par défaut, le programme nécessite deux arguments : le chemin du fichier d'entrée et le chemin du fichier de sortie. Les options `-S` et `-SR` peuvent être utilisées pour gérer les statistiques des opérations effectuées. 


    Une seule option ne peut être selectionnée à la fois, on peut soit lancer le programme normalement avec les fichiers json en arguments, soit en utilisant l'argument `-S` ou soit en utilisant l'argument `-SR`. 

FICHIERS
    inputfile.json
        Un fichier JSON contenant les données des demandes d'assurance maladie à analyser. La structure de ce fichier doit correspondre au format attendu pour que le programme puisse traiter correctement les demandes.

    outputfile.json
        Un fichier JSON où le programme écrira les résultats de l'analyse, y compris les remboursements calculés pour les demandes d'assurance fournies dans le fichier d'entrée.

EXEMPLES
    Pour calculer les remboursements pour les demandes d'assurance maladie, cette commande lira les données des demandées à partir de 'inputfile.json', calculera les remboursements et écrira les résultats dans 'outputfile.json':

        java -jar Refund.jar inputfile.json outputfile.json

    Pour afficher les statistiques des opérations précédentes : 

		java -jar Refund.jar -S  
	
    Pour réinitialiser les statistiques à zéro :		
	
		java -jar Refund.jar -SR

AUTEUR
    Écrit par Louis Dupuy.

RAPPORT DE BUGS
    Signalez les bugs à dupuy.louis@courrier.uqam.ca.

DROITS D'AUTEUR
    Copyright (c) 2024 UQAM. Tous droits réservés.
```

## Format des fichiers
#### Exemple de fichier d'entrée
```json:
{
    "dossier": "A100323",
    "mois": "2023-01",
    "reclamations": [
        {
            "soin": 175,
            "date": "2023-01-11",
            "montant": "130.00$"
        },
        {
            "soin": 200,
            "date": "2023-01-17",
            "montant": "100,00$"
        }
    ]
}
```
Le fichier d'entré doit contenir les propriétés suivantes :
- `dossier` : Le numéro de dossier du client. Il doit être une suite de caractère débutant par une lettre majuscule de A à E suivi de 6 chiffres
- `mois` : Le mois où on eu lieu les réclamations. Le mois doit toujours suivre le format "YYYY-MM".
- `reclamations` : La propriété qui contient toute la liste des réclamations de la demande. Elle doit toujours être un tableau [].


Chacune des réclamations doivent contenir les propriétés suivantes :
- `soin` : Le numéro de soins de la réclamation. Cela doit être un nombre dans la liste suivante : 0, 100, 150, 175, 200, 300 à 399, 400, 500, 600 ou 700.
- `date` : La date quand à eu lieu le soin. Cette date doit suivre le format "YYYY-MM-DD" et doit avoir eu lieu dans le même mois qui est inscrit dans la propriété ­"mois".
- `montant` : Le montant total du soin qu'on veut réclamer. Ce montant doit être une suite de caractère sous la forme de "0.00$". Il doit avoir deux chiffre après la décimal, qui peut être un point ou une virgule, ainsi que terminer la le symbole $.

#### Exemple de fichier de sortie
```json:
{
    "dossier": "A123456",
    "mois": "2023-01",
    "remboursements": [
        {
            "soin": 100,
            "date": "2023-01-11",
            "montant": "17.50$"
        },
        {
            "soin": 355,
            "date": "2023-01-17",
            "montant": "0.00$"
        }
    ],
    "total": "17.50$"
}
```
Le fichier de sortie, si tout se déroule normalement, ressemble à ceci :
- `dossier` : Le numéro de dossier du client.
- `mois` : Le mois où on eu lieu les réclamations.
- `reclamations` : La propriété qui contient toute la liste des remboursement de la demande.
- `total` : Le montant total qui est remboursé au client.

Chacun des remboursements contiennent les propriétés suivantes :
- `soin` : Le numéro de soins de la réclamation.
- `date` : La date quand à eu lieu le soin.
- `montant` : Le montant total du soin qui est remboursé.

## Structure
Le répertoire de travail est organisé comme ceci:
```
$ tree .
.
├── equipe.md
├── output.json
├── planDeTests.md
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── inf2050
│   │   │       └── equipe15
│   │   │           └── refund
│   │   │               ├── Claim.java
│   │   │               ├── Client.java
│   │   │               ├── InputJsonFileFields.java
│   │   │               ├── Insurer.java
│   │   │               ├── InvalidJsonDataException.java
│   │   │               ├── JsonInputFilesManager.java
│   │   │               ├── Main.java
│   │   │               ├── Money.java
│   │   │               ├── Refund.java
│   │   │               ├── Statistics.java
│   │   │               └── Validation.java
│   │   └── resources
│   │       ├── contracts.json
│   │       └── inputfile.json
│   └── test
│       └── java
│           └── inf2050
│               └── equipe15
│                   └── refund
│                       ├── ClaimTest.java
│                       ├── ClientTest.java
│                       ├── InputJsonFileFieldsTest.java
│                       ├── InsurerTest.java
│                       ├── InvalidJsonDataExceptionTest.java
│                       ├── JsonInputFilesManagerTest.java
│                       ├── MockStatistics.java
│                       ├── MoneyTest.java
│                       ├── RefundTest.java
│                       ├── StatisticsTest.java
│                       └── ValidationTest.java
├── style.md
└── workflow.md

13 directories, 31 files
```

## Statistiques
À chaque exécution, le logiciel calculera et enregistrera les statistiques suivantes :

-   Nombre de réclamations valides traitées
-   Nombre de réclamations rejetées
-   Nombre de soins déclarés pour chaque type de soin

Ces statistiques seront enregistrées dans le fichier `.UQAM-INF2050-refund-stats` à la racine du projet et peuvent être affichées ou réinitialisées via des arguments spécifiques lors de l'exécution du programme.

## Details
Ce projet a été créé dans le cadre du cours INF2050 du cursus de génie logiciel de l'Université
du Québec à Montréal.

Ce code peut être utilisé afin de calculer des remboursements de demandes d'assurance maladie.

## Contributeurs
- Louis Dupuy
- Julien Danilo
- Joey Mitron-Brazeau
- Yoan Desjardins

## Droits d'Auteur
Copyright&copy; 2024 UQAM. Tous droits réservés.