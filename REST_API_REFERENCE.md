# REST API Reference (v1)

1. [API Endpoint](#api-endpoint)
2. [Analytics](#analytics)
3. [Application](#application)
4. [Content](#content)
5. [Custom Project](#custom-project)
   * [License](#license)
   * [AppCollection](#appcollection)


## API Endpoint

Base URL: `http://elimu.ai/rest/v1`


## Analytics

TODO


## Application

### Read

URL: /application/list

Arguments:
  * deviceId String
  * checksum String
  * locale ai.elimu.model.enums.Locale
  * deviceModel String
  * osVersion Integer
  * applicationId String
  * appVersionCode Integer

Example request:
```
HTTP GET
/application/list
                ?deviceId=abcdef123456
                &checksum=c0e08c173958ce4f1624068b131e3c59
                &locale=EN
                &deviceModel=Google+Pixel+C
                &osVersion=21
                &applicationId=ai.elimu.appstore
                &appVersionCode=2000008
```

Example response:
```json
{
	"result": "success",
	"applications": [{
		"applicationVersions": [{
			"application": {
				"applicationStatus": "ACTIVE",
				"numeracySkills": [],
				"packageName": "ai.elimu.analytics",
				"id": 9,
				"locale": "EN",
				"literacySkills": []
			},
			"fileSizeInKb": 1392,
			"timeUploaded": {
				"month": 9,
				"year": 2017,
				"dayOfMonth": 1,
				"hourOfDay": 18,
				"minute": 30,
				"second": 7
			},
			"fileUrl": "/apk/ai.elimu.analytics-2000000.apk",
			"id": 10,
			"checksumMd5": "d991c36b6990a4b04e8f28efcf56e3b7",
			"contentType": "application/octet-stream",
			"versionCode": 2000000
		}],
		"applicationStatus": "ACTIVE",
		"numeracySkills": [],
		"packageName": "ai.elimu.analytics",
		"id": 9,
		"locale": "EN",
		"literacySkills": []
	}]
}
```


## Content

TODO


## Custom Project

### License

#### Read

URL: `/project/licenses`

Arguments:  
  * licenseEmail: String 
  * licenseNumber: String

Example request:
```
HTTP GET
/project/licenses?licenseEmail=info@elimu.ai&licenseNumber=bddf-d8f4-2adf-a365
```

Example response:
```json
{
    "result": "success",
    "appCollectionId": 12
}
```

### AppCollection

#### Read

URL: `/project/app-collections/{appCollectionId}`

Arguments:  
  * licenseEmail: String 
  * licenseNumber: String

Example request:
```
HTTP GET
/project/app-collections/12?licenseEmail=info@elimu.ai&licenseNumber=bddf-d8f4-2adf-a365
```

Example response:
```json
{
    "result": "success",
    "appCollection": {
        "appCategories": [
            {
                "appGroups": [
                    {
                        "applications": [
                            { ... },
                            { ... },
                            { ... }
                        ]
                    },
                    { ... },
                    { ... }
                ]
            },
            { ... },
            { ... }
        ]
    }
}
```

#### Read Applications

URL: `/project/app-collections/{appCollectionId}/applications`

Arguments:  
  * licenseEmail: String 
  * licenseNumber: String

Example request:
```
HTTP GET
/project/app-collections/12/applications?licenseEmail=info@elimu.ai&licenseNumber=bddf-d8f4-2adf-a365
```

Example response:
```json
{
    "result": "success",
    "applications": [
        { ... },
        { ... },
        { ... }
    ]
}
```
