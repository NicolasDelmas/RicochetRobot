# RicochetRobot

Le ricochet-Robot est un jeu de plateau. L'objectif de ce projet était de créer une version de ce jeu pouvant être jouée sur ordinateur.



Règle issue du site https://regle.escaleajeux.fr/rasen_rg.pdf : 

But du jeu
Lors de chaque tour, le but est de gagner le jeton cible au centre. Sur le plateau, le même dessin est représenté une fois, avec le même symbole et la même couleur. C’est la cible du tour. Votre but est de trouver comment le robot de même couleur, dit robot actif, peut atteindre cette cible en le moins de coups possible. Le joueur qui trouve le plus court mouvement gagne le jeton. Celui qui gagne le plus de jetons gagne la partie !


Planification des mouvements
Au début d’un tour, les robots ne bougent que dans les cerveaux des joueurs. Chaque joueur essaye de découvrir le moyen le plus économe en mouvements pour amener le robot actif sur la cible. Les robots se déplacent horizontalement ou verticalement, sans tourner, selon les directives des joueurs, mais une fois en mouvement, ils ne peuvent plus s’arrêter jusqu’à ce qu’ils rencontrent un obstacle devant lequel ils s’arrêtent. Les obstacles sont les bords du plateau, les murs dessinés, la pièce centrale et les autres robots. Lorsqu’un robot heurte un obstacle, il peut s’arrêter ou ricocher à angle droit, à droite ou à gauche, jusqu’à ce qu’il heurte un nouvel obstacle, et ainsi de suite. Chaque mouvement d’un robot jusqu’à un obstacle compte comme un mouvement.


Déroulement d’un tour
· Un tour commence lorsqu’un joueur prend un jeton et le place face visible sur la pièce centrale.

· Chaque joueur cherche alors comment le robot actif (le robot de la même couleur que la cible) peut atteindre la cible avec le moins de mouvements possible. La plupart du temps, un ou plusieurs autres robots devront être déplacés pour libérer un chemin ou pour former obstacle ; ces mouvements comptent évidemment dans le décompte total des mouvements.

· Si le jeton à atteindre est le « Vortex cosmique » qui est de toutes les couleurs, n’importe quel robot peut l’atteindre.

· Pour atteindre la cible, le robot actif doit ricocher au moins une fois (tourner à droite ou à gauche) sur un obstacle. S’il peut atteindre la cible sans ricocher, il faut trouver une autre route.

· Dès qu’un joueur a trouvé une solution, il peut annoncer le nombre de mouvements qu’il pense être le bon. Il retourne alors le sablier. Les autres joueurs ont maintenant une minute pour trouver une solution plus économe en mouvements et annoncer un nouveau nombre. Il n’y a pas d’ordre pour annoncer, et chacun peut annoncer plusieurs fois. Les annonces successives sont généralement plus faibles, mais peuvent également être de même hauteur, voire plus élevées (par exemple quand un autre joueur a annoncé un total improbable).

· Quand un joueur a fait une annonce, il ne peut la changer pour une annonce plus forte. Un autre joueur seul peut le faire.

· Quand le sablier se termine, le joueur qui a fait la plus petite annonce joue. Il bouge les robots en comptant les mouvements à haute voix. Si le nombre de mouvements est égal ou plus petit que le nombre annoncé, il prend le jeton et le tour est terminé. S’il échoue, il remet les robots sur leur case de départ (les jetons carrés de couleur servent de témoin) et la main passe au joueur qui a l’annonce suivante la plus petite. En cas d’égalité, le joueur ayant gagné jusqu’à présent le moins de jetons prend la main. On continue ainsi jusqu’à ce qu’un joueur démontre sa solution. Si personne n’y parvient, le jeton est retourné et mélangé avec les jetons qui n’ont pas encore été tirés.

· Dès qu’un tour est terminé, un autre peut commencer. Placez les jetons carrés de couleur sous leurs robots respectifs à leurs nouveaux emplacements, tirez une nouvelle cible et placez la sur la pièce au centre du plateau, etc.


Fin du jeu
Une partie à 2 se termine dès qu’un joueur a gagné 8 jetons ; une partie à 3 se termine avec un gain de 6 jetons et une partie à 4 avec un gain de 5 jetons. Si plus de 4 joueurs participent, continuer jusqu’à ce que tous les jetons soient gagnés. Bien entendu, les joueurs peuvent s’entendre sur une autre façon de déterminer la fin de la partie.


A noter
La plupart des situations peuvent être résolues en moins de 10 mouvements, mais il arrive dans certains cas qu’il faille vingt mouvements ou plus. De telles situations sont intellectuellement intéressantes, et peuvent faire l’objet de problèmes, mais au cours d’une partie, elles peuvent engendrer la lassitude chez de nombreux joueurs. C’est pourquoi nous recommandons qu’après 4 ou 5 minutes sans annonce, un joueur soit autorisé à retourner le sablier. Si au bout d’une minute le sablier se termine sans qu’aucune annonce n’ait été produite, on mélange le jeton avec les autres et on tire un nouveau jeton.



Auteur : Alex Randolph

Graphisme : Franz Vohwinkel

© 1999 Hans im Glück Verlags-GmbH

Traduction française : François Haffner - haffner@free.fr - http://jeuxsoc.free.fr
