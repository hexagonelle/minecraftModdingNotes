# 1 Basic Structure

## 1.1 Parts of Minecraft

Broadly speaking, Minecraft consists of four different things: blocks, items, entities, and data. Blocks are the things that make up the terrain and buildings in the world, and items are the things in your inventory and chests.

Entities and data are more complicated. Entities *usually* have the ability to move. This includes mobs (animals and monsters), boats, and minecarts. It also includes blocks that can fall, but only *while* they are in the falling state.  Some less intuitive examples are projectiles (snowballs, tridents, and eggs when thrown), experience orbs, armor stands, and dropped items. Exceptions to the moving requirement include item frames, paintings, and the knot of a lead when tied to a fence.

Data is a way of group technical information. It includes loot tables (the list of items dropped when killing a mob or breaking a block), recipes, blockstates, models, textures, localization, and tags.

### 1.1.4 CreativeModeTab

## 1.2 Code of a mod

### 1.2.1 Classes
The three different categories we see in the game correspond to three different **classes**: the `Block` class, the `Item` class, and the `Entity` class. Minecraft has these base classes defined, as well as several classes that extend the base classes. For example these classes extend the base `Block` class:
-   `LeavesBlock`
-   `CropBlock`
-   `SaplingBlock`
It is also possible to write your *own* extension to the base classes. For example, you might write a `CrystalBlock` class that extends `Block`. This would be useful if there are several different *types* of crystal block you want to add. For example, maybe all crystal blocks have the same properties like explosion resistance and being mineable with a pickaxe, but come in different colors.

### 1.2.2 Deferred register
In order for the game to recognize the blocks, items, and entities that we add, we must **register** these things. We do this using a **deferred register**.

The `DeferredRegister<>` class basically works like an `Array`: it stores a "list" of things. An instance `DeferredRegister<Block>` would be a list of the blocks we want to add.

There is a complication. Each base class, like `Item`, contains a variety of information. But from a game perspective, we don't need *all* of the information at once, all the time. Often, the functioning of the game will only require *one* of the object's properties. Calling on the entire `Item` object every time we only need one of its properties is very inefficient.

So instead, we create a `RegistryObject<>` for each thing we want to add to the game. Each `RegistryObject<>` **maps** a `String`, called the **key**, to one of the game objects, called the **value**. Then, instead of calling the entire game object, we can use the key in order to access only the property that we need.

### 1.2.3 Datagen

#### 1.1.5.1 Blockstates

#### 1.1.5.2 Item models
#### 1.1.5.3 Block tags
#### 1.1.5.4 Item Tags

#### 1.1.5.5 Recipes

#### 1.1.5.6 Worldgen

#### 1.1.5.7 Loot

A mod can then be summarized as:
1. Instances of the base classes, or extensions to the base classes:
	- The `Block` class.
	- The `Item` class.
	- The `Entity` class.
	We will call each instance a "game object".
2. For each game object, we make a registry object:
	- For each block, a `RegistryObject<Block>`
	- For each item, a `RegistryObject<Item>`
	- For each entity, a `RegistryObject<Entity>`
3. All registry objects of a single type are **registered** to a deferred register of the same type:
	- A `DeferredRegister<Block>` that holds all the blocks the mod adds.
	- A `DeferredRegister<Item>` that holds all the items the mod adds.
	- A `DeferredRegister<Entity>` that holds all the entities the mod adds.

Now that we have this in place, we can look at the specific classes.
# 2 Documentation of classes

## 2.1 `Item`

**Constructor**
```java
public Item(Item.Properties properties)
```

**Some commonly used attributes**
- `Rarity rarity`
- `int maxDamage`
- `int maxStackSize`
- `boolean isFireResistant`
- `boolean canRepair`

**Some commonly used attribute classes**
```java
public static class Properties {
int maxStackSize = 64;
int maxDamage;
Rarity rarity = Rarity.COMMON;
boolean isFireResistant;
// etc, etc.
}
```

**Some commonly used methods**
```java
public String toString(){
	return BuiltInRegistries
		.ITEM
		.getKey(this)
		.getPath()
}
```

Each property of the item is an attribute of the subclass `Properties`. Thus, to set the item properties, we use the setter methods given in the `Properties` subclass, for example:
```java
public Item.Properties stacksTo(
	int setMaxStackSize
){

	if (this.maxDamage > 0){
		throw new RuntimeException(
			"Unable to have damage AND stack."
		)
	} else {
		this.maxStackSize = setMaxStackSize;
		return this
	}
}
```

This method checks if the item has durability. If so, it cannot be stacked. Otherwise, it sets the `maxStackSize` attribute of the Properties instance to `setMaxStackSize`.

## 2.2 `Block`

The `Block` class is actually an extension of the `BlockBehaviour` class. This is mostly a matter of convenience; `BlockBehaviour` holds many of the game properties of the block, which the `Block` class then inherits.

**Constructor**
```java
public Block(BlockBehaviour.Properties properties)
```

**Some commonly used attributes** (inherited from BlockBehavior)
- `boolean hasCollision`
- `float explosionResistance`
- `SoundType soundType`
- `float destroyTime`

