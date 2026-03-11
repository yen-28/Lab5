"""
Пример 2: Отдельный класс-валидатор для CollectionManager
Подходит для сложных случаев с множеством правил, переиспользованием валидации
и когда нужно тестировать валидацию отдельно
"""

from typing import List, Optional, Any, Callable, Dict
from datetime import datetime
from dataclasses import dataclass


@dataclass
class ValidationError:
    """Информация об ошибке валидации"""
    field: str
    message: str
    value: Any
    
    def __str__(self):
        return f"{self.field}: {self.message} (got: {self.value})"


class ValidationException(Exception):
    """Исключение при валидации с поддержкой нескольких ошибок"""
    
    def __init__(self, errors: List[ValidationError]):
        self.errors = errors
        super().__init__(f"Validation failed with {len(errors)} error(s)")
    
    def __str__(self):
        return "\n".join(str(err) for err in self.errors)


class CollectionItemValidator:
    """Валидатор для элементов коллекции"""
    
    @staticmethod
    def validate_name(value: Any, field_name: str = "name") -> List[ValidationError]:
        """Валидация имени элемента"""
        errors = []
        
        if not isinstance(value, str):
            errors.append(ValidationError(
                field=field_name,
                message=f"Must be a string, got {type(value).__name__}",
                value=value
            ))
            return errors
        
        if not value.strip():
            errors.append(ValidationError(
                field=field_name,
                message="Cannot be empty or whitespace only",
                value=value
            ))
        
        if len(value) > 100:
            errors.append(ValidationError(
                field=field_name,
                message="Cannot exceed 100 characters",
                value=value
            ))
        
        return errors
    
    @staticmethod
    def validate_value(value: Any, field_name: str = "value") -> List[ValidationError]:
        """Валидация значения элемента"""
        errors = []
        
        if value is None:
            errors.append(ValidationError(
                field=field_name,
                message="Cannot be None",
                value=value
            ))
            return errors
        
        allowed_types = (int, float, str, list, dict, tuple)
        if not isinstance(value, allowed_types):
            errors.append(ValidationError(
                field=field_name,
                message=f"Must be one of {[t.__name__ for t in allowed_types]}, "
                       f"got {type(value).__name__}",
                value=value
            ))
        
        return errors
    
    @classmethod
    def validate(cls, name: Any, value: Any) -> List[ValidationError]:
        """Полная валидация элемента"""
        errors = []
        errors.extend(cls.validate_name(name))
        errors.extend(cls.validate_value(value))
        return errors


class CollectionManagerValidator:
    """Валидатор для CollectionManager"""
    
    def __init__(self):
        self.item_validator = CollectionItemValidator()
    
    @staticmethod
    def validate_collection_name(value: Any) -> List[ValidationError]:
        """Валидация имени коллекции"""
        errors = []
        
        if not isinstance(value, str):
            errors.append(ValidationError(
                field="collection_name",
                message=f"Must be a string, got {type(value).__name__}",
                value=value
            ))
            return errors
        
        if len(value) < 3:
            errors.append(ValidationError(
                field="collection_name",
                message="Must be at least 3 characters",
                value=value
            ))
        
        if len(value) > 50:
            errors.append(ValidationError(
                field="collection_name",
                message="Cannot exceed 50 characters",
                value=value
            ))
        
        if not all(c.isalnum() or c in ('_', '-', ' ') for c in value):
            errors.append(ValidationError(
                field="collection_name",
                message="Can only contain alphanumeric characters, "
                       "underscores, hyphens and spaces",
                value=value
            ))
        
        return errors
    
    def validate_add_item(
        self, 
        item: Any, 
        existing_items: List[Any],
        max_size: int
    ) -> List[ValidationError]:
        """Валидация добавления элемента"""
        errors = []
        
        # Проверка типа (используем forward reference чтобы избежать циклического импорта)
        if type(item).__name__ != "CollectionItem":
            errors.append(ValidationError(
                field="item",
                message=f"Expected CollectionItem, got {type(item).__name__}",
                value=item
            ))
            return errors
        
        # Валидация самого элемента
        item_errors = self.item_validator.validate(item.name, item.value)
        for err in item_errors:
            err.field = f"item.{err.field}"
        errors.extend(item_errors)
        
        # Проверка размера коллекции
        if len(existing_items) >= max_size:
            errors.append(ValidationError(
                field="collection",
                message=f"Size limit reached (max: {max_size})",
                value=len(existing_items)
            ))
        
        # Проверка на дубликаты
        if any(existing.name == item.name for existing in existing_items):
            errors.append(ValidationError(
                field="item.name",
                message=f"Duplicate name '{item.name}'",
                value=item.name
            ))
        
        return errors


