# SmartBudget

**SmartBudget:** 
- Es una aplicación móvil administrativa orientada a la gestión y control de las finanzas personales.


### Integrantes del Grupo
* Facundo Alejandro Sanz Palomino
* Anthony Stefano Cervantes Cohaila

### Docente
* Josue Miguel Flores Parra

### Institución
* Universidad La Salle
* **Curso:** Programación para Dispositivos Móviles



### Estructura de Arquitectura MVVM 

El proyecto está estructurado siguiendo las mejores prácticas de desarrollo en Android, aplicando **MVVM**; a continuacion la estrutura de lo subido al repositorio:

```text
smartbudget/
│
├── data/                       # Interacción con fuentes externas
│   ├── remote/                 # Conexiones a servicios de internet, APIs o Firebase directo.
│   └── repository/           
│             
│
├── di/                         
│   └──                         # Módulos para centralizar y proveer instancias de Firebase o Repositorios.
│
├── domain/                     
│   ├── model/                  # Modelos de datos o entidades de la app 
│   └── repository/             # Interfaces o contratos que definen qué acciones se pueden hacer
│
├── navigation/                 
│   ├── Screen.kt               # Contiene de forma segura y tipada las rutas de las pantallas.
│   └── AppNavigation.kt        # Controla el flujo de pantallas y restricciones de login.
│
├── ui/                         
│   ├── auth/                   # Pantallas, componentes y ViewModels de Autenticación
│   ├── home/                   # Pantallas del Dashboard principal, balances y navegación base.
│   └── theme/                  # Configuración visual global 
│
└── utils/                      
    └── Resource.kt             # Clases genéricas auxiliares para el control de estados 
