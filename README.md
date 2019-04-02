# Cache-Simulation
In acest program am realizat implemetarea a 3 tipuri de Cache ce folosesc 3 metode diferite de stocare a obiectelor, folosind principiile OOP in Java.

---Pentru rulare---:
      
      1.make
      2.make run

Rezultatul va fi generat in fisierul a.out, iar fisierul de input este test.in(poate fi modificat din Makefile).

Astfel, cele 3 tipuri de cache sunt:


---LRU(least recently used) - elementul cel mai putin utilizat va fi inlocuit cu
noul obiect.


---FIFO (first in first out) - elementul de la inceputul cozii va fi eliminat, iar
noul element va fi inserat la finalul cozii


---LFU (least frequently used) - de fiecare data cand un obiect trebuie eliminat din cache, se va elimina cel
care a fost accesat de cele mai putine ori de cand se afla in cache.Daca toate obiectele care se afla in cache la un momentdat au fost accesate de un numar de ori egal, atunci cel care va fi eliminat va fi primul care a fost adaugat (fiind cel mai vechi). 


Memoria principala va fi reprezentata sub forma unui array ce contine toate
obiectele ce vor fi adaugate la un moment dat. Chiar daca un obiect se gaseste in cache, acesta va fi
prezent si in memoria principala.


Obiectele folosite de cele 2 memorii sunt de 3 tipuri. Se va porni de la clasa
Subscriptie (clasa abstracta) si vom obtine trei clase: Free (mosteneste clasa abstracta
Subscriptie), Basic (mosteneste clasa Free) si Premium (mosteneste clasa Basic). Toate
tipurile de subscriptie contin un nume (sir de caractere) ce reprezinta numele titularului ce
are subscriptia respectiva.


Fiecare subscriptie contine numarul de cereri pana la epuizarea celei de tipul
Premium si Basic, iar cea Free este nelimitata. In cazul operatiilor de tip GET numele scris
este fix tipul subscriptiei (Premium, Basic sau Free). Asta inseamna ca pentru fiecare
subscriptie de tip Premium, se va mentiona un numar ce reprezinta numarul maxim de
accesari (GET). In momentul in care acest numar este epuizat, subscriptia va deveni
automat Basic, analog devenind ulterior Free care poate fi accesata de un numar nelimitat
de ori.

---Operatii---


1.ADD [nume_obiect] [cereri_basic] [cereri_premium] - la fiecare adaugare de
nou element, acesta va fi inserat numai in memoria principala, nu si in
cache, iar in cazul in care un element exista deja in memoria principala sau
in ambele, se va suprascrie in memoria principala si va fi eliminat din
cache. Parametrul cereri_basic este obligatoriu, iar cereri_premium este
optional. Asta inseamna ca pot exista input-uri ca:


○ ADD nume_obiect 3 - ceea ce inseamna ca avem 2 accesari de tip
GET basic pentru obiect


○ ADD nume_obiect 3 5 - ceea ce inseamna ca avem mai intai 5
accesari de tip GET premium, urmate de 3 basic.


○ Va trebui sa parsati liniile cu operatii de tipul ADD si sa identificati
daca ele contin 1 sau 2 intregi, urmand sa luati decizii ulterior pentru
cererile premium/basic.


2.GET nume_obiect - aceasta operatie va intoarce un intreg in functie de
apartenenta obiectului la cache:


○ 0 - obiectul se gaseste in cache


○ 1 - obiectul se gaseste doar in memoria principala. Ulterior acestei
operatii, obiectul va fi mutat si in cache


○ 2 - obiectul nu a fost gasit in memoria principala


○ Alaturi de numarul mentionat mai sus, se va scrie pe aceeasi linie,
separat prin spatiu, tipul de subscriptie folosit: “Free”, “Basic” sau
“Premium”. Pentru obiectele care nu se gasesc in memoria
principala (intorc 2 la rezultatul GET), singurul lucru printat va fi
numarul 2.



----Deatlii despre implementare----


   1.In Main citesc datele input din fisier si in functie de ele creez un obiect de tip MainMemory in care am un ArrrayList 
ce va reprezenta lista(vectorul) de obiecte adaugate si un obiect ce poarte definita interfetei Cache(tipul obiectului dat
va fi creat conform cerinterlor din fisiere, adica LRU,LFU sau FIFO).Prin urmare, conform datelor din fisier efectuezvcomanda
"ADD" cu ajutorul metodei add() din clasa MainMemory si comanda "GET" cu ajutorul metodei addInCache() din interfata Cache
care e suprascrisa in clasele ce extind aceasta interfata.
Obiectele adaugate vor fi de tip Subscriptie, care reprezinta o clasa abstracta in care avem un String name ce reprezinta
numele fiecarui obiect adaugat si un int nr_acces in care pastrez numarul de accesari a obiectului.De asemenea mai avem o metoda decrement pe care o suprascriem in clasele ce extind clasa abastracta Subscriptie.


  2.In clasa MainMemory avem metoda createObject() in care creeam un nou obiect in functie de parametrii primiti, apeland
