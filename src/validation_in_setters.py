"""
Пример 1: Валидация непосредственно в сеттерах класса CollectionManager
Подходит для простых случаев с небольшим количеством правил
"""

from typing import List, Optional, Any
from datetime import datetime


class CollectionItem:
    """Элемент коллекции"""
    
    def __init__(self, name: str, value: Any, created_at: Optional[datetime] = None):
        self._name = None
        self._value = None
        self._created_at = created_at or datetime.now()
        
        # Инициализация через сеттеры (триггерит валидацию)
        self.name = name
        self.value = value
    
    @property
    def name(self) -> str:
        return self._name
    
    @name.setter
    def name(self, value: str):
        # Валидация прямо в сеттере
        if not isinstance(value, str):
            raise TypeError(f"Name must be a string, got {type(value).__name__}")
        
        if not value.strip():
            raise ValueError("Name cannot be empty or whitespace only")
        
        if len(value) > 100:
            raise ValueError("Name cannot exceed 100 characters")
        
        self._name = value.strip()
    
    @property
    def value(self) -> Any:
        return self._value
    
    @value.setter
    def value(self, value: Any):
        # Более сложная валидация
        if value is None:
            raise ValueError("Value cannot be None")
        
        # Пример ограничения на тип
        if not isinstance(value, (int, float, str, list, dict)):
            raise TypeError(
                f"Value must be int, float, str, list or dict, got {type(value).__name__}"
            )
        
        self._value = value
    
    @property
    def created_at(self) -> datetime:
        return self._created_at
    
    def __repr__(self):
        return f"CollectionItem(name='{self.name}', value={self.value})"


class CollectionManager:
    """Менеджер коллекции с валидацией в сеттерах"""
    
    def __init__(self, collection_name: str):
        self._collection_name = None
        self._items: List[CollectionItem] = []
        self._max_size = 1000
        
        # Триггерим валидацию через сеттер
        self.collection_name = collection_name
    
    @property
    def collection_name(self) -> str:
        return self._collection_name
    
    @collection_name.setter
    def collection_name(self, value: str):
        if not isinstance(value, str):
            raise TypeError("Collection name must be a string")
        
        if len(value) < 3:
            raise ValueError("Collection name must be at least 3 characters")
        
        if len(value) > 50:
            raise ValueError("Collection name cannot exceed 50 characters")
        
        # Проверка на допустимые символы
        if not all(c.isalnum() or c in ('_', '-', ' ') for c in value):
            raise ValueError(
                "Collection name can only contain alphanumeric characters, "
                "underscores, hyphens and spaces"
            )
        
        self._collection_name = value.strip()
    
    @property
    def items(self) -> List[CollectionItem]:
        return self._items.copy()  # Возвращаем копию для инкапсуляции
    
    @property
    def size(self) -> int:
        return len(self._items)
    
    def add_item(self, item: CollectionItem):
        """Добавить элемент с проверкой ограничений"""
        if not isinstance(item, CollectionItem):
            raise TypeError(f"Expected CollectionItem, got {type(item).__name__}")
        
        if self.size >= self._max_size:
            raise ValueError(
                f"Collection size limit reached (max: {self._max_size})"
            )
        
        # Проверка на дубликаты имен
        if any(existing.name == item.name for existing in self._items):
            raise ValueError(f"Item with name '{item.name}' already exists")
        
        self._items.append(item)
    
    def remove_item(self, name: str):
        """Удалить элемент по имени"""
        if not isinstance(name, str):
            raise TypeError("Item name must be a string")
        
        for i, item in enumerate(self._items):
            if item.name == name:
                return self._items.pop(i)
        
        raise ValueError(f"Item with name '{name}' not found")
    
    def get_item(self, name: str) -> Optional[CollectionItem]:
        """Получить элемент по имени"""
        for item in self._items:
            if item.name == name:
                return item
        return None
    
    def __len__(self):
        return self.size
    
    def __repr__(self):
        return f"CollectionManager('{self.collection_name}', items={self.size})"


# Пример использования
if __name__ == "__main__":
    # Создание менеджера
    manager = CollectionManager("MyCollection")
    
    # Добавление элементов
    item1 = CollectionItem("item1", 42)
    item2 = CollectionItem("item2", {"key": "value"})
    
    manager.add_item(item1)
    manager.add_item(item2)
    
    print(manager)
    print(manager.items)
    
    # Примеры ошибок валидации:
    try:
        bad_item = CollectionItem("", "value")  # Пустое имя
    except ValueError as e:
        print(f"Error: {e}")
    
    try:
        manager.collection_name = "AB"  # Слишком короткое имя
    except ValueError as e:
        print(f"Error: {e}")
    
    try:
        manager.add_item(item1)  # Дубликат
    except ValueError as e:
        print(f"Error: {e}")
