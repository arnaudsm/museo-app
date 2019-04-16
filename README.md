# Museo
Projet 4A EPF Android

# Interface
2 panneaux :
- Scanner 
- Musées (déjà scannés)


# Todo
- [ ] Scanner un QRCode et visualiser une fiche desciptive du musée correspondant,
- [ ] Visualiser les photos associées à un musée,
- [ ] Ajouter des photos à la fiche d’un musée,
- [ ] Garder sur le téléphone les données des musées déjà scannés,
- [ ] Visualiser les musées déjà scannés sans avoir une connexion à Internet.
- [ ] Documentation utilisateur

# Authors
- [Arnaud de Saint Meloir](https://arnaud.at/) 
- [Ferdinand DS](https://github.com/fduchet)

# Sujet
Votre objectif est de réaliser une application mobile sur la plate-forme Android.

Cette application doit permettre à son utilisateur de :
- Scanner un QRCode et visualiser une fiche desciptive du musée correspondant,
- Visualiser les photos associées à un musée,
- Ajouter des photos à la fiche d’un musée,
- Garder sur le téléphone les données des musées déjà scannés,
- Visualiser les musées déjà scannés sans avoir une connexion à Internet.


Les données concernant les musées sont accessibles via l'API Rest suivante :

<http://vps449928.ovh.net/swagger-ui.html>

## Exemples de requêtes :

-   Fiche d'un musée : <http://vps449928.ovh.net/api/musees/5c637e3c61e55c808b31e1ae12a57fc5c4842b4b>

-   Liste des photos d'un musée : <http://vps449928.ovh.net/api/musees/5c637e3c61e55c808b31e1ae12a57fc5c4842b4b/pictures>

-   Télécharger une photos : <http://vps449928.ovh.net/api/musees/5c637e3c61e55c808b31e1ae12a57fc5c4842b4b/pictures/2.jpeg>

## Contraintes

-   Vous devez réaliser ce projet en binôme.
-   Vous devez fournir le code source de l'application et une documentation utilisateur.

## Liens utiles

-   Android Developers : [https://developer.android.com](https://developer.android.com/)
-   QRCode (bibliothèque Android): <https://github.com/dm77/barcodescanner>
-   QRCode (générateur) : <https://fr.qr-code-generator.com/>
-   Retrofit : <https://square.github.io/retrofit/>
-   Glide : <https://bumptech.github.io/glide/>
