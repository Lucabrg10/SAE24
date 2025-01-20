# Processo Scrum

Come descritto nel **Project Plan**, il nostro team segue il framework **Scrum** per la gestione del progetto. Ogni iterazione (Sprint) dura due settimane e al termine di ogni Sprint, il team si riunisce per una **Sprint Retrospective** per valutare il processo e apportare miglioramenti. Il ruolo di **Scrum Master** cambia ad ogni iterazione, con un nuovo membro del team che assume il ruolo di Scrum Master all'inizio di ogni Sprint. Lo Scrum Master è responsabile di facilitare le cerimonie Scrum (Sprint Planning, Daily Stand-ups, Sprint Review e Sprint Retrospective) e di rimuovere gli ostacoli che il team potrebbe incontrare.

## Ciclo di vita dello sviluppo di una funzionalita
![Sviluppo funzionalità](../uml/DiagrammaDelleAttività.PNG)



# SPRINT:
## Sprint 1: 22 novembre - 5 dicembre
**Scrum Master**: [Luca Brugnetti]

Durante il primo Sprint, il team si concentrerà sulla creazione dell'architettura di base del sistema e sull'inizializzazione del progetto. L'attività principale sarà la definizione e l'assegnazione delle **User Stories** per l'inizio del progetto. In particolare ci concentriamo sullo sviluppo del **Project Plan** e sullo sviluppo del **Product Backlog**.

**Obiettivi principali**:
- Creazione Project Plan
- Creazione Product Backlog e specifica dei requisiti
- Creazione dell'architettura iniziale.
- Configurazione dell'ambiente di sviluppo (Eclipse con JavaFx, SceneBuilder e Maven).
- Definizione delle User Stories iniziali.

### Sprint 1 Review: 22 novembre - 5 dicembre
  Inizialmente ci sono state diverse difficoltà in particolare cone le nuove tecnologie adottate tra cui JavaFx per l'interfaccia e EclipseLink come ORM per la creazione del database. Dopo aver superato le principali difficoltà di configurazione i membri del team attraverso branch diversi sono riusciti a lavorare in modo corretto e puntuale. Il primo sprint è andato come previsto.
  
## Sprint 2: 6 dicembre - 19 dicembre
**Scrum Master**: [Luca Fustinoni]

Il secondo Sprint sarà dedicato al completamento delle funzioni principali e alla realizzazione di diagrammi uml.

**Obiettivi principali**:
-Creazione dei  service per il database
-Implementazioni logiche dei controller
-Aggiornamento documentazione

### Sprint 2 Review: 6 dicembre - 19 dicembre
Lo sprint due è stato molto prolifico in quanto sono state svolte alcune delle principali funzionalità del software:
sono state create le interfacce per la visualizzzazione delle funzionalità che i manager e i dipendenti avevano a disposizione come forma di prototipo per la visualizazione del desing finale 
sono state inoltre create e implementate le principali logiche dei controller come la possibilità di accedere come dipendente visualizzare le task iniziarle e terminarle,


## Sprint 3: 20 dicembre - 2 gennaio
**Scrum Master**: [Lorenzo Capelli]

Il terzo sprint sarà dedicato all'implementazione del service per la gestione del database e sul perfezionamento della logica legata al manager.

**Obiettivi principali**:
- Sviluppo delle classi service (manager) per ;a gestione delle operazioni sul database
- Implementazione delle chiamate al database nelle classi che lo necessitano
- uniformazione del css
- Aggiornamento documentazione

### Sprint 3 Review: 20 dicembre - 2 gennaio
L'implementazione di tutte le funzionalità previste è stata completata rispettando i tempi di sviluppo pianificati. Non sono stati riscontrati ritardi o problematiche durante lo sprint.

## Sprint 4: 3 gennaio - 16 gennaio
**Scrum Master**: [Luca Brugnetti]

L'ultimo Sprint sarà necessario per completare le ultime funzioni dell'applicazione e completare la documentazione.

**Obiettivi principali**:
- Correzione delle interazioni tra applicativo Dipendente e Manager
- Sviluppo grafici di andamento commessa e personale
- Correzione interfaccia
- Refactor codice

### Sprint 4 Review: 22 novembre - 5 dicembre

Tutte le funzioni sono state implementate in modo corretto, il technical debt c'è ancora anche se è stato fatto comunque una grande operazione di refactor
