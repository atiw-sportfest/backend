# Example Bodies

## POST /authentication

Format: `application/x-www-url-formencoded`
Data:

```
username=username&password=password-as-md5
```

## GET /klasse

Gibt alle Klassen zurück.

Format: JSON

    [
        {
            "kid": 1234,
            "name": "1234"
        },
        {
            "kid": 1235,
            "name": "name2"
        }
    ]


## POST /klasse/anmeldung

## PUT /klasse

## PUT /disziplin

Format: `application/json`

Data:

    {
        "name": "Weitsprung",
        "beschreibung": "Weit springen",
        "minTeilnehmer": 0,
        "maxTeilnehmer": 4,
        "aktiviert": true,
        "teamleistung": false,
        "variablen": [
            {
                "name": "Weite",
                "desc": "Gesprungene Weite in m",
                "expressionParameter": "weite",
                "typ": {
                    "tid": 103
                }
            }
        ],
        "regeln": [
            {
                "index": 0,
                "expression": "geschlecht == \"m\" && weite >= 2.4",
                "points": 10
            },
            {
                "index": 1,
                "expression": "geschlecht == \"m\" && weite >= 1.2",
                "points": 5
            },
            {
                "index": 2,
                "expression": "geschlecht == \"m\" && weite >= 0.6",
                "points": 2
            },
            {
                "index": 3,
                "expression": "geschlecht == \"m\" && weite >= 0.3",
                "points": 1
            },
            {
                "index": 4,
                "expression": "geschlecht == \"w\" && weite >= 1.2",
                "points": 10
            },
            {
                "index": 5,
                "expression": "geschlecht == \"w\" && weite >= 0.6",
                "points": 5
            },
            {
                "index": 6,
                "expression": "geschlecht == \"w\" && weite >= 0.3",
                "points": 2
            },
            {
                "index": 7,
                "expression": "geschlecht == \"w\" && weite >= 0.15",
                "points": 1
            }
        ],
        "kontrahentenAnzahl": 0
    }

## POST /disziplin/{did}

## PUT /leistung

Format: `application/json`

Data:

- `did`: Disziplin ID
- `kid`: Klassen-ID
- `sid`: Schüler-ID (leer wenn Teamleistung)
- `var_id`: Variablen-ID

<br/>

    {
        "did": 1337,
        "kid": 1234,
        "sid": 3400,
        "ergebnisse": [
            {
                "wert": "120",
                "var": {
                    "var_id": 2000
                }
            },
            {
                "wert": "fische",
                "var": {
                    "var_id": 2001
                }
            }
        ],
        "timestamp": 1498729147000
    }

## PUT /leistung/versus

Format: `application/json`

Mehrer Leistungen anlegen, die zusammengehören. (Bspw. bei den Teams die gegeneinander gespielt haben).

Data (Liste mit Disziplinen, siehe `PUT /leistung`):

    [
        {
            "did": 1337,
            "...": "..."
        },
        {
            "did": 1337,
            "...": "..."
        }
    ]

