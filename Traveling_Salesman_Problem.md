# PROBLEM TRGOVAČKOG PUTNIKA (TSL)

#### Radili: Rijad Mutapčić i Omar Omerhodžić

#### Korišteni moduli u aplikaciji 

##### - _Fragmenti_

Android Fragment predstavlja dio aktivnosti (activity) ili podaktivnost. U aktivnostima možemo imati više od jednog fragmenta. Fragmenti nam omogućavaju ponovnu upotrebu dijelova ekrana koje posmatramo na Android uređajima, pomažu nam obogatiti dizajn interfejsa te prenos podataka između različitih ekrana te se također prilagođavaju različitim konfiguracijama uređaja. Unutar aplikacije fragmente prepoznajemo kao "ekrane" unutar jedne aktivnosti. Životni ciklus fragmenta utiče na životni ciklus aktivnosti jer su fragmenti uključeni u aktivnost.

##### - _Constraint Layout_
ConstraintLayout predstavlja ViewGroup koji nam omogućava pozicioniranje i veličinu widgeta na fleksibilan način. Ograničenje (constraint) definira odnos između dva widgeta u layout-u na način da kontroliše kako će ti widgeti biti pozicionirani unutar layout-a. Glavna prednost ConstraintLayouta je što nam omogućava izradu složenih layout-a u kojima nema ugniježđenih grupa kao što su unutar RelativeLayout ili LinearLayout te se zbog toga preporučuje kod kreiranja kompleksnih layouta

##### - _View binding_
View binding nam omogućava lakše pisanje koda koji je povezan sa pogledima (views). Kada koristimo view binding tada se za svaki layout generiše binding klasa koja pohranjuje sve reference na određene poglede (views). Ta klasa ima gotovo sličan naziv kao layout xml fajl. Prednost View binding-a je u podržavanju null safety opcije koja ne dozvoljava  pozivanje nepostojećeg pogleda ili ID-a pa na taj način sprječava iznenadna rušenja aplikacije.

##### - _Data binding_
DataBinding pruža način povezivanja korisničkog interfejsa s biznis logikom pri čemu se vrijednosti sa korisničkog interfejsa automatski ažuriraju. Na ovaj način se smanjuje boilerplate kod u biznis logici koji se piše za sinhronizaciju korisničkog interfejsa kada su dostupni novi podaci

##### - _RecyclerView_
RecyclerView predstavlja naprednu i fleksibilniju verziju ListView-a i GridView-a. Koristi za prikaz velike količine skupova podataka koji se mogu pomicati korištenjem scroll opcije. Uglavnom se koristi kada imamo skup podataka čiji se elementi mogu mijenjati tokom rada aplikacije. Te promjene mogu biti akcije korisnika ili neki network event

##### - _Navigation komponenta_
Navigation komponenta je biblioteka koja upravlja složenim navigacijama fragmenata, dubinskim povezivanjem, animacijama i argumentima koji se razmjenjuju između fragmenata u aplikaciji. Sastoji se iz 3 dijela: Navigation graph, NavHost i NavController. Navigation graph predstavlja xml fajl koji na jednom mjestu sadrži sve informacije vezane za navigaciju. NavHost predstavlja kontejner koji prikazuje odredišta iz Navigation graph-a dok NavController objekat upravlja navigacijom aplikacije unutar NavHost-a tj. on mijenja odredišta unutar NavHost-a kako se korisnik kreće kroz aplikaciju.

##### - _Safe Args plug-in_
SafeArgs je gradle plug-in koji nam omogućuje da unesemo informacije u navigacijski graf o argumentima koje želimo proslijediti. Zatim se generiše kod koji rješava stvaranje Bundle-a za te argumente i izvlačenja tih argumenata iz Bundle-a s druge strane.

##### - _Lifecycle observer_
Lifecycle observer je apstraktna klasa u Androidu koja se povezuje s životnim ciklusom aplikacije. Pomaže u upravljanju lifecycle event-a aktivnosti ili fragmenta kao i stanja aplikacije. Možemo upravljati događajima životnog ciklusa kao što su onCreate, onStart i onStop.

##### - _Activities_
Android aplikacije se sastoje od "aktivnosti" koje predstavljaju jedinstvene akcije koje korisnik može poduzeti. Preporučuje se da aplikacija prikazuje samo jednu aktivnost na ekranu. Na ovaj način korisniku su date samo najrelevantnije informacije te mu se omogućuje da pokrene novi ekran za dodatne informacije ili klikne dugme "back" za pregled prethodne aktivnosti.

##### - _Intents_
Android aplikacije obično "posuđuju" usluge drugih aplikacija koje su već na uređaju. Koristeći Intents možemo pojednostaviti zahtjeve programiranja za svoju aplikaciju i ponuditi jednostavnije, manje pretrpane ekrane.

##### - _Resources_
Resursi u Androidu se odnose na stvari poput slika, nizova i drugih sličnih stvari koje naša aplikacija koristi tokom rada



## Opis rada aplikacije

Aplikacija se sastoji iz 6 fragmenata i to: TitleFragment, AddFragment, MapFragment, OptionsFragment, AboutFragment i TLSFragment. Izgled fragmenata je prikazan na slikama koje su priložene uz dokumentaciju

#### _TitleFragment_

