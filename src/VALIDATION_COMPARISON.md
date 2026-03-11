# Сравнение подходов к валидации в Python

## Где реализовывать исключения для ограничений на поля класса?

Для класса `CollectionManager` есть **два основных подхода**:

---

## 1️⃣ Валидация непосредственно в сеттерах

**Файл:** `validation_in_setters.py`

### ✅ Преимущества:
- **Простота** — весь код в одном месте, легко читать
- **Инкапсуляция** — логика проверки внутри класса
- **Автоматический вызов** — валидация срабатывает при любом изменении поля
- **Меньше кода** — не нужно создавать дополнительные классы

### ❌ Недостатки:
- **Сложность тестирования** — трудно тестировать валидацию отдельно от класса
- **Повторение кода** — если нужна такая же валидация в другом месте, придётся дублировать
- **Раздувание класса** — при большом количестве правил класс становится громоздким
- **Нет агрегации ошибок** — обычно выбрасывается первая ошибка, а не все сразу

### 📌 Когда использовать:
- Простые правила валидации (1-3 проверки на поле)
- Уникальная валидация, которая не будет переиспользоваться
- Быстрый прототип или небольшой проект

### Пример:
```python
@name.setter
def name(self, value: str):
    if not isinstance(value, str):
        raise TypeError(f"Name must be a string, got {type(value).__name__}")
    if not value.strip():
        raise ValueError("Name cannot be empty")
    if len(value) > 100:
        raise ValueError("Name cannot exceed 100 characters")
    self._name = value.strip()
```

---

## 2️⃣ Отдельный класс-валидатор

**Файл:** `validation_in_separate_class.py`

### ✅ Преимущества:
- **Переиспользование** — один валидатор можно использовать в разных классах
- **Тестируемость** — валидатор тестируется независимо от бизнес-логики
- **Агрегация ошибок** — можно собрать все ошибки валидации за один проход
- **Разделение ответственности** — класс занимается бизнес-логикой, валидатор — проверками
- **Гибкость** — легко добавлять новые правила, стратегии валидации
- **DRY** — нет дублирования кода валидации

### ❌ Недостатки:
- **Больше кода** — нужно создавать дополнительные классы
- **Усложнение структуры** — больше файлов и классов для поддержки
- **Возможные циклические импорты** — нужно аккуратно проектировать

### 📌 Когда использовать:
- Сложные правила валидации (5+ проверок на поле)
- Валидация используется в нескольких местах
- Требуется собирать все ошибки сразу (не только первую)
- Проект с долгосрочной поддержкой
- Нужны юнит-тесты на валидацию

### Пример структуры:
```python
# ValidationError — модель ошибки
@dataclass
class ValidationError:
    field: str
    message: str
    value: Any

# ValidationException — исключение с несколькими ошибками
class ValidationException(Exception):
    def __init__(self, errors: List[ValidationError]):
        self.errors = errors

# CollectionItemValidator — валидатор для элементов
class CollectionItemValidator:
    @staticmethod
    def validate_name(value: Any) -> List[ValidationError]:
        errors = []
        # ... проверки ...
        return errors
    
    @classmethod
    def validate(cls, name: Any, value: Any) -> List[ValidationError]:
        errors = []
        errors.extend(cls.validate_name(name))
        errors.extend(cls.validate_value(value))
        return errors

# CollectionManager использует валидатор
class CollectionManager:
    def __init__(self, collection_name: str):
        self.validator = CollectionManagerValidator()
        errors = self.validator.validate_collection_name(collection_name)
        if errors:
            raise ValidationException(errors)
```

---

## 📊 Сравнительная таблица

| Критерий | В сеттерах | Отдельный класс |
|----------|-----------|-----------------|
| Простота реализации | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| Переиспользование | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| Тестируемость | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| Агрегация ошибок | ⭐ | ⭐⭐⭐⭐⭐ |
| Поддержка DRY | ⭐⭐ | ⭐⭐⭐⭐⭐ |
| Читаемость (малый проект) | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |
| Читаемость (большой проект) | ⭐⭐ | ⭐⭐⭐⭐⭐ |

---

## 🎯 Рекомендация для CollectionManager

### Если проект небольшой:
→ Используйте **валидацию в сеттерах** (файл `validation_in_setters.py`)

### Если проект средний/крупный или планируется рост:
→ Используйте **отдельный класс-валидатор** (файл `validation_in_separate_class.py`)

### Гибридный подход (рекомендуется):
1. **Базовые проверки** (тип, None) — в сеттерах для защиты состояния объекта
2. **Бизнес-правила** — в отдельном валидаторе для гибкости и переиспользования

```python
# В сеттере — только защита от некорректных типов
@name.setter
def name(self, value: str):
    if not isinstance(value, str):
        raise TypeError("Name must be a string")
    self._name = value

# В методе или валидаторе — бизнес-правила
def add_item(self, item: CollectionItem):
    errors = self.validator.validate_add_item(item, self._items, self._max_size)
    if errors:
        raise ValidationException(errors)
    self._items.append(item)
```

---

## 🔧 Как реализовать отдельные исключения

### 1. Создайте модель ошибки:
```python
from dataclasses import dataclass
from typing import Any

@dataclass
class ValidationError:
    field: str      # Какое поле не прошло валидацию
    message: str    # Описание проблемы
    value: Any      # Значение, которое вызвало ошибку
```

### 2. Создайте исключение для множества ошибок:
```python
class ValidationException(Exception):
    def __init__(self, errors: List[ValidationError]):
        self.errors = errors
        super().__init__(f"Validation failed with {len(errors)} error(s)")
    
    def __str__(self):
        return "\n".join(str(err) for err in self.errors)
```

### 3. Валидатор возвращает список ошибок:
```python
def validate_name(value: Any) -> List[ValidationError]:
    errors = []
    if not isinstance(value, str):
        errors.append(ValidationError("name", "Must be string", value))
    if len(value) > 100:
        errors.append(ValidationError("name", "Too long", value))
    return errors  # Все ошибки сразу!
```

### 4. Выбрасывайте исключение если есть ошибки:
```python
errors = validator.validate_name(value)
if errors:
    raise ValidationException(errors)
```

---

## 📁 Файлы для изучения:

1. **`validation_in_setters.py`** — пример валидации в сеттерах
2. **`validation_in_separate_class.py`** — пример с отдельным валидатором

Оба файла содержат рабочий код с примерами использования и демонстрацией ошибок.
