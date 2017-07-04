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

```
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
```


## POST /klasse/anmeldung

## PUT /klasse

## PUT /disziplin

## POST /disziplin/{did}

## PUT /leistung

Format: `application/json`

Data:

- `did`: Disziplin ID
- `kid`: Klassen-ID
- `sid`: Schüler-ID (leer wenn Teamleistung)
- `var_id`: Variablen-ID

```
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
```

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

