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
Functiile noi:
1. Functiile din Admin:
- addUser: Aceasta functie primeste ca parametru un string commandInput, ce reprezinta comanda data de user. Daca
username-ul este deja folosit de un alt user, se va afisa un mesaj corespunzator. Acest lucru il
verificam in doua feluri:
1. verificam daca usernameul a fost deja creat in arrayul USERNAMES_IN_CURRENT_TEST, pentru
cazul in care se vrea adaugat acelasi user de doua ori in acelasi timestamp
2. verificam daca usernameul a fost deja creat din functia getUser
    - getUser: cauta un user dupa nume si returneaza userul gasit. Daca userul nu exista, va returna null.
Dupa ce verificam aceste doua cazuri, facem rost din commandInput de type, age si city, iar apoi cream un user
pe baza typeului dat. Daca typeul este "User", atunci cream un user normal in arrayul users, daca typeul este "Artist",
atunci cream un artist in arrayul artists, iar daca typeul este "Host", atunci cream un host in arrayul hosts.
Dupa ce am creat userul / artistul / hostul, il adaugam in USERNAMES_IN_CURRENT_TEST si afisam un mesaj corespunzator.

- deleteUser: Aceasta functie primeste ca parametru un string commandInput, ce reprezinta comanda data de user. Creem un
userToDelete pe baza usernameului dat, prin functia findUser
    - findUser: cauta un user dupa nume prin arrayurile users, artists si hosts si returneaza userul gasit. Daca userul
    nu exista, va returna null.
Daca userToDelete este null, atunci inseamna ca userul nu exista, si se va afisa un mesaj corespunzator.
Daca userToDelete interactioneaza cu o pagina de artist sau de host, atunci se va afisa un mesaj corespunzator.
    - isAnyUserInteractingWithPages: verifica daca exista un alt user si daca acesta interactioneaza cu o pagina
    de artist sau de host. Daca ambele conditii sunt indeplinite, se va returna true, altfel se va returna false.
      - isOnArtistOrHostPage: verifica daca pagina curenta a userului e ArtistPage sau HostPage, artistul sau hostul
      selectat exista si daca usernameul este acelasi cu numele artistului sau hostului selectat. Daca toate conditiile
        sunt indeplinite, se va returna true, altfel se va returna false. (functie scrisa in User)
Apoi, se face rost de toate entryurile asociate userului/artisului/hostului si se verifica daca un alt user
interactioneaza cu aceste entryuri. Daca da, se va afisa un mesaj corespunzator.
    - getAssociatedEntries: face rost de toate entryurile asociate userului/artisului/hostului. Daca userul este
    de tip Artist, se vor lua toate albumele si melodiile din albumele artistului. Daca userul este de tip Host, se vor
    lua toate podcasturile si episoadele din podcasturile hostului. Daca userul este de tip User, se vor lua toate
    playlisturile si melodiile din playlisturile userului.
    - isAnyUserInteractingWith: verifica daca exista un alt user si daca acesta interactioneaza cu entryurile date.
    Daca ambele conditii sunt indeplinite, se va returna true, altfel se va returna false.
      - isInteractingWith: se face rost de statusul playerului in acel moment. Daca statusul exista si timpul ramas
      din status, atunci verificam daca numele entryului este acelasi cu numele entryului din status. Daca da, se va
        returna true, altfel se va returna false. (functie scrisa in User)
Daca niciun user nu interactioneaza cu userul/artisul/hostul in nici un fek, atunci se va sterge userul/artisul/hostul,
folosind functia removeUser si afiseaza un mesaj corespunzator.
    - removeUser: sterge userul/artisul/hostul. Daca userul este de tip Artist, se apeleaza functia clearAlbums pentru
    a sterge toate albumele artistului, se va apela removeAlbum pentru a sterge toate melodiile din fiecare
    album al artistului, iar apoi se va sterge artistul din arrayul artists. De asemenea, se vor sterge melodiile si din
    likedSongs facute de artistul abia sters.
        - clearAlbums: sterge toate albumele artistului (functie scrisa in Artist)
        - removeAlbum: sterge toate melodiile din album din arrayul songs
        - removeArtistSongsFromLikedSongs: sterge toate melodiile din likedSongs facute de artistul abia sters.
        Ia toate melodiile din likedSongs si verifica daca numele artistului este acelasi cu numele artistului
        abia sters.
    Daca userul este de tip Host, se apeleaza functia clearPodcasts pentru a sterge toate podcasturile hostului,
    se va apela removePodcast pentru a sterge fiecare podcast al hostului, iar apoi se va sterge
    hostul din arrayul hosts.
        - clearPodcasts: sterge toate podcasturile hostului (functie scrisa in Host)
        - removePodcast: sterge fiecare podcast din arrayul podcasts
    Iar daca userul este de tip User, se sterge fiecare playlist la care a dat follow userul, apoi se sterge fiecare
    playlist facut de user, iar apoi se sterge userul din arrayul users.
        - removeUserPlaylists: sterge fiecare playlist facut de user. Ia toate playlisturile din arrayul playlists
        si verifica daca numele propietarului este acelasi cu numele userului abia sters.