constructorii necesari din clasele Free,Basic,Premium (clase care extind clasa abastracta Subscriptie).
In meotda add adaugam obiectul creat in lista de memoria principala. In caz ca acesta deja exista in memoria principala,
il actualizam, iar in caz ca exista si in memoria cache (verificam cu ajutorul metodei searchInCache din interfata Cache
implementata de cele 3 clase ce reprezinta tipul de memorie cache) il stergem din lista de memorie cache(si ii setam 
nr_acces cu 0, acesta reprezinta numarul de accesari(comenzi "GET") care au fost efectuate asupra obiectului respectiv,
necesar pentru implementarea clasei LFU(vezi mai jos implementare)).


  3.Clasa ce extinde clasa abstracta Subsriptie este clasa Free, care la randul sau este extinsa de clasa Basic, care e 
extinsa la randul sau de clasa Premium.In clasele Basic,Premium si Free suprascrim metoda decrement in functie de
neceseitatile fiecaruia, astfel in clasele Basic si Premium mai adaugam un int nr_B si respectiv int_P care reprezinta
numarul posibil de accesari pentru fiecare tip. In cazul in care nr_P din clasa Premium ajunge la 0, cu ajutorul super()
apelam metoda decrement din clasa parinte a acestuia(Basic), altfel decrementam nr_P si returnam ,,Premium" aceasta
insemnand ca obiectul dat inca ramne a fi de tip Premium. La fel si cu decrement din Basic.

  4.In interfata Cache avem 3 metode pe care de asemenea le suprascriem in clasele ce implementeaza aceasta interfata,
acestea fiind clasele LFUCache, LRUCache si FIFOCache.

  5.Cu ajutorul metodei addInCache din clasa LRUCache adaugam un obiect in memoria cache asupra caruia a fost aplicata
o comanda de tip GET. Initial verificam daca obiectul dat se afla in memoria principala si respectiv in memoria cache
cu ajutorul metodelor searchInMainM si searchInCacheM.Respectiv, in functie de rezultatul primit efectuam urmatoarele
actiuni: 


  -daca obiectul nu exista nici in memoria principala nici in memoria cache este afisat "2"
  -in cazul in care acesta este prezent doar in memoria principala afisam "1",il adaugam in memoria cache si aplicam metoda de decrementare:	
  
   *in cazul care memoria cache este plina stergem primul element si adaugam obiectul la sfarsitul listei,astfel
		fiind sters cel mai vechi element.In functia de adaugare intotdeauna adaugam noul obiect la inceputul listei,
		astfel cel mai ,,vechi" obiect adaugand se va afla mereu la sfarsitul listei
		
		
 -in cazul in care este prezent si in memoria principala si in memoria cache, afisam "0" si aplicam funtia de decrementare,
	il scoatem din lista si il adaugam din nou la inceputul listei.
	
 6.In clasa FIFOCache, metoda addInCache funtioneaza aproape la fel, doar ca obiectul va fi mereu adaugat la sfarsitul listei,iar in caz ca memoria cache va fi plina, vom sterge intotdeauna primul element din lista.

 7.In clasa LFUCache, metoda addInCache funtioneaza in general la fel, adica obiectul nou va fi intotdeauna adaugat la inceputul listei. Dar aici mai avem si un int nr_acces, pe care il crestem de fiecare data cand efectuam o operatiunea "GET" asupra obiectului.Astfel, in cazul in care memoria cache va fi plina si va trebui sa eliminam un element din list, apelam metoda forRemove pentru a gasi elementul care va fi eliminat. In aceasta metoda, luam un "min" de tip int si un "last" de tip subscriptie pe care initial le setam cu valorile primului element din lista, adica "min" va fi egal cu nr_acces a primului element din lista, iar last va pointa catre acest elemet.Prin urmare, parcurgem toata lista de elemente( adica obiectele din memoria cache) si gasim elementul ce are nr_acces cel mai mic, dupa care "last" va pointa catre elementul respectiv, acesta fiind elementul care va fi eliminat din lista.
Insa, daca avem mai multe elemente din lista care au acelasi nr_acces si acesta reprezentand minimu-ul, mai facem o verificare a indexu-lui acestor elemente, prin urmare selectand-ul pe cel cu index-ul mai mare( adica a elementului care a fost adaugat in lista cu mai mult timp in urma, celui mai "vechi").

