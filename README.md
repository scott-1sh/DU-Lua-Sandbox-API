
#DU offline sandbox API (version 0.559)

<img src=".\DOC\LogoDUAPI_PNG.png" width="250">
  
#DU Offline Sandbox API# 
  
Ce document est un tutoriel pour l'outil de d?velopement DU offline sandbox API.  

J'ai cr?e cet API en 2 parties.

1. Une api LUA  contenant (presque) toute les classes de DU. (duElementAPI) 
2. Une api JAVA permettant d'?muler une sandbox LUA/Dual universe en temps r?el. (duJavaAPI)

La sandbox permet d'?muler la quasi totalit?e des ?l?ments du jeu, des ?crans utilisant le html5,  ainsi que la base de donn?e interne (database.getPlayer, database.getConstruct, databse.getElement).

Il est simple et versatil.  La configuration ce fait ? partir de fichier lua nomm? Preload (voir la section preload de ce document).    

Le code est en open source.  Il sera acc?ssible via un GitHub (todo). 


 A qui s'adresse cet outil.

L'api ? ?t? concu pour travailler sur des ?crans de dual universe (L'?l?ment in game). 

Il ? ?t? con?u pour travailler en mode "projet".  La versatilit?e du fichier de chargement (preload) permet de  cr?er des librairies de fonctions
et de faire du code r?utilisable.  Les scripts sont automatiquement int?gr? lors de l'exports.  La sandbox ? particuli?rement ?t? con?u pour concevoir des applications ou m?me des jeux.  
L'?l?ment databank est d?ja impl?ment? ? 100%.

Il n'est pas encore assez avanc? pour ?tre efficace au niveau des system de vol, mais il le sera probablement sur le long (si j'ai un peu d'aide). 

L'objectif ? long terme est que son utilisation soit un incontournable pour la cr?ation de d'applications in Game, pour les script de drones, d'automatisation et toute sortes de projets. 

Et possiblement, un outil pour les theorycrafters.

---
####Installation####

D?compressez le contenu du fichier DUOfflineSandbox.zip ? l'emplacement de votre choix.   

Installation de JAVA

Java JDK 12 (et plus). doit ?tre install? sur votre ordinateur pour que la librairie fonctionne.  

Vous pouvez le t?l?charger ? l'adresse Java SE Downloads

Il arrive parfois qu'il y ai des probl?mes avec le PATH de java.  Plusieurs tutoriels sont disponnible sur google pour vous aider.
 
 
---
#Utilisation dans Eclispe LDT (mode interpreteur)#

La sandbox peut ?tre utilis? comme interpreteur LUA. 

1. Installez Eclipse LDT.
1. D?mmarez Eclipse LDT.  S?lectionnez le r?pertoire de r?pertoire cr?? ? l'?tape pr?c?dente lorsque demand?.
1. D?compressez l'archive DUOfflineSandbox.zip dans le r?pertoire de travail cr?e ? l'?tape pr?c?dente.
1. Cr?ez un nouveau projet avec comme source, le r?pertoire de DUOfflineSandbox.
1. Cr?ez un lunch configuration pour chaques fichiers de preload. (menu Run->Run configuration) 
     - <img src="./DOC/runconfiguration.png" width="450px" alt="Run Configurations">
1. Acc?dez ? la liste des interpreters. (menu Run->Run configuration->bouton Manage interpreters)
     - <img src="./DOC/interpreter1.png" width="450px" alt="pr?f?rences">
1. Ajouter la sandbox dans la liste d'interpreters. (Menu Run->Run configuration->bouton Manage interpreters->Add)
     - <img src="./DOC/addinterpreter.png" width="450px" alt="add interpreters"> 

cr?ez un lunch configuration pour chaques fichiers de preload.  Cliquez sur le tabb du fichier preload puis cliquez sur RUN (ctrl-F11) pour lancer le projet.


#Utilisation en mode stand alone#

Vous pouvez utiliser l'?diteur LUA que vous souhaitez et lancer l'application directement de votre syst?me de fichier.

un fichier de racourci est disponible pour chaques examples de la sandbox (voir le r?pertoire .\DUOfflineSandbox).  

pour l'ancer la sandbox. Cliquez sur \DUOfflineSandbox\DUOfflineSandbox.jar

La boite a outil de l'application permet de relancer le script en appuyant sur "Reload (F9)" ou de charger un nouveau script par la bar de lancement. "RUN (F5)"




#Mode Fen?tr? dos#

Ouvrez une fen?tre terminal (cmd.exe) et lancez la commande: java.exe -jar DUOfflineSandbox.jar [fichier_preload.lua]

[fichier_preload.lua] indique le fichier ? charger. Par default la sandbox utilise default_load.lua.

 ** Notez qu'il ne faut pas lancer directement l'application java (DUOfflineSandbox.jar) dans la fen?tre dos, 
   car vous n'aurrez pas les r?sultats de la console. **


**ASTUCE: UTILISER AVEC DUAL UNIVERSE EN MODE FENETR?** 

Utiliser le jeu en mode fen?tr? et lorsque vous voulez travailler sur un projet, verrouillez l'?cran du jeu (TAB), 
remarquez que le curseur peut alors sortir de la fen?tre de jeu.  Glisser le curseur de la souris en dehors de la fen?tre du jeu permet 
de travailler sur votre projet. tout en surveillant l'?cran de jeu. Parfait pour un long voyage.  La sandbox g?re l'?cran d'affichage (fonction showOnScreen()).
\s\s
____
#PRELOAD

Le preload est un fichiers de configuration en LUA.  Il configure l'environnement et ajoute les ?l?ments (door, unit, databank....)

Par standard, le fichier de preload est nom? [nomprojet]_load.lua.   Il est utilis? pour d?marrer votre projet.  

Le preload charge les fichiers LUA pour les ?v?nements (start, tick, stop...) reli? aux scripts. Il sert aussi pour configurer la sandbox et rajouter des joueurs et constructs.

Toute les fonctions du LUA sont disponnible. Y compris les fonctions de lecture/ecriture du syst?me de fichier et les fonction lua "dofile" et load. 

L'outil vous permet de conserver vos librairies r?utilisable dans des fichiers distinctif qui peuvent ?tre charg? dans le preload.
 
