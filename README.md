# RepasoKotlin

E-commerce comida rapida "Food Truck"
Objetivos 
  Que contenga crud
  pago en linea
  bd
  diseño 
  app movil

  
app/src/main/java/com/foodtruck/
├── MainActivity.kt
├── data/
│   ├── database/
│   │   ├── FoodTruckDatabase.kt
│   │   ├── FoodTruckDao.kt
│   │   └── Converters.kt
│   └── repository/
│       └── FoodTruckRepository.kt
├── domain/
│   └── models/
│       ├── FoodItem.kt
│       ├── CartItem.kt
│       ├── Order.kt
│       └── OrderStatus.kt
├── presentation/
│   ├── screens/
│   │   ├── MenuScreen.kt
│   │   ├── CartScreen.kt
│   │   └── OrdersScreen.kt
│   ├── components/
│   │   ├── MenuItemCard.kt
│   │   └── CartItemCard.kt
│   ├── viewmodels/
│   │   ├── MenuViewModel.kt
│   │   └── CartViewModel.kt
│   ├── navigation/
│   │   └── Navigation.kt
│   └── theme/
│       └── Theme.kt
└── di/ (opcional para Dependency Injection)
    └── AppModule.kt
