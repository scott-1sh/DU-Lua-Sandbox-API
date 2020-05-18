
DU offline sandbox API (version 0.559)
  
{DU:API}
  
Ce document est un tutoriel pour l'outil de dévelopement DU offline sandbox API.  

J'ai crée cet API en 2 parties.

1 - Une api LUA  contenant (presque) toute les classes de DU. (duElementAPI) 
2 - Une api JAVA permettant d'émuler une sandbox LUA/Dual universe en temps réel. (duJavaAPI)

La sandbox permet d'émuler la quasi totalitée des éléments du jeu, des écrans utilisant le html5,  ainsi que la base de donnée interne (database.getPlayer, database.getConstruct, databse.getElement).

Il est simple et versatil.  La configuration ce fait à partir de fichier lua nommé Preload (voir la section preload de ce document).    

Le code est en open source.  Il sera accèssible via un GitHub (todo). 


 A qui s'adresse cet outil.

L'api à été concu pour travailler sur des écrans de dual universe (L'élément in game). 

Il à été conçu pour travailler en mode "projet".  La versatilitée du fichier de chargement (preload) permet de  créer des librairies de fonctions
et de faire du code réutilisable.  Les scripts sont automatiquement intégré lors de l'exports.  La sandbox à particulièrement été conçu pour concevoir des applications ou même des jeux.  
L'élément databank est déja implémenté à 100%.

Il n'est pas encore assez avancé pour être efficace au niveau des system de vol, mais il le sera probablement sur le long (si j'ai un peu d'aide). 

L'objectif à long terme est que son utilisation soit un incontournable pour la création de d'applications in Game, pour les script de drones, d'automatisation et toute sortes de projets.
 
Et possiblement, un outil pour les theorycrafters.


Installation

Décompressez le contenu du fichier DUOfflineSandbox.zip à l'emplacement de votre choix.   

Installation de JAVA

Java JDK 12 (et plus). doit être installé sur votre ordinateur pour que la librairie fonctionne.  

Vous pouvez le télécharger à l'adresse Java SE Downloads

Il arrive parfois qu'il y ai des problèmes avec le PATH de java.  Plusieurs tutoriels sont disponnible sur google pour vous aider.
 
 

Utilisation dans Eclispe LDT (mode interpreteur)

La sandbox peut être utilisé comme interpreteur LUA. 


  
     Installez Eclipse LDT.
  
  
     Démmarez Eclipse LDT.  Sélectionnez le répertoire de répertoire créé à l'étape précédente lorsque demandé.
  
  
     décompressez l'archive DUOfflineSandbox.zip dans le répertoire de travail crée à l'étape précédente.
  
  
     Créez un nouveau projet avec comme source, le répertoire de DUOfflineSandbox.
  
  
     Créez un lunch configuration pour chaques fichiers de preload. (menu Run->Run configuration) 
     
     
  
  
     accédez à la liste des interpreters. (menu Run->Run configuration->bouton Manage interpreters)
     
     
     
  
   Ajouter la sandbox dans la liste d'interpreters. (menu Run->Run configuration->bouton Manage interpreters->Add 
    
    
  



créez un lunch configuration pour chaques fichiers de preload.  Cliquez sur le tabb du fichier preload puis cliquez sur RUN (ctrl-F11) pour lancer le projet.


 




Utilisation en mode stand alone

Vous pouvez utiliser l'éditeur LUA que vous souhaitez et lancer l'application directement de votre système de fichier.

un fichier de racourci est disponible pour chaques examples de la sandbox (voir le répertoire .\DUOfflineSandbox).  

pour l'ancer la sandbox. Cliquez sur \DUOfflineSandbox\DUOfflineSandbox.jar

La boite a outil de l'application permet de relancer le script en appuyant sur "Reload (F9)" ou de charger un nouveau script par la bar de lancement. "RUN (F5)"


Mode Fenêtré dos

Ouvrez une fenêtre terminal (cmd.exe) et lancez la commande: java.exe -jar DUOfflineSandbox.jar [fichier_preload.lua]

[fichier_preload.lua] indique le fichier à charger. Par default la sandbox utilise default_load.lua.

 * Notez qu'il ne faut pas lancer directement l'application java (DUOfflineSandbox.jar) dans la fenêtre dos, 
   car vous n'aurrez pas les résultats de la console. *


ASTUCE: UTILISER AVEC DUAL UNIVERSE EN MODE FENETRÉ 

