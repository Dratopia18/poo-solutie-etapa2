Etapa 2 POO - Pagination
Solutie creata de Racolta Andrei-Vlad
(folosind solutia oficiala de la etapa 1 primit de la echipa POO)

Functiile actualizate din etapa 1:
search: In afara de a cauta melodii, podcasturi si playlisturi, acum poate cauta
albume, artisti si hosti de podcasturi. Albumul poate fi cautat dupa nume, dupa propietar
si dupa descriere. Daca unul sau mai multe criterii sunt atinse, atunci se va adauga albumul dat
in arrayul entries.In timp ce artistul si hostul sunt cautati dupa numele lor. Pentru amandoi
s-a dat override la functia booleana matchesName si matchesArtist din LibraryEntry,
pentru a putea fi cautati dupa nume. Daca unul sau mai multe criterii sunt atinse,
atunci se va adauga artistul sau hostul dat in arrayul entries.
select: In afara de a selecta melodii, podcasturi si playlisturi, acum poate selecta si
paginiile de artisti si hosti. Atunci cand are de selectat artistul sau hostul, se va
seta pagina curenta la pagina artistului sau hostului selectat si se va afisa un mesaj
corespunzator.
shuffle: In afara de a amesteca melodiile dintr-un playlist, acum poate amesteca si melodiile
dintr-un album. Daca albumul nu este gol, atunci se va amesteca arrayul de melodii din album.
Si daca ce trebuie amestecat nu e playlist sau shuffle, atunci se va afisa un mesaj corespunzator
diferit de cel afisat din etapa 1.
like: In afara de a da like / dislike la o melodie sau la un playlist, acum poate da like si la un album.
Daca albumul nu este gol, atunci se va da like / dislike la album si se va afisa un mesaj corespunzator.
Daca ce trebuie like-uit nu e melodie, playlist sau album, atunci se va afisa un mesaj corespunzator
diferit de cel afisat din etapa 1.
getTop5Songs: Acum ca avem albume create de artisti, trebuie sa adaugam acele melodii din albume in
arrayulistul de melodii sortedSongs. Pentru fiecare album a fiecarui artist, se va adauga in arrayul
sortedSongs melodiile din albumul respectiv. Dupa ce s-au adaugat toate melodiile din albume in arrayul
sortedSongs, sortarea se face asa cum se facea inainte, de unde vom afisa cele mai likeuite 5 melodii.

Functionalitati noi:
In etapa aceasta, am avut de facut un sistem de pagini pentru patru tipuri de pagini:
1. Home Page: Este pagina in care vor fi dusi toti userii atunci cand sunt prima data creati. Aceasta functioneaza
in felul urmator: Creem o copie la likedSongs si followedPlaylists din userul curent. Apoi, pentru fiecare melodie
likeuita si pentru fiecare playlist urmarit, se vor sorta dupa numarul de likeuri pe care le-a primit fiecare melodie
in ordine descrescatoare, in cazul lui likedSongs, si dupa numrul de likeuri combinat
al tuturor melodiilor din playlist, in cazul lui followedPlaylists. Pentru asta, am folosit interfata Comparator.
Dupa ce s-au sortat cele doua arrayuri, se va creea un StringBuilder in care se va adauga un mesaj, structurat in
felul urmator: mai intai, afisam cele mai likeuite 5 melodii din likedSongs, iar apoi, afisam cele mai likeuite 5
playlisturi din followedPlaylists. In ambele cazuri, in cazul in care likedSongs sau followedPlaylists nu au 5
elemente, se vor afisa toate elementele din arrayul respectiv, iar daca acestea sunt goale, se va afisa [].
Dupa ce am construit mesajul, se va returna mesajul ca si String.
2. Liked Content Page: Este pagina in care toti userii pot vedea toate melodiile apreciate si playlisturile urmarite
de acestia. Aceasta functioneaza in felul urmator: luam likedSongs si followedPlaylists din userul curent, iar apoi
creem un StringBuilder in care se va adauga un mesaj, structurat in felul urmator: mai intai, pentru fiecare melodie
likeuita, se va afisa numele melodiei si numele artistului sub forma "numeMelodie - numeArtist", iar pentru fiecare
playlist urmarit, se va afisa numele playlistului si numele propietarului sub forma "numePlaylist - numePropietar".
Daca likedSongs sau followedPlaylists sunt goale, se va afisa []. Dupa ce am construit mesajul, se va returna mesajul
ca si String.
3. Artist Page: Este pagina pe care userul o poate accesa atunci cand da click pe numele unui artist.
Aceasta functioneaza in felul urmator: luam numele artistului caruia a fost accesata pagina, iar apoi luam albumele,
merchandise-ul si evenimentele artistului. Aici a fost singurul caz in care am putut face afisarea directa sub forma
de String, deoarece nu am avut nevoie de niciun fel de sortare. Dupa ce am luat toate informatiile necesare, se va
afisa un mesaj, structurat in felul urmator: mai intai, se vor afisa numele tuturor albumelor artistului, apoi
se vor afisa numele, pretul si descrierea tuturor produselor din merchandise-ul artistului sub forma
"numeProdus - pretProdus - descriereProdus", iar la final se vor afisa numele, data si descrierea tuturor
evenimentelor artistului sub forma "numeEveniment - dataEveniment - descriereEveniment".
4. Host Page: Este pagina pe care userul o poate accesa atunci cand da click pe numele unui host. Aceasta functioneaza
in felul urmator: luam numele hostului caruia a fost accesata pagina, iar apoi luam podcasturile si
anunturile hostului. Aici am fost nevoit sa folosesc StringBuilder pentru a putea afisa mesajul in felul dorit.
La primele doua pagini, asa mi s-a parut cel mai potrivit sa afisez mesajul. Dupa ce am luat toate informatiile
necesare, se va afisa un mesaj, structurat in felul urmator: mai intai, se vor afisa numele tuturor podcasturilor, ce
include si o lista cu numele si descrierea fiecarui episod din podcast, sub forma
"numePodcast: numeEpisod1- descriereEpisod1, numeEpisod2 - descriereEpisod2, ...", iar la final se vor afisa numele si
descrierea tuturor anunturilor hostului sub forma "numeAnunt - descriereAnunt". In final, se va returna mesajul ca si
String.
De asemenea, sistemul de pagini are doua comenzi specifice, ce pot fi folosite de user:
1. changePage: Aceasta comanda primeste ca parametru un string nextPage, ce reprezinta pagina in care userul doreste
sa fie dus. Daca nextPage este fie "Home", fie "LikedContent", atunci se va seta pagina curenta la pagina respectiva.
Daca nextPage e orice altceva, acea pagina nu exista, si se va afisa un mesaj corespunzator.
2. printCurrentPage: Aceasta comanda nu primeste niciun parametru, si afiseaza pagina curenta in care se afla userul.
Daca pagina curenta este "Home" sau "LikedContent", se va afisa pagina de Home sau LikedContent din sistemul de pagini.
Daca pagina curenta este "ArtistPage" sau "HostPage", se verifica mai intai daca exista artistul sau hostul selectat.
Daca exista, se va afisa pagina de ArtistPage sau HostPage din sistemul de pagini. Daca nu exista, se va afisa un mesaj
corespunzator.