_TitleFragment_ predstavlja klasu koja se sastoji od sljedećih funkcija i njihovih funkcionalnosti:
- _OnCreateView()_  -  funkcija koja prikazuje fragment_title.xml fajl koji se na početku sastoji od praznog RecyclerView-a u kojem se trebaju nalaziti gradovi za koje posmatramo Traveling Salesman Problem.
- _OnCreateOptionsMenu_ - funkcija u kojoj definišemo search bar koji služi za pretragu gradova RecyclerView-a. Unutar nje definišemo funkcije _onQueryTextSubmit_ i _onQueryTextChange_ koje služe za izvršavanje pretage prilikom klika na dugme te za filtriranje RecyclerView-a za unesenu pretragu. Za filtriranje koristimo funkciju _filter_.
- _OnOptionsItemSelected_ - 
- _onActivityCreated_ - funkcija u kojoj se zajedno sa funkcijom _prepareItems_ priprema RecyclerView na način da se iz fajla koji se nalazi u internoj memoriji uređaja izvlače podaci o gradovima koje korisnik želi posmatrati. Na samom početku je fajl prazan ali svaki put kad bude prikazan dati fragment će se provjeriti sadržaj fajla te ispisati unutar RecyclerView-a. 

#### _AddFragment_

_AddFragment_ predstavlja klasu koja se sastoji od sljedećih funkcija i njihovih funkcionalnosti:
- _OnCreate()_  - funkcija u kojoj inicijaliziramo Places API koji je korišten za pretragu gradova po imenima
- _OnCreateView()_  -  funkcija koja prikazuje fragment_add.xml fajl u kojem se nalazi search bar koji služi za odabir gradova koje korisnik želi posmatrati prilikom Traveling Salesman problema.
- _onActivityCreated_ - funkcija u kojoj definišemo search bar. Kada korisnik ukuca ime grada pojavljuje mu se niz gradova sa istim ili sličnim imenom. Klikom na jednu od ponuđenih opcija korisnik dodaje grad unutar RecyclerView-a. Postupak se može ponoviti za dodavanje novog grada. Grad se zapisuje u fajl koji se nalazi u internoj memoriji uređaja pomoću iz kojeg zatim Recyclerview čita kada se aktivira _TitleFragment_. 
- 
#### _MapFragment_

_MapFragment_ predstavlja klasu koja se sastoji od sljedećih funkcija i njihovih funkcionalnosti:
- _OnCreate()_  - funkcija u kojoj prikazujemo fragment_map.xml koji predstavlja Google mapu
- _onActivityCreated_ - funkcija u kojoj se vrši proračun najbolje rute koja će dati odgovor na Traveling Salesman Problem. Najprije se kreira lista u kojoj se kao prvi element nalazi polazni grad a zatim dodaju ostali gradovi. Gradovi su pročitani iz fajla koristeći funkciju _prepareItems_ a sam fajl se nalazi u internoj memoriji uređaja. Zatim tražimo sve permutacije gradova pri čemu ne uzimamo polazni grad pri traženju tih permutacija. Korištenjem funkcije _distanceBetween_ računamo ukupnu udaljenost između gradova za svaku od permutacija te biramo onu permutaciju gradova za koju je udaljenost najmanja. Za odabranu permutaciju spašavamo koordinate gradova te iscrtavamo poligon na mapi, pri čemu vrhove poligona predstavljaju gradovi u optimalnoj permutaciji. Također za svaki grad dodajemo marker da bude uočljiv na mapi te poziciju na mapi pozicioniramo da pokazuje grad iz kojeg polazimo. Bitno je naglasiti da se permutacije traže rekurzivno i to u (n-1)! kombinacija, gdje n predstavlja broj gradova. Ova mala optimizacija je postignuta zahvaljujući izbacivanju polaznog grada iz traženja optimalne permutacije. Nakon što se računanje završi, korisniku se prikazuje optimalna kombinacija gradova pri čemu se vodi računa da je potrebno da se vrati u polazni grad te se prikazuje broj kilometara koji je potrebno preći za se posjete svi gradovi
- 
#### _OptionFragment_

_OptionFragment_ predstavlja klasu koja se sastoji od sljedećih funkcija i njihovih funkcionalnosti:
- _OnCreate()_  - funkcija pomoću koje prikazujemo fragment_option.xml koji predstavlja opciju za brisanje datog grada


#### _RulesFragment_

_RulesFragment_ predstavlja klasu koja se sastoji od sljedećih funkcija i njihovih funkcionalnosti:
- _OnCreateView()_  - funkcija pomoću koje prikazujemo fragment_rules.xml koji predstavlja opis Traveling Salesman problema

#### _AboutFragment_

_RulesFragment_ predstavlja klasu koja se sastoji od sljedećih funkcija i njihovih funkcionalnosti:
- _OnCreateView()_  - funkcija pomoću koje prikazujemo fragment_about.xml koji predstavlja detalje o autorima aplikacije

### _Korišteni dependencies_
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
    implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
    implementation "androidx.appcompat:appcompat:$supportlibVersion"
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
    implementation "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
    implementation "androidx.navigation:navigation-ui-ktx:$navigationVersion"
    implementation 'androidx.recyclerview:recyclerview:1.2.1'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation "com.google.android.material:material:$version"
    implementation "com.google.android.material:material:1.2.1"
    implementation 'com.google.android.gms:play-services-maps:18.0.2'
    implementation 'com.google.maps.android:android-maps-utils:0.4'
    implementation 'com.google.android.gms:play-services-location:19.0.1'
    implementation 'com.google.android.libraries.places:places:2.6.0'
    implementation 'androidx.mediarouter:mediarouter:1.3.0'
    }


