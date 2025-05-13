# iboot-print Rendering Engine

[![JDK](https://img.shields.io/badge/JDK-17+-green.svg)](https://www.oracle.com/java/technologies/javase-downloads.html)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%203.0-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## Project Introduction

iboot-print is a server-side rendering engine based on vue-plugin-hiprint, providing HTML generation functionality from templates and JSON print data for backend automation. Developed with Java 17 and Solon framework, using HtmlUnit for server-side rendering.

## Key Features

- Server-side template rendering
- PDF generation support (Pending implementation)

## Technology Stack

- JDK 17+
- Solon 3.3.0
- HtmlUnit 2.70.0
- Hutool 5.8.37
- vue-plugin-hiprint 0.0.58-fix

## Quick Start

### Requirements

- JDK 17 or higher
- Gradle 8.0 or higher

### Build Project

```bash
# Clone repository
git clone https://github.com/anganing/iboot-print.git

# Enter project directory
cd iboot-print

# Build project
./gradlew build
```

### Run Project

```bash
java -jar build/libs/iboot-print-1.0.jar
```

## Usage Examples

**HTTP API Example**

Generate HTML endpoint:
```
POST http://{{ip}}:{{port}}/api/print/generateHtml
```

Request parameters (JSON format):
```json
{
  "templateContent": "vue-plugin-hiprint template JSON string",
  "printData": "Print data JSON string (must be array for multiple copies or object)"
}
```

Response example (HTML content):
```html
HTML string
```

Get vue-plugin-hiprint version endpoint:
```
GET http://{{ip}}:{{port}}/api/print/getHiprintVersion
```

Response example:
```text
0.0.58-fix
```

## Contributing

Welcome to submit Issues and Pull Requests.

## License

This project is licensed under [GPL 3.0](LICENSE).