- showAlbums: Aceasta functie primeste ca parametru un string artistUsername, ce reprezinta numele artistului caruia
vrem sa ii afisam albumele. Se face rost de artistul cu numele artistUsername, iar daca acesta nu exista, se va afisa
o lista goala. Se va face o lista de mapuri cu keyul String si valueul Object albumsInfo, in care se vor pune
informatiile despre albumele artistului. Pentru fiecare album al artistului, se va face un map cu keyul String si
valueul Object albumInfo, in care se vor pune informatiile despre album. Mai intai, se va pune numele albumului,
apoi se vor pune numele melodiilor din album intr-un Collector.toList(), iar la final punem albumInfo in albumsInfo.
Dupa ce am pus toate informatiile despre albumele artistului in albumsInfo, se va returna albumsInfo.
    - getArtist: cauta un artist dupa nume si returneaza artistul gasit. Daca artistul nu exista, va returna null.
- showPodcasts: Aceasta functie primeste ca parametru un string hostUsername, ce reprezinta numele hostului caruia
vrem sa ii afisam podcasturile. Se face rost de hostul cu numele hostUsername, iar daca acesta nu exista, se va afisa
o lista goala. Se va face o lista de mapuri cu keyul String si valueul Object podcastsInfo, in care se vor pune
informatiile despre podcasturile hostului. Pentru fiecare podcast al hostului, se va face un map cu keyul String si
valueul Object podcastInfo, in care se vor pune informatiile despre podcast. Mai intai, se va pune numele podcastului,
apoi se vor pune numele episoadelor din podcast intr-un Collector.toList(), iar la final punem podcastInfo in
podcastsInfo. Dupa ce am pus toate informatiile despre podcasturile hostului in podcastsInfo, se va returna podcastsInfo.
    - getHost: cauta un host dupa nume si returneaza hostul gasit. Daca hostul nu exista, va returna null.
- getTop5Albums: Afiseaza cele mai likeuite 5 albume din sistem. Aceasta functioneaaza in felul urmator: se face rost
de toate albumele din sistem, iar apoi se sorteaza albumele dupa numarul de likeuri pe care le-au primit, in ordine
descrescatoare, iar apoi se compara dupa numele albumului. Dupa ce s-au sortat albumele, se va face o lista de stringuri
in care se vor pune numele celor mai likeuite 5 albume. Se afiseaza lista de albume.
- getTop5Artists: Afiseaza cei mai likeuiti 5 artisti din sistem. Aceasta functioneaaza in felul urmator: se face rost
de toti artistii din sistem, iar apoi se sorteaza artistii dupa numarul de likeuri pe care le-au primit, in ordine
descrescatoare. Dupa ce s-au sortat artistii, se va face o lista de stringuri in care se vor pune numele celor mai
likeuiti 5 artisti. Se afiseaza lista de artisti.
- getAllUsers: afiseaza toti userii din sistem (useri, artisti si hosti). Aceasta functioneaza in felul urmator: se face
o lista de stringuri allUsers in care se vor pune numele tuturor userilor din sistem.
Se adauga fiecare user, artist si host in allUsers, iar apoi se afiseaza lista de useri.
- getOnlineUsers: afiseaza toti userii normali care sunt online. Aceasta functioneaza in felul urmator: se face o lista
de stringuri onlineUsers in care se vor pune numele tuturor userilor normali care sunt online. Se verifica pentru
fiecare user normal daca este online, iar daca da, se va adauga in onlineUsers. Dupa ce s-au adaugat toti userii online
in onlineUsers, se afiseaza lista de useri.
2. Functiile din Artist:
- addAlbum: Aceasta functie creaza un album nou si il adauga in arrayul albums. Daca albumul exista deja, se va afisa
un mesaj corespunzator. Daca adauga acceasi melodie de doua ori in acelasi album, se va afisa un mesaj corespunzator.
Dupa ce trecem de aceste doua cazuri, se va crea un album nou, ce va fi adaugat in arrayul albums si se va afisa un
mesaj corespunzator.
- removeAlbum: Aceasta functie sterge albumul dat din arrayul albums. Avem un Album albumToDelete, ce reprezinta
albumul pe care vrem sa il stergem. Daca albumToDelete este null, atunci inseamna ca albumul nu exista, si se va afisa
un mesaj corespunzator. Daca albumToDelete nu este null, dar cineva asculta unul din melodiile albumului, atunci se va
afisa un mesaj corespunzator. Daca niciuna din cele doua conditii nu este indeplinita, atunci se va sterge albumul
din arrayul albums, apeleaza removeAlbum pentru a sterge melodiile din album din arrayul songs si se va afisa un mesaj
corespunzator.
    - isAlbumInUse: verifica daca userul foloseste albumul sau daca are o melodii din album in playlisturile sale.
    Daca una din aceste conditii este indeplinita, se va returna true, altfel se va returna false.
      - isUsingAlbum: verifica daca playerul userului este pe o melodie din album. Daca playerul e null, atunci
        inseamna ca userul nu asculta nimic, si se va returna false. Daca playerul nu e null, atunci se va verifica
        daca numele melodiei din player este acelasi cu numele melodiei din album. Daca da, se va returna true,
        altfel se va returna false. (functie scrisa in User)
      - hasAlbumSongInPlaylist: verifica daca userul are o melodie din album in playlisturile sale. Se verifica
      in fiecare playlist a userului daca exista o melodie din album. Daca da, se va returna true, altfel se va
        returna false. (functie scrisa in User)