class CollectionItem:
    """Элемент коллекции с отдельным валидатором"""
    
    def __init__(self, name: str, value: Any, created_at: Optional[datetime] = None,
                 skip_validation: bool = False):
        self._name = None
        self._value = None
        self._created_at = created_at or datetime.now()
        
        if not skip_validation:
            # Используем валидатор
            validator = CollectionItemValidator()
            errors = validator.validate(name, value)
            if errors:
                raise ValidationException(errors)
        
        # Если валидация прошла (или пропущена), устанавливаем значения
        self._name = name.strip() if isinstance(name, str) else name
        self._value = value
    
    @property
    def name(self) -> str:
        return self._name
    
    @name.setter
    def name(self, value: str):
        errors = CollectionItemValidator.validate_name(value)
        if errors:
            raise ValidationException(errors)
        self._name = value.strip()
    
    @property
    def value(self) -> Any:
        return self._value
    
    @value.setter
    def value(self, value: Any):
        errors = CollectionItemValidator.validate_value(value)
        if errors:
            raise ValidationException(errors)
        self._value = value
    
    @property
    def created_at(self) -> datetime:
        return self._created_at
    
    def __repr__(self):
        return f"CollectionItem(name='{self.name}', value={self.value})"


class CollectionManager:
    """Менеджер коллекции с отдельным валидатором"""
    
    def __init__(self, collection_name: str, max_size: int = 1000):
        self._collection_name = None
        self._items: List[CollectionItem] = []
        self._max_size = max_size
        self.validator = CollectionManagerValidator()
        
        # Валидация через отдельный класс
        errors = self.validator.validate_collection_name(collection_name)
        if errors:
            raise ValidationException(errors)
        
        self._collection_name = collection_name.strip()
    
    @property
    def collection_name(self) -> str:
        return self._collection_name
    
    @collection_name.setter
    def collection_name(self, value: str):
        errors = self.validator.validate_collection_name(value)
        if errors:
            raise ValidationException(errors)
        self._collection_name = value.strip()
    
    @property
    def items(self) -> List[CollectionItem]:
        return self._items.copy()
    
    @property
    def size(self) -> int:
        return len(self._items)
    
    def add_item(self, item: CollectionItem):
        """Добавить элемент с валидацией"""
        errors = self.validator.validate_add_item(
            item, 
            self._items, 
            self._max_size
        )
        
        if errors:
            raise ValidationException(errors)
        
        self._items.append(item)
    
    def remove_item(self, name: str) -> CollectionItem:
        """Удалить элемент по имени"""
        if not isinstance(name, str):
            raise ValidationException([ValidationError(
                field="name",
                message="Must be a string",
                value=name
            )])
        
        for i, item in enumerate(self._items):
            if item.name == name:
                return self._items.pop(i)
        
        raise ValidationException([ValidationError(
            field="name",
            message=f"Item '{name}' not found",
            value=name
        )])
    
    def get_item(self, name: str) -> Optional[CollectionItem]:
        """Получить элемент по имени"""
        for item in self._items:
            if item.name == name:
                return item
        return None
    
    def validate_all(self) -> List[ValidationError]:
        """Проверить всю коллекцию на валидность"""
        all_errors = []
        
        # Валидация имени коллекции
        all_errors.extend(
            self.validator.validate_collection_name(self._collection_name)
        )
        
        # Валидация всех элементов
        for item in self._items:
            errors = self.validator.item_validator.validate(item.name, item.value)
            all_errors.extend(errors)
        
        # Проверка размера
        if len(self._items) > self._max_size:
            all_errors.append(ValidationError(
                field="size",
                message=f"Exceeds maximum size ({self._max_size})",
                value=len(self._items)
            ))
        
        return all_errors
    
    def __len__(self):
        return self.size
    
    def __repr__(self):
        return f"CollectionManager('{self.collection_name}', items={self.size})"


# Пример использования
if __name__ == "__main__":
    print("=== Тестирование отдельного валидатора ===\n")
    
    # Создание менеджера
    try:
        manager = CollectionManager("TestCollection")
        print(f"Создан: {manager}")
    except ValidationException as e:
        print(f"Ошибка создания: {e}\n")
    
    # Добавление элементов
    try:
        item1 = CollectionItem("item1", 42)
        item2 = CollectionItem("item2", {"key": "value"})
        
        manager.add_item(item1)
        manager.add_item(item2)
        print(f"Добавлены элементы: {manager}")
    except ValidationException as e:
        print(f"Ошибка добавления: {e}\n")
    
    # Пример множественных ошибок валидации
    print("\n=== Пример множественных ошибок ===")
    try:
        bad_item = CollectionItem("", None)  # Несколько ошибок
    except ValidationException as e:
        print(f"Несколько ошибок:\n{e}")
    
    # Тестирование валидатора отдельно
    print("\n=== Прямое использование валидатора ===")
    validator = CollectionItemValidator()
    errors = validator.validate("", None)
    print(f"Ошибки валидации: {errors}")
    
    # Полная проверка коллекции
    print(f"\nПроверка всей коллекции: {manager.validate_all()}")