A la fin du preload,  seul les configurations aquise et les ?l?ments ajout?s sont conserv?s.  La session LUA est remise ? z?ro puis la sandbox est lanc?e (avec l'environement du jeu).

Evidemment, les fonctions utilitaires li? au chargement du preload ne sont pas utilisable dans l'environnement de jeu.

Notez qu'il y a une librairie json disponnible dans la session du preload. (https://github.com/rxi/json.lua)    



**Example de fichier preload** 

```
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

-- ajout d'un ?cran
obj = ScreenUnit('screen1', 1024, 612)
moveElement(obj, 300, 5)
```

 


Fonctions du preload 



 
Outils



    showOnScreen(screenId)
    D?finit l'?cran de d?marrage de la sandbox lua (en dual screen).
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          screenId  
          [0,1]  
          num?ro de l'?cran physique  
        
        
    




    verboseLua(status) 
    Affiche ou non les messages du debugger li? au LUA.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          status  
          [0,1]  
          1 pour activer les messages lua
            
        
        
    





    verboseJava(status)
    Affiche ou non les messages du debugger li? au Java.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          status  
          [0,1]  
          1 pour activer les messages java
            
        
        
    




    script = loadScript(fichier)
    Affiche ou non les messages du debugger li? au Java.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          fichier  
          string  
          fichier LUA ? charger.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          script  
          Le script lua charg?.  
        
         
    





    abort(msg)
    Cancel l'ex?cution du script et affiche un message dans un popup.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          msg  
          string  
          Message ? afficher.  
        
        
    





    die(msg)
    Cancel l'ex?cution du script et affiche un message dans la console.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          msg  
          string  
          Message ? afficher.  
        
        
    




    moveElement(id, x, y)
    D?place le widget de l'?l?ment [id] ? l'emplacement x/y. Par d?faut les ?l?ments sont plac? de haut en bas en partant de la gauche.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          int  
          Id de l'?l?ment ? d?placer  
        
        
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
          dur?e de la pause en ms.  
        
        
    

 

Configuration




    addChannel(id, channel, script)

    Associe le script ? au canaux des ?l?ments emitters/receivers. (voir emitter_load.lua)N?cessaire ? l'utilisation des emitters/receivers.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          id  
          L'id de l'?l?ment "receiver" qui recevra les messages.  
        
        
          channel  
          string  
          Nom de canal du message  
        
        
          script
          string  
          Le script ? assigner ? l'event.  
        
        
    




    setupDatabase(player, construct, MasterPlayerId)
    Configure la base de donn?e interne de la sandbox. N?cessaire pour les fonctions "database", les radars et la fonction getMasterPlayerId().     
    
        
        
        
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
          Id du joueur actif (joueur ayant activ? l'unit) selon l'array "player".  
        
        
    




    setupTimer(id, nom, script )
    Cr?e et configure un timer (tick) et associe le script au timer li? ? l'Unit [id]. Le timer doit ?tre activ? dans les scripts avec la commande setTimer(nom, d?lais).
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          id  
          Id de l'unit servant ? la maintenance.  
        
        
          nom  
          string  
          Nom du timer ? utliser pour la fonction setTimer  
        
        
          script  
          string  
          Script LUA ? utiliser pour le timer.  
        
        
    

 


?l?ments





    id = ButtonUnit(name, label, script)
    Initialise un ?l?ment button.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          id  
          nom de l'?l?ment.  
        
        
          label  
          string  
          Libell? du bouton.  
        
        
          script  
          string  
          Script LUA ? utiliser pour l'event du bouton.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
        
    




    id = ContainerUnit(name)
    Initialise un ?l?ment container.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          id  
          nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Core(size, constructType, g, selfConstructId)
    Initialise un ?l?ment core.
     
    
        
        
        
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
          La gravit?e local.  
        
        
          selfConstructId  
          id  
          Assigne le core ? un construct. L'id correspond aux constructs de la base donn?e interne (voir SetupDatabase(...)).  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = DataBankUnit(name)
    Initialise un ?l?ment DataBank.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = DoorUnit(name)
    Initialise un ?l?ment Door.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    





    id = EmitterUnit(name)
    Initialise un ?l?ment Emitter.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    





    id = ForceFieldUnit(name)
    Initialise un ?l?ment ForceField.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = GyroUnit(name)
    Initialise un ?l?ment Gyro.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = GyroUnit(name)
    Initialise un ?l?ment LandingGear.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = LightUnit(name)
    Initialise un ?l?ment Light.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Navigator(name)
    Initialise un ?l?ment Navigator.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = RadarUnit(name, range, scriptEnter, scriptExit)
    Initialise un ?l?ment Radar. N?c?ssite l'initialisation de la base de donn?e interne pour les dummy targets (voir SetupDatabase). voir l'example radar_load.lua pour plus de d?tailles. 
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
          range  
          int  
          Distance de d?tection.  
        
        
          scriptEnter  
          string  
          Script de l'event Enter.  
        
        
          scriptExit  
          string  
          Script de l'event Exit.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = ReceiverUnit(name)
    Initialise un ?l?ment Receiver. N?c?ssite l'utilisation de addChannel(id, channel, script) pour assigner les scripts aux channels. (voir emitter_load.lua) 
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = ScreenUnit(name, sizeX, sizeY)
    Initialise un ?l?ment Screen. La taille des ?cran est normalement de 1024x614.  Vous pouvez cependant utiliser les annotations vw et vh pour r?duire la taille de l'?cran.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
          int  
          string  
          Dimension horizontal de l'?cran.  
        
        
          int  
          string  
          Dimension vertical de l'?cran.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = SwitchUnit(name)
    Initialise un ?l?ment Switch.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Unit(scriptStart, scriptStop)
    Initialise un unit (programming board). fonctionne avec setupTimer(unitId, timerName, script) pour l'assignement des scripts aux timers.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          scriptStart  
          string  
          Script ? associer ? l'event start.  
        
        
          scriptStop  
          string  
          Script ? associer ? l'event stop  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    


 





Examples

Des examples de code source sont disponnible dans le r?pertoire de l'application.


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

Les commandes d'exports de la toolboar, permet d'acc?der ? une version du script LUA pouvant facilement ?tre copi? vers l'?diteur de Dual Universe.  

L'export compile toute les fichiers .lua du projet (librairies, ?v?nements...) en un seul fichier.

La fonction d'exports de l'api permet d'acc?der au code source de l'API LUA.   Il peut parfois vous permettre de d?buguer vos scripts et 
peut permettre de comprendre le fonctionnement interne de l'API.


 




Module addons

La version final de l'api aura un syst?me d'addon.  La cr?ation d'addon sera facilement r?alisable grace au lua, ? des outils faciles et ? l'acc?es ? la m?moire de la sandbox.

L'ajout de module se fait dans le fichier de preload (_load.lua) via la fonction LoadAddon(fichier_addon.lua).

L'impl?mentation des modules d'Addon n'est pas encore fait, mais restes dans mes objectifs ? moyen terme. (si possible pour la version release de DU)


 




Dummy constructs

Les dummy constructs servent ? remplir la base de donn?e interne du jeu.  Que ce soit pour la database de DU (getPlayer(), getConstruct()) ou les radars.

Pour activer la base de donn?e interne, vous devez utiliser la fonction SetupDatabase du fichier preload.

les arrays suivent une structure assez simple.
 
Exemple d'initialisation de dummy players et de dummy constructs:

playerList = {} -- also used as owners list
playerList[1] = {id = 0, name = 'unreachable', worldPos = {0, 0, 0}}
playerList[2] = {id = 1, name = 'Nmare418', worldPos = {0, 0, 0}}

constructList = {}

-- Static construct
constructList[1] = {id = 1, 
       ?owner = 7,  
       ?name = 'Base 1',  
       ?ctype='static',   
       ?pos = {133, -6233, 66},  
       ?size = {115, 134, 122},  
       ?speed = {0, 0, 0},  
       ?mass = 2101.323}

-- moving construct
constructList[2] = {id = 2, 
        ?owner = 2,
        ?name = 'Ship 1',
        ?ctype='dynamic',
        ?pos = {4353, 3233, 59},
        ?size = {15, 6, 12}, 
        ?speed = {25, 34, 0}, 
        ?mass = 12.43}

-- unreachable (player offline)
constructList[3] = {id = 2, 
        ?owner = 0,
        ?name = 'Ship 2',
        ?ctype='dynamic',
        ?pos = {4353, 3233, 59},
        ?size = {15, 6, 12}, 
        ?speed = {0, 0, 0}, 
        ?mass = 12.43}

-- setup the internal database. playerlist, constructlist, main player
setupDatabase(playerList, constructList, 1)




 




BD SQL interne (H2)

Les dataBanks utilisent un syst?me de base de donn?e interne.  Il est semblable ? des base de donn?e relationnel (mysql, oracle, sql server...), mais , mais tiens en m?moire. 

Cet outil vous permet de maintenir des bases de donn?es. De copier des tables, copier des donn?es et d'effacer directement le contenue des databanks.  Il peut permettre d'exporter des donn?es.

Notez que, le nom de l'?l?ment est aussi le nom de la base de donn?e dans H2.  Si vous nommez les dataBanks avec le m?me nom dans 2 projets diff?rents, 
ils utiliseront la m?me source de donn?e dans la base de donn?e H2.

Documentation de H2 H2 Database Engine
 
Example de requ?te:
SELECT ID, COUNT(*) FROM TEST GROUP BY ID;SELECT * FROM DB1;


Notes:

Outil admin: Console H2 - (attention: une sandbox ayant une databank doit ?tre en fonction).
url JDBC: jdbc:h2:./DataBank.db;AUTO_SERVER=TRUE
Le login/password par d?faut est: sa/sa



 




A faire (todo)

Liste des objectifs ? cour et moyen terme:?


Impl?mentation d'OpenGL.
Syst?me d'addon en LUA donnant acc?s aux fonctions de l'API Java ainsi qu'a divers outil.
Addon console (system.out)
Addon HelpFile (exemple de html file reader)
Les addons doivent pouvoir upgrader les fonctionnalit?es des ?l?ments existant. (exemple, pouvoir configurer des ?l?ments en XS, S, M, L, XL)
Ajoutez de nouveau ?l?ments (dont l'industry) et les mettre ? jour.
fonctions systemResolution3 et systemResolution2 (impl?ment? mais retoune de faux r?sultats).
fonctions manquante des gyros, navigator et cores (impl?ment? mais retoune de faux r?sultats).
Meilleur gestion des erreurs.
Ecran output d'un cockpit (low priority)   
Support Linux, tests.
Rendre l'acc?s ? la bd H2 disponnible pendant la phase PRELOAD.
La position du radar ne suit pas encore les d?placements du core.  



L'objectif ? long terme est que son utilisation soit un incontournable pour la cr?ation de script de drones.
 
Et un outil pour les theorycrafters.


 


Cr?dits
Con?u par St?phane Boivin aka Nmare418 (DevGeek studio).

D?veloppement Java/Lua par St?phane Boivin.

Illustrations par l'artiste Val?rie Dandois (https://valeriedandois.wixsite.com/valdandois)


Fureteur HTML:  DJNativeSwing par Christopher Deckers -  http://djproject.sourceforge.net/ns/index.html

Interpreter LUA:  LuaJ par James Roseborough, Ian Farmer - luaj.org

Base de donn?e: H2  par Thomas Mueller, HSQLDB Group - www.h2database.com

Json JAVA: json-simple  par  Yidong Fang - https://github.com/fangyidong/json-simple

Json LUA: jsonv par rxi - https://github.com/rxi/json.lua


Nmare418 est membre de l'organisation Penrose laboratories.

 
 

DU offline sandbox API (version 0.559)
  
{DU:API}
  
Ce document est un tutoriel pour l'outil de d?velopement DU offline sandbox API.  

J'ai cr?e cet API en 2 parties.

1 - Une api LUA  contenant (presque) toute les classes de DU. (duElementAPI) 
2 - Une api JAVA permettant d'?muler une sandbox LUA/Dual universe en temps r?el. (duJavaAPI)

La sandbox permet d'?muler la quasi totalit?e des ?l?ments du jeu, des ?crans utilisant le html5,  ainsi que la base de donn?e interne (database.getPlayer, database.getConstruct, databse.getElement).

Il est simple et versatil.  La configuration ce fait ? partir de fichier lua nomm? Preload (voir la section preload de ce document).    

Le code est en open source.  Il sera acc?ssible via un GitHub (todo). 


 A qui s'adresse cet outil.

L'api ? ?t? concu pour travailler sur des ?crans de dual universe (L'?l?ment in game). 

Il ? ?t? con?u pour travailler en mode "projet".  La versatilit?e du fichier de chargement (preload) permet de  cr?er des librairies de fonctions
et de faire du code r?utilisable.  Les scripts sont automatiquement int?gr? lors de l'exports.  La sandbox ? particuli?rement ?t? con?u pour concevoir des applications ou m?me des jeux.  
L'?l?ment databank est d?ja impl?ment? ? 100%.

Il n'est pas encore assez avanc? pour ?tre efficace au niveau des system de vol, mais il le sera probablement sur le long (si j'ai un peu d'aide). 

L'objectif ? long terme est que son utilisation soit un incontournable pour la cr?ation de d'applications in Game, pour les script de drones, d'automatisation et toute sortes de projets.
 
Et possiblement, un outil pour les theorycrafters.


Installation

D?compressez le contenu du fichier DUOfflineSandbox.zip ? l'emplacement de votre choix.   

Installation de JAVA

Java JDK 12 (et plus). doit ?tre install? sur votre ordinateur pour que la librairie fonctionne.  

Vous pouvez le t?l?charger ? l'adresse Java SE Downloads

Il arrive parfois qu'il y ai des probl?mes avec le PATH de java.  Plusieurs tutoriels sont disponnible sur google pour vous aider.
 
 

Utilisation dans Eclispe LDT (mode interpreteur)

La sandbox peut ?tre utilis? comme interpreteur LUA. 


  
     Installez Eclipse LDT.
  
  
     D?mmarez Eclipse LDT.  S?lectionnez le r?pertoire de r?pertoire cr?? ? l'?tape pr?c?dente lorsque demand?.
  
  
     d?compressez l'archive DUOfflineSandbox.zip dans le r?pertoire de travail cr?e ? l'?tape pr?c?dente.
  
  
     Cr?ez un nouveau projet avec comme source, le r?pertoire de DUOfflineSandbox.
  
  
     Cr?ez un lunch configuration pour chaques fichiers de preload. (menu Run->Run configuration) 
     
     
  
  
     acc?dez ? la liste des interpreters. (menu Run->Run configuration->bouton Manage interpreters)
     
     
     
  
   Ajouter la sandbox dans la liste d'interpreters. (menu Run->Run configuration->bouton Manage interpreters->Add 
    
    
  



cr?ez un lunch configuration pour chaques fichiers de preload.  Cliquez sur le tabb du fichier preload puis cliquez sur RUN (ctrl-F11) pour lancer le projet.


 




Utilisation en mode stand alone

Vous pouvez utiliser l'?diteur LUA que vous souhaitez et lancer l'application directement de votre syst?me de fichier.

un fichier de racourci est disponible pour chaques examples de la sandbox (voir le r?pertoire .\DUOfflineSandbox).  

pour l'ancer la sandbox. Cliquez sur \DUOfflineSandbox\DUOfflineSandbox.jar

La boite a outil de l'application permet de relancer le script en appuyant sur "Reload (F9)" ou de charger un nouveau script par la bar de lancement. "RUN (F5)"


Mode Fen?tr? dos

Ouvrez une fen?tre terminal (cmd.exe) et lancez la commande: java.exe -jar DUOfflineSandbox.jar [fichier_preload.lua]

[fichier_preload.lua] indique le fichier ? charger. Par default la sandbox utilise default_load.lua.

 * Notez qu'il ne faut pas lancer directement l'application java (DUOfflineSandbox.jar) dans la fen?tre dos, 
   car vous n'aurrez pas les r?sultats de la console. *


ASTUCE: UTILISER AVEC DUAL UNIVERSE EN MODE FENETR? 

Utiliser le jeu en mode fen?tr? et lorsque vous voulez travailler sur un projet, verrouillez l'?cran du jeu (TAB), 
remarquez que le curseur peut alors sortir de la fen?tre de jeu.  Glisser le curseur de la souris en dehors de la fen?tre du jeu permet 
de travailler sur votre projet. tout en surveillant l'?cran de jeu. Parfait pour un long voyage.  La sandbox g?re l'?cran d'affichage (fonction showOnScreen()).
 
 



PRELOAD

Le preload est un fichiers de configuration en LUA.  Il configure l'environnement et ajoute les ?l?ments (door, unit, databank....)

Par standard, le fichier de preload est nom? [nomprojet]_load.lua.   Il est utilis? pour d?marrer votre projet.  

Le preload charge les fichiers LUA pour les ?v?nements (start, tick, stop...) reli? aux scripts. Il sert aussi pour configurer la sandbox et rajouter des joueurs et constructs.

Toute les fonctions du LUA sont disponnible. Y compris les fonctions de lecture/ecriture du syst?me de fichier et les fonction lua "dofile" et load. 

L'outil vous permet de conserver vos librairies r?utilisable dans des fichiers distinctif qui peuvent ?tre charg? dans le preload.
 
A la fin du preload,  seul les configurations aquise et les ?l?ments ajout?s sont conserv?s.  La session LUA est remise ? z?ro puis la sandbox est lanc?e (avec l'environement du jeu).

Evidemment, les fonctions utilitaires li? au chargement du preload ne sont pas utilisable dans l'environnement de jeu.

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

-- ajout d'un ?cran
obj = ScreenUnit('screen1', 1024, 612)
moveElement(obj, 300, 5)


 


Fonctions du preload 



 
Outils



    showOnScreen(screenId)
    D?finit l'?cran de d?marrage de la sandbox lua (en dual screen).
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          screenId  
          [0,1]  
          num?ro de l'?cran physique  
        
        
    




    verboseLua(status) 
    Affiche ou non les messages du debugger li? au LUA.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          status  
          [0,1]  
          1 pour activer les messages lua
            
        
        
    





    verboseJava(status)
    Affiche ou non les messages du debugger li? au Java.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          status  
          [0,1]  
          1 pour activer les messages java
            
        
        
    




    script = loadScript(fichier)
    Affiche ou non les messages du debugger li? au Java.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          fichier  
          string  
          fichier LUA ? charger.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          script  
          Le script lua charg?.  
        
         
    





    abort(msg)
    Cancel l'ex?cution du script et affiche un message dans un popup.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          msg  
          string  
          Message ? afficher.  
        
        
    





    die(msg)
    Cancel l'ex?cution du script et affiche un message dans la console.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          msg  
          string  
          Message ? afficher.  
        
        
    




    moveElement(id, x, y)
    D?place le widget de l'?l?ment [id] ? l'emplacement x/y. Par d?faut les ?l?ments sont plac? de haut en bas en partant de la gauche.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          int  
          Id de l'?l?ment ? d?placer  
        
        
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
          dur?e de la pause en ms.  
        
        
    

 

Configuration




    addChannel(id, channel, script)

    Associe le script ? au canaux des ?l?ments emitters/receivers. (voir emitter_load.lua)N?cessaire ? l'utilisation des emitters/receivers.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          id  
          L'id de l'?l?ment "receiver" qui recevra les messages.  
        
        
          channel  
          string  
          Nom de canal du message  
        
        
          script
          string  
          Le script ? assigner ? l'event.  
        
        
    




    setupDatabase(player, construct, MasterPlayerId)
    Configure la base de donn?e interne de la sandbox. N?cessaire pour les fonctions "database", les radars et la fonction getMasterPlayerId().     
    
        
        
        
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
          Id du joueur actif (joueur ayant activ? l'unit) selon l'array "player".  
        
        
    




    setupTimer(id, nom, script )
    Cr?e et configure un timer (tick) et associe le script au timer li? ? l'Unit [id]. Le timer doit ?tre activ? dans les scripts avec la commande setTimer(nom, d?lais).
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          id  
          Id de l'unit servant ? la maintenance.  
        
        
          nom  
          string  
          Nom du timer ? utliser pour la fonction setTimer  
        
        
          script  
          string  
          Script LUA ? utiliser pour le timer.  
        
        
    

 


?l?ments





    id = ButtonUnit(name, label, script)
    Initialise un ?l?ment button.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          id  
          nom de l'?l?ment.  
        
        
          label  
          string  
          Libell? du bouton.  
        
        
          script  
          string  
          Script LUA ? utiliser pour l'event du bouton.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
        
    




    id = ContainerUnit(name)
    Initialise un ?l?ment container.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          id  
          nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Core(size, constructType, g, selfConstructId)
    Initialise un ?l?ment core.
     
    
        
        
        
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
          La gravit?e local.  
        
        
          selfConstructId  
          id  
          Assigne le core ? un construct. L'id correspond aux constructs de la base donn?e interne (voir SetupDatabase(...)).  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = DataBankUnit(name)
    Initialise un ?l?ment DataBank.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = DoorUnit(name)
    Initialise un ?l?ment Door.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    





    id = EmitterUnit(name)
    Initialise un ?l?ment Emitter.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    





    id = ForceFieldUnit(name)
    Initialise un ?l?ment ForceField.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = GyroUnit(name)
    Initialise un ?l?ment Gyro.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = GyroUnit(name)
    Initialise un ?l?ment LandingGear.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = LightUnit(name)
    Initialise un ?l?ment Light.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Navigator(name)
    Initialise un ?l?ment Navigator.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = RadarUnit(name, range, scriptEnter, scriptExit)
    Initialise un ?l?ment Radar. N?c?ssite l'initialisation de la base de donn?e interne pour les dummy targets (voir SetupDatabase). voir l'example radar_load.lua pour plus de d?tailles. 
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
          range  
          int  
          Distance de d?tection.  
        
        
          scriptEnter  
          string  
          Script de l'event Enter.  
        
        
          scriptExit  
          string  
          Script de l'event Exit.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = ReceiverUnit(name)
    Initialise un ?l?ment Receiver. N?c?ssite l'utilisation de addChannel(id, channel, script) pour assigner les scripts aux channels. (voir emitter_load.lua) 
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = ScreenUnit(name, sizeX, sizeY)
    Initialise un ?l?ment Screen. La taille des ?cran est normalement de 1024x614.  Vous pouvez cependant utiliser les annotations vw et vh pour r?duire la taille de l'?cran.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
          int  
          string  
          Dimension horizontal de l'?cran.  
        
        
          int  
          string  
          Dimension vertical de l'?cran.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = SwitchUnit(name)
    Initialise un ?l?ment Switch.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Unit(scriptStart, scriptStop)
    Initialise un unit (programming board). fonctionne avec setupTimer(unitId, timerName, script) pour l'assignement des scripts aux timers.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          scriptStart  
          string  
          Script ? associer ? l'event start.  
        
        
          scriptStop  
          string  
          Script ? associer ? l'event stop  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    


 





Examples

Des examples de code source sont disponnible dans le r?pertoire de l'application.


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

Les commandes d'exports de la toolboar, permet d'acc?der ? une version du script LUA pouvant facilement ?tre copi? vers l'?diteur de Dual Universe.  

L'export compile toute les fichiers .lua du projet (librairies, ?v?nements...) en un seul fichier.

La fonction d'exports de l'api permet d'acc?der au code source de l'API LUA.   Il peut parfois vous permettre de d?buguer vos scripts et 
peut permettre de comprendre le fonctionnement interne de l'API.


 




Module addons

La version final de l'api aura un syst?me d'addon.  La cr?ation d'addon sera facilement r?alisable grace au lua, ? des outils faciles et ? l'acc?es ? la m?moire de la sandbox.

L'ajout de module se fait dans le fichier de preload (_load.lua) via la fonction LoadAddon(fichier_addon.lua).

L'impl?mentation des modules d'Addon n'est pas encore fait, mais restes dans mes objectifs ? moyen terme. (si possible pour la version release de DU)


 




Dummy constructs

Les dummy constructs servent ? remplir la base de donn?e interne du jeu.  Que ce soit pour la database de DU (getPlayer(), getConstruct()) ou les radars.

Pour activer la base de donn?e interne, vous devez utiliser la fonction SetupDatabase du fichier preload.

les arrays suivent une structure assez simple.
 
Exemple d'initialisation de dummy players et de dummy constructs:

playerList = {} -- also used as owners list
playerList[1] = {id = 0, name = 'unreachable', worldPos = {0, 0, 0}}
playerList[2] = {id = 1, name = 'Nmare418', worldPos = {0, 0, 0}}

constructList = {}

-- Static construct
constructList[1] = {id = 1, 
       ?owner = 7,  
       ?name = 'Base 1',  
       ?ctype='static',   
       ?pos = {133, -6233, 66},  
       ?size = {115, 134, 122},  
       ?speed = {0, 0, 0},  
       ?mass = 2101.323}

-- moving construct
constructList[2] = {id = 2, 
        ?owner = 2,
        ?name = 'Ship 1',
        ?ctype='dynamic',
        ?pos = {4353, 3233, 59},
        ?size = {15, 6, 12}, 
        ?speed = {25, 34, 0}, 
        ?mass = 12.43}

-- unreachable (player offline)
constructList[3] = {id = 2, 
        ?owner = 0,
        ?name = 'Ship 2',
        ?ctype='dynamic',
        ?pos = {4353, 3233, 59},
        ?size = {15, 6, 12}, 
        ?speed = {0, 0, 0}, 
        ?mass = 12.43}

-- setup the internal database. playerlist, constructlist, main player
setupDatabase(playerList, constructList, 1)




 




BD SQL interne (H2)

Les dataBanks utilisent un syst?me de base de donn?e interne.  Il est semblable ? des base de donn?e relationnel (mysql, oracle, sql server...), mais , mais tiens en m?moire. 

Cet outil vous permet de maintenir des bases de donn?es. De copier des tables, copier des donn?es et d'effacer directement le contenue des databanks.  Il peut permettre d'exporter des donn?es.

Notez que, le nom de l'?l?ment est aussi le nom de la base de donn?e dans H2.  Si vous nommez les dataBanks avec le m?me nom dans 2 projets diff?rents, 
ils utiliseront la m?me source de donn?e dans la base de donn?e H2.

Documentation de H2 H2 Database Engine
 
Example de requ?te:
SELECT ID, COUNT(*) FROM TEST GROUP BY ID;SELECT * FROM DB1;


Notes:

Outil admin: Console H2 - (attention: une sandbox ayant une databank doit ?tre en fonction).
url JDBC: jdbc:h2:./DataBank.db;AUTO_SERVER=TRUE
Le login/password par d?faut est: sa/sa



 




A faire (todo)

Liste des objectifs ? cour et moyen terme:?


Impl?mentation d'OpenGL.
Syst?me d'addon en LUA donnant acc?s aux fonctions de l'API Java ainsi qu'a divers outil.
Addon console (system.out)
Addon HelpFile (exemple de html file reader)
Les addons doivent pouvoir upgrader les fonctionnalit?es des ?l?ments existant. (exemple, pouvoir configurer des ?l?ments en XS, S, M, L, XL)
Ajoutez de nouveau ?l?ments (dont l'industry) et les mettre ? jour.
fonctions systemResolution3 et systemResolution2 (impl?ment? mais retoune de faux r?sultats).
fonctions manquante des gyros, navigator et cores (impl?ment? mais retoune de faux r?sultats).
Meilleur gestion des erreurs.
Ecran output d'un cockpit (low priority)   
Support Linux, tests.
Rendre l'acc?s ? la bd H2 disponnible pendant la phase PRELOAD.
La position du radar ne suit pas encore les d?placements du core.  



L'objectif ? long terme est que son
DU offline sandbox API (version 0.559)
  
{DU:API}
  
Ce document est un tutoriel pour l'outil de d?velopement DU offline sandbox API.  

J'ai cr?e cet API en 2 parties.

1 - Une api LUA  contenant (presque) toute les classes de DU. (duElementAPI) 
2 - Une api JAVA permettant d'?muler une sandbox LUA/Dual universe en temps r?el. (duJavaAPI)

La sandbox permet d'?muler la quasi totalit?e des ?l?ments du jeu, des ?crans utilisant le html5,  ainsi que la base de donn?e interne (database.getPlayer, database.getConstruct, databse.getElement).

Il est simple et versatil.  La configuration ce fait ? partir de fichier lua nomm? Preload (voir la section preload de ce document).    

Le code est en open source.  Il sera acc?ssible via un GitHub (todo). 


 A qui s'adresse cet outil.

L'api ? ?t? concu pour travailler sur des ?crans de dual universe (L'?l?ment in game). 

Il ? ?t? con?u pour travailler en mode "projet".  La versatilit?e du fichier de chargement (preload) permet de  cr?er des librairies de fonctions
et de faire du code r?utilisable.  Les scripts sont automatiquement int?gr? lors de l'exports.  La sandbox ? particuli?rement ?t? con?u pour concevoir des applications ou m?me des jeux.  
L'?l?ment databank est d?ja impl?ment? ? 100%.

Il n'est pas encore assez avanc? pour ?tre efficace au niveau des system de vol, mais il le sera probablement sur le long (si j'ai un peu d'aide). 

L'objectif ? long terme est que son utilisation soit un incontournable pour la cr?ation de d'applications in Game, pour les script de drones, d'automatisation et toute sortes de projets.
 
Et possiblement, un outil pour les theorycrafters.


Installation

D?compressez le contenu du fichier DUOfflineSandbox.zip ? l'emplacement de votre choix.   

Installation de JAVA

Java JDK 12 (et plus). doit ?tre install? sur votre ordinateur pour que la librairie fonctionne.  

Vous pouvez le t?l?charger ? l'adresse Java SE Downloads

Il arrive parfois qu'il y ai des probl?mes avec le PATH de java.  Plusieurs tutoriels sont disponnible sur google pour vous aider.
 
 

Utilisation dans Eclispe LDT (mode interpreteur)

La sandbox peut ?tre utilis? comme interpreteur LUA. 


  
     Installez Eclipse LDT.
  
  
     D?mmarez Eclipse LDT.  S?lectionnez le r?pertoire de r?pertoire cr?? ? l'?tape pr?c?dente lorsque demand?.
  
  
     d?compressez l'archive DUOfflineSandbox.zip dans le r?pertoire de travail cr?e ? l'?tape pr?c?dente.
  
  
     Cr?ez un nouveau projet avec comme source, le r?pertoire de DUOfflineSandbox.
  
  
     Cr?ez un lunch configuration pour chaques fichiers de preload. (menu Run->Run configuration) 
     
     
  
  
     acc?dez ? la liste des interpreters. (menu Run->Run configuration->bouton Manage interpreters)
     
     
     
  
   Ajouter la sandbox dans la liste d'interpreters. (menu Run->Run configuration->bouton Manage interpreters->Add 
    
    
  



cr?ez un lunch configuration pour chaques fichiers de preload.  Cliquez sur le tabb du fichier preload puis cliquez sur RUN (ctrl-F11) pour lancer le projet.


 




Utilisation en mode stand alone

Vous pouvez utiliser l'?diteur LUA que vous souhaitez et lancer l'application directement de votre syst?me de fichier.

un fichier de racourci est disponible pour chaques examples de la sandbox (voir le r?pertoire .\DUOfflineSandbox).  

pour l'ancer la sandbox. Cliquez sur \DUOfflineSandbox\DUOfflineSandbox.jar

La boite a outil de l'application permet de relancer le script en appuyant sur "Reload (F9)" ou de charger un nouveau script par la bar de lancement. "RUN (F5)"


Mode Fen?tr? dos

Ouvrez une fen?tre terminal (cmd.exe) et lancez la commande: java.exe -jar DUOfflineSandbox.jar [fichier_preload.lua]

[fichier_preload.lua] indique le fichier ? charger. Par default la sandbox utilise default_load.lua.

 * Notez qu'il ne faut pas lancer directement l'application java (DUOfflineSandbox.jar) dans la fen?tre dos, 
   car vous n'aurrez pas les r?sultats de la console. *


ASTUCE: UTILISER AVEC DUAL UNIVERSE EN MODE FENETR? 

Utiliser le jeu en mode fen?tr? et lorsque vous voulez travailler sur un projet, verrouillez l'?cran du jeu (TAB), 
remarquez que le curseur peut alors sortir de la fen?tre de jeu.  Glisser le curseur de la souris en dehors de la fen?tre du jeu permet 
de travailler sur votre projet. tout en surveillant l'?cran de jeu. Parfait pour un long voyage.  La sandbox g?re l'?cran d'affichage (fonction showOnScreen()).
 
 



PRELOAD

Le preload est un fichiers de configuration en LUA.  Il configure l'environnement et ajoute les ?l?ments (door, unit, databank....)

Par standard, le fichier de preload est nom? [nomprojet]_load.lua.   Il est utilis? pour d?marrer votre projet.  

Le preload charge les fichiers LUA pour les ?v?nements (start, tick, stop...) reli? aux scripts. Il sert aussi pour configurer la sandbox et rajouter des joueurs et constructs.

Toute les fonctions du LUA sont disponnible. Y compris les fonctions de lecture/ecriture du syst?me de fichier et les fonction lua "dofile" et load. 

L'outil vous permet de conserver vos librairies r?utilisable dans des fichiers distinctif qui peuvent ?tre charg? dans le preload.
 
A la fin du preload,  seul les configurations aquise et les ?l?ments ajout?s sont conserv?s.  La session LUA est remise ? z?ro puis la sandbox est lanc?e (avec l'environement du jeu).

Evidemment, les fonctions utilitaires li? au chargement du preload ne sont pas utilisable dans l'environnement de jeu.

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

-- ajout d'un ?cran
obj = ScreenUnit('screen1', 1024, 612)
moveElement(obj, 300, 5)


 


Fonctions du preload 



 
Outils



    showOnScreen(screenId)
    D?finit l'?cran de d?marrage de la sandbox lua (en dual screen).
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          screenId  
          [0,1]  
          num?ro de l'?cran physique  
        
        
    




    verboseLua(status) 
    Affiche ou non les messages du debugger li? au LUA.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          status  
          [0,1]  
          1 pour activer les messages lua
            
        
        
    





    verboseJava(status)
    Affiche ou non les messages du debugger li? au Java.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          status  
          [0,1]  
          1 pour activer les messages java
            
        
        
    




    script = loadScript(fichier)
    Affiche ou non les messages du debugger li? au Java.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          fichier  
          string  
          fichier LUA ? charger.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          script  
          Le script lua charg?.  
        
         
    





    abort(msg)
    Cancel l'ex?cution du script et affiche un message dans un popup.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          msg  
          string  
          Message ? afficher.  
        
        
    





    die(msg)
    Cancel l'ex?cution du script et affiche un message dans la console.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          msg  
          string  
          Message ? afficher.  
        
        
    




    moveElement(id, x, y)
    D?place le widget de l'?l?ment [id] ? l'emplacement x/y. Par d?faut les ?l?ments sont plac? de haut en bas en partant de la gauche.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          int  
          Id de l'?l?ment ? d?placer  
        
        
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
          dur?e de la pause en ms.  
        
        
    

 

Configuration




    addChannel(id, channel, script)

    Associe le script ? au canaux des ?l?ments emitters/receivers. (voir emitter_load.lua)N?cessaire ? l'utilisation des emitters/receivers.     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          id  
          L'id de l'?l?ment "receiver" qui recevra les messages.  
        
        
          channel  
          string  
          Nom de canal du message  
        
        
          script
          string  
          Le script ? assigner ? l'event.  
        
        
    




    setupDatabase(player, construct, MasterPlayerId)
    Configure la base de donn?e interne de la sandbox. N?cessaire pour les fonctions "database", les radars et la fonction getMasterPlayerId().     
    
        
        
        
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
          Id du joueur actif (joueur ayant activ? l'unit) selon l'array "player".  
        
        
    




    setupTimer(id, nom, script )
    Cr?e et configure un timer (tick) et associe le script au timer li? ? l'Unit [id]. Le timer doit ?tre activ? dans les scripts avec la commande setTimer(nom, d?lais).
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          id  
          id  
          Id de l'unit servant ? la maintenance.  
        
        
          nom  
          string  
          Nom du timer ? utliser pour la fonction setTimer  
        
        
          script  
          string  
          Script LUA ? utiliser pour le timer.  
        
        
    

 


?l?ments





    id = ButtonUnit(name, label, script)
    Initialise un ?l?ment button.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          id  
          nom de l'?l?ment.  
        
        
          label  
          string  
          Libell? du bouton.  
        
        
          script  
          string  
          Script LUA ? utiliser pour l'event du bouton.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
        
    




    id = ContainerUnit(name)
    Initialise un ?l?ment container.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          id  
          nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Core(size, constructType, g, selfConstructId)
    Initialise un ?l?ment core.
     
    
        
        
        
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
          La gravit?e local.  
        
        
          selfConstructId  
          id  
          Assigne le core ? un construct. L'id correspond aux constructs de la base donn?e interne (voir SetupDatabase(...)).  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = DataBankUnit(name)
    Initialise un ?l?ment DataBank.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = DoorUnit(name)
    Initialise un ?l?ment Door.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    





    id = EmitterUnit(name)
    Initialise un ?l?ment Emitter.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    





    id = ForceFieldUnit(name)
    Initialise un ?l?ment ForceField.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = GyroUnit(name)
    Initialise un ?l?ment Gyro.
     
    
        
        
        
          Argument  
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = GyroUnit(name)
    Initialise un ?l?ment LandingGear.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = LightUnit(name)
    Initialise un ?l?ment Light.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Navigator(name)
    Initialise un ?l?ment Navigator.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = RadarUnit(name, range, scriptEnter, scriptExit)
    Initialise un ?l?ment Radar. N?c?ssite l'initialisation de la base de donn?e interne pour les dummy targets (voir SetupDatabase). voir l'example radar_load.lua pour plus de d?tailles. 
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
          range  
          int  
          Distance de d?tection.  
        
        
          scriptEnter  
          string  
          Script de l'event Enter.  
        
        
          scriptExit  
          string  
          Script de l'event Exit.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = ReceiverUnit(name)
    Initialise un ?l?ment Receiver. N?c?ssite l'utilisation de addChannel(id, channel, script) pour assigner les scripts aux channels. (voir emitter_load.lua) 
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = ScreenUnit(name, sizeX, sizeY)
    Initialise un ?l?ment Screen. La taille des ?cran est normalement de 1024x614.  Vous pouvez cependant utiliser les annotations vw et vh pour r?duire la taille de l'?cran.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
          int  
          string  
          Dimension horizontal de l'?cran.  
        
        
          int  
          string  
          Dimension vertical de l'?cran.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = SwitchUnit(name)
    Initialise un ?l?ment Switch.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          name  
          string  
          Nom de l'?l?ment.  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    




    id = Unit(scriptStart, scriptStop)
    Initialise un unit (programming board). fonctionne avec setupTimer(unitId, timerName, script) pour l'assignement des scripts aux timers.
     
    
        
        
        
          Argument
          Type  
          Description  
        
        
        
          scriptStart  
          string  
          Script ? associer ? l'event start.  
        
        
          scriptStop  
          string  
          Script ? associer ? l'event stop  
        
        
       
       
        
        
          Valeur de retour  
          Description  
        
        
        
          id  
          Id de l'?l?ment cr?e.  
        
         
    


 





Examples

Des examples de code source sont disponnible dans le r?pertoire de l'application.


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

Les commandes d'exports de la toolboar, permet d'acc?der ? une version du script LUA pouvant facilement ?tre copi? vers l'?diteur de Dual Universe.  

L'export compile toute les fichiers .lua du projet (librairies, ?v?nements...) en un seul fichier.

La fonction d'exports de l'api permet d'acc?der au code source de l'API LUA.   Il peut parfois vous permettre de d?buguer vos scripts et 
peut permettre de comprendre le fonctionnement interne de l'API.


 




Module addons

La version final de l'api aura un syst?me d'addon.  La cr?ation d'addon sera facilement r?alisable grace au lua, ? des outils faciles et ? l'acc?es ? la m?moire de la sandbox.

L'ajout de module se fait dans le fichier de preload (_load.lua) via la fonction LoadAddon(fichier_addon.lua).

L'impl?mentation des modules d'Addon n'est pas encore fait, mais restes dans mes objectifs ? moyen terme. (si possible pour la version release de DU)


 




Dummy constructs

Les dummy constructs servent ? remplir la base de donn?e interne du jeu.  Que ce soit pour la database de DU (getPlayer(), getConstruct()) ou les radars.

Pour activer la base de donn?e interne, vous devez utiliser la fonction SetupDatabase du fichier preload.

les arrays suivent une structure assez simple.
 
Exemple d'initialisation de dummy players et de dummy constructs:

playerList = {} -- also used as owners list
playerList[1] = {id = 0, name = 'unreachable', worldPos = {0, 0, 0}}
playerList[2] = {id = 1, name = 'Nmare418', worldPos = {0, 0, 0}}

constructList = {}

-- Static construct
constructList[1] = {id = 1, 
       ?owner = 7,  
       ?name = 'Base 1',  
       ?ctype='static',   
       ?pos = {133, -6233, 66},  
       ?size = {115, 134, 122},  
       ?speed = {0, 0, 0},  
       ?mass = 2101.323}

-- moving construct
constructList[2] = {id = 2, 
        ?owner = 2,
        ?name = 'Ship 1',
        ?ctype='dynamic',
        ?pos = {4353, 3233, 59},
        ?size = {15, 6, 12}, 
        ?speed = {25, 34, 0}, 
        ?mass = 12.43}

-- unreachable (player offline)
constructList[3] = {id = 2, 
        ?owner = 0,
        ?name = 'Ship 2',
        ?ctype='dynamic',
        ?pos = {4353, 3233, 59},
        ?size = {15, 6, 12}, 
        ?speed = {0, 0, 0}, 
        ?mass = 12.43}

-- setup the internal database. playerlist, constructlist, main player
setupDatabase(playerList, constructList, 1)




 




BD SQL interne (H2)

Les dataBanks utilisent un syst?me de base de donn?e interne.  Il est semblable ? des base de donn?e relationnel (mysql, oracle, sql server...), mais , mais tiens en m?moire. 

Cet outil vous permet de maintenir des bases de donn?es. De copier des tables, copier des donn?es et d'effacer directement le contenue des databanks.  Il peut permettre d'exporter des donn?es.

Notez que, le nom de l'?l?ment est aussi le nom de la base de donn?e dans H2.  Si vous nommez les dataBanks avec le m?me nom dans 2 projets diff?rents, 
ils utiliseront la m?me source de donn?e dans la base de donn?e H2.

Documentation de H2 H2 Database Engine
 
Example de requ?te:
SELECT ID, COUNT(*) FROM TEST GROUP BY ID;SELECT * FROM DB1;


Notes:

Outil admin: Console H2 - (attention: une sandbox ayant une databank doit ?tre en fonction).
url JDBC: jdbc:h2:./DataBank.db;AUTO_SERVER=TRUE
Le login/password par d?faut est: sa/sa



 




A faire (todo)

Liste des objectifs ? cour et moyen terme:?


Impl?mentation d'OpenGL.
Syst?me d'addon en LUA donnant acc?s aux fonctions de l'API Java ainsi qu'a divers outil.
Addon console (system.out)
Addon HelpFile (exemple de html file reader)
Les addons doivent pouvoir upgrader les fonctionnalit?es des ?l?ments existant. (exemple, pouvoir configurer des ?l?ments en XS, S, M, L, XL)
Ajoutez de nouveau ?l?ments (dont l'industry) et les mettre ? jour.
fonctions systemResolution3 et systemResolution2 (impl?ment? mais retoune de faux r?sultats).
fonctions manquante des gyros, navigator et cores (impl?ment? mais retoune de faux r?sultats).
Meilleur gestion des erreurs.
Ecran output d'un cockpit (low priority)   
Support Linux, tests.
Rendre l'acc?s ? la bd H2 disponnible pendant la phase PRELOAD.
La position du radar ne suit pas encore les d?placements du core.  



L'objectif ? long terme est que son utilisation soit un incontournable pour la cr?ation de script de drones.
 
Et un outil pour les theorycrafters.


 


Cr?dits
Con?u par St?phane Boivin aka Nmare418 (DevGeek studio).

D?veloppement Java/Lua par St?phane Boivin.

Illustrations par l'artiste Val?rie Dandois (https://valeriedandois.wixsite.com/valdandois)


Fureteur HTML:  DJNativeSwing par Christopher Deckers -  http://djproject.sourceforge.net/ns/index.html

Interpreter LUA:  LuaJ par James Roseborough, Ian Farmer - luaj.org

Base de donn?e: H2  par Thomas Mueller, HSQLDB Group - www.h2database.com

Json JAVA: json-simple  par  Yidong Fang - https://github.com/fangyidong/json-simple

Json LUA: jsonv par rxi - https://github.com/rxi/json.lua


Nmare418 est membre de l'organisation Penrose laboratories.

 
 
 utilisation soit un incontournable pour la cr?ation de script de drones.
 
Et un outil pour les theorycrafters.


 


Cr?dits
Con?u par St?phane Boivin aka Nmare418 (DevGeek studio).

D?veloppement Java/Lua par St?phane Boivin.

Illustrations par l'artiste Val?rie Dandois (https://valeriedandois.wixsite.com/valdandois)


Fureteur HTML:  DJNativeSwing par Christopher Deckers -  http://djproject.sourceforge.net/ns/index.html

Interpreter LUA:  LuaJ par James Roseborough, Ian Farmer - luaj.org

Base de donn?e: H2  par Thomas Mueller, HSQLDB Group - www.h2database.com

Json JAVA: json-simple  par  Yidong Fang - https://github.com/fangyidong/json-simple

Json LUA: jsonv par rxi - https://github.com/rxi/json.lua


Nmare418 est membre de l'organisation Penrose laboratories.

 
 
 