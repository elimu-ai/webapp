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

TODO


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
