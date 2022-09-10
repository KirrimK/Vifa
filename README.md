# Vifa-2022

Une application pédagogique de visualisation des forces aérodynamiques sur un avion.

Le code source est disponible à [cette adresse](https://gitlab.com/KirrimK/Vifa-2022), ainsi que quelques indications.

## Comment lancer le projet

Téléchargez la dernière version packagée pour votre plateforme depuis l'onglet Releases (sur Gitlab).

Puis dézippez-la et lancez le script contenu à la racine: ```run_vifa_{os}```.

## Comment construire le projet

Construire le projet requiert:
- python >= 3.8 (avec pyinstaller, numpy, matplotlib, ivy-python installés via pip)
- OpenJDK ~17.0.1
- Maven >= 3.8.1

D'abord, installez le jar de la librairie ivy-java dans le repository local de maven avec la commande suivante
(depuis la racine du projet):

```mvn install:install-file -Dfile=./lib/ivy-java-1.2.18.jar -DgroupId=fr.dgac.ivy -DartifactId=ivy-java -Dversion=1.2.18 -Dpackaging=jar``` (linux)

```mvn install:install-file -Dfile=.\lib\ivy-java-1.2.18.jar -DgroupId=fr.dgac.ivy -DartifactId=ivy-java -Dversion=1.2.18 -Dpackaging=jar``` (windows)

Puis lancez la commande ```mvn javafx:run``` pour construire et lancer l'application. Les dépendances requises
(sauf ivy, à installer comme indiqué plus haut) seront automatiquement téléchargées.

Notez que vous ne verrez pas apparaître un avion car le programme ivyCommunications n'est pas lancé.

Vous pouvez le lancer en exécutant ```python ./lib/ivyCommunications/ivyCommunications.py``` dans un autre terminal.

Vous pouvez aussi le packager sous forme exécutable en lançant ```pyinstaller ivyCommunications.py```,
après vous être mis dans le dossier lib/ivyCommunications.
Le résultat est dans le dossier dist qui sera créé par la suite.
Vous pouvez alors redistribuer le dossier généré sans avoir besoin d'installer python sur la machine destinataire.

Pour packager l'IHM sur la même plateforme que votre machine, lancez ```mvn package```.
Le résultat est le .jar shaded dans le dossier "target".

Le .jar peut alors être redistribué sur toute machine ayant OpenJDK ~17.0.1 installé
(de la même plateforme que celle sur laquelle vous avez construit le projet),
ou bien vous pouvez joindre une copie du JDK avec.

## Auteurs :

Interface Graphique:

- BRÉVART Rémy
- COURTADON Hugo
- de CREVOISIER Victor
- DUTAU Charles

Backend:
- DRUOT Thierry
- DAUPTAIN Pascal
