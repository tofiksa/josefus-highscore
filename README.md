# Applikasjonsnavn

## Om applikasjonen
Dette er en Java-applikasjon som holder orden på opprette brukere, pålogging og håndtering
av brukerdata. Den er bygget med Spring Boot og bruker en RESTful API-tilnærming.

## Systemkrav
- Java 21 eller nyere
- Maven 3.6 eller nyere
- En database (f.eks. MySQL, PostgreSQL) hvis du ønsker å bruke databaselagring

## Hvordan starte applikasjonen

### Alternativ 1: Kjøre med Maven
1. Sørg for at du har Maven installert på maskinen din
2. Naviger til prosjektmappen i terminalen
3. Kjør følgende kommando:
   ```
   mvn clean install
   mvn exec:java -Dexec.mainClass="com.eksempel.HovedKlasse"
   ```

### Alternativ 2: Kjøre med JAR-fil
1. Bygg JAR-filen:
   ```
   mvn clean package
   ```
2. Kjør JAR-filen:
   ```
   java -jar target/applikasjonsnavn-1.0.jar
   ```

### Alternativ 3: Kjøre i IDE
1. Åpne prosjektet i IntelliJ IDEA
2. Finn hovedklassen som inneholder `main`-metoden
3. Høyreklikk på filen og velg "Run"

## API-dokumentasjon (Swagger)
Applikasjonen har API-dokumentasjon tilgjengelig via Swagger UI.

### Tilgang til Swagger-dokumentasjon
1. Start applikasjonen som beskrevet ovenfor
2. Åpne en nettleser og naviger til:
   ```
   http://localhost:8080/swagger-ui.html
   ```
   (Erstatt 8080 med riktig portnummer hvis applikasjonen kjører på en annen port)

### Funksjoner i Swagger UI
- Interaktiv utforskning av alle API-endepunkter
- Mulighet til å teste API-kall direkte fra nettleseren
- Fullstendig dokumentasjon av forespørsel- og responsmodeller
- Beskrivelser av parametre og returverdier

### OpenAPI-spesifikasjon
Den rå OpenAPI-spesifikasjonen er tilgjengelig på: