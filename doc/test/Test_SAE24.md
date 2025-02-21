# Casi Test SAE
## 1. Introduzione

Il testing del software è una fase cruciale del ciclo di vita dello sviluppo software. Ha lo scopo di garantire che il prodotto finale soddisfi i requisiti definiti e funzioni correttamente in tutti i contesti previsti. Questo documento illustra le tipologie di testing adottate, le strategie utilizzate e i risultati ottenuti nel processo di verifica del software.

## 2. Obiettivi del Testing

Abbiamo svolto i test per ottenere i seguenti obbienttivi:

Identificazione dei bug: Individuare e correggere eventuali errori o anomalie presenti nel software.

Conformità ai requisiti: Verificare che il software rispetti le specifiche funzionali e non funzionali.

Miglioramento della qualità: Assicurare che il prodotto sia stabile, affidabile.

Riduzione dei rischi: Mitigare i rischi derivanti da malfunzionamenti o difetti nel sistema.

Semplificare manutenzione: assicurarsi che se un indomani dovessimo mettere mano al codice sia facilmente verificabile la sua corretteza.

## 3. Tipologie di Testing Utilizzate

## 3.1 Test Unitari

I test unitari verificano il corretto funzionamento delle singole unità di codice (ad esempio, metodi o classi). Sono stati implementati utilizzando JUnit, con particolare attenzione ai seguenti aspetti:

Validazione di metodi critici come scegliDipendete, addCommessa, e statusFigli.

Gestione delle eccezioni e verifica delle condizioni limite.

## 3.2 Test di Integrazione

Questi test hanno verificato il funzionamento combinato di più componenti, come:

La comunicazione tra il livello di servizio (CommessaService) e il database tramite JPA.

La coerenza dei dati tra entità come Commessa, Task e TaskDipendente.

## 3.3 Test dei controller

non sono stati eseguiti sui controller che sono stati sufficientemente testati a mano durante la scrittura del codice

## 4. Strumenti e Tecnologie

Framework di testing: JUnit per l'esecuzione dei test unitari e di integrazione.

Database: Utilizzo di un database in memoria per i test (“dip-test”) al fine di isolare l'ambiente di testing.

Report di copertura: Generazione dei report tramite strumenti di analisi della copertura del codice con JUnit.

5. Strategia di Testing

La strategia di testing adottata ha seguito un approccio incrementale:

Definizione dei casi di test: Ogni metodo critico è stato associato a uno o più casi di test per coprire scenari validi, non validi e limite.

Esecuzione progressiva: I test unitari sono stati eseguiti prima dei test di integrazione e di sistema.

Automazione: Tutti i test sono stati integrati nel processo di build tramite script automatizzati.

Analisi dei risultati: I risultati sono stati analizzati per identificare eventuali falle e migliorare il codice.

## 6. Risultati del Testing

## 6.1 Copertura del Codice

La copertura del codice ha riportato le seguenti statistiche
![grafico con percentuali di test per le varie classi](./Immagine1.png)



## 6.2 Bug Identificati

Durante il processo di testing sono stati individuati e risolti i seguenti tipi di bug:

Errori di logica: Mancata verifica di alcune condizioni nei metodi di gestione delle commesse figlie.

Eccezioni non gestite: Come la NoResultException nel metodo getTask.

Anomalie di integrazione: Problemi nella persistenza di dati con relazioni gerarchiche complesse.

## 6.3 esempi pratici casi di test

Nel programma sono stati implementati 35 casi di test. Qui di seguito vedremo alcuni esempi con relativa implementazione e spiegazione:

## 6.3.1 	Esempio: testAggiornaTaskDipendente_Success

