# Postman Testide Kasutusjuhend

##  Ülevaade

See Postman Collection sisaldab kõiki vajalikke teste Spring Boot värvide CRUD rakenduse jaoks. Testid on jagatud kahte kategooriasse:

1. **Color Controller** - Värvide CRUD operatsioonid
2. **Mixed Color Controller** - Segatud värvide operatsioonid

##  Seadistamine

### 1. Import Postman failid
1. Ava Postman
2. Import Collection: `Colors_API_Tests.postman_collection.json`
3. Import Environment: `Colors_API_Environment.postman_environment.json`

### 2. Vali Environment
- Vali "Colors API Environment" Postman'is
- Kontrolli, et `baseUrl` on `http://localhost:8080`

### 3. Käivita rakendus

IntelliJ IDEA-s
- Käivita ColorsApplication.java


##  Color Controller Testid

### Põhilised CRUD operatsioonid:

#### **GET operatsioonid:**
- **Get All Colors** - Hangi kõik värvid
- **Get Color by ID** - Hangi värv ID järgi (0-3)
- **Debug - Get All Colors** - Detailne värvide info

#### **POST operatsioonid:**
- **Create New Color - Oranž** - Loo uus oranž värv
- **Create New Color - Lilla** - Loo uus lilla värv

#### **PUT operatsioonid:**
- **Update Color** - Uuenda punast värvi tumedamaks

#### **DELETE operatsioonid:**
- **Delete Color** - Kustuta värv

#### **Vigade testid:**
- **Test Invalid Color ID** - Testi olematut ID-d
- **Test Invalid Color Creation** - Testi vigaseid andmeid

##  Mixed Color Controller Testid

### Segatud värvide operatsioonid:

#### **GET operatsioonid:**
- **Get All Mixed Colors** - Hangi kõik segatud värvid
- **Get Mixed Color by ID** - Hangi segatud värv ID järgi
- **Get Mixed Colors by Color ID** - Hangi segatud värvid konkreetse värvi järgi

#### **POST operatsioonid:**
- **Create Mixed Color - Punane + Roheline** - Testi punase ja rohelise segamist
- **Create Mixed Color - Punane + Sinine** - Testi punase ja sinise segamist
- **Create Mixed Color - Punane + Kollane** - Testi punase ja kollase segamist
- **Create Mixed Color - Roheline + Sinine** - Testi rohelise ja sinise segamist
- **Create Mixed Color - Roheline + Kollane** - Testi rohelise ja kollase segamist
- **Create Mixed Color - Sinine + Kollane** - Testi sinise ja kollase segamist
- **Create Mixed Color - Auto Name** - Testi automaatse nime genereerimist

#### **DELETE operatsioonid:**
- **Delete Mixed Color** - Kustuta segatud värv

#### **Vigade testid:**
- **Test Duplicate Mixed Color** - Testi sama kombinatsiooni uuesti
- **Test Same Color Mixing** - Testi sama värvi segamist
- **Test Invalid Color IDs** - Testi olematute värvide ID-dega

##  Testide käivitamine

### 1. Eelnevad tingimused
- Rakendus peab olema käivitatud (`http://localhost:8080`)
- Andmebaas peab sisaldama põhivärve (ID 0-3)

### 2. Testide järjekord
1. **Alusta Color Controller testidega:**
   - Käivita "Get All Colors" - kontrolli, et värvid on olemas (ID 0-3)
   - Käivita "Debug - Get All Colors" - vaata detailset infot

2. **Jätka Mixed Color Controller testidega:**
   - Käivita "Get All Mixed Colors" - kontrolli, et pole segatud värve
   - Käivita segatud värvide loomise testid (kasuta ID-sid 0-3)

### 3. Oodatud tulemused

#### **Color Controller:**
- **Get All Colors**: Peaks tagastama 4 värvi (ID 0-3)
- **Get Color by ID**: Peaks tagastama õige värvi
- **Create New Color**: Peaks tagastama 201 Created
- **Update Color**: Peaks tagastama 200 OK
- **Delete Color**: Peaks tagastama 204 No Content

#### **Mixed Color Controller:**
- **Get All Mixed Colors**: Peaks tagastama tühja massiivi või olemasolevad segatud värvid
- **Create Mixed Color**: Peaks tagastama 201 Created ja segatud värvi info
- **Test Duplicate**: Peaks tagastama 400 Bad Request
- **Test Same Color**: Peaks tagastama 400 Bad Request

##  Värvide ID-d

| ID | Nimi | RGB | HEX |
|----|------|-----|-----|
| 0 | Punane | (255,0,0) | #FF0000 |
| 1 | Roheline | (0,255,0) | #00FF00 |
| 2 | Sinine | (0,0,255) | #0000FF |
| 3 | Kollane | (255,255,0) | #FFFF00 |

##  Segatud värvide oodatud tulemused

| Värv 1 | Värv 2 | Segatud värv | RGB | HEX |
|--------|--------|--------------|-----|-----|
| Punane | Roheline | Oranž | (127,127,0) | #7F7F00 |
| Punane | Sinine | Lilla | (127,0,127) | #7F007F |
| Punane | Kollane | Oranž | (255,127,0) | #FF7F00 |
| Roheline | Sinine | Tsüaan | (0,127,127) | #007F7F |
| Roheline | Kollane | Kollakasroheline | (127,255,0) | #7FFF00 |
| Sinine | Kollane | Roheline | (127,127,127) | #7F7F7F |

##  Probleemide lahendamine

### Rakendus ei vasta
- Kontrolli, et rakendus on käivitatud
- Kontrolli, et port 8080 on vaba
- Vaata konsooli logidest

### 404 vead
- Kontrolli, et URL on õige
- Kontrolli, et rakendus töötab õigel pordil

### 400 vead
- Kontrolli JSON andmete vormingut
- Kontrolli, et ID-d on õiged (0-3)

### 500 vead
- Vaata rakenduse logidest
- Kontrolli andmebaasi ühendust

##  Testide kohandamine

### Uue värvi lisamine
1. Kopeeri "Create New Color" test
2. Muuda JSON andmed
3. Käivita test

### Uue segatud värvi test
1. Kopeeri "Create Mixed Color" test
2. Muuda color1Id ja color2Id
3. Käivita test

### Environment muutmine
- Muuda `baseUrl` väärtust, kui rakendus töötab teisel pordil
- Lisa uusi muutujaid vastavalt vajadusele
