# AlgaT - Apprendimento interattivo delle tecniche di programmazione Divide et Impera

## Progetto del corso di Algoritmi e Strutture Dati dell'Alma Mater Studiorum di Bologna

### Prerequisiti
L'applicazione utilizza funzionalità della libreria grafica JavaFX e richiede i
seguenti software in versione uguale o successiva alla descritta:
- Java SDK 13
- JavaFX 12
- Gradle 6.0.1

### Struttura delle directories
La root directory "/src/main" contiente due sotto directories, java e resources
come definito dalla struttura standard di Gradle.
Sono poi cosi suddivisi i file nelle sotto-directories
```
/java/algat/testi - contiene tutti i file ".java"
/resources/layout - contiene tutti i file "fxml"
/resources/lezioni - contiene i file di testo, comprese le domande delle lezioni
/resources/immagini - contiene le immagini presenti nelle lezioni e necessarie alla corretta esecuzione del sorgente
```
I file ".java" contengono il codice sorgente, suddiviso in macroclassi.
I file ".fxml" realizzati con l'aiuto dell'applicativo SceneBuilder contengono i
layout grafici.

### Inserimento di testi, immagini e nuove lezioni
Per inserire una nuova lezione è preventivamente necessario predisporre dei file
di testo, eventualmente immagini, necessarie alla visualizzazione di quest'ultima.
Per abilitare la nuova lezione si richiede di incrementare la variabile LESSON_NR
situata in src/sample/LessonBox variando il valore in base al numero di lezioni da
utilizzare, in seguito è richiesta la revisione del vettore contenente le pagine
delle singole lezioni. Questo vettore formato da "Lesson" (la quale è una banale
lista di interi) è situato nella prima riga della funzione "initialize" della
classe "LessonBox".
Per ultimare la creazione della nuova lezione bisogna specificare di che tipo
sono le schermate nella lezione definendo i valori degli interi appartenenti alla
lista della "Lesson".
Le schermate vengono identificate con i successivi numeri
```
1 - schermata con solo testo.
2 - schermata composta da testo e immagine.
3 - schermata con testo e possibilità di avviare una simulazione.
4 - schermata con domanda a risposta multipla.
```
Per aggiungere i testi bisogna inserire nella cartella "lezioni" un file di testo
chiamato "NL.NS.txt" dove "NL" è il numero della lezione,mentre "NS" è il numero
della schermata in cui si vuole visionare il testo.
Per aggiungere un’immagine il procedimento è il medesimo ma l’immagine dovrà essere
inserita nella cartella "immagini" al posto di "lezioni" e bisogna accertarsi che
il numero di schermata sia di tipo 2.
Per aggiungere una domande bisogna seguire le istruzioni per l’inserimento di un
testo, l’unica differenza si trova nella scrittura del file, che dovrà esser
composto dalla domanda e dalle quattro risposte, separate ciascuna da una riga vuota.
Una sola risposta può essere giusta e quest’ultima deve avere nella riga sopra ad
essa la lettera T(TRUE).
