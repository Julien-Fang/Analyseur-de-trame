Pr�sentation du projet

L�objectif du projet est de r�aliser un visualisateur des flux de trafic r�seau. En prenant un fichier trace au format texte, notre analyseur devra prendre en mesure la couche 2 (Ethernet), couche 3 (IPv4), couche 4 (TCP) et couche 7 (HTTP). Enfin, nous devons afficher une interface graphique capable de filtrer et de sauvegarder son analyse dans un fichier.

Nous avons r�alis� ce projet dans le langage informatique Java, � l�aide de 6 classes :
* La classe Convertor :
Permet la conversion d�une chaine en binaire, d�cimaux, hexad�cimaux, ASCII gr�ce � des m�thodes statiques.
* La classe Trace :
R�cup�re un fichier de type .txt et nous utilisons sa m�thode lectureTrame() afin de g�n�rer une liste de Trame sous certaines conditions comme, l�offset est cod� sur quatre chiffres d�cimaux, les octets cod�s par deux chiffres hexad�cimaux. Chaque trame contient une liste de chaine de caract�re qui sont les octets hexad�cimaux.
* La classe Ethernet : (Couche 2)
Initialise la trame Ethernet avec l�adresse MAC source/destination et le type. Chaque trame poss�de un num�ro unique utilis� plus tard. Il instancie �galement son type s�il est un IP.
* La classe IP : (Couche 3)
Initialise l�ensemble des donn�es de la trame Internet Protocol avec plusieurs donn�es comme sa version, son identifiant, ses drapeaux (R�serv�, DF, MF), son offset et son adresse source/destination. Elle instancie le protocole TCP si elle existe sinon elle renvoie un message de protocole non trait�.
* La classe TCP : (Couche 4 + Couche 7)
Dans cette classe, nous avons initialiser les donn�es d�une trame TCP mais �galement cette d�une trame HTTP
La trame de Transmission Control Protocol s�occupe de fournir les informations, le source/destination port, le num�ro de s�quence, le num�ro d�acquittement, ses drapeaux, son checksum et ses options
Puisque cette classe g�re aussi la trame http, elle renvoie sa ligne de requ�te si elle est pr�sente. De plus, elle g�re le cas o� le protocole TCP est fragment�.


* La classe InterfaceGraphique :
Cette classe est contrairement aux autres, ex�cutable et cr�e une interface graphique permettant de visualiser en couleur les diff�rents protocoles avec des filtres.
Les diff�rents constructeurs sont l� selon le filtre utilis�. Cela enregistre l'analyse dans un fichier nomm� visualisateur dans le dossier courant.
