Mini-projet Convertisseur JSON/CSV
Participants:
LEDEME Florian, 21602036
BAZIN Sarah, 21600869
DE JESUS Mathieu, 21600387

************MANUEL UTILISATEUR*************
Ce convertisseur permet de convertir un fichier .json en un fichier .cvs ou bien un fichier .csv en un fichier .json.

**Fichier de configuration: quelques indications**
- Un fichier de configuration permet d'apporter diverses modifications lors de la conversion. C'est un fichier texte pouvant être édité manuellement et
contenant des opérations sur les attributs présents dans le fichier à convertir. Par exemple, on peut écrire "attr1 = attr1 * 2 + 1". Le résultat dans le
fichier convertit contiendra "attr1" auquel on a multiplié 2 et ajouté 1.
- Il est possible de modifier un fichier sans le convertir avec fichier de configuration.
- Attention: si vous ajoutez des calculs dans le fichier de configuration, ceux-ci se font de manière linéaire. Il n'y a pas de priorité entre opérateurs.
- Les attributs à gauche du '=' seront présents après conversion.
- Toute ligne du fichier de configuration fausse sera ignorée pour la conversion (exemple: attr1 = attr1 attr1 +).
- Les opérateurs disponibles dans le fichier de configuration sont: '-','+','*','/','|' et '&'.
- Plusieurs lignes peuvent traiter un même attribut dans le fichier de configuration.
- Un attribut ne doit pas avoir de nom possédant un opérateur ci-dessus.
- Seuls les nombres sont autorisés pour les opérateurs '-','+','*','/'.
- L'opérateur '&' permet d'ajouter une valeur à un tableau de valeurs d'un attribut. ([2,3,5,aaa] & jambon donne [2,3,5,aaa,jambon]).
- '|' est un opérateur de concaténation. Par exemple: 4|aza donne 4aza.


**Mode d'emploi:**
- 1)Pour exécuter le convertisseur, il suffit de taper la commande "java -jar Converter_json-csv.jar" dans un terminal.
- 2)Sélectionner en premier le fichier csv ou json à traiter en entrant son nom.
- 3)Vous pouvez modifier le fichier de configuration.
- 4)Entrer le nom du fichier de sortie.
- 5)Sélectionner le type de fichier de sortie (csv ou json).
- 6)Le traitement se lance !

************MANUEL TECHNIQUE*************

**Présentation des classes:
- Classe Converter: Classe de base qui initialise les éléments de base de l'application. Cette classe contient le tableau à deux dimensions qui va permettre de stocker les données d'un fichier CSV ou JSON pour effectuer
la conversion. C'est une sorte de représentation intermédiaire entre CSV et JSON. Il est possible d'initialiser le tableau selon le type de fichier, créer un objet permettant la gestion du fichier de configuration (ConfigManager, voir ci-dessous)
et la sauvegarde dans un fichier. Elle s'occupe donc de lancer les grandes parties de l'application: conversion et fichier de configuration.
- Classe Manager: Classe mère permettant de gérer la conversion de fichiers entre json, csv et inverse. Elle permet notamment la manipulation du tableau intermédiaire (récupérer élément, copie du tableau, ajout d'éléments...).
- Classe CsvManager: Sous-classe de Manager. Cette classe correspond à la partie CSV de la conversion. Elle va permettre de lire un fichier CSV et de remplir le tableau intermédiaire. Elle nous permet également d'écrire dans un nouveau fichier CSV à partir de la représentation intermédiaire
(tableau).
- Classe JsonManager: Sous-classe de Manager. Cette classe correspond à la partie JSON de la conversion. Elle va permettre de lire un fichier JSON et de remplir le tableau intermédiaire et nous permet également d'écrire dans un fichier JSON à partir du tableau intermédiaire (même principe que CsvManager mais
sur du JSON.
- Classe ConfigManager: Sous-classe de Manager. Cette classe se focalise sur la partie fichier de configuration lors de la conversion. Elle va permettre par exemple la création du fichier de configuration par défaut, la lecture d'un fichier de configuration (avec l'évaluation des expressions) ainsi que la mise à jour des données dans notre
tableau intermédiaire.

**Présentation des exceptions créées:
- FileFormatException: Exception lancée lorsque l'on essaie d'ouvrir un fichier non valide (ni json ni csv)
- ConfigFileException: Exception lancée dans la lecture du fichier de configuration lorsque l'on obtient pas deux parties distinctes (attribut et le calcul/modification). Exemple: "att1 = att1 * 2" doit contenir deux parties: "att1" et " att1 * 2".
- CsvException: Exception lancée lors de la construction du tableau intermédiaire si le nombre d'attributs lus n'est pas égal à la largeur du tableau. 

**Présentation des énumérations:
- TypeFile: énumération simple des types de fichiers possibles: json ou csv.
- App: Lancement de l'application, "main" du projet: interactions avec l'utilisateur pour lancer le convertisseur puis lancement.

**Exemple de déroulement d'une conversion csv -> json:
- Interaction avec l'utilisateur (méthode "interact()"): saisie du fichier d'entrée, sortie, type de conversion à effectuer.
- Création d'un Converter: création tableau intermédiaire, création d'un ConfigManager qui va gérer le fichier de configuration (création), création d'un CsvManager qui va gérer la conversion du fichier
csv dans le tableau intermédiaire.
- Côté CsvManager: lecture du fichier et remplissage du tableau intermédiaire.
- Côté ConfigManager: création fichier configuration par défaut "Config.cfg" en lisant le tableau intermédiaire.
A cette étape on a traduit le fichier csv dans une représentation intermédiaire (tableau). Il faut maintenant passer de la représentation intermédiaire au json.
- (Retour fonction interact (dernière étape): appel fonction "saveAs" de la classe Converter. On commence par appeler "ProcessFile()", méthode de ConfigManager, qui va traiter le tableau et
y apporter les modifications du fichier de configuration. Ici, le fichier de sortie est de type json donc on appelle "parseJsonFile()", méthode static de la classe JsonManager. 
On lit le tableau intermédiaire et on utilise une fonction qui parse ("parseJson()") puis on écrit dans un fichier json le résultat.

-> C'est globalement le même procédé pour passer d'un json à un csv: 1) json -> tableau intermédiaire; 2) Modifications tableau intermédiaire avec fichier de configuration; 3) Parser le tableau et écrire dans le fichier csv.