- addEvent: Aceasta functie creaza un eveniment nou si il adauga in arrayul events. Daca evenimentul are o data
invalida, se va afisa un mesaj corespunzator. Daca evenimentul exista deja, se va afisa un mesaj corespunzator. Daca
evenimentul nu exista, se va crea un eveniment nou, ce va fi adaugat in arrayul events si se va afisa un mesaj
corespunzator.
    - isValidDate: verifica daca data evenimentului este valida. Daca anul evenimentului este mai mic de 1900 sau mai
    mare de 2023, se va returna false. Daca luna evenimentului este mai mare de 12, se va returna false. Daca luna este
    februarie si ziua evenimentului este mai mare de 28, se va returna false. Daca luna este aprilie, iunie, septembrie
    sau noiembrie si ziua evenimentului este mai mare de 30, se va returna false. Daca niciuna din aceste conditii nu
    este indeplinita, se va returna true. Pentru a verifica data, am folosit simpleDateFormat.
    - hasEvent: verifica daca evenimentul exista deja. Se verifica in arrayul events daca exista un eveniment cu
    acelasi nume, folosind anyMatch. Daca da, se va returna true, altfel se va returna false.
- removeEvent: Aceasta functie sterge evenimentul dat din arrayul events. Avem un eventName, ce este numele
evenimentului. Daca nu exista evenimentul, se va afisa un mesaj corespunzator. Daca exista evenimentul, il vom sterge
din arrayul events si se va afisa un mesaj corespunzator.
- addMerch: Aceasta functie creaza un produs nou si il adauga in arrayul merch. Daca produsul exista deja, se va afisa
un mesaj corespunzator. Daca pretul produsului este negativ, se va afisa un mesaj corespunzator. Daca produsul nu
exista, se va crea un produs nou, ce va fi adaugat in arrayul merch si se va afisa un mesaj corespunzator.
3. Functiile din Host:
- addPodcast: Aceasta functie creaza un podcast nou si il adauga in arrayul podcasts. Daca podcastul exista deja, se
va afisa un mesaj corespunzator. Daca se adauga acelasi episod de doua ori in acelasi podcast, se va afisa un mesaj
corespunzator. Dupa ce trecem de aceste doua cazuri, se va crea un podcast nou, ce va fi adaugat in arrayul podcasts
si se va afisa un mesaj corespunzator.
- removePodcast: Aceasta functie sterge podcastul dat din arrayul podcasts. Avem un Podcast podcastToRemove, ce
reprezinta podcastul pe care vrem sa il stergem. Daca podcastToRemove este null, atunci inseamna ca podcastul nu
exista, si se va afisa un mesaj corespunzator. Daca podcastToRemove nu este null, dar cineva asculta unul din
episoadele podcastului, atunci se va afisa un mesaj corespunzator. Daca niciuna din cele doua conditii nu este
indeplinita, atunci se va sterge podcastul din arrayul podcasts si se va afisa un mesaj corespunzator.
    - isCurrentlyPlaying: verifica daca userul asculta un episod din podcastul dat. Se verifica in sursa curenta
    a playerului daca aceasta contine un episod din podcastul dat. Daca da, se va returna true,
    altfel se va returna false.
- addAnnouncement: Aceasta functie creaza un anunt nou si il adauga in arrayul announcements. Daca anuntul exista deja,
se va afisa un mesaj corespunzator. Daca anuntul nu exista, se va crea un anunt nou, ce va fi adaugat in arrayul
announcements si se va afisa un mesaj corespunzator.
- removeAnnouncement: Aceasta functie sterge anuntul dat din arrayul announcements. Avem un announcementName, ce este
numele anuntului. Daca nu exista anuntul, se va afisa un mesaj corespunzator. Daca exista anuntul, il vom sterge
din arrayul announcements si se va afisa un mesaj corespunzator.
4. Functiile din User:
- switchConnectionStatus: aceasta functie schimba online statusul userului. Se cauta userul in arrayul users, iar apoi
se verifica daca userul dat este unul normal. Daca nu este, se va afisa un mesaj corespunzator. Daca este, se va schimba
online statusul userului si se va afisa un mesaj corespunzator.
    - switchOnlineStatus: schimba online statusul userului. Daca userul este online, se va schimba online statusul
    la false, iar daca userul este offline, se va schimba online statusul la true.