**Some commonly used attribute classes** (inherited from BlockBehavior)
```java
public static class Properties {
Function<BlockState, MapColor> mapColor
	= (p_284884_) -> { return MapColor.NONE; };  
boolean hasCollision = true;  
SoundType soundType = SoundType.STONE;  
ToIntFunction<BlockState> lightEmission = (p_60929_) -> {  
   return 0;  
};  
float explosionResistance;  
float destroyTime;
// etc, etc.
}
```

**Some commonly used methods of the subclass Properties of the class BlockBehavior**
```java
public static BlockBehaviour.Properties copy(BlockBehaviour blockBehaviour)
```

The above method is extremely useful. Note that since the `Block` class extends the `BlockBehavior` class, we can use any existing `Block` as an `BlockBehaviour` argument in the above method.

In practice, this lets us create a new `Block` instance with the properties of some pre-existing `Block` instance. Vanilla Minecraft defines all `Block` instances in the class `Blocks`. So, for example, we can copy the properties of Vanilla Minecraft's oak planks:

```java
BlockBehaviour.Properties oakPlankProperties =
	BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS);
```

Then, we can make our own planks block with all the properties of oak planks:

```java
applewoodPlanksBlock = Block(oakPlankProperties);
```

## 2.3 `DeferredRegister<>`

**Constructor**
```java
public static <B> DeferredRegister<B> create(
	IForgeRegistry<B> reg,
	String modid
)
```

Note that even though I am calling this a "constructor", it is actually a method. Thus, instead of writing

```java
exampleDeferredRegister<Item> = DeferredRegister<Item>(
	//some arguments
)
```

we use

```java
exampleDeferredRegister = DeferredRegister.create(
	//some arguments
)
```

It is worth pausing to discuss the first argument of `DeferredRegister.create()`. 

For forge mods, Forge defines all vanilla and Forge `IForgeRegistry<>` objects in the `ForgeRegistries` class. For example, if we want to create a deferred register for modded block objects, we would pass `ForgeRegistries.BLOCKS` as the `IForgeRegistry<>` argument in the `DeferredRegister.create()` method. In other words, we would write:

```java
DeferredRegister<Block> MODDED_BLOCKS = 
	DeferredRegister.create(
		ForgeRegistries.BLOCKS,
		"ReallyCoolMod"
	)
```

**Some commonly used attributes** (inherited from BlockBehavior)
- `ResourceKey<? extends Registry<T>> registryKey`
- `String modid`

**Some commonly used attribute classes** (inherited from BlockBehavior)
```java
public static class Properties {
Function<BlockState, MapColor> mapColor
	= (p_284884_) -> { return MapColor.NONE; };  
boolean hasCollision = true;  
SoundType soundType = SoundType.STONE;  
ToIntFunction<BlockState> lightEmission = (p_60929_) -> {  
   return 0;  
};  
float explosionResistance;  
float destroyTime;
// etc, etc.
}
```

**Some commonly used methods**
```java
public <I extends T> RegistryObject<I> register(
	final String name,
	final Supplier<? extends I> sup
)
```

```java
public void register(IEventBus bus)
```

```java
public ResourceKey<? extends Registry<T>> getRegistryKey()
```

## 2.4 `RegistryObject<>`

**Constructor**
```java
// none
```

Like the `DeferredRegister<>` class, the `RegistryObject<>` class does not have a public constructor. Although it does have some `create` methods available, we can also just use the `DeferredRegister.register()` method. For example, we can create a registry object for the `Block applewoodPlanksBlock` we defined earlier, using the `DeferredRegister<Block> MODDED_BLOCKS` we defined earlier:

```java
RegistryObject<Block> APPLEWOOD_PLANKS = 
	MODDED_BLOCKS.register(  
		"applewood_planks",  
		applewoodPlanksBlock  
	);
```

This creates a new registry object with a key of `String "applewood_planks"` and a value of `Block applewoodPlanksBlock`. Moreover, it adds the `RegistryObject<Block> APPLEWOOD_PLANKS` to the `DeferredRegister<Block> MODDED_BLOCKS`.
# 3 Procedural Steps to Make Game Objects

## 3.1 Blocks
### 3.1.1 stuff

1.  Create a `DeferredRegister<Block>`. The datatype `DeferredRegister` is a container; we want the contained datatype to be of type `Block`.
2.  Identify or create the class that the block belongs to.
    1.  The simplest class is just `Block`, but many other classes extend this.
        
    2.  Each extension has its own special properties, which we expect that class to have.
3.  Create a new instance of the desired `Block` extension.
    1.  Each class that extends `Block` will have its own constructor methods, that take different arguments.
        1.  For the base class `Block`, there is a single argument `BlockBehavior.Properties blockProperties`. This determines the properties of the block, such as:
            -   whether it has collisions
            -   what the block drops
            -   how resistant it is to explosions
            -   etc.
        2.  The `blockProperties` can also be copied from existing blocks: use `BlockBehavior.Properties.copy(RegistryObject<Block> existing block)`