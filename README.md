# JsonFlow - Lightweight JSON Inheritance & Merging for Java

**JsonFlow** is a simple yet powerful Java library that enables JSON inheritance, extension, and merging. With JsonFlow, you can effortlessly load JSON files that extend base configurations, override properties, and seamlessly combine data structures.

### ✨ Features
✔️ Extend JSON files dynamically.<br>
✔️ Merge properties while maintaining hierarchy. <br>
✔️ Override values from parent JSON structures.<br>
✔️ Lightweight & efficient, with minimal dependencies.

### 🔨 Example Usage
```java
public class Example{
    public void loadJson(){
        Block block = JsonFlow.load("grass_block.json");
        System.out.println(json.getMaterial());
    }
}
```