Utiliser le jeu en mode fenêtré et lorsque vous voulez travailler sur un projet, verrouillez l'écran du jeu (TAB), 
remarquez que le curseur peut alors sortir de la fenêtre de jeu.  Glisser le curseur de la souris en dehors de la fenêtre du jeu permet 
de travailler sur votre projet. tout en surveillant l'écran de jeu. Parfait pour un long voyage.  La sandbox gère l'écran d'affichage (fonction showOnScreen()).
 
 



PRELOAD

Le preload est un fichiers de configuration en LUA.  Il configure l'environnement et ajoute les éléments (door, unit, databank....)

Par standard, le fichier de preload est nomé [nomprojet]_load.lua.   Il est utilisé pour démarrer votre projet.  

Le preload charge les fichiers LUA pour les évènements (start, tick, stop...) relié aux scripts. Il sert aussi pour configurer la sandbox et rajouter des joueurs et constructs.

Toute les fonctions du LUA sont disponnible. Y compris les fonctions de lecture/ecriture du système de fichier et les fonction lua "dofile" et load. 

L'outil vous permet de conserver vos librairies réutilisable dans des fichiers distinctif qui peuvent être chargé dans le preload.
 
A la fin du preload,  seul les configurations aquise et les éléments ajoutés sont conservés.  La session LUA est remise à zéro puis la sandbox est lancée (avec l'environement du jeu).

Evidemment, les fonctions utilitaires lié au chargement du preload ne sont pas utilisable dans l'environnement de jeu.

Notez qu'il y a une librairie json disponnible dans la session du preload. (https://github.com/rxi/json.lua)    



Example de fichier preload 


showOnScreen(1) 
verboseLua(1)
verboseJava(0)

-- scripts de l'unit
UnitStart = [[ 
  screen1.activate()
  screen1.addContent(0,0,"hello world")
]]
UnitStop = "print('closing')"

-- ajout de l'unit
obj = Unit(UnitStart, UnitStop)

-- ajout d'un écran
obj = ScreenUnit('screen1', 1024, 612)
moveElement(obj, 300, 5)


 


Fonctions du preload 



 
Outils



    showOnScreen(screenId)
    Définit l'écran de démarrage de la sandbox lua (en dual screen).
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          screenId  
          [0,1]  
          numéro de l'écran physique  
        
        
    




    verboseLua(status) 
    Affiche ou non les messages du debugger lié au LUA.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          status  
          [0,1]  
          1 pour activer les messages lua
            
        
        
    





    verboseJava(status)
    Affiche ou non les messages du debugger lié au Java.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          status  
          [0,1]  
          1 pour activer les messages java
            
        
        
    




    script = loadScript(fichier)
    Affiche ou non les messages du debugger lié au Java.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          fichier  
          string  
          fichier LUA à charger.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          script  
          Le script lua chargé.  
        
         
    





    abort(msg)
    Cancel l'exécution du script et affiche un message dans un popup.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          msg  
          string  
          Message à afficher.  
        
        
    





    die(msg)
    Cancel l'exécution du script et affiche un message dans la console.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          msg  
          string  
          Message à afficher.  
        
        
    




    moveElement(id, x, y)
    Déplace le widget de l'élément [id] à l'emplacement x/y. Par défaut les éléments sont placé de haut en bas en partant de la gauche.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          int  
          Id de l'élément à déplacer  
        
        
          x  
          int  
          Position X de la nouvelle emplacement.  
        
        
          y
          int  
          Position Y de la nouvelle emplacement..  
        
        
    




    pause(temps)
    Fait une pause de [temps] ms.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          temps  
          int  
          durée de la pause en ms.  
        
        
    

 

Configuration




    addChannel(id, channel, script)

    Associe le script à au canaux des éléments emitters/receivers. (voir emitter_load.lua)Nécessaire à l'utilisation des emitters/receivers.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          id  
          L'id de l'élément "receiver" qui recevra les messages.  
        
        
          channel  
          string  
          Nom de canal du message  
        
        
          script
          string  
          Le script à assigner à l'event.  
        
        
    




    setupDatabase(player, construct, MasterPlayerId)
    Configure la base de donnée interne de la sandbox. Nécessaire pour les fonctions "database", les radars et la fonction getMasterPlayerId().     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          player  
          player array  
          array contenant les informations des joueurs. (voir rubrique "dummy players")  
        
        
          construct  
          construct array  
          array contenant les informations des constructs. (voir rubrique "dummy constructs")  
        
        
          MasterPlayerId
          int  
          Id du joueur actif (joueur ayant activé l'unit) selon l'array "player".  
        
        
    




    setupTimer(id, nom, script )
    Crée et configure un timer (tick) et associe le script au timer lié à l'Unit [id]. Le timer doit être activé dans les scripts avec la commande setTimer(nom, délais).
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          id  
          Id de l'unit servant à la maintenance.  
        
        
          nom  
          string  
          Nom du timer à utliser pour la fonction setTimer  
        
        
          script  
          string  
          Script LUA à utiliser pour le timer.  
        
        
    

 


Éléments





    id = ButtonUnit(name, label, script)
    Initialise un élément button.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          id  
          nom de l'élément.  
        
        
          label  
          string  
          Libellé du bouton.  
        
        
          script  
          string  
          Script LUA à utiliser pour l'event du bouton.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
        
    




    id = ContainerUnit(name)
    Initialise un élément container.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          id  
          nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = Core(size, constructType, g, selfConstructId)
    Initialise un élément core.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          size  
          id  
          Taille du core par facteur de 16. (xs = 16)  
        
        
          constructType  
          id  
          dynamic ou static  
        
        
          g  
          id  
          La gravitée local.  
        
        
          selfConstructId  
          id  
          Assigne le core à un construct. L'id correspond aux constructs de la base donnée interne (voir SetupDatabase(...)).  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = DataBankUnit(name)
    Initialise un élément DataBank.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = DoorUnit(name)
    Initialise un élément Door.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    





    id = EmitterUnit(name)
    Initialise un élément Emitter.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    





    id = ForceFieldUnit(name)
    Initialise un élément ForceField.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = GyroUnit(name)
    Initialise un élément Gyro.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = GyroUnit(name)
    Initialise un élément LandingGear.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = LightUnit(name)
    Initialise un élément Light.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = Navigator(name)
    Initialise un élément Navigator.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = RadarUnit(name, range, scriptEnter, scriptExit)
    Initialise un élément Radar. Nécéssite l'initialisation de la base de donnée interne pour les dummy targets (voir SetupDatabase). voir l'example radar_load.lua pour plus de détailles. 
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
          range  
          int  
          Distance de détection.  
        
        
          scriptEnter  
          string  
          Script de l'event Enter.  
        
        
          scriptExit  
          string  
          Script de l'event Exit.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = ReceiverUnit(name)
    Initialise un élément Receiver. Nécéssite l'utilisation de addChannel(id, channel, script) pour assigner les scripts aux channels. (voir emitter_load.lua) 
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = ScreenUnit(name, sizeX, sizeY)
    Initialise un élément Screen. La taille des écran est normalement de 1024x614.  Vous pouvez cependant utiliser les annotations vw et vh pour réduire la taille de l'écran.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
          int  
          string  
          Dimension horizontal de l'écran.  
        
        
          int  
          string  
          Dimension vertical de l'écran.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = SwitchUnit(name)
    Initialise un élément Switch.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'élément.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    




    id = Unit(scriptStart, scriptStop)
    Initialise un unit (programming board). fonctionne avec setupTimer(unitId, timerName, script) pour l'assignement des scripts aux timers.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          scriptStart  
          string  
          Script à associer à l'event start.  
        
        
          scriptStop  
          string  
          Script à associer à l'event stop  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'élément crée.  
        
         
    


 





Examples

Des examples de code source sont disponnible dans le répertoire de l'application.


  helloworld_load.lua: Un simpe helloworld.
  helloworld2_load.lua: Un simpe helloworld. un peu plus complexe.
  ball_load.lua: Un test d'animation utilisant du svg.
  buttonManager_load.lua: un button manager simple d'utilisation.
  databank_load.lua: utilisation d'une databank.
  debug_load.lua: quelques fonctions de debuguage.
  emitter_load.lua: utilisation d'un emitter.
  helloworld_load.lua: Un simpe helloworld.
  mapview_load.lua: Radar de typee mapview (vue de haut)
  radar_load.lua: Utilisation d'un radar.


 




Exporter un script

Les commandes d'exports de la toolboar, permet d'accéder à une version du script LUA pouvant facilement être copié vers l'éditeur de Dual Universe.  

L'export compile toute les fichiers .lua du projet (librairies, évènements...) en un seul fichier.

La fonction d'exports de l'api permet d'accéder au code source de l'API LUA.   Il peut parfois vous permettre de débuguer vos scripts et 
peut permettre de comprendre le fonctionnement interne de l'API.


 




Module addons

La version final de l'api aura un système d'addon.  La création d'addon sera facilement réalisable grace au lua, à des outils faciles et à l'accèes à la mémoire de la sandbox.

L'ajout de module se fait dans le fichier de preload (_load.lua) via la fonction LoadAddon(fichier_addon.lua).

L'implémentation des modules d'Addon n'est pas encore fait, mais restes dans mes objectifs à moyen terme. (si possible pour la version release de DU)


 




Dummy constructs

Les dummy constructs servent à remplir la base de donnée interne du jeu.  Que ce soit pour la database de DU (getPlayer(), getConstruct()) ou les radars.

Pour activer la base de donnée interne, vous devez utiliser la fonction SetupDatabase du fichier preload.

les arrays suivent une structure assez simple.
 
Exemple d'initialisation de dummy players et de dummy constructs:

playerList = {} -- also used as owners list
playerList[1] = {id = 0, name = 'unreachable', worldPos = {0, 0, 0}}
playerList[2] = {id = 1, name = 'Nmare418', worldPos = {0, 0, 0}}

constructList = {}

-- Static construct
constructList[1] = {id = 1, 
        owner = 7,  
        name = 'Base 1',  
        ctype='static',   
        pos = {133, -6233, 66},  
        size = {115, 134, 122},  
        speed = {0, 0, 0},  
        mass = 2101.323}

-- moving construct
constructList[2] = {id = 2, 
         owner = 2,
         name = 'Ship 1',
         ctype='dynamic',
         pos = {4353, 3233, 59},
         size = {15, 6, 12}, 
         speed = {25, 34, 0}, 
         mass = 12.43}

-- unreachable (player offline)
constructList[3] = {id = 2, 
         owner = 0,
         name = 'Ship 2',
         ctype='dynamic',
         pos = {4353, 3233, 59},
         size = {15, 6, 12}, 
         speed = {0, 0, 0}, 
         mass = 12.43}

-- setup the internal database. playerlist, constructlist, main player
setupDatabase(playerList, constructList, 1)




 




BD SQL interne (H2)

Les dataBanks utilisent un système de base de donnée interne.  Il est semblable à des base de donnée relationnel (mysql, oracle, sql server...), mais , mais tiens en mémoire. 

Cet outil vous permet de maintenir des bases de données. De copier des tables, copier des données et d'effacer directement le contenue des databanks.  Il peut permettre d'exporter des données.

Notez que, le nom de l'élément est aussi le nom de la base de donnée dans H2.  Si vous nommez les dataBanks avec le même nom dans 2 projets différents, 
ils utiliseront la même source de donnée dans la base de donnée H2.

Documentation de H2 H2 Database Engine
 
Example de requête:
SELECT ID, COUNT(*) FROM TEST GROUP BY ID;SELECT * FROM DB1;


Notes:

Outil admin: Console H2 - (attention: une sandbox ayant une databank doit être en fonction).
url JDBC: jdbc:h2:./DataBank.db;AUTO_SERVER=TRUE
Le login/password par défaut est: sa/sa



 




A faire (todo)

Liste des objectifs à cour et moyen terme: 


Implémentation d'OpenGL.
Système d'addon en LUA donnant accès aux fonctions de l'API Java ainsi qu'a divers outil.
Addon console (system.out)
Addon HelpFile (exemple de html file reader)
Les addons doivent pouvoir upgrader les fonctionnalitées des éléments existant. (exemple, pouvoir configurer des éléments en XS, S, M, L, XL)
Ajoutez de nouveau éléments (dont l'industry) et les mettre à jour.
fonctions systemResolution3 et systemResolution2 (implémenté mais retoune de faux résultats).
fonctions manquante des gyros, navigator et cores (implémenté mais retoune de faux résultats).
Meilleur gestion des erreurs.
Ecran output d'un cockpit (low priority)   
Support Linux, tests.
Rendre l'accès à la bd H2 disponnible pendant la phase PRELOAD.
La position du radar ne suit pas encore les déplacements du core.  



L'objectif à long terme est que son utilisation soit un incontournable pour la création de script de drones.
 
Et un outil pour les theorycrafters.


 


Crédits
Conçu par Stéphane Boivin aka Nmare418 (DevGeek studio).

Développement Java/Lua par Stéphane Boivin.

Illustrations par l'artiste Valérie Dandois (https://valeriedandois.wixsite.com/valdandois)


Fureteur HTML:  DJNativeSwing par Christopher Deckers -  http://djproject.sourceforge.net/ns/index.html

Interpreter LUA:  LuaJ par James Roseborough, Ian Farmer - luaj.org

Base de donnée: H2  par Thomas Mueller, HSQLDB Group - www.h2database.com

Json JAVA: json-simple  par  Yidong Fang - https://github.com/fangyidong/json-simple

Json LUA: jsonv par rxi - https://github.com/rxi/json.lua


Nmare418 est membre de l'organisation Penrose laboratories.

 
 
