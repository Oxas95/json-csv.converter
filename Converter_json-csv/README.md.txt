Mini-projet Convertisseur JSON/CSV
Participants:
LEDEME Florian, 21602036
BAZIN Sarah, 21600869
DE JESUS Mathieu, 21600387

************MANUEL UTILISATEUR*************
Ce convertisseur permet de convertir un fichier .json en un fichier .cvs ou bien un fichier .csv en un fichier .json.

**Quelques indications** 
*Un fichier de configuration permet d'apporter diverses modifications lors de la conversion. C'est un fichier texte pouvant être édité manuellement et
contenant des opérations sur les attributs présents dans le fichier à convertir. Par exemple, on peut écrire "attr1 = attr1 * 2 + 1". Le résultat dans le
fichier convertit contiendra "attr1" auquel on a multiplié 2 et ajouté 1.
*Il est possible de modifier un fichier sans le convertir avec fichier de configuration.
*Attention: si vous ajoutez des calculs dans le fichier de configuration, ceux-ci se font de manière linéaire. Il n'y a pas de priorité entre opérateurs.
*Les attributs à gauche du '=' seront présents après conversion.
*Toute ligne du fichier de configuration fausse sera ignorée pour la conversion (exemple: attr1 = attr1 attr1 +).

**Mode d'emploi**
*Pour exécuter le convertisseur, il suffit de taper la commande "java -jar Converter_json-csv.jar" dans un terminal.
*Sélectionner en premier le fichier csv ou json à traiter en entrant son nom.
*Vous pouvez modifier le fichier de configuration.
*Entrer le nom du fichier de sortie.
*Sélectionner le type de fichier de sortie (csv ou json).
*Le traitement se lance !

************MANUEL TECHNIQUE*************

*Présentation des classes:
