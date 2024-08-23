#### Exigences fonctionnelles
| **Identifiants des fonctionnalit&eacute;s** | **Description des fonctionnalit&eacute;s** |
| --- | --- |
| **EF - 001** | Utiliser des arguments pass&eacute; au logiciel |
| **EF - 002** | Lire un fichier JSON |
| **EF - 003** | Valider les donn&eacute;es soumises dans un fichier JSON |
| **EF - 004** | Produire des remboursements |
| **EF - 005** | V&eacute;rifier l'existence de toutes donn&eacute;es n&eacute;c&eacute;ssaires dans un fichier JSON |
| **EF - 006** | Stocker des statistiques sur les ex&eacute;cutions |
| **EF - 007** | Afficher les statistiques |
| **EF - 008** | R&eacute;initialiser les statistiques |

#### Suites et cas de tests

| **Identifiants des fonctionnalit&eacute;s** | **Identifiants des suites de tests** | **Description des suites de tests** | **Nombre de cas de tests** |
| --- | --- | --- | --- |
| **EF - 001** | **ST - 001** | Lecture d'arguments | 2 |
| **EF - 002** | **ST - 002** | Lecture d'un fichier JSON | 2 |
| **EF - 003** | **ST - 003** | Valider les donn&eacute;es soumises | 5 |
| **EF - 004** | **ST - 004** | Produire des remboursements | 2 |
| **EF - 005** | **ST - 005** | V&eacute;rifier pour des donn&eacute;es manquantes | 1 |
| **EF - 006** | **ST - 006** | &eacute;crire des statistiques | 2 |
| **EF - 007** | **ST - 007** | Affichage des statistiques | 1 |
| **EF - 008** | **ST - 008** |  R&eacute;initialiser les statistiques | 1 |

| **Identifiant des suites de tests** | **Identifiant des cas de tests** | **Description des cas de test** | **Pr&eacute;conditions** | **Sortie attendue** | **Priorit&eacute;** |
| --- | --- | --- | --- | --- | --- |
| ST-001 | CT-001 | Utiliser au moins un argument invalid | Aucune | Message d'erreur de mauvaise utilisation | Basse |
| ST-001 | CT-002 | Nombre d'argument incorrecte | Nombre d'arguments < 1 ou > 2  | Message d'erreur de mauvaise utilisation | Basse |
| ST-002 | CT-003 | Fichier Json d'entr&eacute; inexistant | Fichier Json qui n'existe pas  | Message d'erreur de mauvaise utilisation | Basse |
| ST-002 | CT-004 | Fichier Json d'entr&eacute; a un format invalide (le fichier est &eacute;crit incorrectement) | Fichiers Json d'entr&eacute; et de sortie saisis | Fichier Json sortie indique un message d'erreur, statistique r&eacute;clamation rejet&eacute; incr&eacute;ment&eacute; | Moyenne |
| ST-003 | CT-005 | Le num&eacute;ro de dossier de la r&eacute;clamation est invalide | Fichiers Json d'entr&eacute; et de sortie saisis  | Fichier Json sortie indique un message d'erreur, statistique r&eacute;clamation rejet&eacute; incr&eacute;ment&eacute; | Haute |
| ST-003 | CT-006 | Le mois de la r&eacute;clamation est invalide | Fichiers Json d'entr&eacute; et de sortie saisis  | Fichier Json sortie indique un message d'erreur, statistique r&eacute;clamation rejet&eacute; incr&eacute;ment&eacute; | Haute |
| ST-003 | CT-007 | Le montant d'une r&eacute;clamation est invalide | Fichiers Json d'entr&eacute; et de sortie saisis  | Fichier Json sortie indique un message d'erreur, statistique r&eacute;clamation rejet&eacute; incr&eacute;ment&eacute; | Haute |
| ST-003 | CT-008 | La date d'une r&eacute;clamation est invalide | Fichiers Json d'entr&eacute; et de sortie saisis  | Fichier Json sortie indique un message d'erreur, statistique r&eacute;clamation rejet&eacute; incr&eacute;ment&eacute; | Haute |
| ST-003 | CT-009 | Le num&eacute;ro de soin d'une r&eacute;clamation est invalide | Fichiers Json d'entr&eacute; et de sortie saisis  | Fichier Json sortie indique un message d'erreur, statistique r&eacute;clamation rejet&eacute; incr&eacute;ment&eacute; | Haute |
| ST-004 | CT-010 | Produire remboursement valide | Fichier Json d'entr&eacute; et de sortie saisis | Fichier Json sortie contient remboursements pour chaque demandes de la r&eacute;clamation | Haute |
| ST-004 | CT-011 | Utiliser un maximum mensuel remboursement valide | Fichier Json d'entr&eacute; et de sortie saisis dont certaines r&eacute;clamations possède un maximum mensuel | Fichier Json sortie contient remboursements pour chaque demandes de la r&eacute;clamation dont les montants peuvent atteindre un maximum mensuel (donc ne pas être rembours&eacute;) | Haute |
| ST-005 | CT-012 | Propri&eacute;t&eacute; n&eacute;c&eacute;ssaire est manquante dans le fichier JSON d'entr&eacute; | Fichiers Json d'entr&eacute; et de sortie saisis | Fichier Json sortie indique un message d'erreur, statistique r&eacute;clamation rejet&eacute; incr&eacute;ment&eacute; | Haute |
| ST-006 | CT-013 | Incr&eacute;menter les r&eacute;clmatations trait&eacute; | Fichier Json d'entr&eacute; complètement valide | Nombre de r&eacute;clamations trait&eacute;s augment&eacute; de 1. Les soins de cette r&eacute;clamations sont not&eacute;s et augment&eacute; de 1 | Basse |
| ST-007 | CT-014 | Incr&eacute;menter les r&eacute;clmatations rejet&eacute; | Fichier Json d'entr&eacute; ayant au moins une donn&eacute;e invalide ou manquante | Nombre de r&eacute;clamations rejet&eacute; augment&eacute; de 1 | Basse |
| ST-008 | CT-015 | Afficher des statistiques | Avoir "-S" comme argument du programme | Le nombre de r&eacute;clamations trait&eacute;es et rejet&eacute;s ainsi que le nombre de chaque soins trait&eacute; | Basse |
| ST-009 | CT-016 | R&eacute;initialis&eacute; les statistiques | Avoir "-SR" comme argument du programme | Les statistiques sont remisent à 0. Un message confirme la r&eacute;initialisation | Basse |