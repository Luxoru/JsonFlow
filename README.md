# JsonFlow



**JsonFlow** is a Java library that enables JSON inheritance, extension, and merging. With JsonFlow, you can effortlessly load JSON files that extend base configurations, override properties, and seamlessly combine data structures.

## ✨ Features

- ✔️ **JSON Inheritance**: Extend JSON files dynamically using parent-child relationships
- ✔️ **Property Merging**: Merge properties while maintaining hierarchy and precedence
- ✔️ **Value Overriding**: Override values from parent JSON structures with ease
- ✔️ **Custom Serialization**: Support for custom serializers and deserializers
- ✔️ **Type Safety**: Strong typing with generic support
- ✔️ **Caching**: Built-in file caching for improved performance

## 🚀 Quick Start

### Basic Usage

```java
public class Example {
    public void loadJson() {
        Block block = JsonFlow.load("grass_block.json", Block.class);
        System.out.println(block.getMaterial()); // Grass
        System.out.println(block.isOpaque());    // false
    }
}
```

### JSON Inheritance Example

Create modular JSON configurations that extend base structures:

**entity.json** (Base configuration)
```json
{
  "name": null,
  "width": null,
  "height": null,
  "position": { "x": 10, "y": 10 }
}
```

**damageable.json** (Mixin configuration)
```json
{
  "health": 10.0,
  "maxHealth": 0.0,
  "invulnerable": false
}
```

**player.json** (Extended configuration)
```json
{
  "parent": ["damageable.json", "entity.json"],
  "name": "Player",
  "position": {
    "x": 100.0,
    "y": 100.0
  },
  "width": 2,
  "height": 5,
  "maxHealth": 20
}
```

## 📋 Installation

### Maven
```xml
<dependency>
    <groupId>me.luxoru</groupId>
    <artifactId>jsonflow</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle
```gradle
implementation 'me.luxoru:jsonflow:1.0.0'
```



### Custom Entity Implementation

```java
@Getter
@FlowSerializable
public class Player extends AbstractEntity {
    private float health;
    private Position position;

    public void damage(float damage) {
        health -= damage;
    }

    public void move(float dx, float dy) {
        this.position.moveX(dx);
        this.position.moveY(dy);
    }
}
```

### Custom Serialization

Create custom serializers for complex types:

```java
public class PositionSerializer implements JsonNodeConversionHandler<Position> {
    @Override
    public ObjectNode deserialize(Position object, ObjectMapper mapper) {
        ObjectNode positionNode = mapper.createObjectNode();
        positionNode.put("x", object.getX());
        positionNode.put("y", object.getY());
        return positionNode;
    }

    @Override
    public Position serialize(JsonNode node) {
        float x = node.get("x").floatValue();
        float y = node.get("y").floatValue();
        return new Position(x, y);
    }
}
```

### Using Custom Serializers

```java
@Getter
@Setter
@NoArgsConstructor
@NodeSerializable(PositionSerializer.class)
public final class Position {
    private float x;
    private float y;
    
    // ... methods
}
```

## 📖 Complete Example

```java
public class EntityExample {
    public static void main(String[] args) {
        // Load player configuration with inheritance
        Player player = JsonFlow.load("player", Player.class);

        // Convert back to JSON
        String json = player.toJsonObject().toPrettyString();
        System.out.println(json);

        // Use the loaded entity
        player.move(2, 2);
        System.out.println(player.getPosition());
    }
}
```

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 🙏 Acknowledgments

- Built with [Jackson](https://github.com/FasterXML/jackson) for JSON processing
- Inspired by configuration management needs in modern Java applications

## 📞 Support

If you have any questions or run into issues, please:
- Check the [Issues](../../issues) page
- Create a new issue if your problem isn't already reported
- Include relevant code samples and error messages
