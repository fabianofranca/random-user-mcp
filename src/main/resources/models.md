# Random User MCP API Models

This document describes the model structure that the Random User MCP API can respond with. The API integrates with the randomuser.me API and exposes functionalities as tools that can be consumed by AI agents and other MCP-compatible clients.

## Model Hierarchy

The API response follows this hierarchical structure:

```
RandomUserResponse
├── results: List<UserResult>
│   ├── gender: String
│   ├── name: UserName
│   ├── location: UserLocation
│   │   ├── street: UserStreet
│   │   ├── coordinates: UserCoordinates
│   │   └── timezone: UserTimezone
│   ├── email: String
│   ├── login: UserLogin
│   ├── dob: UserDob
│   ├── registered: UserRegistered
│   ├── phone: String
│   ├── cell: String
│   ├── id: UserId
│   ├── picture: UserPicture
│   └── nat: String
└── info: Info
```

## Model Definitions

### RandomUserResponse

The top-level response from the API.

| Property | Type | Description |
|----------|------|-------------|
| results | List<UserResult> | List of user results |
| info | Info | Metadata about the request |

### UserResult

Represents a single user result.

| Property | Type | Description |
|----------|------|-------------|
| gender | String? | User's gender (e.g., "male", "female") |
| name | UserName? | User's name |
| location | UserLocation? | User's location |
| email | String? | User's email address |
| login | UserLogin? | User's login information |
| dob | UserDob? | User's date of birth |
| registered | UserRegistered? | User's registration information |
| phone | String? | User's phone number |
| cell | String? | User's cell phone number |
| id | UserId? | User's ID |
| picture | UserPicture? | User's pictures |
| nat | String? | User's nationality |

### UserName

Represents a user's name.

| Property | Type | Description |
|----------|------|-------------|
| title | String | Title (e.g., "Mr", "Mrs", "Ms") |
| first | String | First name |
| last | String | Last name |

### UserLocation

Represents a user's location.

| Property | Type | Description |
|----------|------|-------------|
| street | UserStreet | Street information |
| city | String | City |
| state | String | State |
| country | String | Country |
| postcode | String? | Postal code (can be string or number in API, serialized as string) |
| coordinates | UserCoordinates | Geographic coordinates |
| timezone | UserTimezone | Timezone information |

### UserStreet

Represents a street address.

| Property | Type | Description |
|----------|------|-------------|
| number | Int | Street number |
| name | String | Street name |

### UserCoordinates

Represents geographic coordinates.

| Property | Type | Description |
|----------|------|-------------|
| latitude | String | Latitude as a string |
| longitude | String | Longitude as a string |

### UserTimezone

Represents timezone information.

| Property | Type | Description |
|----------|------|-------------|
| offset | String | Timezone offset (e.g., "+01:00") |
| description | String | Timezone description (e.g., "Central European Time") |

### UserLogin

Represents a user's login information.

| Property | Type | Description |
|----------|------|-------------|
| uuid | String | Universally unique identifier |
| username | String | Username |
| password | String | Password |
| salt | String | Salt used for password hashing |
| md5 | String | MD5 hash of the password |
| sha1 | String | SHA-1 hash of the password |
| sha256 | String | SHA-256 hash of the password |

### UserDob

Represents a user's date of birth.

| Property | Type | Description |
|----------|------|-------------|
| date | String | Date of birth in ISO format |
| age | Int | Age in years |

### UserRegistered

Represents a user's registration information.

| Property | Type | Description |
|----------|------|-------------|
| date | String | Registration date in ISO format |
| age | Int | Number of years since registration |

### UserId

Represents a user's ID.

| Property | Type | Description |
|----------|------|-------------|
| name | String | ID type (e.g., "SSN", "passport") |
| value | String? | ID value (can be null if no ID is available) |

### UserPicture

Represents a user's pictures.

| Property | Type | Description |
|----------|------|-------------|
| large | String | URL of large-sized picture |
| medium | String | URL of medium-sized picture |
| thumbnail | String | URL of thumbnail-sized picture |

### Info

Represents metadata about the request.

| Property | Type | Description |
|----------|------|-------------|
| seed | String | Random seed used |
| results | Int | Number of results |
| page | Int | Page number |
| version | String | API version |

## Special Serialization

### PostcodeSerializer

A custom serializer for the `postcode` field in `UserLocation`. This serializer handles the fact that postcodes can be either strings or numbers in the API response:

- When serializing: Converts the postcode to a string (or empty string if null)
- When deserializing: Handles both string and numeric formats, converting them to a string

## Request Parameters

### GetUsersArgs

Represents the arguments for getting users.

| Property | Type | Default | Description |
|----------|------|---------|-------------|
| results | Int | 1 | Number of results to return |
| page | Int | 1 | Page number |
| nationality | String | "us" | Nationality filter |
| version | String | "1.4" | API version |
| seed | String? | null | Random seed |
| gender | String? | null | Gender filter |
| password | String? | null | Password filter |
| include | String? | null | Fields to include |
| exclude | String? | null | Fields to exclude |