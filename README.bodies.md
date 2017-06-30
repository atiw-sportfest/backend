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