```java

@Test

public void testAggiornaTaskDipendente_Success() {

em.getTransaction().begin();

// Crea un TaskDipendente

Dipendente dipendente = new Dipendente();

dipendente.setNome("Giovanni giovannini");

dipendente.setReparto(Reparto.CABLAGGIO);

em.persist(dipendente);

Commessa commessa = new Commessa();

commessa.setNome("Commessa Completa 3");

commessa.setReparto(Reparto.CABLAGGIO);

commessa.setTempoStimato(100L);

em.persist(commessa);

CommessaInstance commessaInstance= new CommessaInstance();

commessaInstance.setCommessa(commessa);

commessaInstance.setId(4L);

em.persist(commessaInstance);

Task task = new Task();

task.setCommessa(commessa);

task.setCommessaInstance(commessaInstance);

em.persist(task);

TaskDipendente taskDipendente = new TaskDipendente();

taskDipendente.setDipendente(dipendente);

taskDipendente.setTask(task);

taskDipendente.setStatus("IN_LAVORAZIONE");

taskDipendente.setInizio(LocalDateTime.now().minusHours(2));

taskDipendente.setFine(null);

em.persist(taskDipendente);

em.getTransaction().commit();

taskDipendente.setFine(LocalDateTime.now());

terminaTurnoService.aggiornaTaskDipendente(taskDipendente);

TaskDipendente updatedTask = em.find(TaskDipendente.class, taskDipendente.getId());

assertNotNull("Il task aggiornato non dovrebbe essere null", updatedTask);

assertEquals("Lo stato del task non è corretto", taskDipendente.getFine(), updatedTask.getFine());

}
```
Descrizione: Questo test verifica che l'aggiornamento di un oggetto TaskDipendente avvenga correttamente. Dopo aver creato e persistito i dati necessari per creare una Task, il test aggiorna l'orario di fine del task e utilizza il servizio updateTask per andarlo a modificare anche nella memoria del DB. Infine, il task aggiornato viene recuperato mediante una query al DB  e verificato con il dato che ci aspettiamo di trovare per confermare la correttezza dell'operazione.

## 6.3.2 	Esempio: testModificaPasswordDipendente_Fail_MatricolaInesistente
```java
@Test

public void testModificaPasswordDipendente_Fail_MatricolaInesistente() {

// Tenta di modificare la password di un dipendente inesistente

String risultato = dipendenteService.modificaPasswordDipendente("99999", "passwordNuova");

assertEquals("L'operazione dovrebbe fallire per matricola inesistente.", "Errore durante l'aggiornamento della password.", risultato);

}
```
Descrizione: Questo test verifica che il metodo modificaPasswordDipendente reagisca correttamente alla richiesta di modifica di un Dipendente inesistente. Passiamo alla funzione una matricola non registrata nel DB e ci aspettiamo che il codice ritorni il seguente codice di errore:      ”Errore durante l'aggiornamento della password”.

## 6.3.3 	Esempio: testTasksAssegnate_ReturnsCorrectTasks
```java
@Test

public void testTasksAssegnate_ReturnsCorrectTasks() {

Commessa commessa = new Commessa();

commessa.setNome("Commessa Test");

em.getTransaction().begin();

em.createQuery("DELETE FROM TaskDipendente").executeUpdate();

em.persist(commessa);

em.getTransaction().commit();

CommessaInstance instance = new CommessaInstance();

instance.setCommessa(commessa);

instance.setId(22L);

em.getTransaction().begin();

em.persist(instance);

em.getTransaction().commit();

Task task = new Task(commessa, instance);

em.getTransaction().begin();

em.persist(task);

em.getTransaction().commit();

Dipendente dipendente = new Dipendente();

dipendente.setMatricola("123492");

dipendente.setPassword("passwordDipendente");

dipendente.setNome("Mario Rossi");

dipendente.setReparto(Reparto.CABLAGGIO);

em.persist(dipendente);

TaskDipendente td1 = new TaskDipendente(task, dipendente);

TaskDipendente td2 = new TaskDipendente(task, dipendente);

em.getTransaction().begin();

em.persist(td1);

em.persist(td2);

em.getTransaction().commit();

List<TaskDipendente> result = commessaService.tasksAssegnate(task);

assertNotNull(result);

assertEquals("Il numero di TaskDipendente non corrisponde.", 2, result.size());

}
```
Descrizione: Questo test verifica che tasksAssegnate ritorni il giusto numero di task assegnate. Dopo aver creato e persistito i dati necessari per la creazione di 2 task chiama la funzione tasksAssegnate e va a vedere che il risultato non sia nullo e che sia uguale esattamente ai due elementi che abbiamo inserito


