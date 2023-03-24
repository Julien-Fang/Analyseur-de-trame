Présentation du projet

L’objectif du projet est de réaliser un visualisateur des flux de trafic réseau. En prenant un fichier trace au format texte, notre analyseur devra prendre en mesure la couche 2 (Ethernet), couche 3 (IPv4), couche 4 (TCP) et couche 7 (HTTP). Enfin, nous devons afficher une interface graphique capable de filtrer et de sauvegarder son analyse dans un fichier.

Nous avons réalisé ce projet dans le langage informatique Java, à l’aide de 6 classes :
* La classe Convertor :
Permet la conversion d’une chaine en binaire, décimaux, hexadécimaux, ASCII grâce à des méthodes statiques.
* La classe Trace :
Récupère un fichier de type .txt et nous utilisons sa méthode lectureTrame() afin de générer une liste de Trame sous certaines conditions comme, l’offset est codé sur quatre chiffres décimaux, les octets codés par deux chiffres hexadécimaux. Chaque trame contient une liste de chaine de caractère qui sont les octets hexadécimaux.
* La classe Ethernet : (Couche 2)
Initialise la trame Ethernet avec l’adresse MAC source/destination et le type. Chaque trame possède un numéro unique utilisé plus tard. Il instancie également son type s’il est un IP.
* La classe IP : (Couche 3)
Initialise l’ensemble des données de la trame Internet Protocol avec plusieurs données comme sa version, son identifiant, ses drapeaux (Réservé, DF, MF), son offset et son adresse source/destination. Elle instancie le protocole TCP si elle existe sinon elle renvoie un message de protocole non traité.
* La classe TCP : (Couche 4 + Couche 7)
Dans cette classe, nous avons initialiser les données d’une trame TCP mais également cette d’une trame HTTP
La trame de Transmission Control Protocol s’occupe de fournir les informations, le source/destination port, le numéro de séquence, le numéro d’acquittement, ses drapeaux, son checksum et ses options
Puisque cette classe gère aussi la trame http, elle renvoie sa ligne de requête si elle est présente. De plus, elle gère le cas où le protocole TCP est fragmenté.


* La classe InterfaceGraphique :
Cette classe est contrairement aux autres, exécutable et crée une interface graphique permettant de visualiser en couleur les différents protocoles avec des filtres.
Les différents constructeurs sont là selon le filtre utilisé. Cela enregistre l'analyse dans un fichier nommé visualisateur dans le dossier courant.
