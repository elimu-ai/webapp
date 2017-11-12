# REST API Reference (v1)

1. [API Endpoint](#api-endpoint)
2. [Analytics](#analytics)
3. [Application](#application)
4. [Content](#content)
5. [Custom Project](#custom-project)


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

URL: `/project/license`

Example request:
```
HTTP GET
/project/license?licenseEmail=info@elimu.ai&licenseNumber=bddf-d8f4-2adf-a365
```

Example response:
```json
{
    "result": "success",
    "appCollectionId": 12
}
```

### AppCollection

URL: `/project/app-collection`

Example request:
```
HTTP GET
/project/app-collection/12?licenseEmail=info@elimu.ai&licenseNumber=bddf-d8f4-2adf-a365
```

Example response:
```json
{
    "result": "success",
    "appCollection": {
        "appCategories": [
            { ... },
            { ... },
            { ... }
        ]
    }
}
```
