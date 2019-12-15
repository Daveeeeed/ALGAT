AlgaT - Apprendimento interattivo delle tecniche di programmazione Divide et Impera

Progetto del corso di Algoritmi e Strutture Dati dell'Alma Mater Studiorum di Bologna

Autori: Davide Tinti e Matteo Feroli



Prerequisiti

L'applicazione utilizza funzionalità della libreria grafica JavaFX e richiede Java
versione 1.8.0 o successiva.
A partire da Java 1.11.0 la libreria grafica JavaFX non è più integrata nativamente
nella JVM, si ritiene quindi necessario il download.



Struttura delle directories

La root directory, chiamata src contiene le seguenti sotto directory:
- "testi" che contiene i file di testo, comprese le domande delle lezioni
- "immagini" che contiene le immagini presenti nelle lezioni e necessarie alla
  corretta esecuzione del sorgente
- "sample" che contiene tutti i file ".java" e ".fxml".
I file ".java" contengono il codice sorgente, suddiviso in macroclassi.
I file ".fxml" realizzati con l'aiuto dell'applicativo SceneBuilder contengono i
layout grafici.



Inserimento di testi, immagini e nuove lezioni

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
Le schermate vengono identificate con i successivi numeri:
1 - schermata con solo testo.
2 - schermata composta da testo e immagine.
3 - schermata con testo e possibilità di avviare una simulazione.
4 - schermata con domanda a risposta multipla.

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
